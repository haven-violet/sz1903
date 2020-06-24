package com.baidu.day08

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * @Author liaojincheng
  * @Date 2020/5/12 8:21
  * @Version 1.0
  * @Description 模拟两个人说话
  */
object _08AkkaOps {
  def main(args: Array[String]): Unit = {
    //首先获取ActorSystem
    val as: ActorSystem = ActorSystem("As")
    //通过ActorSystem创建ActorRef
    val yl = as.actorOf(Props[YLActor], "yl")
    val zs = as.actorOf(Props(new ZSActor(yl)), "zs")
    //启动
    yl ! "start"
    zs ! "start"
  }
}

class ZSActor(val name:ActorRef) extends Actor{
  override def receive: Receive = {
    case "start" =>{
      println("真生说: hello !")
      name! "炎龙"
    }
    case "真生" => {
      println("真身说: 吃了,吃的抱抱的!")
      Thread.sleep(1000)
      name! "炎龙"
    }
  }
}

class YLActor extends Actor{
  override def receive: Receive = {
    case "start"=> println("炎龙说: Hello !")
    case "炎龙"=>{
      println("炎龙说: 你吃饭了吗")
      Thread.sleep(1000)
      sender()!"真生"
    }
  }
}
