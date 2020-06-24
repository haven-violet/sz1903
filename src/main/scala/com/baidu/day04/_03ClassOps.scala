package com.baidu.day04

/**
  * @Author liaojincheng
  * @Date 2020/5/6 22:25
  * @Version 1.0
  * @Description
  * 构造器
  * scala的默认构造器在 类名后面 和 { 前面之间被定义,把这个构造器称为主构造器
  * 主构造器就是 类名后面 和 { 前面被定义的部分, ()中便是主构造器的参数列表,可以为空
  * 如果是空参的可以不写
  * scala的辅助构造器是使用关键字this来替换Java中的类名,注意在定义辅助构造器的时候
  * 一定要清楚,辅助构造器的第一句,必须要调用主构造器或者其他辅助构造器
  */
object _03ClassOps {
  def main(args: Array[String]): Unit = {
//    val teacher: Teacher = new Teacher("小红", 20)
//    teacher.show()
    println("---辅助构造器---")
    val t: Teacher = new Teacher(23,"米兴")
    t.show()
  }
}

//在定义有参主构造器的时候,我们的辅助构造器参数不要和主构造器一致
//不然我们在实例化类的时候,那么导致传参报错
class Teacher(val name2:String, val age2:Int){
  var name:String = "小明"
  var age:Int = 12
  println("这是scala的默认构造器嘛? -> 主构造器")

  def Teacher(): Unit = {//经过验证,这不是scala的构造器
    println("这是scala的默认构造器嘛?")
  }

  def this(name:String){
    this(name, 13)
    this.name = name
  }

  //注意: 别和主构造器参数一致(包括类型)
  def this(age:Int, name:String){//辅助构造器
    this(name)
    this.name = name
    this.age = age
    println("这个是scala的辅助构造器")
  }

  def show(): Unit = {
    println(name + "---> " + age2)
  }
}
