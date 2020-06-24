/**
  * @Author liaojincheng
  * @Date 2020/4/30 8:19
  * @Version 1.0
  * @Description
  */
object _04ForTest {
  def main(args: Array[String]): Unit ={
    //java: for(int i = 1; i < 10; i++){循环体}
    //Scala: for(变量名 <- 集合){循环体}
    //获取range数据,通过如下两个方法实现,until和to区别主要在于元素个数
    val range = 1 until 10 //包头不包尾
    val range1 = 1 to 10 //包头包尾
    var sum = 0
    for (n <- range){
      sum += n
    }
    println("sum= " + sum)
  }
}
