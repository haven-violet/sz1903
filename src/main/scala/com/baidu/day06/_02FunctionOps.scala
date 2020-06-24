package com.baidu.day06

import scala.collection.mutable

/**
  * @Author liaojincheng
  * @Date 2020/5/8 23:22
  * @Version 1.0
  * @Description
  *             scala中常见的高阶函数
  *             这些函数都是作用在集合上面的
  */
object _02FunctionOps {



  /**
    * filter(过滤)
    * filter:(p: A => Boolean)
    * 该函数作用在集合的每个元素A的上面,返回值为Boolean类型,filter意思为过滤
    * 要过滤掉集合中的某一部分数据(满足过滤条件的),返回值为true或者false
    * 过滤掉为false的,留下为true的
    */
  def filterOps() = {
    val arr = 1 to 10
    //过滤调集合中的偶数
    var filtered = arr.filter((num : Int) => num % 2 != 0)
    println(filtered.mkString("[", ",", "]"))
    filtered = arr.filter( (num) => num % 2 != 0)
    println(filtered.mkString("[", ",", "]"))
    filtered = arr.filter( num => num % 2 != 0)
    println(filtered.mkString("[", ",", "]"))
    filtered = arr.filter( _ % 2 != 0)
    println(filtered.mkString("[", ",", "]"))
  }

  /**
    * map (p: A => B)
    * 一个集合中的所有元素A,都要作用在该匿名函数之上,每个元素调用一个该函数
    * 将元素A,最后转换成元素B
    * 需要注意的是, A和B的数据类型可以一致,也可以不一致
    */
  def mapOps() = {
    val arr = 1 to 10
    //将集合中的每个元素,扩大原来的2倍
    var newArray = arr.map((num:Int) => num * 2)
    println(newArray.mkString("[", ",", "]"))
    newArray = arr.map((num) => num * 2)
    println(newArray.mkString("[", ",", "]"))
    newArray = arr.map(num => num * 2)
    println(newArray.mkString("[", ",", "]"))
    newArray = arr.map(_ * 2)
    println(newArray.mkString("[", ",", "]"))

  }



  /**
    * flatMap-->(f: A => Iterable[B])
    * flatMap和map操作比较相似,不同之处在于返回值类型
    * 同时flatMap不光是进行每个元素处理,他还会进行压平操作
    */
  def flatMapOps(): Unit ={
    val arr: Array[String] = Array("zhang san", "li si", "wang wu")
    //提取这个数组中的每个单词
    val wordsArr: Array[String] = arr.flatMap((line: String) => {
      val words: Array[String] = line.split(" ")
      words
    })
    wordsArr.foreach(println)
    println()

    val wordsArr3: Array[String] = arr.flatMap(_.split(" "))
    wordsArr3.foreach(println)

    //flatMap等于map和flatten的结合版
//    val wordsArr2: Array[String] = arr.map((line: String) => {
//      val words: Array[String] = line.split(" ")
//      words
//    }).flatten
//    wordsArr2.foreach(println)
//    println()

  }


  /**
    * reduce(p: (A, A) => A)
    * reduce是一个聚合函数,将2个A转换成一个A(reduce函数作用在集合中的元素,进行聚合)
    * 这里面三个A分别代表什么意思?
    * 第一个A代表聚合的结果值,第二个A代表每次循环元素,第三个A代表函数的返回值
    */
  def reduceOps(): Unit = {
    val array = 1 to 6
    var sum = 0
    for(i <- array){
      sum+=i
    }
    println("sum="+sum)
    println("---使用reduce函数来聚合---")
    array.reduce((v1:Int, v2:Int) => {
      println(s"v1:${v1}, v2:${v2}")
      v1+v2
    })
    println("sum="+sum)
    println("---使用reduce函数来聚合---")
    sum = array.reduce((v1, v2)=>v1+v2)
    println("sum="+sum)
    println("---使用reduce函数来聚合---")
    sum = array.reduce(_+_)
    println("sum="+sum)
  }


  /**
    * dropWhile (p: A => Boolean)
    * 和filter一样,作用在集合的每个元素上面,返回值为Boolean类型
    * 作用: 删除满足Boolean条件的元素,直到不满足为止
    */
  def dropWhileOps(): Unit = {
//    val arr = Array(1,2,1,1)
    val arr = Array(1,3,1,1,1,2,1)
    //循环删除其中的偶数(删除不满足条件的元素),但是遇到满足条件的元素后,后面不考虑
    val newArr: Array[Int] = arr.dropWhile((num: Int) => num % 2 != 0)
    println(newArr.mkString("[", ",", "]"))
  }

  /**
    * sortWith --> 排序
    * (A, A)=>Boolean
    */
  def sortWithOps(): Unit = {
    val array = Array(5,6,1,8,4,2)
    var newArray: Array[Int] = array.sortWith((a:Int, b:Int) => a < b)
    println(newArray.mkString("[",",","]"))
    newArray = array.sortWith(_>_)
    println(newArray.mkString("[",",","]"))
    println("---sortBy---")
    newArray = array.sortBy((x:Int) => -x)
    println(newArray.mkString("[",",","]"))
    newArray = array.sortBy(x => x)
    println(newArray.mkString("[",",","]"))
  }


  def sortByOps(): Unit = {
    val arr: Array[(String, Int)] = Array(("zhangsan", 20), ("lisi", 21), ("wangwu", 22))
    //按照年龄进行降序
    var newArr: Array[(String, Int)] = arr.sortBy((x) => x._2).reverse
    println(newArr.mkString("[",",","]"))
    newArr = arr.sortBy((x) => -x._2)
    println(newArr.mkString("[",",","]"))
  }



  /**
    * groupBy分组 其实就是Sql中的group by
    */
  def groupByOps(): Unit ={
    val array = Array(
      "广东,深圳",
      "北京,朝阳",
      "上海,浦东",
      "河北,石家庄",
      "广东,广州",
      "北京,昌平",
      "上海,松江",
      "河北,邯郸")
    //按照省份,对每一个城市进行分组
    val pro: Map[String, Array[String]] = array.groupBy(
      (line: String) => line.substring(0, line.indexOf(","))
    )
    for((p,c) <- pro){
      println(s"${p}-->${c.mkString("[",",","]")}")
    }
    //将原始集合按照大小进行分组(了解即可)
    val arr: Iterator[Array[String]] = array.grouped(3)
    arr.foreach(ele => {
      println(ele.mkString("[",",","]"))
    })
  }

  def main(args: Array[String]): Unit = {
    partitionOps()
  }
  /**
    * partition
    * 将集合进行分区,分区大小我们自己传入即可
    */
  def partitionOps(): Unit ={
    val array = Array(1,2,3,4,56,5,4,5,6)
    //将集合arr按照6分区,小于6的在一个分区中,大于6的在一个分区中
    //这个函数不是很常用,因为在学习Spark的话,和这个函数完全不一样
    val (left,right): (Array[Int], Array[Int]) = array.partition((num:Int) => num < 6)
    println(left.mkString("[",",","]"))
    println(right.mkString("[",",","]"))
  }


}
