package com.baidu.day28

import java.lang

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

/**
  * @Author liaojincheng
  * @Date 2020/6/10 11:51
  * @Version 1.0
  * @Description
  */
object home {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("kafka").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))
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
      //      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (true: lang.Boolean)
    )

    val inputDStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(
      ssc,
      //本地化策略,均匀的将kafka内的分区数据分配到各个sparkStreaming的Executor
      LocationStrategies.PreferConsistent,
      //消费者策略
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )
    //打印 要选择打印的内容,如果直接输出,会报序列化错误,因为我们打印的是对象,但是程序不识别对象
    //所以运行出错
    inputDStream.map(x => x.value()).print()
    //启动
    ssc.start()
    ssc.awaitTermination()
  }
}
