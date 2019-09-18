package com.project.AlgoritmsAndStructs.Utils;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс, позволяющий сортировать данные удобным для нас способом.
 */
public interface SortI {
    <T extends Object> T[] insertionSort(T[] arr, boolean ASC);
    <T extends Object> List<T> insertionListSort(List<T> list, boolean ASC);
    <K extends Object, V extends Object> Map<K, V> insertionMapSort(Map<K, V> map, boolean byValue, boolean ASC);
}
