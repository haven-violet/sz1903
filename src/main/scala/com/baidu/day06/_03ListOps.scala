package com.baidu.day06

/**
  * @Author liaojincheng
  * @Date 2020/5/10 18:09
  * @Version 1.0
  * @Description List列表
  */
object _03ListOps {
  def main(args: Array[String]): Unit = {
    val first: List[Int] = List(1,2,3,4,5)
    //三个基本方法 head, isEmpty, tail
    val head: Int = first.head
    val tail: List[Int] = first.tail
    val empty: Boolean = first.isEmpty
    println(s"${head}")
    println(s"${tail}")
    println(s"${empty}")
  }
}
