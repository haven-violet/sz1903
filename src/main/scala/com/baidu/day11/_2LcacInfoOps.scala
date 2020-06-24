package com.baidu.day11

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/5/18 8:14
  * @Version 1.0
  * @Description
  * 基站案例
  * 需求: 在一定时间内,求用户在所有基站内停留的时间,求TopN
  */
object _2LacInfoOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("lac").setMaster("local")
    val sc = new SparkContext(conf)
    //获取用户访问基站数据
    val lines: RDD[String] = sc.textFile("F:\\教学视频\\千峰大数据\\电商数仓\\day24\\资料\\lacduration\\log")
    //处理用户数据(切分)
    val userLacData = lines.map(line => {
      val arr: Array[String] = line.split(",")
      val phone = arr(0) //手机号
      val time = arr(1).toLong //时间
      val lac = arr(2) //基站ID
      val eventType = arr(3) //事件类型
      //获取时间差
      val time_long = if (eventType == 1) -time else time
      ((phone, lac), time_long)
    })
    //计算停留时长
    val userLacTime = userLacData.reduceByKey(_+_)

    //获取基站经纬度
    val lacInfo = sc.textFile("F:\\教学视频\\千峰大数据\\电商数仓\\day24\\资料\\lacduration\\lac_info.txt")
    //切分数据
    val lacXY = lacInfo.map(line => {
      val arr = line.split(",")
      val lac = arr(0) //基站ID
      val x = arr(1) //经度
      val y = arr(2) //维度
      (lac, (x, y))
    })
    //修改用户的数据结构
    val upUserLac: RDD[(String, (String, Long))] = userLacTime.map(line => {
      (line._1._2, (line._1._1, line._2))
    })
    //join数据
    val joinUser: RDD[(String, ((String, Long), (String, String)))] = upUserLac.join(lacXY)
    //排序取TopN
    val upUserRes = joinUser.map(x => {
      val phone = x._2._1._1 //手机号
      val time = x._2._1._2 //停留时长
      val xy = x._2._2 //经纬度
      (phone, time, xy)
    })

    //按照手机号分组
    val groupUser = upUserRes.groupBy(_._1)
    //排序
    val result = groupUser.mapValues(_.toList.sortBy(_._2).reverse.take(2))
    result.foreach(println)
  }
}
