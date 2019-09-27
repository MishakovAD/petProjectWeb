public class Test extends A{

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    public Test() {
        super();
        System.out.println("3");
    }

    public final static void main(String[] args) {
        System.out.println("4");
        Test main = new Test();
        System.out.println("5");
    }
}

class A {

    static {
        System.out.println("6");
    }

    {
        System.out.println("7");
    }

    public A() {
        System.out.println("8");
    }
}
