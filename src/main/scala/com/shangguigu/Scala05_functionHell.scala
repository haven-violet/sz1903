package com.shangguigu

/**
  * @Author liaojincheng
  * @Date 2020/5/1 15:41
  * @Version 1.0
  * @Description
  */
object Scala05_functionHell {
  def main(args: Array[String]): Unit = {
    //TODO Scala是完全面向函数式编程语言

    //函数在scala可以做任何的事情
    //函数可以赋值给变量
    //函数可以作为函数的参数
    //函数可以作为函数的返回值
//    def f(): Unit = {
//      println("function")
//    }
//
//    def f0() = {
//      //返回函数
//      //直接返回函数,有问题,需要增加特殊符号: 下划线
//      f _
//    }
//    //这个是直接返回该函数的执行结果Unit
//    //val unit: Unit = f0()
//
//    //加上f _,概述编译器,这个函数我不执行,需要返回该函数本身
//    //val function: () => Unit = f0()
//    f0()()

    /*
    def f1(i: Int) = {
      def f2(j: Int): Int = {
        i*j
      }

      f2 _
    }

    println(f1(2)(3))
    */

    //TODO 函数柯里化
    /*
    def f3(i:Int)(j:Int): Int = {
      i*j
    }

    println(f3(2)(4))
    */

    //TODO 闭包
    //一个函数在实现逻辑时,将外部的变量引入到函数的内容,改变了这个变量的生命周期,称之为闭包
    /*
    def f1(i: Int) = {

      def f2(j: Int): Int = {
        i * j
      }
      f2 _
    }

    println(f1(10)(2))*/


    //将函数作为参数传递给另外一个函数,需要采用特殊的声明方式
    // ()=>Unit
    //参数列表=> 返回值类型
    /*def f4(f: () => Int): Int = {
      f() + 10
    }

    def f5(): Int = {
      5
    }

    println(f4(f5))
*/

    def f6(f: () => Unit): Unit = {
      f()
    }

    f6(()=>{println("877")})
  }
}
