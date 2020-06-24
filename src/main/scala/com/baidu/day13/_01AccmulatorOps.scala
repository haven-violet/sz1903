package com.baidu.day13

import org.apache.spark.util.{DoubleAccumulator, LongAccumulator}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/5/19 18:20
  * @Version 1.0
  * @Description
  * 新版累加器
  */
object _01AccmulatorOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("acc").setMaster("local")
    val sc = new SparkContext(conf)

    //创建RDD
    val rdd1 = sc.parallelize(List(1,2,3,4,5,6))
    val rdd2 = sc.parallelize(List(1.0,2.0,3.0,4.0,5.0,6.0))

    //创建LongAccumulator
    val zz: LongAccumulator = longAcc(sc, "zhangsan")
    rdd1.foreach(x=>{
      zz.add(x)
    })
    println(zz.value)
    println(zz.avg)
    println(zz.count)
    println(zz.sum)

    //浮点数累加
    val xx = doubleAcc(sc, "xiaohong")
    rdd2.foreach(x=>{
      xx.add(x)
    })
    println(xx.value)
  }

  def longAcc(sc:SparkContext, name:String):LongAccumulator = {
    val acc = new LongAccumulator
    //在spark中注册累加器
    sc.register(acc, name)
    acc
  }

  def doubleAcc(sc:SparkContext, name:String):DoubleAccumulator = {
    val acc = new DoubleAccumulator
    //在spark中注册累加器
    sc.register(acc)
    acc
  }
}
