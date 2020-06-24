package com.baidu.day05

/**
  * @Author liaojincheng
  * @Date 2020/5/7 20:59
  * @Version 1.0
  * @Description
  * 初始化操作
  * 我们在创建class的时候,可以不用new,但是必须要在伴生对象中实现apply函数
  * 才可以进行实例化,但是在我们使用类的时候,一般还是遵循java的原则,进行new
  */
class _01ApplyOps(val name:String) {

}

object _01ApplyOps{
  //此方法默认被加载
  def apply(name: String): _01ApplyOps = new _01ApplyOps(name)
}

object ApplyTest{
  def main(args: Array[String]): Unit = {
    val app = _01ApplyOps("zhangsan")
    val lisi = _01ApplyOps.apply("lisi")
    println(app.name)
    println(lisi.name)

    val arr = Array(1,2,3,4,5)
  }
}