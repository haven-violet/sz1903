package com.baidu.day07

/**
  * @Author liaojincheng
  * @Date 2020/5/11 9:06
  * @Version 1.0
  * @Description
  * 模式匹配--> 集合匹配(Array、List、Tuple)
  * 集合进行匹配的时候,主要是针对集合内的元素个数进行匹配
  */
object _08ModeMatchOps {
  def main(args: Array[String]): Unit = {
    val arr = Array(1,1,1,2,3) //按照数组的元素个数进行匹配的
    arr match {
      case Array(1,2,x,y) => println(x) //只要匹配上其中一个,那么后面的所有都不考虑
      case Array(1,2,y) => println(s"${y}_")
      case _ => println("Nothing ...")
    }
    //List列表
    val list = List("zhangsan", "lisi", "wangwu")
    list match {
      case "zhangsan" :: Nil => println("hello zhangsan")
      case x :: y ::z :: Nil => println(x+" "+y+" "+z)
      case _ => println("Nothing ...")
    }
  }
}
