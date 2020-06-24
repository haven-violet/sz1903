package com.baidu.day29

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, HasOffsetRanges, KafkaUtils, LocationStrategies, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @Author liaojincheng
  * @Date 2020/6/11 8:51
  * @Version 1.0
  * @Description
  * offset维护到redis中
  */
object _01OffsetFromRedis {
  def main(args: Array[String]): Unit = {
    //创建执行入口
    val conf = new SparkConf().setAppName("offset").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(3))
    //配置消费参数
    val topics = Array("spark")
    val groupId = "1903test"
    val kafkaParams = Map[String, Object](
      //配置连接
      "bootstrap.servers" -> "hadoop201:9092,hadoop202:9092,hadoop203:9092",
      //配置反序列化器
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      //配置消费者组
      "group.id" -> groupId,
      "auto.offset.reset" -> "earliest",
      //手动维护offset一定要把它换成false,不能自动提交了
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    //redis开启常连接(程序不停,redis不关)
    val jedis = _01JedisPoolOps.getResource()
    //获取redis中的offset,但是redis中可能没有offset也可能有offset,那么此时我们在获取
    //数据流的时候,需要有两种情况,从0开始，和从1开始
    val fromOffset: Map[TopicPartition, Long] = _01JedisOffset(jedis, groupId)
    //在获取输入流,在获取kafka输入流数据的时候,sparkStreaming分区是和kafka分区一一对应
    val stream: InputDStream[ConsumerRecord[String, String]] =
      if(fromOffset.size == 0){ //第一次获取数据,没有offset
        KafkaUtils.createDirectStream(
          ssc,
          //本地化策略,均匀的将kafka内的分区数据分配到各个SparkStreaming的Executor中
          LocationStrategies.PreferConsistent,
          //消费者策略
          ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
        )
      }else{ //第二次获取的时候有offset
        KafkaUtils.createDirectStream(
          ssc,
          //本地化策略,均匀的将kafka内的分区数据分配到各个SparkStreaming的Executor中
          LocationStrategies.PreferConsistent,
          //消费者策略
          ConsumerStrategies.Assign(fromOffset.keys, kafkaParams, fromOffset)
        )
      }
    //数据处理(更新offset)
    stream.foreachRDD(rdd => {
      //获取偏移量数组(主要是为了获取OffsetRange内部的offset)
      val ranges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      //数据处理
      rdd.map(_.value()).foreach(println)
      //更新offset
      for(or <- ranges) {
        jedis.hset(groupId, or.topic+"_"+or.partition, or.untilOffset.toString)
      }
    })
    //启动程序
    ssc.start()
    ssc.awaitTermination()
  }

}
