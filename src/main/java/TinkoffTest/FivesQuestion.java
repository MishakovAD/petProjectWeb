package TinkoffTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class FivesQuestion {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int countOfPoint = Integer.parseInt(reader.readLine());
        List<String> coordinatesList = new LinkedList<>();
        for (int i = 0; i < countOfPoint; i++) {
            String coordinates = reader.readLine();
            coordinatesList.add(coordinates);
        }
        reader.close();
        String[] x_y = coordinatesList.get(0).split(" ");
        Map<Integer, Double> dist = new TreeMap<>();
        int x = Integer.parseInt(x_y[0]);
        int y = Integer.parseInt(x_y[1]);
        for (int i = 1; i < coordinatesList.size(); i++) {
            int x1 = Integer.parseInt(coordinatesList.get(i).split(" ")[0]);
            int y1 = Integer.parseInt(coordinatesList.get(i).split(" ")[1]);
            double r = (double) Math.sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
            dist.put(i, r);
        }
        List<Double> maxValues = dist.values().stream().sorted().skip(dist.values().size()-2).collect(Collectors.toList());
        Map<Integer, Double> newMap = new HashMap<>();
        for (Map.Entry entry : dist.entrySet()) {
            Integer key = (Integer) entry.getKey();
            Double value = (Double) entry.getValue();
            if (maxValues.contains(value)) {
                newMap.put(key, value);
            }
        }
        double square = 0;
        StringBuilder strDist = new StringBuilder();
        StringBuilder strCoord = new StringBuilder();
        newMap.forEach((k, v) -> {
            strDist.append(v + " ");
            strCoord.append(coordinatesList.get(k) + " ");
        });
        String[] coordinates = strCoord.toString().split(" ");
        int x1 = Integer.parseInt(coordinates[0]);
        int y1 = Integer.parseInt(coordinates[1]);
        int x2 = Integer.parseInt(coordinates[2]);
        int y2 = Integer.parseInt(coordinates[3]);
        double first = Double.parseDouble(strDist.toString().split(" ")[0]);
        double second = Double.parseDouble(strDist.toString().split(" ")[1]);
        double therd = (double) Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
        double p = (double) (therd + first + second) / 2;
        double s = Math.sqrt(p*(p-first)*(p-second)*(p-therd));
        System.out.println(s);


    }
}
