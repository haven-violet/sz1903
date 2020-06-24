package com.baidu.day07

/**
  * @Author liaojincheng
  * @Date 2020/5/11 14:02
  * @Version 1.0
  * @Description
  * 泛型--> 上边界 Bounds <:
  * 泛型的类型必须是某个类的子类
  */
object _13GenericOps {
  def main(args: Array[String]): Unit = {
    //普通的判断比较
//    val long = new CmpLong(8L, 9L)
//    println(long.bigger)
//    val int = new CmpInt(1,2)
//    println(int.bigger)

    //有泛型限定的比较
    //我们发现在Int的源码中,没有继承比较器
    //导致无法使用比较器,而且还进行类型限定了
//    val cmp = new CmpComm(1, 2)

    //在这里我们既不想换数据类型,还是用比较器,那么就要找到Integer来帮转换
    //因为Integer内部是继承了比较器的,可以进行数据比较
//    val cmp = new CmpComm(Integer.valueOf(1), Integer.valueOf(2))
    val cmp = new CmpComm[Integer](1,2)
    println(cmp.bigger)
  }

}

class CmpInt(a:Int, b:Int){
  def bigger = if(a>b) a else b
}
class CmpLong(a:Long, b:Long){
  def bigger = if(a>b) a else b
}
class CmpComm[T <: Comparable[T]](o1:T, o2:T){
  //比较器
  def bigger = if(o1.compareTo(o2)>0) o1 else o2
}
