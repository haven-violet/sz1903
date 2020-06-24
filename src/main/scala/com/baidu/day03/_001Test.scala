package com.baidu.day03

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

/**
  * @Author liaojincheng
  * @Date 2020/5/3 17:51
  * @Version 1.0
  * @Description
  */
object _001Test {
  def main(args: Array[String]): Unit = {
    println(getSum(2, 5))
  }

  def getSum(a: Int, count: Int) = {
    var sum = 0
    var temp = 0
    for(i <- 0 until count){
      temp = 0
      for(j <- 0 to i) {
        temp += a * Math.pow(10, j).toInt
      }
      sum += temp
      print(s"${temp}\t")
    }
    sum
  }
}
