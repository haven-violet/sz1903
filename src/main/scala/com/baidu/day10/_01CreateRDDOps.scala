package com.baidu.day10

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/5/14 21:54
  * @Version 1.0
  * @Description
  * 创建RDD的方式(两种)
  */
object _01CreateRDDOps {
  def main(args: Array[String]): Unit = {
    //创建执行入口
    val conf = new SparkConf().setAppName("createRDD").setMaster("local")
    val sc = new SparkContext(conf)
    //创建RDD（集合中创建RDD）
    //两者区别: makeRDD底层调用了parallelize算子
    val rdd1: RDD[Int] = sc.makeRDD(Array(1,2,3,4,5), 2)
    val rdd2: RDD[Int] = sc.parallelize(Array(1,2,3,4,5), 5)
    //从外部存储中创建RDD
//    sc.textFile("hdfs://")
    println(rdd1.aggregate(0)(_+_, _+_))
    println(rdd1.reduce(_+_))
    val rdd3: RDD[(String, Int)] = sc.parallelize(List(("cat",1),("dog",2),("cat",4),("dog",3),("cat",1)),1)
    rdd3.aggregateByKey(100)(_+_, _+_).foreach(println)
  }
}
