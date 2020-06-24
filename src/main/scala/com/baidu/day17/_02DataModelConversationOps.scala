package com.baidu.day17

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * @Author liaojincheng
  * @Date 2020/5/25 21:05
  * @Version 1.0
  * @Description
  * spark各个数据模型之间的相互转换
  * RDD
  * rdd --> DataFrame
  * rdd --> DataSet
  * 其实前面学习的toDS和toDF就是转换操作的代码
  * DataFrame
  * df --> rdd
  * df --> ds
  * DataSet
  * ds --> rdd
  * ds --> df
  */
object _02DataModelConversationOps {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("DataModelConversation")
      .master("local")
      .getOrCreate()
    val person = List(
      Human("张三", 15, 88.5),
      Human("李四", 13, 18.5),
      Human("王五", 10, 38.5),
      Human("赵六", 11, 58.5),
      Human("天气", 16, 68.5)
    )
    //创建RDD
    val rdd: RDD[Human] = spark.sparkContext.parallelize(person)
    //导入隐式转换
    import spark.implicits._
    println("rdd --> DF or DS")
    //rdd --> DF
    val df: DataFrame = rdd.toDF()
    df.printSchema()
    df.show()
    //rdd --> DS
    val ds: Dataset[Human] = rdd.toDS()
    ds.printSchema()
    ds.show()
    println("DF --> rdd")
    val rdd2 = df.rdd
    println("获取内部的数据")
    println(rdd2.count())
    println("DF --> DS")
    //注意DF不推荐转换成DS,因为我们在使用的过程中,DS的数据类型是基于这种样例类的模式
    //而我们的DF是基于Row类型模式,那么也就是说,row不符合这种样例类的操作
    val ds2 = df.as("log")
    ds2.show()
    println("ds --> rdd")
    val rdd3: RDD[Human] = ds.rdd
    println(rdd3.count)
    println("ds --> df")
    val df2: DataFrame = ds.toDF()
    df2.show()
    spark.stop()
  }
}

case class Human(name:String, age:Int, score:Double)
