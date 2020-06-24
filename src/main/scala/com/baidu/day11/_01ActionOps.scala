package com.baidu.day11

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/5/18 17:51
  * @Version 1.0
  * @Description
  */
object _01ActionOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Action").setMaster("local")
    val sc = new SparkContext(conf)

    val rdd = sc.parallelize(List(1,2,3,4,5))
    val rdd2 = sc.parallelize(List((1, "123")))
    rdd.foreach(println) //输出数据,这种foreach输出适合local模式,集群不行,不会有结果
    rdd.collect() //数据收集,有返回值(Array)
    rdd.count() //统计个数,有返回值(Int)
    rdd2.collectAsMap() //数据收集,返回值是Map(只针对KV形式)
    rdd.reduce(_+_) //聚合操作,有返回值
//    rdd.foreachPartition() 输出一个分区的数据
    rdd.aggregate(0)(_+_, _+_) //聚合操作,有返回值
    rdd.top(2) //获取数据前N个,有返回值
    rdd.take(2) //获取数据前N个,有返回值
    rdd.takeOrdered(2) //获取数据前N个(内部有排序),有返回值
    rdd2.countByKey() //对KV形式的元素进行聚合操作(会触发shuffle),有返回值
    rdd.saveAsTextFile("") //无返回值,数据存入外部设备
    rdd.first() //有返回值,去第一个元素
    rdd.fold(0)(_+_) //聚合操作,有返回值的,和reduce有点类似
  }
}
