package com.baidu.day08

import java.io.File

import scala.io.Source

/**
  * @Author liaojincheng
  * @Date 2020/5/11 21:52
  * @Version 1.0
  * @Description 隐式转换
  */
object _04ImplicitsOps {
  def main(args: Array[String]): Unit = {
    val file = new File("F:\\test.txt")
    val filename = file.getName
    println("filename:"+filename)
    //获取文件内容
    val lines: List[String] = file.read()
    lines.foreach(println)
  }

  //定义隐式转换操作,让程序自动调用此方法
  implicit def file2RichFile(file:File):RichFile = {
    new RichFile(file)
  }

  //为了丰富file的类库,特此创建读取文件方法
  class RichFile(file:File){
    def read() ={
      Source.fromFile(file).getLines().toList
    }
  }
}
