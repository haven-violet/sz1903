package com.baidu.day08

/**
  * @Author liaojincheng
  * @Date 2020/5/11 22:23
  * @Version 1.0
  * @Description
  */
object _05ImpMan {
  implicit def man2SuperMan(man:Man) = {
    new SuperMan(man.name)
  }
}
