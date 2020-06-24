package com.baidu.day18

import org.apache.spark.sql.SparkSession

/**
  * @Author liaojincheng
  * @Date 2020/5/27 0:07
  * @Version 1.0
  * @Description
  */
object home1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    //TODO 1. 求出未婚和已婚各自身高最高的两位老师
    spark.sql(
      s"""
         |select
         |  *
         |from
         |(
         |  select
         |   *, row_number() over(partition by married sort by height desc) rank
         |  from spark.teacher
         |) temp
         |where rank <= 2
       """.stripMargin).show()

    //TODO 2. 求出每一个单词的次数
    spark.sql(
      s"""
         |select
         | word, count(word) as sum
         |from
         |(
         |  select
         |    explode(split(line, " ")) word
         |  from spark.test
         |) temp
         |group by word
       """.stripMargin).show()
  }
}
