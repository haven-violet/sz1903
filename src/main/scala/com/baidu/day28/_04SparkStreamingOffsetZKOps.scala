package com.baidu.day28

import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, HasOffsetRanges, KafkaUtils, LocationStrategies, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @Author liaojincheng
  * @Date 2020/6/10 8:24
  * @Version 1.0
  * @Description
  * 手动维护offset,存放到Zookeeper中
  * 使用kafka版本是0.10以上版本
  * 手动维护偏移量的好处:
  * 1.偏移量不易丢失（Zookeeper中）
  * 2.数据消费不会重复
  * 3.可以任意定位offset进行消费数据
  * 4.程序宕机或者其他原因挂掉后,offset不会丢失
  */
object _04SparkStreamingOffsetZKOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("kafka").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))
    //配置消费者参数
    val topics = Array("spark")
    val kafkaParams = Map[String, Object](
      //配置连接
      "bootstrap.servers" -> "hadoop201:9092,hadoop202:9092,hadoop203:9092",
      //配置反序列化器
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      //配置消费者组
      "group.id" -> "test1903",
      "auto.offset.reset" -> "earliest",
      //手动维护offset 一定要把它换成false, 不能自动提交了
      "enable.auto.commit" -> (false:java.lang.Boolean)
    )
    //获取zk连接(创建client)
    val zKManager = new KafkaOffsetZKManager("hadoop201:2181,hadoop202:2181,hadoop203:2181")
    //获取输入流
    val inputDStream: InputDStream[ConsumerRecord[String, String]] =
      KafkaUtils.createDirectStream(
        ssc,
        //本地化策略,均匀的将kafka内的分区数据分配到各个sparkStreaming的Executor中
        LocationStrategies.PreferConsistent,
        //消费者策略
        ConsumerStrategies.Subscribe[String, String](
          topics,
          kafkaParams,
          zKManager.getFromOffset(topics, "test1903")
        )
      )
    /*
    打印 要选择打印的内容,如果直接输出,会报序列化错误,因为我们打印的是对象,但是程序不识别对象
    所以运行出错
    更新维护偏移量,那么我们处理完数据后,一定要更新
    使用foreachRDD就是将数据流转换成RDD算子操作
     */
    inputDStream.foreachRDD(rdd => {
      //获取偏移量数组(主要是为了获取offsetRange内部的offset)
      val ranges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      //输出数据即可,后期如果有数据业务的处理,也在这里完成
      rdd.map(_.value()).map((_,1)).reduceByKey(_+_).foreach(println)
      //更新offset操作
      zKManager.storeOffsets(ranges, "test1903")
    })
    //启动
    ssc.start()
    ssc.awaitTermination()
  }
}

//1.创建Zookeeper客户端
class KafkaOffsetZKManager(zkServer: String) {
  //拿走zk客户端
  val zkClient = {
    val client = CuratorFrameworkFactory
      .builder()
      .connectString(zkServer)
      .retryPolicy(new ExponentialBackoffRetry(1000, 3))
      .build()
    client.start()
    client
  }
  //保存offset的路径
  val _base_path_of_kafka_offset = "/kafka/offset"
  /*
  获取消费者组对应的topic已经消费的偏移量(即上次消费的位置offset)
   */
  def getFromOffset(topics:Array[String], groupName:String) ={
    //用的是0.10版本以上的
    //0.10版本获取topic的分区类: TopicPartition
    //0.8版本获取topic的分区类: TopicAndPartition
    var fromOffset: Map[TopicPartition, Long] = Map()
    //循环处理每一个Topic
    for(topic <- topics){
      //读取zk中保存的offset,作为DStream的消费起始位置,如果没有创建该路径,并从0开始消费
      val zkTopicPath = s"${_base_path_of_kafka_offset}/${groupName}/${topic}"
      //检查路径是否存在
      checkZKPathExists(zkTopicPath)
      //获取topic的分区
      val list = zkClient.getChildren.forPath(zkTopicPath)
      //导入Scala集合转换包
      import scala.collection.JavaConversions._
      for(p <- list){
        //遍历每一个分区内的offset
        val offsetData: Array[Byte] = zkClient.getData.forPath(s"${zkTopicPath}/${p}")
        println(offsetData.toString + "----")
        //将offset转换为Long
        val offset = java.lang.Long.valueOf(new String(offsetData)).toLong
        //将获取好的数据存入Map集合
        fromOffset+=(new TopicPartition(topic, Integer.parseInt(p)) -> offset)
      }
    }
    fromOffset
  }

  //检查zk中路径是否存在,不存在则创建该路径
  def checkZKPathExists(zkTopicPath: String) = {
    if(zkClient.checkExists().forPath(zkTopicPath) == null){//不存在,则创建
      zkClient.create().creatingParentsIfNeeded().forPath(zkTopicPath) //创建该路径
    }
  }

  //更新offset偏移量
  def storeOffsets(offsetRange: Array[OffsetRange], groupName: String) = {
    //循环处理每个分区内的offset
    for(o <- offsetRange){
      //检查更新的路径是否存在
      val zkPath = s"${_base_path_of_kafka_offset}/${groupName}/${o.topic}/${o.partition}"
      //不存在的话创建
      checkZKPathExists(zkPath)
      //向对应的分区写入offset即可
      println("---offset写入zk---topic:"+o.topic+" , Partition"+ o.partition +" , offset:" + o.untilOffset)
      zkClient.setData().forPath(zkPath, o.untilOffset.toString.getBytes)
    }
  }
}
