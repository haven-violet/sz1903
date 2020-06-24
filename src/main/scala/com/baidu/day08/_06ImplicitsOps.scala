package com.baidu.day08

/**
  * @Author liaojincheng
  * @Date 2020/5/11 22:27
  * @Version 1.0
  * @Description
  * 隐式参数
  * 也就是说一个函数的参数被implicit关键字修改时,把这些参数称之为隐式转换操作
  * 首先,该参数不一定非得要传入,因为有时候默认值,其次,该参数,会自动的在其作用
  * 范围内进行检查,找到适应的变量,来完成赋值,但是变量必须要用implicit修饰
  */
object _06ImplicitsOps {
  def main(args: Array[String]): Unit = {
    //需要注入隐式参数,那么得导包
    import com.baidu.day08.ImplicitPen.signPen
    getSignPen("zhangsan")

  }

  //这里面有一个隐式参数
  def getSignPen(name:String)(implicit signPen: SignPen): Unit = {
    signPen.write(name+"come to exam in time ~~~")
  }

}

class SignPen{
  def write(content:String) = println(content)
}

