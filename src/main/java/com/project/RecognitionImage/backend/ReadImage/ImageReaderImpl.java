package com.project.RecognitionImage.backend.ReadImage;

import com.project.RecognitionImage.backend.Entity.PictureDot;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageReaderImpl implements ImageReader {
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
        System.out.println("OpenCV Mat: " + m);
        Mat mr1 = m.row(1);
        mr1.setTo(new Scalar(1));
        Mat mc5 = m.col(5);
        mc5.setTo(new Scalar(5));
        System.out.println("OpenCV Mat data:\n" + m.dump());

        String filename = "C:/b.png";
        ImageReaderImpl i = new ImageReaderImpl();
        //i.readeImage("C:/b.png");
    }

    @Override
    public BufferedImage readeImage(String filePath) {
        Map<Integer, Integer> set = new HashMap<>();
        List<PictureDot> pictureDots = new ArrayList<>();
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    int color = image.getRGB(j, i);
                    pictureDots.add(new PictureDot(j, i, image.getHeight(), image.getWidth(), color));
                    //printSimplePicture(color); //выводит понятный текст исключительно для простых изображений
                    if (set.get(image.getRGB(j, i)) != null) {
                        int value = set.get(image.getRGB(j, i)) + 1;
                        set.put(image.getRGB(j, i), value);
                    } else {
                        set.put(image.getRGB(j, i), 1);
                    }
                }
                System.out.println();
            }
            int removeKey = getKeyToMaxValue(set);
            set.remove(removeKey);
            int pictureKey = getKeyToMaxValue(set);
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    if (image.getRGB(j, i) == pictureKey) {
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

        return null;
    }

    private int getKeyToMaxValue (Map<Integer, Integer> map) {
        AtomicInteger maxKey = new AtomicInteger(0);
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

    private void printSimplePicture(int color) {
        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;
        if (blue > green && red > green) {
            System.out.print("1");
        } else if (blue > red && green > red) {
            System.out.print("2");
        } else if (green > blue && red > blue) {
            System.out.print("4");
        } else {
            System.out.print("*");
        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }


}
