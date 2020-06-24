package com.baidu.day25

import java.util

import redis.clients.jedis.{Jedis, ScanParams, ScanResult}

/**
  * @Author liaojincheng
  * @Date 2020/6/5 9:03
  * @Version 1.0
  * @Description
  * Redis数据类型
  *   String: 字符串
  *   List: 队列
  *   Hash: 散列
  *   Set: 集合
  *   Zset: 有序集合
  */
object _03JedisDataTypeOps {
  def main(args: Array[String]): Unit = {
    val jedis = new Jedis("hadoop201", 6379)
    jedis.auth("hadoop")
    //清空数据库
    jedis.flushDB()

    /**
      * 字符串API操作
      */
    jedis.set("bigdata", "db")
    println(jedis.get("bigdata"))
    //在原来key的value值基础上追加一个字符串,如果key不存在,则创建一个新Key
    println(jedis.append("bigdata", "test")) //打印的是长度
    println(jedis.get("bigdata"))
    //自增1, 等价于count++ 默认值为0
    jedis.incr("count")
    println(jedis.get("count"))
    //等价于 count = count + 10
    jedis.incrBy("count", 10)
    println(jedis.get("count"))
    //浮点数自增
    jedis.incrByFloat("double", 1.5)
    println(jedis.get("double"))
    //自减1 等价于 count--
    jedis.decr("count")
    jedis.decrBy("count", 10)
    println(jedis.get("count"))
    //获取key的值,并设置这个值
    jedis.getSet("count", "100")
    println(jedis.get("count"))
    //获取指定key范围的字符串,起始位置,结束位置
    println(jedis.getrange("bigdata", 0, 2))
    //替换
    jedis.setrange("bigdata", 0, "Go")
    println(jedis.get("bigdata"))
    //只有key不存在才生效,相当于set方法,如果存在就不生效
    jedis.setnx("counts", "200")
    println(jedis.get("counts"))
    //获取字符串长度
    println(jedis.strlen("bigdata"))
    //获取多个key的值
    val list: util.List[String] = jedis.mget("count", "bigdata")
    println(list)
    jedis.mset("abc", "456", "def", "789")
    //删除
    jedis.del("bigdata")
    println(jedis.exists("bigdata"))
    println(jedis.mget("abc", "def"))

    println("=================")
    /*
    Hash散列
     */
    //添加元素
    jedis.hset("user", "name", "zhangsan")
    jedis.hset("user", "age", "18")
    //获取指定元素
    println(jedis.hget("user", "name"))
    //获取所有元素
    println(jedis.hgetAll("user"))
    //判断某个key对应的字段是否存在
    println(jedis.hexists("user", "name"))
    //累加
    jedis.hincrBy("user", "age", 10)
    jedis.hincrByFloat("user", "height", 1.8)
    println(jedis.hget("user", "age"))
    println(jedis.hget("user", "height"))
    //获取key的字段数量
    println(jedis.hlen("user"))
    //获取多个字段的值
    println(jedis.hmget("user", "name", "age", "height"))
    //删除
    println(jedis.hdel("user", "name"))
    //获取所有的字段值
    println(jedis.hvals("user"))
    //查找key
    jedis.hset("users", "jsl", "1")
    jedis.hset("users", "java2", "2")
    jedis.hset("users", "abcjs3", "3")
    val scan = new ScanParams()
    //查找是否包含er的key
    val params: ScanParams = scan.`match`("*er*")
    val value: ScanResult[String] = jedis.scan("0", scan)
    println(value.getResult)
    println("===============")
    /*
    List队列操作
     */
    jedis.lpush("names", "zhangsan", "lisi", "wangwu")
    jedis.rpush("names", "zhaoliu", "tianqi")
    //获取长度
    println(jedis.llen("names"))
    //获取序列的元素(按照索引获取)
    println(jedis.lrange("names", 0, -1))
    //删除指定的元素
    jedis.lrem("names", 1, "zhangsan")
    println(jedis.lpop("names")) //从左边删除
    println(jedis.rpop("names")) //从右边删除
    println(jedis.lrange("names", 0, -1))
    //从左边插入元素
    jedis.lpushx("names", "wanger")
    jedis.rpushx("names", "laoba")
    println(jedis.lrange("names", 0, -1))
    //修改（替换）
    jedis.lset("names", 0, "222")
    println(jedis.lrange("names", 0, -1))
    //返回指定索引的元素
    println(jedis.lindex("names", 0))
    //截取并保持序列中指定范围的元素
    println(jedis.ltrim("names", 0, 2))
    println(jedis.lrange("names", 0, -1))

    /*
    set集合
    元素无序,不可重复
     */
    //添加元素
    jedis.sadd("hobbys", "吃", "喝", "玩", "乐")
    //查询元素个数
    println(jedis.scard("hobbys"))
    //求多个集合中的差集
    jedis.sadd("hobbys2", "吃")
    println(jedis.sdiff("hobbys", "hobbys2"))
    println(jedis.smembers("hobbys2"))
    //获取多个元素集合的差集,然后将结果放入新的集合
    println(jedis.sdiffstore("hobbys", "hobbys2"))
    println(jedis.smembers("hobbys2"))
    //求集合交集
    println(jedis.sinter("hobbys", "hobbys2"))
    //判断元素是否在集合中
    println(jedis.sismember("hobbys", "睡"))
    //获取集合中元素
    println(jedis.smembers("hobbys"))
    println(jedis.smembers("hobbys2"))
    //随机获取元素
    println(jedis.srandmember("hobbys", 2))
    //删除元素
    jedis.srem("hobbys", "吃")
    println(jedis.smembers("hobbys"))
    //模糊查询
    val scan1 = new ScanParams
    jedis.sadd("test", "javascript", "php", "c++", "c", "c#")
    scan1.`match`("*java*")
    val value1 = jedis.sscan("test", "0", scan1)
    println(value1.getResult)


    /*
    Zset 有序集合
    根据score排名
    score值越大,排名越靠后
     */
    //添加元素
    jedis.zadd("price", 1, "1")
    jedis.zadd("price", 5, "10")
    jedis.zadd("price", 2, "5.2")
    jedis.zadd("price", 6, "6")
    jedis.zadd("price", 7, "3")
    jedis.zadd("price", 10, "五百")
    //获取元素
    println(jedis.zrange("price", 0, -1))
    //获取自定义值范围的元素数量
    println(jedis.zcount("price", 1, 4))
    //获取分数排名,从0开始计算
    println(jedis.zrank("price", "3"))
    //删除元素
    jedis.zrem("price", "五百", "3")
    println(jedis.zrange("price", 0, -1))
    //获取元素的排名,从0开始计算
    println(jedis.zscore("price", "10"))

    /*
    模拟搜索
     */
    jedis.zadd("aa", 0, "a")
    jedis.zadd("aa", 0, "b")
    jedis.zadd("aa", 0, "c")
    jedis.zadd("aa", 0, "d")
    jedis.zadd("aa", 0, "e")
    jedis.zadd("aa", 0, "f")
    jedis.zadd("aa", 0, "g")
    //获取a-d范围的元素,包含a  不包含d  (代表不包含, [代表包含
    println(jedis.zrangeByLex("aa", "[a", "(d"))
    //倒着获取指定范围元素
    println(jedis.zrevrangeByLex("aa", "[f", "[b"))

  }
}
