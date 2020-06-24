package com.baidu.day03

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * @Author liaojincheng
  * @Date 2020/5/3 9:46
  * @Version 1.0
  * @Description
  */
object _03ArrayOps {
  def main(args: Array[String]): Unit = {
    //创建一个空的数组
    val arr: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    //添加元素,可以添加一个或者多个元素,并且可以直接添加一个完整的数组(Array)
    arr.append(0,16,2,3)
    arr.+=(4,5,6)
    arr.++=(Array(7,8,9))
    println(arr)
    //截取元素
//    arr.trimEnd(4) //对本身数组进行操作,从后往前截取
//    println(arr)
//    arr.trimStart(2) //对本身数组进行操作,从前往后截取
//    println(arr)
    //插入元素
    arr.insert(1,9)
    println(arr)

    //使用remove删除指定位置的元素
    arr.remove(1)
    println(arr)

    //转换操作
    val array: Array[Int] = arr.toArray //转换成不可变数组
    val buffer: mutable.Buffer[Int] = array.toBuffer //转换成可变数组

    //循环遍历数组
//    for(i <- 0 until buffer.length){
//      println(buffer(i))
//    }

    //跳跃式遍历
//    for(i <- 0 until (buffer.length, 2)){
//      println(buffer(i))
//    }

    //倒序遍历
//    for(i <- (0 until buffer.length).reverse){
//      println(buffer(i))
//    }

    //最简单遍历方式
//    for(i <- buffer.reverse) println(i)

    //使用函数遍历
    buffer.foreach(x=>println(x))

  }
}
