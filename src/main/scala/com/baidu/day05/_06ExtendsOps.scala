package com.baidu.day05

/**
  * @Author liaojincheng
  * @Date 2020/5/8 8:43
  * @Version 1.0
  * @Description
  * 超类的构造过程
  */
object _06ExtendsOps {
  def main(args: Array[String]): Unit = {
    val zi = new Zi(20)
  }
}

class Fu(name:String, age:Int) {
  println("---Fu 主构造器---")
  def this(name:String) {
    this(name, 18)
    println("---Fu 辅助构造器---")
  }
}

/**
  * 1. extends Fu(name) 子类的辅助构造器 => 子类的主构造器 => 父类的辅助构造器 => 父类的主构造器
  * 2. extends Fu(name, age) 子类的辅助构造器 => 子类的主构造器 => 父类的主构造器
  * 在Scala的辅助构造器的第一行必须要调用本类的主构造器或者是其他辅助构造器
  * 所以,scala子类的辅助构造器是不可能直接调用父类的主构造器的,
  * 只能是子类的主构造器调用父类的构造器
  * @param name
  * @param age
  */
class Zi(name:String, age:Int) extends Fu(name) {
  println("---Zi 主构造器---")
  def this(age:Int) {
//    super("张三", age)
    this("张三", age)
    println("---Zi 辅助构造器---")
  }
}