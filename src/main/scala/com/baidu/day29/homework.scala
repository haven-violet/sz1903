package com.baidu.day29

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * @Author liaojincheng
  * @Date 2020/6/12 15:56
  * @Version 1.0
  * @Description
  */
object homework {
  def main(args: Array[String]): Unit = {
    /**
      * 1.sparkCore 主要是RDD   （sparkConf, sparkContext）  textFile返回值为RDD
      * 2.sparkSQl  主要是RDD + DataFrame + DataSet    （sparkSession）  read返回值为DF
      */
    val spark = SparkSession.builder()
      .appName("homework")
      .master("local[2]")
      .getOrCreate()
    val lagouRDD: RDD[String] = spark.sparkContext.textFile("file:///F:\\教学视频\\千峰大数据\\练习题\\Test_001\\lagou.txt")

    import spark.implicits._
    val df = lagouRDD.map(t => {
      val str = t.split("\\^")
      Person(str(0).toLong, str(1), str(2), str(3), str(4), str(5), str(6), str(7), str(8), str(9))
    }).toDF()

    //创建临时视图
    df.createOrReplaceTempView("lagou")
    //1、求出每个不同地点的招聘数量，输出需求量最大的10个地方，以及岗位需求量
//    spark.sql(
//      s"""
//         |select
//         |  temp.*
//         |from
//         |(
//         |  select
//         |    addr,
//         |    count(1) as sum
//         |  from lagou
//         |  group by addr
//         |) temp
//         |order by temp.sum desc limit 10
//       """.stripMargin).show()

    spark.sql(
      s"""
         |with log1 as(
         |  select
         |    addr,
         |    count(1) as sum
         |  from lagou
         |  group by addr
         |),
         |select
         |  log1.*
         |from log1
         |order by sum desc limit 10
       """.stripMargin).show()
    //2、求出大数据类工作（job字段包含"大数据"，"hadoop"，"spark"就算大数据类工作）岗位对学历的要求，不同学历的需求量和占比




  }
}

//1	Java高级	北苑		信息安全,大数据,架构	闲徕互娱	20k-40k		本科	经验5-10年	游戏		不需要融资
//id	job		addr		tag			company		salary		edu	exp		type1		level
case class Person(id:Long, job:String, addr:String, tag:String, company:String, salary:String, edu:String, exp:String, type1:String, level:String)