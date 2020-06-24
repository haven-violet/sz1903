package com.baidu.day03

/**
  * @Author liaojincheng
  * @Date 2020/5/2 21:25
  * @Version 1.0
  * @Description
  */
object _01FunctionOps {
  def main(args: Array[String]) = {
    //这种语法结构其实是将数组汇总的每一个元素提取出来,传入到函数的参数列表中
    println(add(Array(3,4,5,6):_*))
  }

  def add(arr: Int*): Int = {
    var sum = 0
    for(i <- arr){
      sum += i
    }
    sum
  }
}
