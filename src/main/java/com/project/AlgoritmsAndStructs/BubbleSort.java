package com.project.AlgoritmsAndStructs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

public class BubbleSort implements Comparator {
    public <T extends Object> T[] bubbleSort(T[] array) {
        boolean isSwap = true;
        while (isSwap) {
            isSwap = false;
            for (int i = 0; i < array.length - 1; i++) {
                if (compare(array[i],array[i+1]) == 1) {
                    T tmp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = tmp;
                    isSwap = true;
                }
            }
        }
        return (T[]) array;
    }


    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof Integer && o2 instanceof Integer) {
            int o1Int = (int) o1;
            int o2Int = (int) o2;
            if (o1Int > o2Int) {
                return 1;
            }
        } else if (o1 instanceof String && o2 instanceof String) {
            String o1Str = (String) o1;
            String o2Str = (String) o2;
            if (o1Str.length() > o2Str.length()) {
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        String[] strArr = new String[]{"asd", "a", "asdfs", "rewwerqweds", "a", "as"};
        BubbleSort b = new BubbleSort();
        String[] sortedArr = b.bubbleSort(strArr);
        for (String str : sortedArr) {
            System.out.println(str);
        }
    }
}
