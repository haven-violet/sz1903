package com.baidu.day05

/**
  * @Author liaojincheng
  * @Date 2020/5/7 23:30
  * @Version 1.0
  * @Description
  */
object _05ExtendsOps {
  def main(args: Array[String]): Unit ={
    val x = new Dog("小黄")
    val y = new Dog("小黑")
    x.makeFriends(y);

  }
}

class Animal {
  var name: String = _
  //只能被本类及其子类来访问,但是不能被子类的实例来访问
//  protected [this] var age = 3
  protected var age =3
  def this(name: String, age: Int){
    this
    this.name = name
    this.age = age
  }
  def show(): Unit = {
    println(s"Animal: ${name}, ${age}")
  }
}

class Dog extends Animal{
  age = 5
  def this(name:String){
    this
    this.name = name
  }
  def makeFriends(dog: Dog): Unit = {
    println(this.name + "age is" + age + "和"
          + dog.name + ", age is" + dog.age + ", 交了好朋友")
  }
}

