package com.baidu.day25

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.{Jedis, JedisPool}

/**
  * @Author liaojincheng
  * @Date 2020/6/5 8:45
  * @Version 1.0
  * @Description 连接池测试
  */
object _02JedisPoolOps {
  def main(args: Array[String]): Unit = {
    //加载连接池的config默认配置(连接池总连接、最大空闲连接、最小空闲连接)
    val config = new GenericObjectPoolConfig()
    config.setMaxTotal(10)
    config.setMaxIdle(5)
    //创建JedisPool 加载配置、host、port、连接超时时间、密码、数据库（数据库不写默认选择0）
    val pool = new JedisPool(config, "hadoop201", 6379, 10000, "hadoop")
    val jedis: Jedis = pool.getResource()
    //测试连接
    println(jedis.ping())
    println(jedis.get("a"))
    //关闭
    jedis.close()
    pool.close()
  }
}
