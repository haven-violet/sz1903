package com.baidu.day03

/**
  * @Author liaojincheng
  * @Date 2020/5/3 9:35
  * @Version 1.0
  * @Description
  */
object _02FunctionOps {
  def main(args: Array[String]): Unit = {
    println(fab(3))
    println(fab(4))
    println(fab(5))
  }

  def fab(n:Int): Int = {
    if(n<=2) {
      1
    }
    else{
      fab(n-1) + fab(n-2)
    }
  }
}
