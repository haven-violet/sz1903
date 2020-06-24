package com.shangguigu
import scala.collection.mutable
/**
  * @Author liaojincheng
  * @Date 2020/5/2 11:37
  * @Version 1.0
  * @Description
  */
object Scala01_set {
  def main(args: Array[String]): Unit = {
    //set集合: 无序,不可重复
    //默认scala提供的set集合就是不可变的(immutable)
    val set: Set[Int] = Set(1,2,3,4)
    //增加数据
    println(set + 11)
    //删除数据
    println(set -3)
    //set
    for(elem <- set){
      println(elem)
    }
    println(set.mkString(","))

    //可变Set集合(mutable)
    val ints: mutable.Set[Int] = mutable.Set(1,2,3,4)


  }
}
