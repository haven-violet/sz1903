package com.baidu.day18

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
  * @Author liaojincheng
  * @Date 2020/5/26 11:00
  * @Version 1.0
  * @Description
  */
object _02SparkSQLFuncTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("test")
      .master("local")
      .getOrCreate()

    // 模拟数据 日期  金额  用户ID
    val userLog = Array(
      "2020-05-26,55,1122",
      "2020-05-27,58,1123",
      "2020-05-26,88",
      "2020-05-27,100,1122",
      "2020-05-26,51,1144",
      "2020-05-27,55",
      "2020-05-26,50,1122",
      "2020-05-27,40,1140"
    )
    // 进行有效销售日志（保证日志中必须要有用户ID）的统计
    // 统计每日的销售额，使用DSL来编写
    //1.先过滤 2.挨个处理返回样例类 3.分组聚合count （DSL）
    val rdd = spark.sparkContext.parallelize(userLog)
    val words = rdd.filter(t => {
      if (t.split(",").length >= 3) true else false
    })
      .map(t => {
        UserLog(t.split(",")(0), t.split(",")(1).toInt)
      })
    import spark.implicits._
    val df = words.toDF()
    df.groupBy("time").agg(sum('money)).show()
  }
}
case class UserLog(time: String, money: Int)