package com.baidu.day12

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/5/18 21:09
  * @Version 1.0
  * @Description
  * 累加器使用
  */
object _05AccumulatorOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("acc").setMaster("local")
    val sc = new SparkContext(conf)
    //创建RDD
    val rdd = sc.parallelize(Array(1, 2, 3))
    //为什么sum没有值? 最后还是0?
    //因为foreach处理的时候是没有返回值的,整个计算过程都在executor端完成的
    //此时你打印出的数据是driver端的数据,所以,你无法获取到数据,最后输出为0
//    var sum = 0
//    rdd.foreach(num => {
//      sum += num
//    })
//    println(sum)

    //使用spark中的累加器来进行累加即可
    val sum = sc.accumulator(0)
    rdd.foreach(num => {
      sum += num
    })
    println(sum.value)
  }
}
