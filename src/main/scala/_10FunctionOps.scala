/**
  * @Author liaojincheng
  * @Date 2020/4/30 13:49
  * @Version 1.0
  * @Description
  */
object _10FunctionOps {
  /**
    * scala的默认参数和排名参数
    */
  def main(args: Array[String]): Unit = {
    showAddr("zhangsna", 168686866) //使用默认参数
    showAddr("zhangsan002", 1686868668, "上海")//对默认参数进行了覆盖
    //带名参数
    showAddr(name = "zhangsan003", province = "深圳", phone = 1686868668)

  }

  //在scala中如果给一个参数列表中某一个参数在定义的时候就赋值了,就被称为默认参数
  def showAddr(name:String, phone:Long, province:String="北京"): Unit = {
    println(s"name=${name}, phone=${phone}, province=${province}")
  }
}
