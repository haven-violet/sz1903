package com.baidu.day26

import java.util

import javax.print.attribute.HashAttributeSet
import redis.clients.jedis.{HostAndPort, JedisCluster}

/**
  * @Author liaojincheng
  * @Date 2020/6/9 0:43
  * @Version 1.0
  * @Description
  *             连接集群
  */
object JedisClusterOps {
  def main(args: Array[String]): Unit = {
    //加载配置
    //创建集群连接
    val nodes = new util.HashSet[HostAndPort]()
    nodes.add(new HostAndPort("192.168.25.201", 7001))
    nodes.add(new HostAndPort("192.168.25.201", 7002))
    nodes.add(new HostAndPort("192.168.25.201", 7003))
    val cluster: JedisCluster = new JedisCluster(nodes)
    //获取数据
    println(cluster.get("b"))
    //关闭数据
    cluster.close()
  }
}
