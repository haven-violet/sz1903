package com.baidu.day17

import java.util.Properties

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * @Author liaojincheng
  * @Date 2020/5/26 8:26
  * @Version 1.0
  * @Description
  * 使用SparkSql来操作Hive
  */
object _04SparkSqlOnHiveOps {
  def main(args: Array[String]): Unit = {
    //todo 这是解决权限问题
    System.setProperty("HADOOP_USER_NAME", "hadoop")
    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local")
      .enableHiveSupport() //开启hive机制
      .getOrCreate()
    //如果我们开启了hive的机制后,并且导入了相关的xml文件,可能会有一个小小的问题
    //之前读取的本地磁盘文件,就突然找不到了,为什么?因为我们使用了hive,那么它默认会
    //搜索hdfs上的数据文件,所以本地磁盘会找不到文件,这个问题很好解决,在路径前面
    //加上file:///D:\\table1.json
    //如果不好使,那么你把连接hive的配置文件干掉,因为现在不需要用它

    //TODO wordCount案例,使用sql来操作
    /**
      * F:\教学视频\千峰大数据\电商数仓\day29-SparkSQL\资料\students.txt
      * 将该文件读取,因为是txt会是一个value类型,一个列;需要使用侧展操作explode
      * later view 一般会配合 explode 使用和原来表进行关联;
      * 1.explode炸开
      * 2.group by
      * 3.聚合count
      */
    val df = spark.read.text("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\students.txt")
    df.printSchema()
    df.show()
    df.createOrReplaceTempView("student")

    spark.sql(
      s"""
         |select word, count(1) as counts
         |from
         |  (select explode(split(value, ",")) as word
         |  from student) temp
         |group by word
         |order by counts desc
       """.stripMargin).show()

    //TODO sparkSQL可以将 mysql中的数据传输到hive中
    //spark.sql("select * from spark.stu").show()
//    val url = "jdbc:mysql://localhost:3306/spark"
//    val tab = "stu"
//    val prop = new Properties()
//    prop.setProperty("user", "root")
//    prop.setProperty("password", "root")
//    //todo 读取mysql数据
//    val df = spark.read.jdbc(url, tab, prop)
//    //todo 将mysql数据写入到hive中
//    // todo 权限问题 System.setProperty("HADOOP_USER_NAME", useranme) 完成了sqoop工作
//    df.write.insertInto("spark.stu")

  }
}
