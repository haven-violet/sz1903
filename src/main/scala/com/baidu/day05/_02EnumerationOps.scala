package com.baidu.day05

/**
  * @Author liaojincheng
  * @Date 2020/5/7 21:27
  * @Version 1.0
  * @Description
  * scala枚举
  */
object _02EnumerationOps extends Enumeration {
  val SPRING = Value(0, "spring")
  val SUMMER = Value(1, "summer")
  val AUTUMN = Value(2, "autumn")
  val WINTER = Value(3, "winter")

}

object EnumTest{
  def main(args: Array[String]): Unit = {
    println(_02EnumerationOps(0))
    println(_02EnumerationOps.SPRING)
    println(_02EnumerationOps.withName("winter"))
    //遍历所有枚举值
    for(ele <- _02EnumerationOps.values) println(ele)
  }
}
