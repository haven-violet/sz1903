package com.baidu.day05

/**
  * @Author liaojincheng
  * @Date 2020/5/7 22:22
  * @Version 1.0
  * @Description
  * Scala中的继承(扩展),使用关键字extends
  * 1.scala子类覆盖父类的方法，必须要使用overwrite修饰符
  * 2.子类访问父类成员,需要通过关键字super来完成
  */
object _03ExtendsOps {
  def main(args: Array[String]): Unit = {
    val p = new Person("zhangsan", 23)
    p.show()
    val stu = new Student("小明")
    stu.show()
  }
}

class Person {
  var name: String = _
  var age: Int = 18

  def this(name:String, age:Int){
    this
    this.name = name
    this.age = age
  }

  def show(): Unit ={
    println("person " + name + "--->" + age)
  }

  def setName(name: String) = this.name = name
}

class Student extends Person {
  def this(name:String){
    this
    super.setName(name)
  }

  override def show(): Unit = {
    super.show()
    println("student ---")
  }
}
