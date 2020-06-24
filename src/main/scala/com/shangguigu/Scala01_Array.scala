package com.shangguigu

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * @Author liaojincheng
  * @Date 2020/5/1 18:43
  * @Version 1.0
  * @Description
  */
object Scala01_Array {
  def main(args: Array[String]): Unit = {
    //java数组: int[] ints = new int[3]{1,2,3}
    //ints[0] = 1
    //int i = ints[0]
    //scala中的数组,使用Array对象实现,使用中括号声明数组的类型
    //Array[String]
    //Scala可以使用简单的方式创建数组
    //Array可以通过apply方法来创建数组
    val ints: Array[Int] = Array(1,2,3,4)
    //访问数组: 使用小括号,增加索引的方式来访问数组
//    println(ints(0))

    //数组长度
//    println(ints.length)

//    val str: String = ints.+("ssss")
//    ints + "ssss"
//    println(str)

    //将数组转换成字符串打印出来
    //println(ints.mkString("|"))

    //将数组中的每个元素打印
//    for(elem <- ints){
//      println(elem)
//    }

//    def printlnXXX(i:Int ): Unit = {
//      println(i)
//    }

    //foreach方法会将数组中的每一个元素进行循环遍历,执行指定函数实现逻辑
//    ints.foreach(printlnXXX)
//    ints.foreach((i:Int ) => {println(i)})
//    ints.foreach((i) => {println(i)})
//    ints.foreach({println(_)})
//    ints.foreach(println(_))
//    ints.foreach(println)

    //增加元素
    //采用方法向数组中增加新的元素,但是不会对原有数组造成影响,而是产生新的数组
//    val ints1: Array[Int] = ints:+5
//    println(ints1.mkString(","))
//    println(ints == ints1)
//
//    val ints2: Array[Int] = 6+:ints
//    println(ints2.mkString(","))
//
//    //修改数据
//    ints.update(1, 77)
//    println(ints.mkString("-"))

    //可变数组
    val arrayBuffer : ArrayBuffer[Int] = ArrayBuffer(5,6,7,8)

    //查询值
//    println(arrayBuffer(0))
//    //修改值
//    arrayBuffer(0)=9
//    //将数组转换成字符串
//    println(arrayBuffer.mkString(","))
//
//    //循环遍历
//    arrayBuffer.foreach(println)

    //增加元素,向指定位置增加元素
//    arrayBuffer.insert(1, 88)
//    //增加元素
//    val buffer: ArrayBuffer[Int] = arrayBuffer+=9
//    arrayBuffer.foreach(println)
//    println(arrayBuffer == buffer)

    //删除数据
    val i: Int = arrayBuffer.remove(1)
    println(i)
    println(arrayBuffer)
    arrayBuffer.remove(1, 2)
    println(arrayBuffer)

    //可变数组和不可变数组的转换
    //将不可变数组转换成可变数组
    val buffer: mutable.Buffer[Int] = ints.toBuffer
    //将可变数组转换成不可变数组
    val array: Array[Int] = arrayBuffer.toArray


  }
}
