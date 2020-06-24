package com.baidu.day07

import scala.io.StdIn

/**
  * @Author liaojincheng
  * @Date 2020/5/11 8:55
  * @Version 1.0
  * @Description
  * 模式匹配 --> 变量匹配
  *  Scala的模式匹配类似于java的switch case 语句,但是远远要比java的匹配强大的多
  */
object _06ModeMatchOps {
  def main(args: Array[String]): Unit = {
    println("请输入一个字符:")
    val ch: Char = StdIn.readChar()
    val sing = ch match {
      case '-'=>1
      case '+'=>2
      case '*'=>3
      case '/'=>4
      case _=>0 //如果上述全部没有匹配上,那么有一个默认匹配值
    }
    println("sing: " + sing)
  }
}
