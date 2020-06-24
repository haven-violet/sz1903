package com.baidu.day07

import com.baidu.day07.Enums.Enums

/**
  * @Author liaojincheng
  * @Date 2020/5/11 13:54
  * @Version 1.0
  * @Description
  * 泛型: 类型约束
  * List<T>
  */
object _12GenericOps {
  def main(args: Array[String]): Unit = {
    val clth1 = new Clothes[Enums, String, Int](Enums.上衣, "black", 160)
    println(clth1.size)
    val clth2 = new Clothes[Enums, String, String](Enums.内衣, "black", "L")
    println(clth2.size)
    val clth3 = new Clothes[Enums, String, String](Enums.裤子, "yellow", "XL")
    println(clth3.size)
  }
}

//定义一个泛型类
class Clothes[A,B,C] (val types:A, val color:B, val size:C)

//枚举
object Enums extends Enumeration {
  type Enums = Value
  val 上衣,内衣,裤子 = Value
}