package com.baidu.day04

/**
  * @Author liaojincheng
  * @Date 2020/5/6 14:27
  * @Version 1.0
  * @Description
  */
//伴生类
class _02ConstructOps {
  val age = 20
  //私有化的变量
  private var name = "zhangsan"
  private  val sex = "男"
  //我们在定义val或者var的时候,其实这里面就用到了类似于java中的get和set方法
  //val其实就提供了一个get方法,而没有提供set方法
  //var既提供了get也提供了set方法

  def sayHello(): Unit ={
    println("hello, " + name)
  }

  var name44 = "zadfjdkfdjkf"

  def setName = sex
}

//伴生对象
object _02ConstructOps{
  var name33 = "zaa"
  def main(args: Array[String]): Unit = {
    val ops: _02ConstructOps = new _02ConstructOps

    println(ops.name)

    /**
      * 在定义私有化变量后,如果想要进行访问的话,需要创建一个伴生类(对象)
      * 伴生的类或者对象可以相互访问私有属性,没有限制
      * 但是如果我们定义成private [this] 那么伴生关系也无法访问,因为加了条件限制
      */
    println(ops.sex)
  }
}

//object中其实存放都是静态属性和静态方法
object ClassTest{
  def main(args: Array[String]): Unit = {
    //实例化类
    val ops: _02ConstructOps = new _02ConstructOps
    //ops.age = 30  此时调用的是setAge(),var有,但是val没有
    //var aa = ops.age   此时调用的是getAge(),var和val都有

    //ops.sayHello()
    //println(ops.setName)

  }
}