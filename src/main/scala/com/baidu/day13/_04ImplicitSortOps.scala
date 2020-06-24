package com.baidu.day13

/**
  * @Author liaojincheng
  * @Date 2020/5/20 0:13
  * @Version 1.0
  * @Description
  */
object _04ImplicitSortOps{
  implicit object OrderingOF extends Ordering[QF] {
    override def compare(x: QF, y: QF): Int = {
      if(x.age == y.age){
        y.dep - x.dep
      }else{
        x.age - y.age
      }
    }
  }
}
