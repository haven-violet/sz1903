package com.baidu.day05

/**
  * @Author liaojincheng
  * @Date 2020/5/11 15:50
  * @Version 1.0
  * @Description
  * 子类覆盖父类的字段
  * 只能覆盖被val修饰的变量,不可以覆盖被var修饰的变量
  */
object _07ExtendsOps {
  def main(args: Array[String]): Unit = {
    val zi = new Zi3
    println(zi.name)
    println(zi.age)
    println(zi.address)
  }
}

class Fu3{
  val name:String = "zhangsan"
  val age:Int = 18
  var address:String = "深圳"
}
class Zi3 extends Fu3{
  override val age: Int = 17
  override val name: String = "lisi"

}
