package com.baidu.day16

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
  * @Author liaojincheng
  * @Date 2020/5/23 9:20
  * @Version 1.0
  * @Description 动态编程(编程接口)方式
  */
object _03CreateDataFrameOps {
  def main(args: Array[String]): Unit = {
    //创建执行入口,上下文入口
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local")
      .getOrCreate()
    //获取数据信息
    val lines: RDD[String] = spark.sparkContext.textFile("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\students.txt")
    val words: RDD[Row] = lines.map(t => {
      val str = t.split(",")
      Row(str(0).toInt, str(1), str(2).toInt)
    })

    //接下来构建DF
    //构建structType 说白了就是构建schema信息
    val structType = StructType(
      Array(
        StructField("ids", IntegerType, true),
        StructField("names", StringType, true),
        StructField("ages", IntegerType, true)
    ))
    val df = spark.createDataFrame(words, structType)
    df.printSchema()
    df.show()
  }
}
