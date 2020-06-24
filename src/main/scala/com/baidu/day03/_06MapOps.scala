package com.baidu.day03

/**
  * @Author liaojincheng
  * @Date 2020/5/3 16:17
  * @Version 1.0
  * @Description 不可变
  */
object _06MapOps {
  def main(args: Array[String]): Unit = {
    val personMap = Map[String, Int](
      "zhangsan"->23,
      "lisi"->24,
      "wangwu"->26
    )

    /**没有显示 None; 有显示 Some(23)
      *获取对应的值,通过key来获取get方式拿到value返回一个option类型
      * 注意: 返回的Option类型有两个子类
      *   some: 有值 可以通过get来获取其中的值
      *   none: 无值 如果没有值不用操作,不然会抛出异常 NoSuchElementException("None.get")
      */
    val name = "zhangsan"
    if(personMap.contains(name)){
      val ageOption: Option[Int] = personMap.get(name)
      println(ageOption.get)
    }else{
      println(name + "不存在")
    }

    /**
      * 获取方式二
      * 如果获取的key不存在那么会抛出异常: java.util.NoSuchElementException: key not found:
      */
//    val age: Int = personMap("lisi")
//    println(age)

    /**
      * 上述两种获取方式都不是特别好,接下来使用第三种方式
      * 推荐使用
      */
//    val firstAge: Int = personMap.getOrElse("zhangsan1", 88)
//    println(s"${name}的年龄为: "+firstAge)

    //不可变Map是不可以修改Map元素值
//    personMap("zhangsan") = 67

    //删除
    //删除集合汇总的n个元素,并且返回新的映射,但是原映射不发生改变
    val newMap: Map[String, Int] = personMap.drop(1)
    println(newMap)

    println(personMap)
  }
}
