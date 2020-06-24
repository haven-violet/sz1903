package com.baidu.day13

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.spark_project.jetty.server.Authentication.User

/**
  * @Author liaojincheng
  * @Date 2020/5/19 22:28
  * @Version 1.0
  * @Description
  * 自定义排序
  * 应用场景: 当内部提供的排序方式不满足于我们的需求的时候,此时可以进行自定义排序来解决我们的问题
  */
object _04SortByOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("sort").setMaster("local")
    val sc = new SparkContext(conf)
    //排序规则: 首先按照年龄升序,如果年龄相等,在按照存款降序
    val users = Array("zhangsan 30 100000", "lisi 25 50000","wangwu 25 88888","laoliu 30 78888")
    val rdd = sc.parallelize(users)

    /*
    第一种方式
    整理一下数据
     */
//    val userRDD: RDD[User] = rdd.map(x => {
//      val str = x.split(" ")
//      val name = str(0)
//      val age = str(1).toInt
//      val dep = str(2).toInt
//      new User(name, age, dep)
//    })
//    val sortRDD: RDD[User] = userRDD.sortBy(x => x)
//    sortRDD.foreach(println)
//    val users1: Array[User] = sortRDD.collect()
//    users1.foreach(println)

    /*
    第二种方式
     */
//    val userRDD = rdd.map(x => {
//      val str = x.split(" ")
//      val name = str(0)
//      val age = str(1).toInt
//      val dep = str(2).toInt
//      (name, age, dep)
//    })
//
//    //传入一个原则,和第一种类似,只不过作用的算子不同
//    val sortUser = userRDD.sortBy(u => new User(u._1, u._2, u._3))
//    sortUser.foreach(println)

    /*
    第四种 使用隐式转换方式
     */
//    val userRDD = rdd.map(x => {
//      val str = x.split(" ")
//      val name = str(0)
//      val age = str(1).toInt
//      val dep = str(2).toInt
//      (name, age, dep)
//    })
//
//    //引入隐式转换操作
//    import com.baidu.day13._04ImplicitSortOps.OrderingOF
//    val sortUser = userRDD.sortBy(u => new QF(u._1, u._2, u._3))
//    sortUser.foreach(println)

    /**
      * 第五种 直接对sortBy操作,也是最简单订单
      */
    val userRDD = rdd.map(x => {
      val str = x.split(" ")
      val name = str(0)
      val age = str(1).toInt
      val dep = str(2).toInt
      (name, age, dep)
    })
    val sortRDD = userRDD.sortBy(x => (x._2, -x._3))
    sortRDD.foreach(println)
  }
}

//class  User(val name:String, val age:Int, val dep:Int) extends Ordered[User] with Serializable {
//  override def compare(that: User): Int = {
//    if(this.age == that.age){
//      that.dep - this.dep
//    }else{
//      this.age - that.age
//    }
//  }
//
//  override def toString: String = {
//    s"name:${name}, age:${age}, dep:${dep}"
//  }
//}

/**
  * 第三种方式
  */
case class User(name:String, age:Int, dep:Int) extends Ordered[User] {
  override def compare(that: User): Int = {
    if(this.age == that.age){
      that.dep - this.dep
    }else{
      this.age - that.age
    }
  }

  override def toString: String = {
    s"name:${name}, age:${age}, dep:${dep}"
  }
}

case class QF(name:String, age:Int, dep:Int)

