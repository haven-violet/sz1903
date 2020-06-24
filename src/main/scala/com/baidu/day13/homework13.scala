package com.baidu.day13

import java.text.SimpleDateFormat

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
  * @Author liaojincheng
  * @Date 2020/5/20 20:16
  * @Version 1.0
  * @Description
  * 1	08:45:56   sohu
  * 1	23:10:34   baidu
  * 2	11:08:23   google
  * 2	16:42:17   yahoo
  * 3	05:23:05   google
  * 3	12:09:11   baidu
  * 对数据排序结果如上,id升序,id相同按照时间排序升序
  * 这一题使用自定义排序来搞定,因为还是比较复杂的排序,常见的2次排序
  */
object homework13 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local")
    val sc = new SparkContext(conf)
    val lines = sc.textFile("hdfs://hadoop201:9000/homework/home520.txt")
    val words: RDD[(Int, Long, String)] = lines.map(t => {
      val str = t.split("\\s") //排除\t或者空格
      val id = str(0).toInt
      val time = new SimpleDateFormat("HH:mm:ss").parse(str(1)).getTime()
      val ip = str(2)
      (id, time, ip)
    })
    val sorted = words.sortBy(t => log(t._1, t._2))
    sorted.map(t => {
      (t._1, new SimpleDateFormat("HH:mm:ss").format(t._2), t._3)
    }).groupBy(_._1).take(1).foreach(println)

  }
}
case class log(id:Int, time:Long) extends Ordered[log] {
  override def compare(that: log): Int = {
    if(this.id == that.id){
      return (this.time - that.time).toInt
    }else{
      return this.id - that.id
    }
  }
}
