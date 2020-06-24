package com.baidu.day07

/**
  * @Author liaojincheng
  * @Date 2020/5/11 9:26
  * @Version 1.0
  * @Description
  * 模式匹配-->样例类匹配
  */
object _09ModeMatchOps {
  def main(args: Array[String]): Unit = {
    val teacher:Person = Teacher("zhangsan")
    val students: Person = Students("lisi")

    students match {
      case Teacher(name) => println("Teacher, name is "+name)
      case Students(name)  => println("Students, name is "+name)
      case _ => println("Nothing ...")
    }
  }
}
class Person
//样例类
case class Teacher(name:String) extends Person
case class Students(name:String) extends Person
