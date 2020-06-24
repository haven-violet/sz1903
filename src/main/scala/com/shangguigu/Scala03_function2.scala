package com.shangguigu

/**
  * @Author liaojincheng
  * @Date 2020/5/1 15:28
  * @Version 1.0
  * @Description
  */
object Scala03_function2 {
  def main(args: Array[String]): Unit = {

    //函数式编程 - 扩展
    //TODO 可变参数
//    def test(name: String*): Unit ={
//      println(name)
//    }

    //调用函数,可传多个参数,也可以不传参数WrappedArray(zhangsan, lisi, wangwu)
    //test("zhangsan", "lisi", "wangwu")

    //test()

    //TODO 默认参数
    //如果函数希望函数中的某个一个参数使用默认值,那么可以在声明时直接赋初始值
    def test(name: String, age: Int = 20): Unit ={
      println(s"${name} - ${age}")
    }

    def test1(name2: String = "lisi", name1: String): Unit ={
      println(s"${name1} - ${name2}")
    }

    //带名参数
    //调用函数的时候,参数匹配规则为从前到后,从左到右
    test1(name1 = "aaa", name2 = "zhangsna")
  }
}
