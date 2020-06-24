package com.baidu.day07

import scala.collection.immutable.HashSet
import scala.collection.mutable

/**
  * @Author liaojincheng
  * @Date 2020/5/10 23:39
  * @Version 1.0
  * @Description
  */
object _03SetOps {
  def main(args: Array[String]): Unit = {
    //不可变Set
    val set1 = new HashSet[Int]()
    //添加一个元素,但是会生成新的Set,原有的Set不变
    val set2 = set1 + 4
    println(set1 + " " + set2)
    //添加一个Set集合,元素不可重复
    val set3 = set2 ++ Set(4,5,6)
    println(set3)
    //可变Set
    val set4 = new mutable.HashSet[Int]()
    //添加元素
    set4+=1
    set4.add(2)
    set4++=Set(3,4,5)
    println(set4)
    //删除元素
    set4 -= 1
    set4.remove(2)
    println(set4)
    //遍历
    set4.foreach(println)
    //判断
    set4(1)
    //排序Set
    val sorted = mutable.SortedSet[Int](1,5,4,8,6)
    println(sorted)
  }
}
