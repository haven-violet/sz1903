package com.baidu.day08

/**
  * @Author liaojincheng
  * @Date 2020/5/11 20:23
  * @Version 1.0
  * @Description
  * 泛型 --> 视界  <%
  * 这种类型限定,是可以自动触发隐式转换操作的,
  * 也就是会自己进行类型转换（有局限性,转换的类型必须要存在）
  */
object _02GenericOps {
  def main(args: Array[String]): Unit = {
    //    val cmp = new CmpComm(1, 2)
    //    println(cmp.bigger)
    val p = new Per("zhangsan")
    val s = new Stu("lisi")
    val c = new Cat("huahua")
    //导入隐式转换的包
    import com.baidu.day08._02ImpCat.cat2per
    val py = new Party(c)
    py.play
  }
}

//传入的T必须是Comparable子类
class CmpComm[T <% Comparable[T]](o1:T, o2:T){
  //比较器
  def bigger = if(o1.compareTo(o2) > 0) o1 else o2
}

class Per(val name:String){
  def makePer(p:Per): Unit ={
    println("Hello, I'm"+name)
  }
}

class Stu(name:String) extends Per(name)

class Cat(val name:String)

class Party[T <% Per](p1:T) {
  def play = p1.makePer(p1)
}


