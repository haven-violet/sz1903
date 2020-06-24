/**
  * @Author liaojincheng
  * @Date 2020/4/30 12:44
  * @Version 1.0
  * @Description
  */
object _09FunctionOps {
  /**
    * scala几个基本函数
    * 没有返回值函数
    * 空参函数
    * 单行函数
    * 递归函数
    */
  def main(args: Array[String]): Unit ={
//    printMsg("张三")
    showDate

  }

  //没有返回值的函数
  def printMsg(msg:String){
    println(msg)
  }

  //空参函数,没有参数列表
  //在调用的时候,可以省略掉(),前提是在定义的时候有()
  //如果在定义的时候没有加(),在调用的时候也不能加()
  def showDate(): Unit = {
    println(System.currentTimeMillis())
  }

  //单行函数 --> 函数体只有一行的比较简单的函数
  //单行函数一定要加上=进行类型推断
  def printLine(msg:String) = println(msg)
  var name = "abc"
  def setName(n:String) = name = n

  /**
    * 递归函数:
    * 必须要有结束条件
    * 在开发过程中,能不用递归就不用,因为性能消耗很大
    */
  def factor(n: Int): Int = {
    if(n <= 1){
      1
    }else{
      factor(n - 1)
    }
  }


}
