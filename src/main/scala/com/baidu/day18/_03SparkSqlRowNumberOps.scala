package com.baidu.day18

import org.apache.spark.sql.SparkSession

/**
  * @Author liaojincheng
  * @Date 2020/5/26 21:55
  * @Version 1.0
  * @Description
  * 开窗函数
  * 统计每个学科的老师排名-> 降序
  */
object _03SparkSqlRowNumberOps {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local")
      .getOrCreate()
    //todo 1.先通过rdd进行处理和样例类进行对接,装载好元数据,在转换成DF
    val rdd = spark.sparkContext.textFile("F:\\教学视频\\千峰大数据\\电商数仓\\day31\\资料\\teacher.txt")
    val teacherRDD = rdd.map(t => {
      teacher(t.split("\\s")(0), t.split("\\s")(1))
    })
    import spark.implicits._
    val df = teacherRDD.toDF()
    //todo 2.按照subject,name分组统计count(*) ==> teacherDF
    //todo 使用反射创建的DF, 接下来使用sql操作方式
    //<1> 先要创建视图
    df.createOrReplaceTempView("teacher")

    val teacherDF = spark.sql(
      s"""
        |select subject, name, count(*) counts
        |from teacher
        |group by subject, name
       """.stripMargin)
    teacherDF.createOrReplaceTempView("tempTeacher")

    //todo 3.开窗函数, partition by subject order by counts
    // row_number相同按照出现顺序
    // rank相同空位
    // dense_rank相同不空位
    spark.sql(
      s"""
         |select *
         |from
         |(
         |  select
         |    *, row_number() over(partition by subject sort by counts) as rank
         |  from tempTeacher
         |) temp
         |where rank < 2
       """.stripMargin).show()

  }
}

case class teacher(subject:String, name:String)

