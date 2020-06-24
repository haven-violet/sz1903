import scala.io.StdIn

/**
  * @Author liaojincheng
  * @Date 2020/4/30 13:59
  * @Version 1.0
  * @Description
  */
object _01Homework {
  def main(args: Array[String]): Unit = {
    println("请输入第一个数字:")
    val num1: Int = StdIn.readInt()
    println("请输入第二个数字:")
    val num2: Int = StdIn.readInt()
    println("请输入第三个数字:")
    val num3: Int = StdIn.readInt()
    var arr = Array[Int](num1, num2, num3)
    println("你输入的数字排序后为:" + getSortString(arr))

  }

  def getSortString(arr: Array[Int]): String = {
    var temp: Int = 0
    var str: StringBuffer = null
    //外层定义排序的趟数
    for(i <- 0 until arr.length-1){
      //内层定义每趟比较的次数,不断向最后冒泡
      for(j <- 0 until arr.length-1-i){
        if(arr(j) > arr(j+1)){
          temp = arr(j)
          arr(j) = arr(j+1)
          arr(j+1) = temp
        }
      }
    }

    for(i <- 0 to arr.length-1){
      str.append(arr(i))
    }
    str.toString()
  }


}
