package com.baidu.day10

import scala.collection.mutable.ListBuffer

/**
  * @Author liaojincheng
  * @Date 2020/5/15 16:27
  * @Version 1.0
  * @Description
  */
object Test {
  def main(args: Array[String]): Unit = {
    val arr = List(1,2,3,4)
    var arr1 = ListBuffer[Int]()
    for(i <- 0 until arr.length){
      arr1.append(i)
    }
    val tuples= arr1.zip(arr)
    tuples.foreach(println)
  }
}
