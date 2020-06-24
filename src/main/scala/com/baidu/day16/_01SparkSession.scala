package com.baidu.day16

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext, sql}
import org.apache.spark.sql.{Column, DataFrame, SQLContext, SparkSession}

/**
  * @Author liaojincheng
  * @Date 2020/5/22 14:16
  * @Version 1.0
  * @Description
  * Spark Sql中的编程入口:
  *   2.0以前的入口
  *   SQLContext
  *   HiveContext extends SQLContext
  *   这两者都需要依赖SparkContext
  * 在2.0以后对HiveContext和SQLContext进行了整合统一
  *   SparkSession
  */
object _01SparkSession {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder
      .appName(this.getClass.getName)
      .master("local")
      //      .enableHiveSupport() //开启支持hive机制
      .getOrCreate()

    //    val sc = new SparkContext(new SparkConf())
    //    new SQLContext(sc)
    //    new HiveContext(sc)
        //加载外部数据,得到编程模型 --DateFrame/DataSet(相当于RDD)
    val df: DataFrame = spark.read.json("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\sql\\people.json")
    //df.show() //查看文件内容
    //查看数据的元数据信息
    //schema元数据内容
    //schema信息包括 列名,数据类型,是否为null
//    df.printSchema()
    //类似于select * from table 默认是打印20条数据的,但是可以修改
    //df.show(5)
    println("---1.条件操作---")
    //只显示人名和年龄 select name,age from xxx
//    df.select("name", "age").show()
//    df.select(new Column("name"), new Column("age")).show()
    //源码中给我们一个例子 ds.select($"colA", $"colB"+1)
    //但是我们使用为什么报错呢? 注意: 需要加上隐式转换
//    import spark.implicits._
//    df.select($"name", $"age").show()
    println("----2.列的基本运算----")
    //给每一个人的年龄加上一个10 select name, age+10 from xxx
//    df.select(
//      new Column("name"),
//      new Column("age").+(10).as("age_plus_10")
//    ).show()
//    import spark.implicits._
//    df.select($"name", ($"age" + 10).as("age1_plus_10")).show()
    println("---3.条件查询---")
    //查询年龄在13-25之间的人的信息
    //select name,age,height,province from xxx where age between 13 and 25
    //DSL风格 domain special language 特定领域语言 这种写法非常非常重要,我们必须要掌握
    import spark.implicits._
    df.select("name", "age", "height", "province")
//      .where(new Column("age").between(13,25))
//      .where($"age between 13 and 25")//编译不能通过
      .where($"age">=13 and($"age"<=25) and($"name" like("%om%")))
      .show()

    println("---4.统计---")
    //统计一个省份的人数
    //select province count(1) as counts from xxx group by province
    df.select("province")
      .groupBy("province")
      .count()
      .show()

    println("---5.使用SQL完成上述的操作---")
    //首先要创建一个临时表
    //df.registerTempTable("pop") //已过时,是1.6版本时候的构建方式
    /**
      * 2.0版本以后 不用上面的构建方式了 使用新的API
      * df.createTempView()
      * df.createGlobalTempView()
      * df.createOrReplaceTempView()
      * df.createOrReplaceGlobalTempView()
      * 上述四种情况都可以创建临时表(临时视图),功能如下
      * Global:
      *   有Global: 在整个application的生命周期内有效
      *   无Global: 旨在当前SparkSession中有效,因为一个应用中可以创建多个SparkSession
      * Replace
      *   无Replace: 如果创建的当前临时表已经存在了会报错
      *   有Replace: 如果创建的当前临时表已经存在了会覆盖
      */
    df.createOrReplaceTempView("pop")

    //执行SQL语句
    val sql =
      """
        |select
        | province,
        | count(1) as counts
        |from pop
        |group by province
      """.stripMargin
    spark.sql(sql).show()
  }
}
