package com.baidu.day04

/**
  * @Author liaojincheng
  * @Date 2020/5/7 9:02
  * @Version 1.0
  * @Description Scala的单例
  *              注意: 保证能自己手写出来(为了面试做准备)
  */
object _06SingletonOps {
  def main(args: Array[String]): Unit = {
    val s1 = Singleton.getInstance()
    val s2 = Singleton.getInstance()
    println(s1 == s2)
    println(s1.x)
    println(s2.x)
  }
}

//单例模式-- 饿汉式
//1.私有化主构造
class Singleton private(){
  var x = 5
}

object Singleton {
  //定义一个静态变量
  private val singleton = new Singleton()

  def getInstance() = {
    singleton
  }

}
