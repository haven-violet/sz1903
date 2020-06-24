package com.baidu.day07

/**
  * @Author liaojincheng
  * @Date 2020/5/11 8:46
  * @Version 1.0
  * @Description 高阶函数: 并行计算(多线程 -> 分布式思想)
  */
object _05ParOps {
  def main(args: Array[String]): Unit = {
    val arr = Array(1,2,3,4,5,6,7,8,9)
    //求和
    println(arr.sum)
    //添加并行计算(多线程计算)
    println(arr.par.sum)
    println(arr.reduce(_+_))//单线程计算
    println(arr.par.reduce(_+_))//多线程计算
//    fold()()第一个参数值叫做初始值,第二是元素在函数内的计算逻辑
    println(arr.fold(10)((a,b) => {
      println(s"${a} ${b}")
      a+b
    }))//单线程

    println("---多线程---")
    println(arr.par.fold(10)((a,b) => {
      println(s"${a} ${b}")
      a+b
    }))//多线程

  }
}
