package com.baidu.day08

/**
  * @Author liaojincheng
  * @Date 2020/5/11 20:07
  * @Version 1.0
  * @Description
  * 下边界限定
  * 传入的类型必须是当前这个边界类型的父类
  */
object _01GenericOps {
  def main(args: Array[String]): Unit = {
    val f = new Father("father")
    val c = new Child("child")
    val t = new test("test")
    getSFZ(f)
    getSFZ(c)
    getSFZ(t)
  }

  //下边界, A >: B, A只能是B的父类或者B
  def getSFZ[R >: Child](r:R): Unit = {
    if(r.getClass == classOf[Child]){
      println("领取成功~")
    }else if(r.getClass == classOf[Father]){
      println("先签字,后领取~")
    }else {
      println("无法领取~")
    }
  }
}

class Father(val name:String)
class Child(name:String) extends Father(name)
class test(name:String)
