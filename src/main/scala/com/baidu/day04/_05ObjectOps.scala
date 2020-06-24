package com.baidu.day04

/**
  * @Author liaojincheng
  * @Date 2020/5/6 23:25
  * @Version 1.0
  * @Description
  *   反编译后结果, 要看_05ObjectOps.class而不是看$那个
  *   public final class _05ObjectOps
  *   {
  *   public static void main(String[] paramArrayOfString)
  *   {
  *   _05ObjectOps..MODULE$.main(paramArrayOfString);
  *   }
  *   }
  *   在java中一个类既可以有静态,也可以有非静态,java的主函数必须是static
  *   但是scala中class用来定义非静态的属性,如果想要定义静态的就必须要在object进行定义
  *   通过反编译大家也看的到,出来的结果主要在于有没有static
  *   object的主要作用
  *   1.给scala类提供程序运行的入口,静态的main函数
  *   2.给scala类也提供静态成员-scala类的伴生对象来实现
  */
object _05ObjectOps {
  def main(args: Array[String]): Unit = {
    println("hello scala ~~")
  }
}





