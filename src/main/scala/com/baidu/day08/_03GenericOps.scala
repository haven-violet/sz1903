package com.baidu.day08

/**
  * @Author liaojincheng
  * @Date 2020/5/11 21:32
  * @Version 1.0
  * @Description
  * Scala泛型特点:
  *   在默认情况下,scala泛型在"="左右两侧也得是必须相等的,
  *   不能出现左右两侧是继承关系的,但是注意,
  *   在scala的泛型中可以实现两侧的子父类,
  *   允许两侧不相等
  *   要通过scala的泛型协变与逆变实现
  */
object _03GenericOps {
  def main(args: Array[String]): Unit = {
    //正常情况下"="左右两侧不相等肯定报错
    //val unit: MyList[StudentOne] = new MyList[PersonOne]()
    //使用泛型
    val myList: MyList[PersonOne] = new MyList[StudentOne]()
    val myList1: MyList1[StudentOne] = new MyList1[PersonOne]()
  }
}

class PersonOne
class StudentOne extends PersonOne
class Workers extends PersonOne

//scala中把这种结构叫做泛型的逆变
class MyList1[-T]{
}
//
////scala中把这种结构叫做泛型的协变
class MyList[+T]{
}
