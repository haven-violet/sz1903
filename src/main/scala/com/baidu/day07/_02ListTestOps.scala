package com.baidu.day07

/**
  * @Author liaojincheng
  * @Date 2020/5/10 23:36
  * @Version 1.0
  * @Description
  */
object _02ListTestOps {
  def main(args: Array[String]): Unit = {
    listTest(List(1,2,3,5), "china:")
  }

  def listTest(list: List[Int], prefix: String): Unit = {
    if(list!=Nil){
      println(prefix+list.head)
      listTest(list.tail, prefix)
    }
  }
}
