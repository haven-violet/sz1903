package com.baidu.day13

import java.io.File
import java.net.URL

import org.apache.spark.{Partitioner, SparkConf, SparkContext}

import scala.collection.mutable

/**
  * @Author liaojincheng
  * @Date 2020/5/20 9:26
  * @Version 1.0
  * @Description
  * 学科统计案例
  */
object _06PartitionOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local")
    val sc = new SparkContext(conf)

    //读取数据文件
    val lines = sc.textFile("F:\\教学视频\\千峰大数据\\电商数仓\\day26\\资料\\access.txt",5 )
    val word = lines.map(t => {
      val url = new URL(t.split("\t")(1)) //获取url
      val host = url.getHost
      //截取学科名称
      val str = host.substring(0, host.indexOf("."))
      (str,1)
    })
    //获取数据的key,为了在自定义分区中使用
    val subject: Array[String] = word.keys.distinct.collect()
    //创建自定义分区
    val sub = new SubjectPar(subject)
    //加入自定义分区
//    val reduce = word.reduceByKey(sub, _+_)
//    val reduce = word.reduceByKey(sub, _+_)
    val reduce = word.reduceByKey( _+_)
    deleteDirectory(new File("F:\\subject"))
    reduce.partitionBy(sub).saveAsTextFile("F:\\subject")
    //将数据存入本地
//    reduce.partitionBy(sub).saveAsTextFile("F:\\subject")
//    word.reduceByKey(_+_).saveAsTextFile("F:\\subject")


  }

  def deleteDirectory(file:File): Boolean = {
    //1.先判断该目录或者文件是否存在
    if(file.exists()){
      //2.判断该路径是文件？如果是,直接删除
      if(file.isFile()){
        return file.delete()
      }
      //3.说明file是目录,那么列出所有目录和文件
      val files = file.listFiles()
      for(f <- files){
        deleteDirectory(f)
//        println("xxxx")
      }
      //4.删除完本次file下所有目录和文件,在删除自己
      return file.delete()
    }else{
      println("该目录或者文件不存在")
      return false
    }
  }
}

/**
  * 继承自定义分区
  */
class SubjectPar(subject: Array[String]) extends Partitioner {
  //创建一个Map用来存储学科和分区号
  val hashMap = new mutable.HashMap[String, Int]()
  //定义一个计数器,用来生成分区号
  var i = 0
  for(s <- subject){
    //存入学科和分区
    hashMap.put(s, i)
    i += 1 //自增
  }

  //分区数(总的数量)
  override def numPartitions: Int = subject.size

  //获取key对应的分区号
  override def getPartition(key: Any): Int = hashMap.getOrElse(key.toString, 0)
}
