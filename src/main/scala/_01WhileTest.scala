/**
  * @Author liaojincheng
  * @Date 2020/4/29 10:51
  * @Version 1.0
  * @Description
  */
object _01WhileTest {
  def main(args: Array[String]): Unit = {
    var sum:Int = 0
    var n = 1 //使用类型推断,推断出n的类型是Int
    while(n <= 10){
      //scala中 ++ / -- 都不存在,而是使用+=/-=这种操作来表示自增和自减
      sum += n
      n += 1
    }
    println("sum:" + sum)
  }
}
