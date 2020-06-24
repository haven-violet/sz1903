package com.baidu.day05

/**
  * @Author liaojincheng
  * @Date 2020/5/12 9:22
  * @Version 1.0
  * @Description
  * 匿名子类-->没有名字的子类
  * 通常出现在继承体系中,对于接口 抽象类只用一次的情况
  * 其实就像java匿名内部类一样
  * class XXX{} 不是匿名子类
  * new XXX(){} 匿名子类
  * 作用是方法传参
  */
object _08ExtendsOps {
  def main(args: Array[String]): Unit = {
    val p = new Person3(){
      override def show(): Unit = {
        super.show() //调用父类方法
        println("我们在春暖花开之时,等待英雄凯旋回归~")
      }
      def a(): Unit ={
        println("1111")
      }
    }
    //调用子类方法
    p.show()
    p.a()
    //方法传参
    showPerson(new Person3(){
      override def show(): Unit = {
        super.show()//调用父类的方法
        println("我们在春暖花开之时,等待英雄凯旋回归!")
      }
    })
  }

  def showPerson(p:Person3): Unit = {
    p.show()
  }
}

class Person3{
  val name:String = "我们都是中国人"
  def show(): Unit = {
    println(name)
  }
}

//有名子类
class Student2 extends Person3{
}