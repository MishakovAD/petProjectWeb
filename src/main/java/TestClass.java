import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestClass {
    public static void main(String[] args) throws Exception {
        //System.out.println(fact(170d));
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list.forEach(System.out::print);
        System.out.println();

        System.out.println();
        int[] arr = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
        for (int i = 0; i < arr.length / 2; i++) {
            int current = arr[i];
            int tmp = arr [arr.length - 1 - i];
            arr [arr.length - 1 - i] = current;
            arr[i] = tmp;
        }
        Arrays.stream(arr).forEach(System.out::print);
    }

    static double fact(double i) {
        if (i == 1) {
            return 1;
        }
        return i*fact(i-1);
    }

}
