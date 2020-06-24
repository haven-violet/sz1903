package com.baidu.day17

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}


/**
  * @Author liaojincheng
  * @Date 2020/5/26 0:17
  * @Version 1.0
  * @Description
  */
object home17 {
  def main(args: Array[String]): Unit = {

    val spark = new SparkSession.Builder()
      .appName("homework")
      .master("local")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    val sql1 =
      """
        |create table t1 using org.apache.spark.sql.json
        |options(path"F:\\table1.json")
      """.stripMargin
    spark.sql(sql1)

    val sql2 =
      """
        |create table t2 using org.apache.spark.sql.json
        |options(path"F:\\table2.json")
      """.stripMargin
    spark.sql(sql2)

    spark.sql("show tables").show()

    val sql =
      """
        |select A, B, (D+E) as sum
        |from t1 join t2 on t1.C = t2.C
      """.stripMargin
    spark.sql(sql).show()
  }
}


