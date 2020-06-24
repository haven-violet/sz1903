import scala.io.StdIn

/**
  * @Author liaojincheng
  * @Date 2020/4/29 22:55
  * @Version 1.0
  * @Description
  */
object _03WhileTest {
  /**
    * 小demo: 用户名和密码进行登录操作,有3次试错机会
    * readLine(过时)
    * readInt(过时)
    * StdIn.readLine()
    * StdIn.readInt()
    * @param args
    */
  def main(args: Array[String]): Unit = {
//    val name: String = StdIn.readLine("请输入用户名:")
//    println("姓名为:" + name)
//    print("请输入年龄:")
//    val age = StdIn.readInt()
//    println("年龄为: " + age)

    val dbUser = "violet"
    val dbPwd = "123"
    var count = 3
    while(count > 0){
      val user: String = StdIn.readLine("请输入用户名: ")
      val password: String = StdIn.readLine("请输入密码: ")
      if(dbUser == user && dbPwd == password){
        //格式化输出
        printf("恭喜你! %s 登录成功 ~~~", user)
        count = -1
      }else{ //验证失败
        count -= 1
//        println("用户名或密码有误, 您还有"+count+"次机会重试~")
//       使用语法糖
        println(s"用户名或密码有误，您还有${count}次机会重试")
      }
    }
  }
}
