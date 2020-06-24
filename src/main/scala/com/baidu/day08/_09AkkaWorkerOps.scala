package com.baidu.day08

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * @Author liaojincheng
  * @Date 2020/5/12 8:42
  * @Version 1.0
  * @Description
  *             Worker
  */
object _09AkkaWorkerOps {
  def main(args: Array[String]): Unit = {
    val host = "127.0.0.1" //localhost
    val port = "8888"
    //加载配置模拟进程
    val config =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val cf = ConfigFactory.parseString(config)
    //创建ActorSystem
    val workerSystem = ActorSystem("WorkerSystem", cf)
    val worker = workerSystem.actorOf(Props[Worker], "Worker")
    worker! "start"
  }
}

class Worker extends Actor{
  override def preStart(): Unit = {
    //通过初始化方法接收Master的消息
    val master = context.actorSelection("akka.tcp://WorkerSystem@127.0.0.1:8888/user/master")
    master!"connect"
  }

  override def receive: Receive = {
    case "start" => println("接收自己消息")
    case "reply" => println("接收到Master发送过来的消息")
  }
}
