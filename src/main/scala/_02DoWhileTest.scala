/**
  * @Author liaojincheng
  * @Date 2020/4/29 22:45
  * @Version 1.0
  * @Description
  */
object _02DoWhileTest {
  def main(args: Array[String]): Unit = {
    var sum = 0
    var n = 10
    do{
      sum += n
      n -= 1
    }while(n >= 1)
    //输出
    print("sum:" + sum)
    println("sum:" + sum)
    //格式化输出(用的太少了，了解即可)
    printf("Hi, my name is %s, I'm %d years old . ", "Leo", 30)
    //输入（了解即可,因为我们做的不是java后端开发,所以很少会进行输入操作, 一般都是输出）
    readLine()
  }
}
