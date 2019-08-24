package com.project.RecognitionImage.backend.ReadImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageReaderImpl implements ImageReader {
    public static void main(String[] args) {
        ImageReaderImpl i = new ImageReaderImpl();
        i.readeImage("C:/a.png");
    }

    @Override
    public BufferedImage readeImage(String filePath) {
        Map<Integer, Integer> blueMap = new HashMap<>();
        Map<Integer, Integer> greenMap = new HashMap<>();
        Map<Integer, Integer> redMap = new HashMap<>();
        Map<Integer, Integer> set = new HashMap<>();
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    int color = image.getRGB(j, i);

                    // Components will be in the range of 0..255:
                    int blue = color & 0xff;
                    if (blueMap.get(blue) != null) {
                        int value = blueMap.get(blue) + 1;
                        blueMap.put(blue, value);
                    } else {
                        blueMap.put(blue, 0);
                    }

                    int green = (color & 0xff00) >> 8;
                    if (greenMap.get(green) != null) {
                        int value = greenMap.get(green) + 1;
                        greenMap.put(green, value);
                    } else {
                        greenMap.put(green, 0);
                    }

                    int red = (color & 0xff0000) >> 16;
                    if (redMap.get(red) != null) {
                        int value = redMap.get(red) + 1;
                        redMap.put(red, value);
                    } else {
                        redMap.put(red, 0);
                    }

                    if (set.get(image.getRGB(j, i)) != null) {
                        int value = set.get(image.getRGB(j, i)) + 1;
                        set.put(image.getRGB(j, i), value);
                    } else {
                        set.put(image.getRGB(j, i), 0);
                    }
                    if (image.getRGB(j, i) == -16777216) {
                        System.out.print("*");
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        int maxBlue = Collections.max(blueMap.values());

        return null;
    }

    private int getKeyToMaxValue (Map<Integer, Integer> map) {
        AtomicInteger maxKey = new AtomicInteger();
        int maxValue = Collections.max(map.values());
        map.keySet().forEach(key -> {
            if(map.get(key).equals(maxValue)) {
                maxKey.set(key);
                return;
            }
            return;
        });
        return maxKey.get();
    }
}
