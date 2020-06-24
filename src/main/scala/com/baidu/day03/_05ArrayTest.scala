package com.baidu.day03

import scala.collection.mutable.ArrayBuffer

/**
  * @Author liaojincheng
  * @Date 2020/5/3 15:58
  * @Version 1.0
  * @Description 移除第一个负数之后的所有负数
  */
object _05ArrayTest {
  def main(args: Array[String]): Unit = {
    val a = ArrayBuffer[Int](1,2,3,4,5,-1,-2,-5)
    //定义一个变量,循环判断使用
    var left = 0
    var arrayLen = a.length
    var flag = false //判断是否出现第一个负数
    while(left < arrayLen){
      if(a(left) >= 0){
        left += 1
      }else{
        if(!flag){
          flag = true
          left += 1
        }else{
          a.remove(left)
          arrayLen -= 1
        }
      }
    }
    a.foreach(println)
  }
}
