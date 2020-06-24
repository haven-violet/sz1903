package com.baidu.day07

import scala.collection.mutable.ListBuffer

/**
  * @Author liaojincheng
  * @Date 2020/5/10 18:17
  * @Version 1.0
  * @Description
  */
object _01ListOps {
  def main(args: Array[String]): Unit = {
//    val List = Nil //构建一个空的列表
    val left = List(1,2,3,4,5,6)
    val right = List(6,7,8,9)
    //crud操作
    //增
    var newList = left.+:(6)//表示向首部添加一个元素
    println(" left.+:(6)"+newList)
    newList = left.::(6)//表示向首部添加一个元素
    println(" left.::(6)"+newList)
    newList = left.:+(6)//表示向尾部添加一个元素
    println(" left.:+(6)"+newList)
    //添加集合
    newList = left.++(right)//表示向首部添加集合
    println(" left.++(right)"+newList)
    newList = left.:::(right)//表示向尾部添加集合
    println(" left.:::(right)"+newList)
    newList = left.++:(right)//表示向尾部添加集合
    println(" left.++(right)"+newList)
    //删
    newList = newList.drop(3)
    println(" newList.drop(3)"+newList)
    //改
    val list = ListBuffer(1,2,3)
//    newList(3) = 10//要想改变列表的元素,一定是可变列表
//    println("newList(3)" + newList)
    //查
    val ret = newList(1)
    println("newList(1)" + ret)
    //判断
    println("newList.contains(5)"+newList.contains(5))
    //遍历
    newList.foreach(println)
    //交集并集差集
    val listUnion = left.union(right)//相当于sql汇总的union all
    println(listUnion)
    val listInter = left.intersect(right)//交集
    println(listInter)
    var listDiff = left.diff(right)//差集
    println(listDiff)
    //获取前N个元素
    println(newList)
    val listTake = newList.take(3)
    println(listTake)

  }
}
