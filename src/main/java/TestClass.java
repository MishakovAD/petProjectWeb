public class TestClass {
    public static void main(String[] args) throws Exception {
        System.out.println(fact(170d));
    }

    static double fact(double i) {
        if (i == 1) {
            return 1;
        }
        return i*fact(i-1);
    }

}
