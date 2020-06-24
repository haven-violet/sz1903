package com.baidu.day12

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/5/18 16:22
  * @Version 1.0
  * @Description
  * 变量
  */
object _03BroadcastOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("broadcast").setMaster("local")
    val sc = new SparkContext(conf)
    //创建RDD
    val rdd = sc.parallelize(Array(1,2,3,4,5))
    val factor = 10 //这个变量是driver创建的
//    rdd.map(_*factor)
    val value: Broadcast[Int] = sc.broadcast(factor)
    //使用广播变量
    val words = rdd.map(_ * value.value)
    words.foreach(println)

  }
}
