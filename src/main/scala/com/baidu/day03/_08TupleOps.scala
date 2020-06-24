package com.baidu.day03

/**
  * @Author liaojincheng
  * @Date 2020/5/3 17:39
  * @Version 1.0
  * @Description 元组操作
  */
object _08TupleOps {
  def main(args: Array[String]): Unit = {
    //创建元组
    //注意: 我们在创建元组的时候,元素个数不能超过22个
    val tuple: Tuple1[String] = new Tuple1[String]("agbc")
    println(tuple._1)
    val tuple1: (Int, Double) = (1, 1.1) //这种方式最方便
    //scala简易版元素,变量名要和元素一一对应, 不然不可以这样使用
    val (spring, summer, autumn, winter) = ("spring", "summer", "autumn", "winter")
    println(spring)
    println(summer)
  }
}
