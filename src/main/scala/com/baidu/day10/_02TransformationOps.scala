package com.baidu.day10

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * @Author liaojincheng
  * @Date 2020/5/17 21:14
  * @Version 1.0
  * @Description
  * 转换算子--> 进阶篇
  */
object _02TransformationOps {
//  def main(args: Array[String]): Unit = {
////    mapPartitions()
////    ()
////    rePartition()
////    Distinct()
////    coGroup()
//    combineByKey()
//  }


  def mapPartitions(): Unit = {
    val conf = new SparkConf().setAppName("mapPartition").setMaster("local")
    val sc = new SparkContext(conf)
    //准备模拟数据
    val arr = Array("张三","李四","王五","赵六")
    val nameRDD = sc.makeRDD(arr, 2)
    val hashMap = mutable.HashMap("张三"-> 278, "李四" -> 299, "王五" -> 300, "赵六" -> 222 )
    //根据Key获取分数
    /**
      * map和mapPartition的区别
      * map一次处理一条数据,而mapPartition一次处理一个分区的数据
      * 在性能方面mapPartition优于map,但是在安全方面,map要比mapPartition安全
      * 如果你的RDD数据量不是特别大,那么推荐使用mapPartition来处理数据代码Map操作
      * 但是如果你的RDD数据量特别大,比如说10亿,那么不建议使用mapPartition
      * 可能会内存溢出
      */
    val scores: RDD[Int] = nameRDD.mapPartitions(m => {
      val list = ListBuffer[Int]()
      while (m.hasNext) {
        val name = m.next()
        val score = hashMap.get(name).get
        list.append(score)
      }
      list.iterator
    })
    scores.foreach(println)
  }



  /**
    * 分区算子
    */
  def mapPartitionWithIndex(): Unit = {
    val conf = new SparkConf().setAppName("").setMaster("local")
    val sc = new SparkContext(conf)
    /**
      * 例子: 公司部门整合
      * 公司原先有6个部门(相当于6个分区),但是呢,很不巧,公司效益不好,裁员
      * 裁员以后,我们发现有的部门中得人就没了,但是这个部门还存在,那么我需要将它整合
      * 将一些人少的部门合并,进行压缩
      */
    val list = List("周海龙", "李安乾", "饶子奇",
      "石嘉雄", "杜群琼", "黄伟", "吴镇升", "黄信智", "杨贵中", "廖金城")
    //6个部门
    val rdd = sc.parallelize(list, 6)
    //首先查看之前的部门人员情况, true表示是否要使用当前分区
    val parName: RDD[(Int, String)] = rdd.mapPartitionsWithIndex(mapParIndexFunc, true)
    parName.foreach(println)
    //将部门缩减一倍
    //算子用来做减少分区,建议使用场景,配合filter一起使用
    //使用filter之后数据量肯定会减少,每个分区内的数据分配就不均匀了,如果其中
    //一个分区内过滤了大部分数据,次数我们可以使用当前算子进行压缩,减少分区的资源开销
    val coalPar: RDD[(Int, String)] = parName.(3)
    //查看一下缩减后的部门人员分配
    val parName2 = coalPar.mapPartitionsWithIndex(mapParIndexFunc2, true)
    parName2.foreach(println)
  }

  //f: (Int, Iterator[T]) => Iterator[U]
  def mapParIndexFunc(p1:Int, iter:Iterator[String]): Iterator[(Int, String)] ={
    var res = List[(Int, String)]()
    while(iter.hasNext){
      val name = iter.next()
      res = res.::(p1, name)
    }
    res.iterator
  }

  def mapParIndexFunc2(p1:Int, iter:Iterator[(Int, String)]): Iterator[(Int, (Int, String))] = {
    var res = List[(Int,(Int,String))]()
    while(iter.hasNext){
      val name = iter.next()
      res = res.::(p1,name)
    }
    res.iterator
  }


  def rePartition(): Unit = {
    val conf = new SparkConf().setAppName("rePartition").setMaster("local")
    val sc = new SparkContext(conf)
    //基于上面的案例,进行修改,过了段时间,公司效益超级好
    //需要扩充部门
    val list = List("周海龙", "李安乾", "饶子奇",
      "石嘉雄", "杜群琼", "黄伟", "吴镇升", "黄信智", "杨贵中", "廖金城")
    val rdd = sc.parallelize(list, 3)
    //首先查看之前的部门人数情况
    val parName = rdd.mapPartitionsWithIndex(mapParIndexFunc, true)
    parName.foreach(println)
    //扩充部门
    //相比较算子,reparation算子更适合进行扩大分区,因为reparation
    //默认是触发shuffle操作的,所以适合扩大分区
    val value: RDD[(Int, String)] = parName.repartition(6)
    //查看
    val parName2 = value.mapPartitionsWithIndex(mapParIndexFunc2, true)
    parName2.foreach(println)
  }

  /**
    * 去重算子(shuffle)
    */
  def Distinct(): Unit = {
    val conf = new SparkConf().setAppName("Distinct").setMaster("local")
    val sc = new SparkContext(conf)

    val rdd = sc.parallelize(List(1,1,1,2,3))
    rdd.foreach(print)
    //去重
    val unit: RDD[Int] = rdd.distinct(1)
    unit.foreach(println)
  }

  def main(args: Array[String]): Unit = {
    coGroup()
  }

  def coGroup(): Unit = {
    val conf = new SparkConf().setAppName("coGroup").setMaster("local")
    val sc = new SparkContext(conf)
    //模拟数据
    val stu = Array((1,"leo"),(2,"jack"),(2,"jack"),(3,"tom"))
    val score = Array((1,100),(1,100),(1,100),(2,99),(2,99),(3,88))
    val rdd1 = sc.parallelize(stu)
    val rdd2 = sc.parallelize(score)
    val res: RDD[(Int, (Iterable[String], Iterable[Int]))] = rdd1.cogroup(rdd2)
    res.foreach(println)
  }

  def combineByKey(): Unit = {
    val conf = new SparkConf().setAppName("coGroup").setMaster("local")
    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(List(("cat",1),("dog",2),("cat",2),("dog",2)))
    //聚合
//    rdd.reduceByKey(_+_)
    //第一个参数是我们创建出来的变量,就是我们每个元素
    //第二个参数是每个分区内的局部聚合操作
    //第三个参数是每个分区聚合后的结果进行的全局聚合操作
    val res = rdd.combineByKey(x => x, (a:Int, b) => a + b, (m:Int, n:Int) => m + n)
    res.foreach(println)
  }
}


