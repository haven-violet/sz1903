package com.baidu.day29

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @Author liaojincheng
  * @Date 2020/6/12 0:53
  * @Version 1.0
  * @Description
  * 单词计数累加操作(不是wc的累加,是每个批次结果的累加)
  * 纯基于磁盘操作的累加
  */
object _03UpdateStateByKeyOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("updateStateByKey").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(3))
    //设置中间结果文件夹
    ssc.checkpoint("file:///F:\\check")
    //获取数据
    //配置消费参数
    val topics = Array("spark")
    val kafkaParams = Map[String, Object](
      //配置连接
      "bootstrap.servers" -> "hadoop201:9092,hadoop202:9092,hadoop203:9092",
      //配置反序列化器
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      //配置消费者组
      "group.id" -> "test1903",
      //"auto.offset.reset"-> "earliest",
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )
    val inputDStream: InputDStream[ConsumerRecord[String, String]] =
      KafkaUtils.createDirectStream(
        ssc,
        //本地化策略,均匀的将kafka内的分区数据分配到各个SparkStreaming的Executor中
        LocationStrategies.PreferConsistent,
        //消费者策略
        ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
      )
    val lines: DStream[String] = inputDStream.flatMap(_.value().split(" "))
    val tuple: DStream[(String, Int)] = lines.map((_,1))
//    val value: DStream[(ConsumerRecord[String, String], Int)] = tuple.reduceByKey(_+_)

    //要将每次批次结果累加到一起
    val reduce: DStream[(String, Int)] = tuple.updateStateByKey(
      updateFunc,
      new HashPartitioner(ssc.sparkContext.defaultParallelism),
      true)

    reduce.print()
    //启动
    ssc.start()
    ssc.awaitTermination()

  }
  //updateFunc: (Iterator[(K, Seq[V], Option[S])]) => Iterator[(K, S)]
  //第一个参数, 是我们获取kafka内部的元素(k - v)
  //第二个参数, 是我们进行单词统计的value值， Int类型
  //第三个参数, 是我们每次批次提交的中间结果值(缓存区)
  val updateFunc = (iter: Iterator[(String, Seq[Int], Option[Int])]) => {
    iter.map(t => (t._1, t._2.sum + t._3.getOrElse(0)))
  }

}
