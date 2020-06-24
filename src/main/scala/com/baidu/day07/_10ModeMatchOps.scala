package com.baidu.day07

/**
  * @Author liaojincheng
  * @Date 2020/5/11 13:40
  * @Version 1.0
  * @Description
  */
object _10ModeMatchOps {
  def main(args: Array[String]): Unit = {
    accessRoad(EnumLight.YELLOW)
    accessRoad2(RED("霓虹灯"))
  }

  def accessRoad(light: EnumLight.Value): Unit ={
    light match {
      case EnumLight.RED => println("提示: 行车不规范,亲人泪两行")
      case EnumLight.YELLOW => println("提示: 快人5秒钟,可能快人一辈子")
      case EnumLight.GREEN => println("提示: 开开心心出去,快快乐乐回来")
    }
  }

  def accessRoad2(light: Light): Unit ={
    light match {
      case RED(name) => println("提示: 行车不规范,亲人泪两行")
      case YELLOW(name) => println("提示: 快人5秒钟,可能快人一辈子")
      case GREEN(name) => println("提示: 开开心心出去,快快乐乐回来")
    }
  }

  //定义枚举
  object EnumLight extends Enumeration {
    val RED,YELLOW,GREEN = Value
  }
}

class Light(name:String)

case class RED(name:String) extends Light(name)
case class YELLOW(name:String) extends Light(name)
case class GREEN(name:String) extends Light(name)