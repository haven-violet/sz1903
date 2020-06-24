package com.baidu.day30

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
/**
  * @Author liaojincheng
  * @Date 2020/6/13 9:33
  * @Version 1.0
  * @Description
  * SqL整合Streaming程序
  * 案例: 实时统计热门商品, 分类TopN
  * 数据格式:
  * id brand category
  * 001 lining 123
  * 002 huawei 456
  * 002 huawei 477
  * 003 geli 789
  * 分析:
  *   如果是sparkcore 或者SQL来统计, 这个题很简单, 其实就是分组取TopN,但是现在用的是实时统计
  *   使用updateStateByKey来完成
  *
  */
object _03SparkSqlOnSparkStreamingOps {
  def main(args: Array[String]): Unit = {
    //创建执行入口
    val spark = SparkSession
      .builder()
      .appName("TopN")
      .master("local[2]")
      /*
      加载配置,使用背压机制,动态调节每次批次拉取数据的条数,这样保证程序不被数据搞崩
      系统接收速度和系统处理速度一样快
      spark.streaming.receiver.maxRate 老版本设置的每秒拉取的条数
      新版本 设置 spark.streaming.backpressure.enable true 背压(反压)机制
      如果背压机制不起作用，此时需要我们调整并行度，或者对kafka进行动态扩容
      或者实在不行，将内部缓存或者持久化全部抛弃，释放资源
       */
//      .config(new SparkConf().set("spark.streaming.backpressure.enable", "true"))
      .getOrCreate()

    val ssc = new StreamingContext(spark.sparkContext, Seconds(5))
    //updateStateByKey需要设置检查点
    ssc.checkpoint("file:///F:\\check") //设置本地文件存储位置
    //获取输入流
    val socketDS: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop201", 6666)

    //处理数据
    val words: DStream[(String, Int)] = socketDS.map(line => {
      val str = line.split("\\s")
      if (str == null || str.length != 3) {
        ("", -1)
      } else {
        val brand = str(1)
        val category = str(2)
        (s"${brand}|${category}", 1)
      }
      //filter(_._2 != -1)
    }).filter{case (categoryAndBrand, count) => count != -1}
    //updateStateByKey实时更新批次
    //updateFunc: (Seq[V], Option[S]) => Option[S],
    val usb: DStream[(String, Int)] = words.updateStateByKey[Int](
      (seq: Seq[Int], option: Option[Int]) => Option(seq.sum + option.getOrElse(0))
    )
    //    usb.print()
    /*
    已经统计截止到目前为止的不同分类的各个商品销售量,只需要进行分组TopN求解即可
    使用SparkSql来完成这个工作
    todo 分组要将DStream转换成rdd才行, transform(返回一个新的RDD)方法和foreachRDD方法(直接输出RDD)
     */
    usb.foreachRDD((rdd, bTime) => {
      println("------------")
      println(s"Time: ${bTime}")
      import spark.implicits._
      //rdd: brand | category, count
      val df: DataFrame = rdd.map {
        case (categoryAndBrand, count) => {
          val category = categoryAndBrand.split("\\|")(0)
          val brand = categoryAndBrand.split("\\|")(1)
          (category, brand, count)
        }
      }.toDF("category", "brand", "counts")
      //写SQL前提要有表, 注册临时视图
      df.createOrReplaceTempView("log")
      //执行Sql
      val sql =
        s"""
           |select
           |  temp.*
           |from
           |(
           |  select
           |    category,
           |    brand,
           |    counts,
           |    row_number() over(partition by category order by counts desc) as rank
           |  from log
           |) temp
           |where temp.rank < 3
         """.stripMargin
      //输出数据结果
      spark.sql(sql).show()
    })

    ssc.start()
    ssc.awaitTermination()
  }
}
