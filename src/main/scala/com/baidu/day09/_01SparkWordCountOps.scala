package com.baidu.day09

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/5/13 20:41
  * @Version 1.0
  * @Description
  * Spark的第一个程序(wc)
  */
object _01SparkWordCountOps {
  def main(args: Array[String]): Unit = {
    /*
    如果将数据结果存入本地的话,有可能会有问题,因为我们启动程序后,找不到winutils.exe
    所有会出现存入数据空值现象,那么我们需要配置一个Hadoop的环境
    System.setProperty("hadoop.home.dir", "E:\\hadoop-common-2.2.0-bin-master")
    如果我们想要启动Spark程序,那么首先必须要有执行入口
    SparkContext 执行入口
     */
    //<1> SparkContext 执行入口
    /**
      * setMaster设置任务的运行模式,可以选择两种方式
      * 第一种设置local方式  本地模式
      * local 代表一个核数(线程)
      * local[*] 代表多个(随机)
      * local[2] 代表2个(具体指定数量)
      * 第二种不设置setMaster 代表集群模式
      * setAppName 设置当前任务的名称,如果不写会有默认的名字(UUID)
      */
    val conf = new SparkConf()
      .setMaster("local") //设置本地运行模式,如果想要打包将任务程序提交至集群运行,那么需要注释掉
      .setAppName("SparkWC") //设置当前任务名称
    val sc: SparkContext = new SparkContext(conf)
    //有了执行入口,开始获取数据
//TODO    val lines: RDD[String] = sc.textFile(args(0), 2)
    val lines: RDD[String] = sc.textFile("F://test.txt",3)
    //处理数据,切分数据
    val words: RDD[String] = lines.flatMap(_.split(" "))
    //将数据生成元组(单词, 1)
    val tuples: RDD[(String, Int)] = words.map((_,1))

//    MapPartitionsRDD[3] at map at _01SparkWordCountOps.scala:41
//    RDD是不存储数据的
    println(tuples)


    //将数据分组处理
//    val grouped: RDD[(String, Iterable[(String, Int)])] = tuples.groupBy(_._1)
//    val grouped: RDD[(String, Iterable[Int])] = tuples.groupByKey()
    //统计单词个数
//    val result: RDD[(String, Int)] = grouped.mapValues(_.size)

    //在spark中可以用ReduceByKey来代替groupBy(groupByKey)与mapValues两个算子操作
    //节省编程的代码量
//    val result: RDD[(String, Int)] = tuples.reduceByKey((x, y) => {
//      println(s"${x} ${y}")
//      x + y
//    })
//    val result: RDD[(String, Int)] = tuples.reduceByKey(_+_)

    val result: RDD[(String, Int)] = tuples.aggregateByKey(0)(_+_,_+_)

    result.foreach(println)
    //将数据存入HDFS
//TODO    result.saveAsTextFile(args(1))
    result.saveAsTextFile("F://wc001")
    //关闭应用程序
    sc.stop()
  }
}
