package com.baidu.day12

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author liaojincheng
  * @Date 2020/5/18 21:28
  * @Version 1.0
  * @Description
  */
object homework {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("homework").setMaster("local")
    val sc = new SparkContext(conf)

    //班级ID 姓名 年龄 性别 科目 成绩
    //12 张三 25 男 chinese 50
    //返回所有行数据的列表
    val lines: RDD[String] = sc.textFile("hdfs://hadoop201:9000/homework")

    //  1.  一共有多少人参加考试？
    val sum = lines.map(_.split(" ")).groupBy(_(1)).count()
    println(sum)
    //    1.1 一共有多少个小于20岁的人参加考试？
    //    1.2 一共有多少个等于20岁的人参加考试？
    //    1.3 一共有多少个大于20岁的人参加考试？
    println(lines.map(_.split(" ")).filter(x => x(2).toInt < 20).groupBy(_(1)).count())
    println(lines.map(_.split(" ")).filter(x => x(2).toInt == 20).groupBy(_(1)).count())
    println(lines.map(_.split(" ")).filter(x => x(2).toInt > 20).groupBy(_ (1)).count())


//    2. 一共有多个男生参加考试？
//       一共有多少个女生参加考试？
    println(lines.map(_.split(" ")).filter(x => x(3).equals("男")).groupBy(_ (1)).count())
    println(lines.map(_.split(" ")).filter(x => x(3).equals("女")).groupBy(_ (1)).count())

//    3. 12班有多少人参加考试？
//       13班有多少人参加考试？
    println(lines.map(_.split(" ")).filter(x => x(0).toInt == 12).groupBy(_ (1)).count())
    println(lines.map(_.split(" ")).filter(x => x(0).toInt == 13).groupBy(_ (1)).count())

//    4. 语文科目的平均成绩是多少？
//    4.1 数学科目的平均成绩是多少？
//    4.2 英语科目的平均成绩是多少？
    println(lines.map(_.split(" ")).filter(x => x(4).equals("chinese")).map(_ (5).toInt).mean())
    println(lines.map(_.split(" ")).filter(x => x(4).equals("math")).map(_ (5).toInt).mean())
    println(lines.map(_.split(" ")).filter(x => x(4).equals("english")).map(_ (5).toInt).mean())
//    5. 单个人平均成绩是多少？
    println(lines.map(_.split(" ")).map(x => (x(1), x(5).toInt)).groupByKey().map(y => (y._1, y._2.sum / y._2.size)))

//    6. 12班平均成绩是多少？
//       12班男生平均总成绩是多少？
//       12班女生平均总成绩是多少？
//       同理求13班相关成绩
    println(lines.map(_.split(" ")).filter(_ (0) == 12).map(_ (5).toInt).mean())
    println(lines.map(_.split(" ")).filter(_ (0) == 12).filter(_ (3).equals("男")).map(x => (x(1), x(5).toInt)).groupByKey().map(y => (y._1, y._2.sum)).map(x => x._2).mean())
    println(lines.map(_.split(" ")).filter(_ (0) == 12).filter(_ (3).equals("女")).map(x => (x(1), x(5).toInt)).groupByKey().map(y => (y._1, y._2.sum)).map(x => x._2).mean())
    println(lines.map(_.split(" ")).filter(_ (0) == 13).map(_ (5).toInt).mean())
    println(lines.map(_.split(" ")).filter(_ (0) == 13).filter(_ (3).equals("男")).map(x => (x(1), x(5).toInt)).groupByKey().map(y => (y._1, y._2.sum)).map(x => x._2).mean())
    println(lines.map(_.split(" ")).filter(_ (0) == 13).filter(_ (3).equals("女")).map(x => (x(1), x(5).toInt)).groupByKey().map(y => (y._1, y._2.sum)).map(x => x._2).mean())


//    7. 全校语文成绩最高分是多少？
//    7.1 12班语文成绩最低分是多少？
//    7.2 13班数学最高成绩是多少？
//    8. 总成绩大于150分的12班的女生有几个？
//    9. 总成绩大于150分，且数学大于等于70，且年龄大于等于20岁的学生的平均成绩是多少？
    println(lines.map(_.split(" ")).filter(_ (4).equals("chinese")).map(_ (5).toInt).max())
    println(lines.map(_.split(" ")).filter(x => x(0) == 12 && x(4).equals("chinese")).map(_ (5).toInt).min())
    println(lines.map(_.split(" ")).filter(x => x(0) == 13 && x(4).equals("math")).map(_ (5).toInt).max())
    println(lines.map(_.split(" ")).filter(_ (0) == 12).filter(_ (3).equals("女")).map(x => (x(1), x(5).toInt)).groupByKey().map(y => (y._1, y._2.sum)) filter (_._2 > 150))
    lines.map(_.split(" ")).filter(x => {
      x(4).equals("math") && x(5).toInt >= 70 && x(2).toInt >= 20
    }).map(y => (y(1), y(5).toInt)).groupByKey().map(y => (y._1, y._2.sum)).filter(_._2 > 150)

  }
}
