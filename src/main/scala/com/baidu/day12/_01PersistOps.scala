package com.baidu.day12

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/5/18 14:37
  * @Version 1.0
  * @Description
  * 持久化 就是两个公共代码trans算子段,
  * 作用: 可以将重复的代码进行持久化操作,提高执行效率
  */
object _01PersistOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("persist").setMaster("local")
    val sc = new SparkContext(conf)

    val arr = Array(1,1,2,3,45)
    val rdd = sc.makeRDD(arr)

    //持久化
    //两者区别:
    //  cache 主要是在内存进行持久化,不可以修改持久化级别
    //  persist 默认有级别,但是可以进行修改级别,相比较cache更加灵活
    val rdd2 = rdd.map(_*2).cache()
    val rdd3 = rdd.map(_*2).persist(StorageLevel.MEMORY_AND_DISK)

    //释放持久化
    rdd3.unpersist()
    println(rdd2.sum())
  }
}
