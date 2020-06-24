package com.shangguigu

/**
  * @Author liaojincheng
  * @Date 2020/5/1 16:37
  * @Version 1.0
  * @Description
  */
object Scala06_transform {
  def main(args: Array[String]): Unit = {
    //自动转换
    //scala默认的情况下支持数值类型的自动转换
    //byte -> short -> int -> long
    //scala默认的情况下支持多态语法中的类型自动转换
    //child -> parent -> -> trait(interface)

//    implicit def transform(d: Double): Int = {
//      d.toInt
//    }
    /*在相同的作用域内,不能含有相同类型的转换规则
    implicit def transform(d: Double): Int = {
      d.toInt
    }*/

    //scala也允许开发人员自定义类型转换规则
//    val i: Int = 5.0
//
//    println(i)


    implicit val name11:String = "violetd33"
    //隐式参数
    def test(implicit name: String = "zhangsan"): Unit = {
      println("Hello " + name)
    }

    //test("zhangsan11")
    //test()//方法调用时,使用小括号会导致隐式值无法传递
    test//方法调用时,不使用小括号可以传递隐式值

    //如果隐式参数存在默认值以及对应类型的隐式值,那么会优先采用隐式值,如果找不到,那么会使用默认值,如果没有默认值,那么会发生错误





  }
}
