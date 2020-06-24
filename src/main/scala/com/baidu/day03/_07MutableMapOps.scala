package com.baidu.day03

import scala.collection.{SortedMap, mutable}

/**
  * @Author liaojincheng
  * @Date 2020/5/3 17:14
  * @Version 1.0
  * @Description
  */
object _07MutableMapOps {
  def main(args: Array[String]): Unit = {
    //创建map
    val personMap: mutable.Map[String, Int] = mutable.Map[String, Int](
      "zhangsan" -> 27
    )
    //添加元素,添加就是修改;如果key存在就是修改,不存在就是添加
    personMap("zhangsan1") = 28
    println(personMap)
    personMap.put("lisi", 24)
    personMap.put("wangwu", 25)
    println(personMap)

    //删除java.map.delete(key)
    val option: Option[Int] = personMap.remove("zhangsan")
    println("被删除元素对应的value = " + option.getOrElse(null))
    println("删除后元素的集合: " + personMap)
    //获取元素个数
    val size: Int = personMap.size
    println("size: " + size)

    //遍历map
    for(k <- personMap.keySet){
      val value: Int = personMap(k)
      println(s"value = ${value}")
    }
    println("---另外一种方式---")
    for(kv <- personMap){ //这种遍历方式类似于java中的entrySet
      // ---> 取值方式其实是Tuple(元组)
      val key: String = kv._1
      val value: Int = kv._2
      println(s"${key} ---> ${value}")
    }

    println("---另外一种方式---")
    for((k,v) <- personMap){
      println(s"${k} --> ${v}")
    }

    println("---另外一种方式---")
    personMap.foreach(kv => println(s"${kv._1} == ${kv._2}"))

    /**
      * SortedMap和LinkHashMap(了解)
      * 按照key进行自动排序
      */
    val ages: SortedMap[String, Int] = SortedMap("c"->18, "a"->19, "b"->22)
    println(ages)
    //LinkedHashMap会记录我们操作的插入顺序
    val names: mutable.LinkedHashMap[String, Int] = new mutable.LinkedHashMap[String, Int]
    names("c")=223
    names("b")=3
    names("a")=2323
    println(names)
  }
}
