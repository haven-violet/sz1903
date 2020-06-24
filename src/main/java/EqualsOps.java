/**
 * @Author liaojincheng
 * @Date 2020/5/7 22:50
 * @Version 1.0
 * @Description
 * 类型检查与转换
 */
public class EqualsOps {
    public static void main(String[] args) {
        Person2 p1 = new Person2("张三", 23);
        Person2 p2 = new Person2("张三", 23);
        System.out.println(p1 == p2);
        //只要name和age相等,我就认为是一个对象

        System.out.println(p1.equals(p2));
        Student2 s1 = new Student2("张三", 23);
        //instanceof判断obj是否是当前类的实例对象或者子类的实例对象
        System.out.println(p1.equals(s1));
    }
}

class Person2{
    private String name;
    private int age;

    public Person2(String name, int age){
        this.name = name;
        this.age = age;
    }

    //重新比较方法,只要值相等就代表对象相等
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Person2)){
            System.out.println("我进入到了instanceof里面了");
            return false;
        }

        Person2 that = (Person2) obj;
        return this.name.equals(that.name) && this.age == that.age;
    }

    @Override
    public String toString() {
        return "Person2{" +
                "name=" + name + ", " +
                "age=" + age + "};";
    }
}

class Student2 extends Person2 {
    public Student2(String name, int age) {
        super(name, age);
    }
}