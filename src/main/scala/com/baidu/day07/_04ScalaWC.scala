package com.baidu.day07

import scala.io.Source

/**
  * @Author liaojincheng
  * @Date 2020/5/11 8:16
  * @Version 1.0
  * @Description Scala版的WordCount
  */
object _04ScalaWC {
  def main(args: Array[String]): Unit = {
    //读取本地文件
    val list: List[String] = Source.fromFile("F:\\test.txt").getLines().toList
    //val list = List("hello jack hello tom", "hello leo hello tom")
    //根据上述给的集合元素,统计集合内的单词个数(wc)
    //把数据切分并压平
    val lines: List[String] = list.flatMap(_.split(" "))
    //严谨一点就先过滤一下空格或者空值
    val filtered: List[String] = lines.filter(_!=" ")
    //将数据生成元组(word,1)能使用高阶函数,就尽量使用高阶函数
    val tuples: List[(String, Int)] = filtered.map((_,1))
    println(tuples) //这个存储数据
    //以key进行分组
    val grouped: Map[String, List[(String, Int)]] = tuples.groupBy(_._1)
    //开始进行统计单词出现的次数
    val result: Map[String, Int] = grouped.map(x => (x._1, x._2.size))
    //mapValues其实是帮我们已经把key分好了,不需要我们管理key了,直接聚合Value
    val result1: Map[String, Int] = grouped.mapValues(_.size)
    //降序输出
    val arr1: Array[(String, Int)] = result1.toArray.sortBy(-_._2)
    println(arr1)
    println(arr1.toBuffer)
  }
}
