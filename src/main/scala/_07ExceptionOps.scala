/**
  * @Author liaojincheng
  * @Date 2020/4/30 10:48
  * @Version 1.0
  * @Description scala中的异常处理
  */
object _07ExceptionOps {

  def main(args: Array[String]): Unit = {
    //类型转换
    try{
      val num = Integer.valueOf("123d")
    }catch {
      case nfe: NumberFormatException => {
        println(nfe.getMessage())
        throw new RuntimeException("不是数值类型")//抛出异常
      }
    }
  }
}
