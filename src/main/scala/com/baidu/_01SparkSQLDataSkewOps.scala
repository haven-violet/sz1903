package com.baidu

import java.util.Random

import org.apache.spark.sql.SparkSession

/**
  * @Author liaojincheng
  * @Date 2020/5/27 18:54
  * @Version 1.0
  * @Description
  * 使用SQL来解决sparkSQL编程中出现的数据倾斜问题
  * 以group by之后的倾斜为例说明
  * 什么是数据倾斜? 有什么表现?
  *   数据分布不均匀,体现在,某个字段的数据特别多,只有在进行聚合计算的时候,数据才会进行重新分布,
  *   这个过程就是shuffle操作,shuffle操作的本质是相同key的数据汇聚在同一个计算节点
  *   上面,这样才会出现上面所说的倾斜特点
  *   表现一: 大多数task运行都非常快,但是个别task运行相对较慢,极端情况,可能有的task导致任务失败
  *           类似于MR中的Map和Reduce阶段(reduce 一直在 99%)
  *   表现二: 某一天,程序突然挂掉了,发现是OOM异常
  * 怎么解决?
  *   首先你要知道原因所在,假如某一个task过多,导致任务执行效率很低,那么我们需要对task进行重新分
  *   配,打散倾斜的task任务,也就是打散倾斜的数据,这里我们可以加随机前缀,然后在进行聚合操作,之后
  *   在去掉随机前缀,全局聚合。(两个阶段聚合)
  * 需求: 统计每个单词的次数,要求使用sparkSQL实现统计
  */
object _01SparkSQLDataSkewOps {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("sparkSQLDataSkew")
      .master("local[*]")
      .getOrCreate()
    //注册UDF
    spark.udf.register("addPrefix", addPrefix _)
    spark.udf.register("trmPrefix", trmPrefix _)

    val list = List(
      "zhang zhang wen wen wen wen yue yue",
      "gong yi can can can can can can can can can can",
      "chang bao peng can can can can can can"
    )

    //导入隐式转换
    import spark.implicits._
    //创建DF
    val df = list.toDF("line")
    df.createOrReplaceTempView("test")
    println("统计单词个数")
    spark.sql(
      s"""
         |select
         | temp.word,
         | count(1) counts
         |from
         |(
         |  select
         |    explode(split(line, " ")) as word
         |  from test
         |) temp
         |group by temp.word
       """.stripMargin).show()
    println("问题是: 在统计的时候发生了数据倾斜")
    println("step 1. 将原来的数据进行拆分,先不要分组聚合")
    val explodeSQL =
      s"""
         |select
         | explode(split(line, " "))
         |from test
       """.stripMargin
    spark.sql(explodeSQL).show()
    println("step 2.给拆分好的数据添加随机前缀")
    var prefixSQL =
      s"""
         |select
         |addPrefix(word)
         |from
         |(
         |  select
         |    explode(split(line, " ")) as word
         |  from test
         |) t
    """.stripMargin
    spark.sql(prefixSQL).show()
    //系统已经提供了类似操作,个人就不要开发,除非他满足不了你的需求
    prefixSQL =
      s"""
         |select
         |  concat_ws("_", cast(floor((rand() * 2)) as string), t.word) as prefix_word
         |from
         |(
         |  select
         |    explode(split(line, " ")) as word
         |  from test
         |) t
       """.stripMargin
    spark.sql(prefixSQL).show()
    println("3.进行有前缀的局部聚合操作")
    val partAggSQL =
      s"""
         |select
         |  concat_ws("_", cast(floor(rand() * 2) as string), t.word) as prefix_word,
         |  count(1) as counts
         |from
         |(
         |  select
         |    explode(split(line, " ")) as word
         |  from test
         |) t
         |group by prefix_word
       """.stripMargin
    spark.sql(partAggSQL).show()
    println("step 4. 去掉随机前缀")
    var trimPrefixSQL =
      s"""
         |select
         |  trmPrefix(temp2.prefix_word) as trmPrefix_word,
         |  temp2.prefix_word
         |from
         |(
         |  select
         |    concat_ws("_", cast(floor(rand() * 2) as string), temp1.word) as prefix_word,
         |    count(1) as counts
         |  from
         |  (
         |    select
         |      explode(split(line, " ")) as word
         |    from test
         |  ) temp1
         |  group by prefix_word
         |)
         |
       """.stripMargin
    spark.sql(trimPrefixSQL).show()
    //使用系统提供的函数完成前缀的去除
    trimPrefixSQL =
      s"""
         |select
         |  substr(temp2.prefix_word, instr(temp2.prefix_word, "_") + 1) as trmPrefix_word
         |from
         |(
         |  select
         |    concat_ws("_", cast(floor(rand() * 2) as string), temp1.word) as prefix_word,
         |    count(1) as counts
         |  from
         |  (
         |    select
         |      explode(split(line, " ")) as word
         |    from test
         |  ) temp1
         |  group by prefix_word
         |) temp2
       """.stripMargin
    spark.sql(trimPrefixSQL).show()
    println("step 5.去除前缀后的全局聚合")
    val fullAggSQL =
      s"""
         |select
         |  substr(temp2.prefix_word, instr(temp2.prefix_word, "_") + 1) as trmPrefix_word,
         |  sum(temp2.counts) as total
         |from
         |(
         |  select
         |    concat_ws("_", cast(floor(rand() * 2) as string), temp1.word) as prefix_word,
         |    count(1) as counts
         |  from
         |  (
         |    select
         |      explode(split(line, " ")) as word
         |    from test
         |  ) temp1
         |  group by prefix_word
         |)temp2
         |group by trmPrefix_word
       """.stripMargin

    spark.sql(fullAggSQL).show()
  }

  //加随机前缀
  def addPrefix(str:String) = {
    val random = new Random()
    random.nextInt(2)+"_"+str
  }

  //去除随机前缀
  def trmPrefix(word: String) = {
    word.split("_")(1)
  }


}
