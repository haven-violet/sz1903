package com.baidu.day03

/**
  * @Author liaojincheng
  * @Date 2020/5/3 10:04
  * @Version 1.0
  * @Description
  */
object _04ArrayOps {
  def main(args: Array[String]): Unit = {
    //yield可以构建一个新的集合,在循环中使用的话,就叫循环的推导式子
    val a = Array(3,2,1,4,5)
    val b: Array[Int] = for(ele <- a) yield ele*ele
    //b.foreach(x=>println(x))
    //结合if守卫,进行元素取偶数
    //for(ele <- a if ele % 2 == 0) println(ele)
    //a.filter(p => p % 2 == 0).foreach(x=>println(x))
    //这种写法是上面的简易版写法,比较实用,但是我们第一次接触的话
    //还是推荐上面写法,容易理解
    //a.filter(_ % 2 == 0).foreach(x=>println(x))
    //对每个元素乘以2
    //for(ele <- a) println(ele*2)
//    a.map(x=>x*2).foreach(x=>println(x))
    //a.map(_ * 3).foreach(println)

    //    a.sum
    //    a.max
    //    a.min
        //排序
    //    a.sorted.foreach(x=>println(x))
//        val reverse: Array[Int] = a.reverse
//    reverse.foreach(println)

    a.sortWith(_ > _).foreach(println(_))
    a.sortWith(_ < _).foreach(println)
    //格式化数组,转换成String字符串
    println(a.mkString("~"))
    println(a.mkString("<","~",">"))
    println(a.mkString)
  }

}
