package com.baidu.day08

/**
  * @Author liaojincheng
  * @Date 2020/5/11 21:14
  * @Version 1.0
  * @Description
  * 泛型 --> 隐式转换调用
  */
object _02ImpCat {
  implicit def cat2per(cat:Object): Per = {
    if(cat.isInstanceOf[Cat]){
      val _cat = cat.asInstanceOf[Cat]
      new Per(_cat.name)
    }else "Null"
  }
}
