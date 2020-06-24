/**
 * @Author liaojincheng
 * @Date 2020/5/6 22:58
 * @Version 1.0
 * @Description
 */
public class Test {
    public static void main(String[] args) {
        new Outer().new Inner().show();
    }
}

//定义内部类
class Outer{
    int x = 5;
    class Inner{
        int x = 6;
        public void show(){
            int x = 7;
            System.out.println(x);
            System.out.println(this.x);
            System.out.println(Outer.this.x);
        }
    }
}