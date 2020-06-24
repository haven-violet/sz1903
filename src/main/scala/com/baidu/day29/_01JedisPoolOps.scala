package com.baidu.day29

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.{Jedis, JedisPool}

/**
  * @Author liaojincheng
  * @Date 2020/6/11 8:59
  * @Version 1.0
  * @Description
  * 连接池测试
  */
object _01JedisPoolOps {
  //加载连接池的config默认配置(连接池总连接数、最大空闲连接、最小空闲连接)
  val config = new GenericObjectPoolConfig
  config.setMaxTotal(10)
  config.setMaxIdle(5)
  //创建JedisPool加载配置、host、port、连接超时时间、密码、数据库(数据不写默认选择)
  val pool = new JedisPool(config, "hadoop201", 6379, 10000, "hadoop")

  def getResource(): Jedis = {
    pool.getResource
  }
}
