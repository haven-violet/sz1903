package com.baidu.day05

/**
  * @Author liaojincheng
  * @Date 2020/5/7 22:45
  * @Version 1.0
  * @Description
  * 类型检查和转换
  * scala中也有类似于java的类型转换操作,不同之处在于scala中的类型检查转换
  * 都是对象的一个方法
  * isInstanceof ---> 类型检查
  * asInstanceof ---> 类型转换
  */
object _04ExtendsOps {
  def main(args: Array[String]): Unit = {
    val w1 = new Worker("李四", 10)
    val w2 = new Worker("李四", 10)
    println(w1.equals(w2))
  }
}

class Worker{
  var name: String = _
  var age: Int = _
  def this(name:String, age:Int) {
    this
    this.name = name
    this.age = age
  }

  //第一种写法
//  override def equals(obj: Any): Boolean = {
//    //进行类型检查
//    if(!obj.isInstanceOf[Worker]) {
//      false
//    //进行类型转换
//    }else{
//      //转换
//      val that = obj.asInstanceOf[Worker]
//      this.name.equals(that.name) && this.age == that.age
//    }
//  }

  //第二种
  override def equals(obj: Any): Boolean = {
    //这种写法叫做,类似于Java中switch case方式或者if else 多分支
    obj match {
      case that: Worker =>
        this.name.equals(that.name) && this.age == that.age
      case _ => {
        println("默认选项")
        false
      }
    }
  }
}
