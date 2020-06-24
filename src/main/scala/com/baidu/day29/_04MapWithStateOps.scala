package com.baidu.day29

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, State, StateSpec, StreamingContext}

/**
  * @Author liaojincheng
  * @Date 2020/6/12 10:10
  * @Version 1.0
  * @Description
  * 新版的累加计数(使用的部分缓存来完成累加)
  */
object _04MapWithStateOps {
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
      //"auto.offset.reset" -> "earliest"
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
    val reduce: DStream[(String, Int)] = tuple.reduceByKey(_+_)
    //批次累加
    /*
    mappingFunction: (KeyType, Option[ValueType]), state[StateType]
     */
    //第一个参数,是我们获取kafka内部的元素(k - v)
    //第二个参数,是我们进行单词统计的value值,Int类型
    //第三个参数,是我们每次批次提交的中间结果值(缓存中的值)
    val mappingFunction = (word:String, one:Option[Int], state:State[Int]) => {
      //获取当前批次的结果值,和每次累加后的缓存中的结果值
      val sum = one.getOrElse(0) + state.getOption().getOrElse(0)
      val output = (word, sum)
      //每次更新缓存
      state.update(sum)
      //返回值
      output
    }
    //如果当前这个批次没有批次的结果值,那么此时我们的state不更新,不更新的话,当前累加的批次就不会提交
    //说明获取不到内容的新增数据,此时将不会打印,mapWithState走的是缓存操作,不会直接和磁盘接触
    //将判断内存是否有新增数据,如果有就打印,没有就不打印,结果照样累加
    val wcresult = reduce.mapWithState(StateSpec.function(mappingFunction))
    //输出
    wcresult.print()
    //启动
    ssc.start()
    ssc.awaitTermination()

  }
}
