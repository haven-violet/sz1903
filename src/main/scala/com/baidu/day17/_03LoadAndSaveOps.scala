package com.baidu.day17

import java.util.Properties

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * @Author liaojincheng
  * @Date 2020/5/25 22:22
  * @Version 1.0
  * @Description
  *             数据的加载和落地
  *             load: 读取
  *             save: 存入
  */
object _03LoadAndSaveOps {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("loadAndSave")
      .master("local")
      .getOrCreate()
    //读取
//    loadData(spark)
    saveData(spark)
  }

  def loadData(spark: SparkSession) = {
    import spark.implicits._
    //如果使用read.load方式来加载文件,那么它默认的读取格式只要一种,Parquet格式
    var df = spark.read.load("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\sql\\users.parquet")
//    df = spark.read.format("json").load("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\sql\\account.json")
//    df = spark.read.json("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\sql\\account.json")
//    df = spark.read.csv("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\sql\\country.csv")

//    df.toDF("id", "country", "code").show()
    /**
      * 加载非","分割的csv文件怎么办?
      * 指定分隔符的时候,怎么指定?
      * 想让第一行数据为字段的名字,怎么做?
      */
//    df = spark.read
//        .option("sep", "|") //这只读取文件时候的相关配置,切分的字符,参考csv源码注释即可
//        .option("header", "true") //header设置为true,则意味着文件的第一行为表头
//        .csv("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\sql\\location-info.csv")
    //orc是一种列式二进制存储文件,是rc格式的升级版,主要是hive存储格式
//    df = spark.read.orc("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\sql\\student.orc")
    //txt文件格式
    df = spark.read.text("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\sql\\topn.txt")
    df.show()
    //jdbc配置
    val url = "jdbc:mysql://localhost:3306/spark"
    val tab = "spark_info"
    val prop = new Properties()
    prop.setProperty("user", "root")
    prop.setProperty("password", "root")
    //读取Mysql的jdbc数据库
    df = spark.read.jdbc(url, tab, prop)
    df.show()
  }

  /**
    * 我们在进行导入的时候注意: 需要有DF才可以写入数据(有读才有写)
    * @param spark
    */
  def saveData(spark: SparkSession) = {
    val df = spark.read.json("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\sql\\account.json")
    //写入数据 默认的情况下写入的格式是parquet格式,并且内部实现默认的压缩方式是snappy压缩
//    df.write.save("F:\\texst5201")

    /**
      * SaveMode:
      *   Overwrite: 覆盖(删除原来的内容，重建)
      *   ErrorIfExists(默认): 如果目录存在,就报错
      *   Ignore: 忽略(如果已经存在则忽略,如果不存在就创建)
      *   Append: 追加
      */
//    df.write.mode(SaveMode.Overwrite).save("F:\\test5201")
//    df.write.mode(SaveMode.Append).save("F:\\test5201")
    val url = "jdbc:mysql://localhost:3306/spark"
    val tab = "spark_info1"
    val prop = new Properties()
    prop.setProperty("user", "root")
    prop.setProperty("password", "root")
    //写入数据库
    //注意: 我们以后在操作数据库的存入时候,不要用Overwrite,推荐大家使用Append追加
    //同时也别用 Spark来创建库, 这样的话,类型都是固定的
    df.write.mode(SaveMode.Append).jdbc(url, tab, prop)
//    df.write.option("header", "true").csv("F:\\test8989")
  }
}

