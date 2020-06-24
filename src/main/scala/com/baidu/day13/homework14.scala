package com.baidu.day13

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * @Author liaojincheng
  * @Date 2020/5/21 20:58
  * @Version 1.0
  * @Description
  * 需求:
  *       1.通过搜狗Log进行分析,统计在每小时热门类别Top10
  *       2.需要使用自定义分区
  *       3.将字典文件广播
  *       4.把数据存储到MySQL和HDFS各一份
  * 解题思路:
  * 1.首先要进行广播的数据是不变的数据,固定了的,就是ID映射关系表ID.txt,IDInfo
  * 2.解析Log日志数据,(时间, id号)
  * 3.IDInfo表数据结构应该为collectAsMap,HashMap类型,遍历Log日志数据的时候可以使用id号来get()对应的中文名称
  *   返回类型应为((时间, id中文名称), 1)
  *   而后进行reduceByKey进行聚合统计 ==》 计算出了每小时中每个类别总数
  *   最后结果为LogInfo
  * 4.LogInfo后,需要按照小时进行分组,取出前10个  (小时, ((小时, id中文名称), 总数)...)
  * 5.按照小时进行自定义分区存储到HDFS,而后使用sqoop导入到mysql中
  */
object homework14 {
  def main(args: Array[String]): Unit = {
//    00:00:00	8561366108033201	[汶川地震原因]	3 2	www.big38.net/
//    3 图片
//    F:\教学视频\千峰大数据\电商数仓\day27\资料\作业\SogouQ.txt
//    F:\教学视频\千峰大数据\电商数仓\day27\资料\作业\ID.txt
    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local")
    val sc = new SparkContext(conf)
    //1.处理搜狗日志数据
    val logRDD = sc.textFile("F:\\教学视频\\千峰大数据\\电商数仓\\day27\\资料\\作业\\SogouQ.txt")
    val log = logRDD.map(t => {
      val str = t.split("\\s") //切分空格, \t
      (str(0).split(":")(0), str(4))
    })
    //2.处理ID.txt
    val IDRDD = sc.textFile("F:\\教学视频\\千峰大数据\\电商数仓\\day27\\资料\\作业\\ID.txt")
    val ID = IDRDD.map(t => {
      val str = t.split("\\s")
      (str(0), str(1))
    }).collectAsMap()
    //3.对ID进行广播,因为要在传递给算子内部,会进行传输,所以使用广播变量,给一份给Excutor
    val IDBroad = sc.broadcast(ID)
    //4.对搜狗日志进行和IDBroad进行连接获取id中文名称,((时间,id中文名称), 1); 而后进行reduceByKey操作
    //  此时在进行自定义分区操作,按照小时进行分区 ==> 需要先创建自定义分区了
    //Iterator[]不能做什么,需要转换成toList,才能进行distinct

    val list = log.map(_._1).collect().toList.distinct
    val partition = new SougouPartition(list)
    val logSum = log.map(t => {
      val idName = IDBroad.value.getOrElse(t._2, "其他")
      ((t._1, idName), 1)
    }).reduceByKey(partition, _ + _)
    //5.对logSum按照小时进行分组
    val logGroupBy: RDD[(String, Iterable[((String, String), Int)])] = logSum.groupBy(_._1._1).cache()
    logGroupBy.map(t => {
      val word = t._2.toList.sortBy(-_._2).take(10)
      (t._1, word)
    }).saveAsTextFile("hdfs://hadoop201:9000/log004")
  }
}

class SougouPartition(time: List[String]) extends Partitioner {
  //创建一个map来储存小时和分区号
  private val map = new mutable.HashMap[String, Int]()
  var i = 0
  for(s <- time){
    map.put(s, i)
    i += 1
  }
  //定义分区总数
  override def numPartitions: Int = time.size
  //获取key对应的分区号
  override def getPartition(key: Any): Int = map.getOrElse(key.toString, 0)
}

