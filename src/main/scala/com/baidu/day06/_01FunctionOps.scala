package com.baidu.day06

/**
  * @Author liaojincheng
  * @Date 2020/5/8 22:27
  * @Version 1.0
  * @Description
  *             函数式编程
  *             作为值的函数
  */
object _01FunctionOps {
  def main(args: Array[String]): Unit = {
//    funcOps4()
//    functionOps3()
//    functionOps2()
    functionOps1()
  }


  //scala中的函数操作过程中的类型推断
  def funcOps4() = {
    val money: Double => Double = (x:Double) => 100 * x
    println(money(10:Double))
    println(money(10))
    println("------")
    val list = Array(1,2,3,4,5)
    list.foreach( (x:Int) => print(x + "\t") )
    println("\n ---^-^---")
    list.foreach( (x) => print(x + "\t") )//简写一,省略数据类型
    println("\n ---^-^---")
    //如果匿名函数就只有一个参数的话,可以省略()
    list.foreach( x => print(x + "\t") )
    println("\n ---^-^---")
    //还可以使用通配符"_"来代替这个变量x,通配符就不用再写 => 指向操作
    list.foreach(println(_))
    println("\n ---^-^---")
    //最简洁的书写,是连这个通配符都是省略掉
    list.foreach(println)
  }

  /**
    * 高阶函数
    * 带函数参数的函数,函数的参数是函数,把这种函数称之为高阶函数
    */
  def functionOps3() ={
    def sayBye(name: String, func: (String) => Unit) = {
      func(name)
    }

    sayBye("张三", (name:String)=>println(name+"123"))
    sayBye("李四", (name:String)=>func(name))
  }

  def func(str: String) = {
    println("一日之计在于晨")
    println(str)
    println("一年之计在于春")
  }

  /**
    * 匿名函数: 没有名字的函数
    * 完整的函数
    *   def funcName(name):Type = {println("0000")}
    * 匿名函数
    *   (name) => {println("1111")}
    */
  def functionOps2() = {
    //匿名,没有名字,只是sayBye相当于该该匿名函数
    val sayBye = (name:String) => println(name)
    //有名,该函数有名字,有了一个引用
    def sayByeBye = (name:String) => println(name)
    //调用
    sayBye("zhangsan")
    sayByeBye("lisi")
  }

  /**
    * 函数可以作为值,传递给另外一个函数或者变量
    * 语法特点,必须要在函数后面加上下划线'_'
    */
  def functionOps1() = {
    def sayGoodBye(name:String) = {
      println("Hello Scala -- " + name)
    }

    //函数作为值传递给另外一个变量
    val sayBye = sayGoodBye _
    def sayBye2 = sayGoodBye _
    //函数调用
    sayBye("zhangsan")
    sayBye2("lisi")
  }
}

