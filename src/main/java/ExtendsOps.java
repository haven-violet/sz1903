/**
 * @Author liaojincheng
 * @Date 2020/5/7 22:47
 * @Version 1.0
 * @Description
 */
public class ExtendsOps {
    public static void main(String[] args) {
        Fu zi = new Zi();
        zi.show();
    }
}

class Fu {
    public void show(){
        System.out.println("Fu-001");
    }
}

class Zi extends Fu {
    public void printA(){
        System.out.println("Fu-002");
    }

    @Override
    public void show() {
        System.out.println("Fu-003");
    }
}

