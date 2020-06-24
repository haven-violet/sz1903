package com.baidu.day18

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._

/**
  * @Author liaojincheng
  * @Date 2020/5/26 18:57
  * @Version 1.0
  * @Description
  *             sparkSQL内置函数
  */
object _01SparkSqlFunctionOps {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("sparkSQL")
      .master("local")
      .getOrCreate()

    //获取数据
    //根据日期分组聚合
    //导入隐式转换
    import spark.implicits._
    val df = spark.read.json("file:///F:\\教学视频\\千峰大数据\\电商数仓\\day31\\资料\\userlog.json")
    df.printSchema()
    val df2 = df.groupBy(col("date"))
      .agg('date, countDistinct("name").as("counts"))

    //转换成RDD输出
    //不能这么搞,因为DF不可以直接转换成DS
    //df.map() DataSet 容易出错
    val rdd: RDD[Row] = df2.rdd
    rdd.map(t => {
      //在使用DF转换成RDD后,我们的数据类型就是Row类型,Row可以存值,也可以取值
      //在操作Row类型的时候,获取方式通过get来获取数据,get()参数可以传入下标
      //也可以传入字段名,下标从0开始
      val date1 = t.getAs[String](0)
      val date2 = t.getAs[String](1)
      val counts = t.getAs[Long]("counts")
      (date1, date2, counts)
    }).foreach(println)

  }
}
