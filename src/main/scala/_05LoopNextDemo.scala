/**
  * @Author liaojincheng
  * @Date 2020/4/29 13:12
  * @Version 1.0
  * @Description
  */
object _05LoopNextDemo {
  def main(args: Array[String]): Unit = {

    /**
      * 1. 打印一个正方形
      * *****
      * *****
      * *****
      * *****
      * *****
      */
    for(i <- 1 to 5){
      for(j <- 1 to 5){
        print("*")
      }
      println()
    }
    println()

    /**
      * 2.打印下三角形
      * *
      * **
      * ***
      * ****
      * *****
      */
    for(i <- 1 to 5){
      for(j <- 1 to i){
        print("*")
        if(i == j){
          println()
        }
      }
    }

    /**
      * 3.打印上三角形
      * *****
      *  ****
      *   ***
      *    **
      *     *
      */
    for(i <- 1 to 5){
      for(j <- 1 to 5){
        if(i > j){
          print(" ")
        }else{
          print("*")
        }
      }
      println()
    }

    /**
      * 4.九九乘法表
      */
    for(i <- 1 to 9){
      for(j <- 1 to i){
        print(j+"*"+i+"="+j*i+"\t")
      }
      println()
    }
    println()

    for(i <- 1 to 9; j <- 1 to i){
      print(s"${j}*${i}=${i*j}\t")
      if(i == j){
        println()
      }
    }

  }
}
