package com.baidu.day16

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * @Author liaojincheng
  * @Date 2020/5/22 22:53
  * @Version 1.0
  * @Description
  * 构建DF 第一种方式
  * 反射方式
  * 作业: 我写Scala代码的,你写Java
  */
object _02CreateDataFrameOps {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getName)
      .master("local")
      .getOrCreate()
    //获取数据信息
    val lines = spark.sparkContext.textFile("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\students.txt")

    val words = lines.map(t => { //数据处理
      val str = t.split(",")
      (str(0).toInt, str(1), str(2).toInt)
    })

    val words1 = lines.map(t => { //数据处理
      val str = t.split(",")
      student(str(0).toInt, str(1), str(2).toInt)
    })


    /**
      * 反射操作有两种:
      *   1.toDF --> 这种方式也可以加载类,也可以不加载类,那么不加载就不会有表头字段名字,
      *     但是可以自行添加,比较方便,推荐使用这种,或者有名字的表头字段,那么我们也可以重新修改
      *   2.createDataFrame --> 这种方式也是常见的创建DF的方式,相比较toDF来说,其实内部是一样的,
      *     如果我们传入的RDD有类型参数,那么就会将类型参数当做表头,如果没有就用默认值
      *   注意: 如果想用toDF这种操作,那么你要导入Spark隐式转换
      */
    //导入隐式转换操作

    //1.createDataFrame()
    val df: DataFrame = spark.createDataFrame(words)
//    val df = spark.createDataFrame(words1) 加上了student样例类,会以样例类参数为名

    //2.toDF,需要导入隐式转换 import spark.implicits._
    import spark.implicits._
    val df1: DataFrame = words1.toDF("ids", "names", "ages")

    //如果后续有操作,那么注意要先注册临时表视图
    df.createOrReplaceTempView("log")
    //为df1创建临时视图
    df1.createOrReplaceTempView("log1")
    //查询年龄小于18的学员
    val sql =
      """
        |select
        | *
        |from log
      """.stripMargin

    val sql1 =
      """
        |select
        | *
        |from log1
      """.stripMargin

    spark.sql(sql).show()
    spark.sql(sql1).show()

  }
}

case class student(id:Int, name:String, age:Int)