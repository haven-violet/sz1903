package com.baidu.day30

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/6/12 20:56
  * @Version 1.0
  * @Description
  * 滑动窗口操作
  * 案例: 实时统计热点搜索词
  * 每个5秒钟,统计近25秒的搜索词的搜索频次
  * 并打印出排名最靠前的3个搜索词以及出现的次数
  */
object _02WindowOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("window").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))

    //设置检查点
    ssc.checkpoint("file:///F:\\check")

    //获取输入流,还是使用socket
    val socketDS: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop201", 6666)
//    val words = socketDS.map(_.split("\\s")(1)).map((_, 1))

    val words = socketDS.map(t => {
      val arr = t.split("\\s")
      (arr(0), arr(1))
    })
//    val reduce: DStream[(String, Int)] = words.reduceByKeyAndWindow((v1:Int, v2:Int) => v1 + v2, Seconds(25), Seconds(5))
    //处理一下数据
//    reduce.foreachRDD(
//      rdd => {
//        rdd.sortBy(_._2).take(3).foreach(println)
//      }
//    )
    println("-------")
    //滑动窗口分组操作
//    words.groupByKeyAndWindow(Seconds(25), Seconds(5)).print()
//    words.reduceByWindow((x,y) => (x._1, x._2 + y._2), Seconds(25), Seconds(5)).print()

//    words.countByWindow(Seconds(25), Seconds(5)).print()
//    words.countByValueAndWindow(Seconds(25), Seconds(5)).print()


    ssc.start()
    ssc.awaitTermination()
  }
}
