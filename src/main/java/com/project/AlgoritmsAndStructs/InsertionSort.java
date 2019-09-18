package com.project.AlgoritmsAndStructs;

import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        Integer[] arr = new Integer[5];
        arr[0] = 5; arr[1] = 1; arr[2] = 12; arr[3] = 3; arr[4] = 0;

        Character[] arr2 = new Character[5];
        arr2[0] = 'f'; arr2[1] = 'a'; arr2[2] = 'V'; arr2[3] = 'g'; arr2[4] = 'A';

        String[] arr3 = new String[5];
        arr3[0] = "ads"; arr3[1] = "dfd"; arr3[2] = "abc"; arr3[3] = "gtfd"; arr3[4] = "dar";

        arr = (Integer[]) new InsertionSort().sort(arr, true);
        arr3 = (String[]) new InsertionSort().sort(arr3, false);
        Arrays.stream(arr).forEach(System.out::println);
    }

    private <T extends Object> T[] sort(T[] arr) {
        return sort(arr, true);
    }

    private <T extends Object> T[] sort(T[] arr, boolean ASC) {
        if (ASC) {
            return sortByASC(arr);
        } else {
            return sortByDESC(arr);
        }
    }

    private <T extends Object> T[] sortByASC(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0) {
                if (arr[j] instanceof Integer) {
                    if ((int) arr[j] > (int) arr[j+1]) {
                        T tmp = (T) arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = tmp;
                    }
                } else if (arr[j] instanceof Double) {
                    if ((double) arr[j] > (double) arr[j+1]) {
                        T tmp = (T) arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = tmp;
                    }
                }
                else if (arr[j] instanceof String) {
                    char[] firstWordArr = arr[j].toString().toCharArray();
                    char[] secondWordArr = arr[j+1].toString().toCharArray();
                    int minLength = firstWordArr.length > secondWordArr.length ? secondWordArr.length : firstWordArr.length;
                    for (int ind = 0; ind < minLength; ind++) {
                        if (firstWordArr[ind] == secondWordArr[ind]) {
                            continue;
                        } else if (firstWordArr[ind] > secondWordArr[ind]) {
                            T tmp = arr[j];
                            arr[j] = arr[j+1];
                            arr[j+1] = tmp;
                            break;
                        } else {
                            break;
                        }
                    }
                }
                else if (arr[j] instanceof Character) {
                    if ((char) arr[j] > (char) arr[j+1]) {
                        T tmp = (T) arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = tmp;
                    }
                } else if (arr[j] instanceof Float) {
                    if ((float) arr[j] > (float) arr[j+1]) {
                        T tmp = (T) arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = tmp;
                    }
                }
                j = j - 1;
            }
        }
        return arr;
    }

    private <T extends Object> T[] sortByDESC(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0) {
                if (arr[j] instanceof Integer) {
                    if ((int) arr[j] < (int) arr[j+1]) {
                        T tmp = (T) arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = tmp;
                    }
                } else if (arr[j] instanceof Double) {
                    if ((double) arr[j] < (double) arr[j+1]) {
                        T tmp = (T) arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = tmp;
                    }
                }
                else if (arr[j] instanceof String) {
                    char[] firstWordArr = arr[j].toString().toCharArray();
                    char[] secondWordArr = arr[j+1].toString().toCharArray();
                    int minLength = firstWordArr.length > secondWordArr.length ? secondWordArr.length : firstWordArr.length;
                    for (int ind = 0; ind < minLength; ind++) {
                        if (firstWordArr[ind] == secondWordArr[ind]) {
                            continue;
                        } else if (firstWordArr[ind] < secondWordArr[ind]) {
                            T tmp = arr[j];
                            arr[j] = arr[j+1];
                            arr[j+1] = tmp;
                            break;
                        } else {
                            break;
                        }
                    }
                }
                else if (arr[j] instanceof Character) {
                    if ((char) arr[j] < (char) arr[j+1]) {
                        T tmp = (T) arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = tmp;
                    }
                } else if (arr[j] instanceof Float) {
                    if ((float) arr[j] < (float) arr[j+1]) {
                        T tmp = (T) arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = tmp;
                    }
                }
                j = j - 1;
            }
        }
        return arr;
    }
}

/**
 * Описание самого алгоритма.
 *         int[] arr = new int[5];
 *         arr[0] = 5; arr[1] = 1; arr[2] = 12; arr[3] = 3; arr[4] = 0;
 *         for (int i = 1; i < arr.length; i++) { //начало цикла со второго элемента
 *             int j = i - 1; //берем предыдущий элемент
 *             while (j >= 0) { //проходим по всем значениям нашей части массива
 *                 if (arr[j] > arr[j+1]) { //если элементы "разные", меняем их местами
 *                     int tmp = arr[j];
 *                     arr[j] = arr[j+1];
 *                     arr[j+1] = tmp;
 *                 }
 *                 j = j - 1; //уменьшаем  порядковый номер проверяемого элемента на 1
 *                 //и так идем до конца списка, затем, каждый раз, берем все большие
 *                 //и большие отрезки массива, пока не дойдем до полного размера
 *             }
 *         }
 */
