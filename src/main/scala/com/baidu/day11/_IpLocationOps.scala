package com.baidu.day11

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/5/18 9:31
  * @Version 1.0
  * @Description
  * IP解析案例
  * 需求: 根据已知的两个文件(log日志文件,ip解析对照表),统计省份的排名(降序排名)
  */
object _03IpLocationOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("ip").setMaster("local")
    val sc = new SparkContext(conf)
    //读取IP解析数据文件
    val lines = sc.textFile("F:\\教学视频\\千峰大数据\\电商数仓\\day24\\资料\\ip.txt")
    //切分数据
    //获取IP解析的数据,不能直接使用RDD来传参处理,不然你不触发Action的话,是没有数据的
    val ipPro: Array[(Long, Long, String)] = lines.map(x => {
      val str = x.split("[|]")
      val startNum = str(2).toLong //十进制开始位置
      val endNum = str(3).toLong //十进制结束位置
      val pro = str(6) //省份
      (startNum, endNum, pro)
    }).collect()

    //处理原始日志数据
    val userLog = sc.textFile("F:\\教学视频\\千峰大数据\\电商数仓\\day24\\资料\\http.log")
    //处理用户log
    val accessPro: RDD[(String, Int)] = userLog.map(x => {
      val str = x.split("[|]")
      val ip = str(1) //IP
      //将IP转换成十进制
      val ipNum = ip2Long(ip)
      //使用二分法进行查询
      val index = QueryIp(ipPro, ipNum)
      var province = "未知"
      //不能等于-1, -1代表没有找到
      if (index != -1) {
        province = ipPro(index)._3
      }
      (province, 1)
    })
    //聚合排序数据
    val reduce: RDD[(String, Int)] = accessPro.reduceByKey(_+_).sortBy(_._2, false)
    reduce.foreach(println)
  }



  /**
    * ip转换为十进制
    * @param ip
    * @return
    */
  def ip2Long(ip:String): Long = {
    var str = ip.split("[.]")
    var ipNum = 0L
    for(i <- 0 until str.length){
      ipNum = str(i).toLong | ipNum << 8L
    }
    ipNum
  }

  /**
    * 二分查找
    * @param lines
    * @param ip
    * @return
    */
  def QueryIp(lines:Array[(Long, Long, String)], ip:Long): Int = {
    var low = 0
    var high = lines.length - 1
    while(low <= high){
      var middle = (low + high)/2
      if(ip >= lines(middle)._1 && ip <= lines(middle)._2){
        return middle
      }
      if(ip < lines(middle)._1){
        high = middle - 1
      }
      if(ip > lines(middle)._2){
        low = middle + 1
      }
    }
    -1
  }
}
