package com.baidu.day04

/**
  * @Author liaojincheng
  * @Date 2020/5/6 10:50
  * @Version 1.0
  * @Description 表,将集合/数组拉链成一个个元组
  */
object _01ZipOps {
  def main(args: Array[String]): Unit ={
    var province = Array("江西", "湖北", "湖南", "江苏")
    //var captical = Array("南昌", "武汉", "长沙", "广州", "深圳")
    var captical = Array("南昌", "武汉", "长沙")
    val tuples: Array[(String, String)] = province.zip(captical)
    //tuples.foreach(println)

    /**
      * zipAll会使用后面的参数对没有匹配上的值自动做填充,
      * 其中第一个元素,会和第二个集合中的多余元素进行配对
      * 第二个元素,会和第一个集合中的多余元素进行配对
      */

    val tuples1: Array[(String, String)] = province.zipAll(captical, "广东", "南京")
    tuples1.foreach(println)
  }
}
