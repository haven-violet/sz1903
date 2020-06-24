package com.baidu.day18

import org.apache.spark.sql.{Dataset, SparkSession}

/**
  * @Author liaojincheng
  * @Date 2020/5/26 23:14
  * @Version 1.0
  * @Description
  */
object _05SparkSQLUDAFOps {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local")
      .getOrCreate()
    val list = List(
      Score(1, "张三", 90.6f),
      Score(1, "张三", 80.6f),
      Score(1, "张三", 55.6f),
      Score(2, "李四", 99.9f),
      Score(2, "李四", 80.9f),
      Score(2, "李四", 20.9f)
    )

    //todo 1.定义udaf函数
    //     2.注册udaf函数
    spark.udf.register("myAvg", new MyAvgUDAF)
    spark.udf.register("mySum", new MySumUDAF)


    //todo 构建DS
    import spark.implicits._
    val ds: Dataset[Score] = list.toDS()
    //todo 注册临时视图
    ds.createOrReplaceTempView("scores")

    spark.sql(
      s"""
         |select
         |  name,
         |  round(sum(score)  ,1) as sumScore,
         |  round(avg(score)  ,1) as avgScore,
         |  round(myAvg(score),1)  as avgScore1,
         |  mySum(score) as sumScore1
         |from scores
         |group by name
       """.stripMargin).show()
  }
}

case class Score(id:Int, name:String, score:Float)