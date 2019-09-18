package com.project.AlgoritmsAndStructs;

import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        int[] arr = new int[5];
        arr[0] = 5; arr[1] = 1; arr[2] = 12; arr[3] = 3; arr[4] = 0;
        for (int i = 1; i < arr.length; i++) { //начало цикла со второго элемента
            int j = i - 1; //берем предыдущий элемент
            while (j >= 0) { //проходим по всем значениям нашей части массива
                if (arr[j] > arr[j+1]) { //если элементы "разные", меняем их местами
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
                j = j - 1; //уменьшаем  порядковый номер проверяемого элемента на 1
                //и так идем до конца списка, затем, каждый раз, берем все большие
                //и большие отрезки массива, пока не дойдем до полного размера
            }
        }
        Arrays.stream(arr).forEach(System.out::println);
    }
}
