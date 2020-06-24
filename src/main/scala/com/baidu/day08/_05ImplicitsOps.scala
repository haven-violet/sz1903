package com.baidu.day08

/**
  * @Author liaojincheng
  * @Date 2020/5/11 22:21
  * @Version 1.0
  * @Description
  * 引入隐式转换
  *
  */
object _05ImplicitsOps {
  def main(args: Array[String]): Unit = {
    //导入隐式转换包
    import com.baidu.day08._05ImpMan.man2SuperMan
    val man = new Man("克拉克")
    man.man()
  }
}

class Man(val name:String)
class SuperMan(val name:String){
  def man() = println(name+"开始变身,变身成功,我是超人~~ ")
}
