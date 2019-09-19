package TinkoffTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TherdQuestion {
    public static void main(String[] args) throws IOException {
        Pattern smile = Pattern.compile("[:;]-*[()\\[\\]]");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        reader.close();
        Matcher matcher = smile.matcher(input);

        int counter = 0;
        while (matcher.find()) {
            counter++;
        }
        System.out.println(counter);
    }
}
