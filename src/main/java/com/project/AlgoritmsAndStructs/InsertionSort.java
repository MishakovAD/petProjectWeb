package com.project.AlgoritmsAndStructs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InsertionSort {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(4, 5);
        map.put(5, 8);
        map.put(8, 3);
        map.put(10, 4);
        map.put(3, 7);
        Map<Integer, Integer> sortedMap = sortByKey(map, true);
        sortedMap.forEach((k, v) -> {
            System.out.println("Key = " + k + " - Value = " + v);
        });
    }

    public static <K extends Object, V extends Object> Map<K, V> sort(Map<K, V> map, boolean byValue) {
        return sort(map, byValue, true);
    }

    public static <K extends Object, V extends Object> Map<K, V> sort(Map<K, V> map, boolean byValue, boolean ASC) {
        if (byValue) {
            return sortByValue(map, ASC);
        } else {
            return sortByKey(map, ASC);
        }
    }

    public static <K extends Object, V extends Object> Map<K, V> sortByValue(Map<K, V> map) {
        return sortByValue(map, true);
    }

    public static <K extends Object, V extends Object> Map<K, V> sortByValue(Map<K, V> map, boolean ASC) {
        Map<K, V> sortedMap = new LinkedHashMap<>();
        List<V> valuesList = new LinkedList<>(map.values());
        List<V> sortedValueSet = sort(valuesList, ASC);
        sortedValueSet.stream().forEach(value -> {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                K key = entry.getKey();
                V val = entry.getValue();
                if (val.equals(value)) {
                    sortedMap.put(key, val);
                    map.remove(key);
                    break;
                }
            }
        });
        return sortedMap;
    }

    public static <K extends Object, V extends Object> Map<K, V> sortByKey(Map<K, V> map) {
        return sortByKey(map, true);
    }

    public static <K extends Object, V extends Object> Map<K, V> sortByKey(Map<K, V> map, boolean ASC) {
        Map<K, V> sortedMap = new LinkedHashMap<>();
        List<K> keySet = new LinkedList<>(map.keySet());
        List<K> sortedKeySet = sort(keySet, ASC);
        sortedKeySet.stream().forEach(key -> sortedMap.put(key, map.get(key)));
        return sortedMap;
    }

    public static <T extends Object> List<T> sort(List<T> list) {
        return sort(list, true);
    }

    public static <T extends Object> List<T> sort(List<T> list, boolean ASC) {
        List<T> sortedList = new LinkedList<>();
        int len = list.size();
        if (list.get(0) instanceof Integer) {
            Integer[] arr = new Integer[len];
            for (int i = 0; i < len; i++) {
                arr[i] = (int) list.get(i);
            }
            arr = sort(arr, ASC);
            Arrays.stream(arr).forEach(elem -> sortedList.add((T) elem));
            return sortedList;
        } else if (list.get(0) instanceof Double) {
            Double[] arr = new Double[len];
            for (int i = 0; i < len; i++) {
                arr[i] = (double) list.get(i);
            }
            arr = sort(arr, ASC);
            Arrays.stream(arr).forEach(elem -> sortedList.add((T) elem));
            return sortedList;
        } else if (list.get(0) instanceof String) {
            String[] arr = new String[len];
            for (int i = 0; i < len; i++) {
                arr[i] = (String) list.get(i);
            }
            arr = sort(arr, ASC);
            Arrays.stream(arr).forEach(elem -> sortedList.add((T) elem));
            return sortedList;
        } else if (list.get(0) instanceof Character) {
            Character[] arr = new Character[len];
            for (int i = 0; i < len; i++) {
                arr[i] = (char) list.get(i);
            }
            arr = sort(arr, ASC);
            Arrays.stream(arr).forEach(elem -> sortedList.add((T) elem));
            return sortedList;
        } else if (list.get(0) instanceof Float) {
            Float[] arr = new Float[len];
            for (int i = 0; i < len; i++) {
                arr[i] = (float) list.get(i);
            }
            arr = sort(arr, ASC);
            Arrays.stream(arr).forEach(elem -> sortedList.add((T) elem));
            return sortedList;
        } else {
            return list.stream().sorted().collect(Collectors.toList());
        }
    }

    public static <T extends Object> T[] sort(T[] arr) {
        return sort(arr, true);
    }

    public static <T extends Object> T[] sort(T[] arr, boolean ASC) {
        if (ASC) {
            return sortByASC(arr);
        } else {
            return sortByDESC(arr);
        }
    }

    public static <T extends Object> T[] sortByASC(T[] arr) {
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

    public static <T extends Object> T[] sortByDESC(T[] arr) {
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

    public static int[] sort(int[] arr) {
        return sort(arr, true);
    }

    public static int[] sort(int[] arr, boolean ASC) {
        if (ASC) {
            return sortByASC(arr);
        } else {
            return sortByDESC(arr);
        }
    }

    public static int[] sortByASC(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0) {
                if (arr[j] > arr[j+1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
                j = j - 1;
            }
        }
        return arr;
    }

    public static int[] sortByDESC(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0) {
                if (arr[j] < arr[j+1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
                j = j - 1;
            }
        }
        return arr;
    }

    public static double[] sort(double[] arr) {
        return sort(arr, true);
    }

    public static double[] sort(double[] arr, boolean ASC) {
        if (ASC) {
            return sortByASC(arr);
        } else {
            return sortByDESC(arr);
        }
    }

    public static double[] sortByASC(double[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0) {
                if (arr[j] > arr[j+1]) {
                    double tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
                j = j - 1;
            }
        }
        return arr;
    }

    public static double[] sortByDESC(double[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0) {
                if (arr[j] < arr[j+1]) {
                    double tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
                j = j - 1;
            }
        }
        return arr;
    }

    public static char[] sort(char[] arr) {
        return sort(arr, true);
    }

    public static char[] sort(char[] arr, boolean ASC) {
        if (ASC) {
            return sortByASC(arr);
        } else {
            return sortByDESC(arr);
        }
    }

    public static char[] sortByASC(char[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0) {
                if (arr[j] > arr[j+1]) {
                    char tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
                j = j - 1;
            }
        }
        return arr;
    }

    public static char[] sortByDESC(char[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0) {
                if (arr[j] < arr[j+1]) {
                    char tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
                j = j - 1;
            }
        }
        return arr;
    }

    public static String[] sort(String[] arr) {
        return sort(arr, true);
    }

    public static String[] sort(String[] arr, boolean ASC) {
        if (ASC) {
            return sortByASC(arr);
        } else {
            return sortByDESC(arr);
        }
    }

    public static String[] sortByASC(String[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0) {
                char[] firstWordArr = arr[j].toCharArray();
                char[] secondWordArr = arr[j+1].toCharArray();
                int minLength = firstWordArr.length > secondWordArr.length ? secondWordArr.length : firstWordArr.length;
                for (int ind = 0; ind < minLength; ind++) {
                    if (firstWordArr[ind] == secondWordArr[ind]) {
                        continue;
                    } else if (firstWordArr[ind] > secondWordArr[ind]) {
                        String tmp = arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = tmp;
                        break;
                    } else {
                        break;
                    }
                }
                j = j - 1;
            }
        }
        return arr;
    }

    public static String[] sortByDESC(String[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0) {
                char[] firstWordArr = arr[j].toCharArray();
                char[] secondWordArr = arr[j+1].toCharArray();
                int minLength = firstWordArr.length > secondWordArr.length ? secondWordArr.length : firstWordArr.length;
                for (int ind = 0; ind < minLength; ind++) {
                    if (firstWordArr[ind] == secondWordArr[ind]) {
                        continue;
                    } else if (firstWordArr[ind] < secondWordArr[ind]) {
                        String tmp = arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = tmp;
                        break;
                    } else {
                        break;
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
