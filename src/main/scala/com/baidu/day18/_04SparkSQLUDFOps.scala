package com.baidu.day18

import org.apache.spark.sql.SparkSession

/**
  * @Author liaojincheng
  * @Date 2020/5/26 22:53
  * @Version 1.0
  * @Description
  * 自定义函数
  * udf 一个输入, 一个输出  自定义函数
  * udaf 多个输入, 一个输出 自定义聚合函数
  *
  */
object _04SparkSQLUDFOps {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local")
      .getOrCreate()
    val list = List("zhangsan", "listi", "wangwu")
    val rdd = spark.sparkContext.parallelize(list)

    import spark.implicits._
    val df = rdd.toDF("name")
    df.createOrReplaceTempView("log")

    //todo 1.先定义函数
    //     2.注册函数
    val func = (str:String) => str.length
    spark.udf.register("func", func)
    spark.udf.register("func1", func1 _)

    spark.sql(
      s"""
         |select name, func(name), func1(name)
         |from log
       """.stripMargin).show()


    def func1(str:String) ={
      if(str == null) {
        0
      }else{
        str.length
      }
    }

  }

}
