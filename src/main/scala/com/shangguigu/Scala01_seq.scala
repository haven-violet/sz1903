package com.shangguigu

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * @Author liaojincheng
  * @Date 2020/5/2 9:42
  * @Version 1.0
  * @Description
  */
object Scala01_seq {
  def main(args: Array[String]): Unit = {
    //序列 Seq
    //默认scala提供的集合都是不可变的(immutable)
    val list: List[Int] = List(1,2,3,4)
    val list1: List[Int] = List(5,6,7,8)


    //通过索引获取数据
//    println(list(0))

    //增加数据
//    val list1: List[Int] = list:+1
    //    println(list1.mkString(","))
    //    println(list == list1)//false
    //    list1.foreach(println)
//    val list2: List[Int] = 1+:list1
//    println(list2.mkString(","))

//    val list2: List[Int] = list.++(list1)
//    val list3: List[Int] = list++(list1)
//    println(list2.mkString(","))
//    println(list3.mkString("、"))

    //    val ints: List[Int] = list.::(9)
    //    println(ints.mkString(","))
    //list中的冒号运算符的运算顺序从右到左
//    val ints: List[Int] = 7 :: 8 :: 9 :: list
//    println(ints.mkString(","))

//    val list5: List[Int] = 9 :: list
//    val list6: List[Any] = 9 :: list1 ::: list
//
//    println(list6.mkString(","))

    //特殊List集合: 空集合Nil
//    println(Nil)
//    println(List())
//    1 :: 2 :: 3 :: Nil

    //修改
    val list7: List[Int] = list.updated(2, 5)
    //list7.foreach(println)

    //删除数据
//    val list8: List[Int] = list.drop(10)
//    list8.foreach(println)
//
//    //查询数据
//    for(ele <- list8) {
//      println(ele)
//    }

    //TODO 可变集合
    val mlist: ListBuffer[Int] = ListBuffer(1,2,3,4)
//    mlist.insert()
//    mlist.update()
//    mlist.drop()
//    mlist.remove()

    //集合的属性
    //头
//    println(mlist.head)
//    //尾(除了头以外)
//    println(mlist.tail)
//    //最后
//    println(mlist.last)
//    //初始化(除了最后一个)
//    println(mlist.init)

    //队列（一定可变）

    val q: mutable.Queue[Int] = mutable.Queue(1,2,3,4)
    println("add before = " + q)
    q.enqueue(5)
    println("add after " + q)
    val i: Int = q.dequeue()
    println("i = " + i)
    println("delete after " + q)
  }
}
