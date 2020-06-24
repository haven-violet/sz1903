/**
  * @Author liaojincheng
  * @Date 2020/4/30 9:24
  * @Version 1.0
  * @Description 跳出循环
  */
object _06BreakLoopTest {
  def main(args: Array[String]): Unit = {
    /**
      * 1. 变量控制(推荐使用,很方便)
      */
    var flag = true
    var res = 0
    var n = 0
//    while(flag){
//      res += n
//      n += 1
//      if(n == 5){
//        flag = false
//      }
//    }
//    println(res)

//    for(i <- 1 to 10 if flag){
//      res += i
//      if(i == 5){
//        flag = false
//      }
//    }
//    println(res)

    /**
      * 2.使用return控制
      */
    println(add_outer())

    /**
      * 也可以使用break跳出(不推荐)
      */
  }

  def add_outer() = {
    var res = 0

    def add_inner(){
      for(i <- 0 until 10){
        if(i == 5){
          return
        }
        res += i
      }
    }
    add_inner()
    res
  }
}
