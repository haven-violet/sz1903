package com.baidu.day08

import scala.util.matching.Regex

/**
  * @Author liaojincheng
  * @Date 2020/5/12 8:05
  * @Version 1.0
  * @Description
  * 正则表达式 它的入口就是regex
  * 如果匹配身份证、手机号之类的 这些都是通用的(百度找一些就可以啦)
  */
object _07RegexOps {
  def main(args: Array[String]): Unit = {
    val regex: Regex = "Scala".r

    val str = "love Scala is Scalas and ScalaScala"
    println(regex.findAllIn(str).toList)//匹配所有符合内容的字符
    println(regex.findFirstIn(str).get)//匹配第一个出现的字符
    println(regex.replaceAllIn(str, "Java"))//替换所有匹配到的字符

  }
}
