package com.baidu.day17

import org.apache.spark.sql.{Dataset, SparkSession}

/**
  * @Author liaojincheng
  * @Date 2020/5/25 20:13
  * @Version 1.0
  * @Description
  *             构建DataSet
  */
object _01SparkDataSetOps {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("SparkSQLDS")
      .master("local")
      .getOrCreate()

    //构建DS
    val list = List(
      new Stu(1, "黄伟", 24),
      new Stu(1, "彦龙", 24),
      new Stu(1, "新值", 23)
    )
    //导入隐式转换操作
    import spark.implicits._
    val ds: Dataset[Stu] = spark.createDataset(list)
    spark.createDataFrame(list)
//    val ds1: Dataset[Stu] = list.toDS()
    ds.printSchema()
//    ds1.show()
    //关闭
    spark.stop()
  }
}

//注意: 如果创建DS的时候,一定要注意,使用样例类,类是无法抽取元数据的
//普通类()里面就像是参数
//样例类()里面就像是变量
case class Stu(id:Int, name:String, age:Int)