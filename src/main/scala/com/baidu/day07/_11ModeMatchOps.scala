package com.baidu.day07

/**
  * @Author liaojincheng
  * @Date 2020/5/11 13:50
  * @Version 1.0
  * @Description
  */
object _11ModeMatchOps {
  def main(args: Array[String]): Unit = {
    val map: Map[String, Int] = Map("zhangsan"->20, "lisi"->22, "wangwu"->26)
    //val maybeInt: Option[Int] = map.get("zhangsan")
    map.get("zhangsan1") match {
      case Some(age) => println(age)
      case None => println("sorry ...")
    }
  }
}
