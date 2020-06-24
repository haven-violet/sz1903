package com.baidu.day08

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * @Author liaojincheng
  * @Date 2020/5/12 8:52
  * @Version 1.0
  * @Description
  */
object _09AkkaMasterOps {
  def main(args: Array[String]): Unit = {
    val host = "127.0.0.1" //localhost
    val port = "6666"
    //加载配置模拟进程
    val config =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    var cf = ConfigFactory.parseString(config)
    //创建ActorSystem
    val masterSystem = ActorSystem("MasterSystem", cf)
    val master = masterSystem.actorOf(Props[Master], "Master")
    master ! "connect"
  }
}

class Master extends Actor{
  //初始化方法

  override def preStart(): Unit = {
    println("初始化完成")
  }

  //不断接收消息
  override def receive: Receive = {
    case "start"=>println("接收到自己发来的消息")
    case "connect" =>{
      println("发送消息值Worker~")
      sender!"reply"
    }
  }
}