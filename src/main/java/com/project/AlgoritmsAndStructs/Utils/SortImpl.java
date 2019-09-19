package com.project.AlgoritmsAndStructs.Utils;

import com.project.AlgoritmsAndStructs.InsertionSort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SortImpl implements SortI {
    @Override
    public <T> T[] insertionSort(T[] arr, boolean ASC) {
        return InsertionSort.sort(arr, ASC);
    }

    @Override
    public <T> List<T> insertionListSort(List<T> list, boolean ASC) {
        return InsertionSort.sort(list, ASC);
    }

    @Override
    public <K, V> Map<K, V> insertionMapSort(Map<K, V> map, boolean byValue, boolean ASC) {
        return InsertionSort.sort(map, byValue, ASC);
    }
}
