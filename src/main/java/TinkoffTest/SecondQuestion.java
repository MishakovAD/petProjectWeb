package TinkoffTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SecondQuestion {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        String[] coordinates = input.split(" ");
        int x1 = Integer.parseInt(coordinates[0]);
        int y1 = Integer.parseInt(coordinates[1]);
        int x2 = Integer.parseInt(coordinates[2]);
        int y2 = Integer.parseInt(coordinates[3]);
        double k = (double) (y2 - y1) / (x2- x1);
        double b = y1 - x1 * k;

        int counter = 0;
        for (int i = x1; i <= x2; i++) {
            if ((k*i + b) % 1 == 0) {
                counter++;
            }
        }
        System.out.println(counter);
    }
}
