package com.baidu.day07

import scala.util.Random

/**
  * @Author liaojincheng
  * @Date 2020/5/11 9:00
  * @Version 1.0
  * @Description
  * 模式匹配-->类型匹配
  */
object _07ModeMatchOps {
  def main(args: Array[String]): Unit = {
    val classTest = new ClassTest
    val array: Array[Any] = Array("太空", 20, true,classTest)
    //随机获取数据
    val randomVal = array(Random.nextInt(array.length))
    randomVal match {
      case str:String => println("match String ...")
      case int:Int => println("match Int ...")
      case boolean:Boolean => println("match Boolean ...")
      case cla:ClassTest => println("match ClassTest ...")
    }
  }
}

class ClassTest{}
