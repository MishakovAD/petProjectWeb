package com.project.AlgoritmsAndStructs;

import java.util.Arrays;

public class MergeSort<T extends Object> {
    public static void main(String[] args) {
        int[] arr = new int[] {1, 5, 6, 3, 23, 45, 2, 1, 23};
        arr = new MergeSort().mergeSort(arr);
        Arrays.stream(arr).forEach(System.out::println);
        System.out.println();
    }

    public int[] mergeSort(int[] arr) {
        int len = arr.length;
        if (len < 2) {
            return arr;
        }
        int[] left = new int[len/2];
        int[] right = new int[len - len/2];
        System.arraycopy(arr, 0, left, 0, len/2);
        System.arraycopy(arr, len/2, right, 0, len-len/2);
        left = mergeSort(left);
        right = mergeSort(right);
        return merge(left, right);
    }

    private int[] merge(int[] left, int[] right, int[] ... ints) {
        int leftLen = left.length;
        int rightLen = left.length;
        int i = 0;
        int j = 0;
        int index = 0;
        int[] arr = new int[leftLen + rightLen];
        while (i < leftLen || j < rightLen) {
            if ((j == rightLen) || (i < leftLen && left[i] < right[j])) {
                arr[index] = left[i];
                i++;
                index++;
            } else {
                arr[index] = right[j];
                j++;
                index++;
            }
        }
        return arr;
    }

}
