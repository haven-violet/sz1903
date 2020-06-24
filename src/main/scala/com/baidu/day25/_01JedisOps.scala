package com.baidu.day25

import redis.clients.jedis.Jedis

/**
  * @Author liaojincheng
  * @Date 2020/6/5 8:36
  * @Version 1.0
  * @Description
  */
object _01JedisOps {
  def main(args: Array[String]): Unit = {
    //创建jedis的连接
    val jedis = new Jedis("hadoop201", 6379)
    //测试连接 发送ping 返回pong
    //NOAUTH Authentication required.
    jedis.auth("hadoop")
    println(jedis.ping())
    jedis.set("a", "123")
    println(jedis.get("a"))
  }
}
