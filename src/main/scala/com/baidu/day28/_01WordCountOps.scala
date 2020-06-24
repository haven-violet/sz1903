package com.baidu.day28

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @Author liaojincheng
  * @Date 2020/6/9 15:10
  * @Version 1.0
  * @Description
  *             入门案例
  */
object _01WordCountOps {
  def main(args: Array[String]): Unit = {
    //创建sparkStream执行入口
    //注: local 不能这么写,因为这样代表一个线程进行工作,但是我们的程序是实时流程序
    //需要有个线程去拉取数据,也得有个线程去计算数据,所以必须要有两个线程才能完成实时处理工作
    //或者2个以上线程来启动即可
    val conf = new SparkConf().setMaster("local[2]").setAppName("wc")
    //加载配置参数,设置批次提交任务的时间间隔
    val ssc = new StreamingContext(conf, Seconds(5))
    //首先,要获取一个输入的流(Dstream),代表一个输入源(Kafka, Socket等)
    val linesDstream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop201", 6666)
    //处理输入流数据,接下来就编写wc的代码逻辑即可
    val words: DStream[String] = linesDstream.flatMap(_.split(" "))
    val tuple: DStream[(String, Int)] = words.map((_,1))
    val reduce: DStream[(String, Int)] = tuple.reduceByKey(_+_)
    //输出数据即可
    reduce.print()
    //注意: 实时流需要启动,和离线不一样
    ssc.start()
    //等待停止
    ssc.awaitTermination()
  }
}
