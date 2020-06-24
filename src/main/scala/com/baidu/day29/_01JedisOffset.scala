package com.baidu.day29

import java.util

import org.apache.kafka.common.TopicPartition
import redis.clients.jedis.Jedis

/**
  * @Author liaojincheng
  * @Date 2020/6/11 8:34
  * @Version 1.0
  * @Description
  *             获取offset
  */
object _01JedisOffset {
  //如果使用Object对象来做类型传参,那么我们要用到Apply,不然Object不能传参
  def apply(
           jedis:Jedis,
           groupId:String
           ):Map[TopicPartition, Long] = {
    var fromOffset = Map[TopicPartition, Long]()
    //获取jedis连接,查询redis中的所有topic,partition,offset
    val topicPartitionOffset: util.Map[String, String] = jedis.hgetAll(groupId)
    //导入scala集合转换包
    import scala.collection.JavaConversions._
    //将Map集合转换成List
    val list: List[(String, String)] = topicPartitionOffset.toList
    //循环处理所有的topic Partition对应的Offset
    for(topicPF <- list){
      val arr: Array[String] = topicPF._1.split("_") //topic+"_"+partition
      //将数据加载到fromOffset
      fromOffset += (new TopicPartition(arr(0), arr(1).toInt) -> topicPF._2.toLong)
    }
    fromOffset
  }
}
