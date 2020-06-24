/**
 * @Author liaojincheng
 * @Date 2020/5/7 9:04
 * @Version 1.0
 * @Description
 * Java中的单例:
 * 两种模式: 懒汉式, 饿汉式
 * 啥是单例: 所谓单例,就是本类只能创建一个实例对象
 *           就是不能让外部创建对象,构造方法要被私有化
 *           实例对象只能本类提供,只能通过静态来访问
 */
public class SingletonOps {
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println(s1 == s2);
    }
}

/*//单例模式的饿汉式
class Singleton{
    //本类自己创建一个实例对象放到static区
    private static Singleton singleton = new Singleton();
    //私有化构造方法
    private Singleton(){

    }
    //对外提供一个静态访问方法
    public static Singleton getInstance(){
        return singleton;
    }
}*/

//单例模式的懒汉式
class Singleton {
    //本类自己创建一个实例对象,但是不是一开始就创建,所以只是定义
    private static Singleton singleton = null;

    //私有化构造
    private Singleton(){

    }

   //对外提供一个静态方法来获取对象,第一次使用的时候进行创建
   public static Singleton getInstance(){
        //1.考虑到多线程问题,所以需要使用synchronized关键字来进行保护
        //2.考虑到每次线程进来都要等待进入进行判断是否为空,因为不是第一次创建后的后续线程都会进来,这样就影响了效率
        //3.加上一个判断,只有开始没有创建线程前,会有多个线程进入等待判断; 后面一旦创建好该对象后,后续的线程直接判断快速返回
       if(singleton == null) {
           synchronized (Singleton.class) {
               if (singleton == null) {
                   singleton = new Singleton();
               }
           }
       }
       return singleton;
   }

}

