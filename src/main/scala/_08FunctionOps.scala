/**
  * @Author liaojincheng
  * @Date 2020/4/30 12:31
  * @Version 1.0
  * @Description
  */
object _08FunctionOps {
  /**
    * scala函数定义
    * 注意:
    * 1.如果写了函数的返回值类型 = 必须写, 不然报错
    * 2.如果没写函数的返回值类型, = 可以省略,其返回值类型为Unit
    */

  def main(args: Array[String]): Unit = {
    //调用
    val violet: String = printMsg("violet")
    println(violet)
    val haven: String = printMsg1("haven")
    println(haven)
    printMsg2("lucy")
  }

  def printMsg(name:String):String = {
    s"hello ${name}"
  }

  def printMsg1(name:String) = {
    s"hello ${name}"
  }

  def printMsg2(name:String) { //等于unit无返回值
    println(s"hello ${name}")
  }
}
