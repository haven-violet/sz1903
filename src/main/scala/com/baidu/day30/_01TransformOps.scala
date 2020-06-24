package com.baidu.day30

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @Author liaojincheng
  * @Date 2020/6/12 19:41
  * @Version 1.0
  * @Description
  * DStream的流操作(Transform)
  * 内部就是RDD转换操作
  * 案例: 动态过滤黑名单
  * 用户对我们的网站上的广告进行点击,每点击一次就要进行实时计费,点一次算一次钱
  * 但是,对于那些帮组某些无良商家刷广告的人,那么我们就有一个黑名单
  * 只要是黑名单中的用户就过滤掉
  */
object _01TransformOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("transform").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(3))
    //设置黑名单
    val blackList = Array(("zhangsan", true), ("lisi", true))
    //创建RDD
    val rdd: RDD[(String, Boolean)] = ssc.sparkContext.parallelize(blackList)
    //获取输入流
    val socketData: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop201", 6666)
    //接下来根据黑名单规则来过滤数据
    val users: DStream[(String, String)] = socketData.map(line => (line.split(" ")(1), line))
    //transform应用场景,比较适合在业务处理的过程中,使用RDD进行交互操作(业务的过程调用RDD算子)
    val transformDS: DStream[String] = users.transform(user => {
      //根据规则过滤出黑名单数据
      val joinRDD: RDD[(String, (String, Option[Boolean]))] = user.leftOuterJoin(rdd)
      val filterRdd = joinRDD.filter(rdd => {
        if (rdd._2._2.getOrElse(false)) {
          false           //filter里面为false,就会被过滤掉
        } else {
          true            //filter里面为true,就会留下来
        }
      })
      //我们将黑名单过滤后,处理白名单数据
      val userName = filterRdd.map(_._2._1)
      userName
    })
    transformDS.print()
    //比较适合使用输出操作(是RDD的输出操作)
    users.foreachRDD(user => {
      //根据规则过滤出黑名单数据
      val joinRDD: RDD[(String, (String, Option[Boolean]))] = user.leftOuterJoin(rdd)
      val filterRdd: RDD[(String, (String, Option[Boolean]))] = joinRDD.filter(rdd => {
        if (rdd._2._2.getOrElse(false)) {
          false
        } else {
          true
        }
      })
      //我们将黑名单过滤后,处理白名单
      val userName = filterRdd.map(_._2._1)
      userName.foreach(println)
    })
    //启动程序
    ssc.start()
    ssc.awaitTermination()
  }
}
