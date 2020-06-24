package com.baidu.day13

import java.sql.{Date, DriverManager, SQLException}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/5/20 8:22
  * @Version 1.0
  * @Description
  * Mysql  JDBC操作
  */
object _05MysqlJDBC {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local")
    val sc = new SparkContext(conf)
    //读取本地数据
    val lines = sc.textFile("F:\\test.txt")
    val reduceData: RDD[(String, Int)] = lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    //把数据结果存入Mysql数据
    //使用foreachPartition来操作各种connection
//    reduceData.foreach(x=>{
//      //foreach是一条一条数据处理的,那么我们可以想象一下,你要创建多少connection?
//      //一条数据就是一个connection连接,那么一亿条数据就是1亿个连接
//      getConnection(x)
//    })

    reduceData.foreachPartition(ite=>{
      //一个分区才会创建一个connection,大大减少了资源开销
      getConnection(ite)
    })

  }

  /*
  创建connection
   */
  def getConnection(rdd:Iterator[(String,Int)]): Unit = {
    //创建连接
    val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spark", "root", "root")
    val pstmt = conn.prepareStatement("insert into spark_info(word,count,date) values(?,?,?)")
    //写入数据
    try{
      rdd.foreach(data=>{
        pstmt.setString(1, data._1)
        pstmt.setInt(2, data._2)
        pstmt.setDate(3, new Date(System.currentTimeMillis()))
        val i = pstmt.executeUpdate()
        if(i>0) println("添加成功") else println("添加失败")
      })
    }catch {
      case e:SQLException => println(e.getMessage)
    }finally {
      if(pstmt != null){
        pstmt.close()
      }
      if(conn != null){
        conn.close()
      }
    }
  }


}
