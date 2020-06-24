package com.baidu.day12

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/5/18 20:33
  * @Version 1.0
  * @Description
  * 检查点机制(类似持久化,但是存储的话不会和内存发生任何关系,全是落地磁盘)
  */
object _02CheckpointOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("checkpoint").setMaster("local")
    val sc = new SparkContext(conf)

    //设置检查点的文件夹
    sc.setCheckpointDir("hdfs://hadoop201:9000/ck")
    val lines = sc.textFile("F:\\test.txt")
      .flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_ + _)
    //设置检查点,设置检查点和持久化可以不一样,你要承受它带来的磁盘IO的资源消耗
    //可能会导致程序运行效率降低,但是会大大提高容错性
    lines.checkpoint()
//    lines.getCheckpointFile
    lines.foreach(println)

  }
}
