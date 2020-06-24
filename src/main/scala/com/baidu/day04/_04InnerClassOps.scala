package com.baidu.day04

/**
  * @Author liaojincheng
  * @Date 2020/5/6 22:57
  * @Version 1.0
  * @Description
  */
object _04InnerClassOps {
  def main(args: Array[String]): Unit = {
    //scala中没有java中的这种new Outer().new Inner()
    val outer: Outer = new Outer
    val inner: outer.Inner = new outer.Inner //scala内部类对象的构造方式
    inner.show()
  }
}

//内部类
class Outer{ o => //此时o代表的就是本类对象引用
  val x = 5
  class Inner{ i => //此时i代表的就是本类对象的引用
    val x = 6
    def show(): Unit ={
      val x = 8
      println(x)
      println(i.x)
      println(o.x)
    }
  }
}