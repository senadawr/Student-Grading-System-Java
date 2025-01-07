package com.grading.util;

import java.util.List;
import com.grading.model.Student;

public class SortingAlgorithms {
    
    public static void heapSort(List<Student> arr) {
        int n = arr.size();

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        for (int i = n - 1; i >= 0; i--) {
            Student temp = arr.get(0);
            arr.set(0, arr.get(i));
            arr.set(i, temp);

            heapify(arr, i, 0);
        }
    }

    private static void heapify(List<Student> arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr.get(left).getGwa() < arr.get(largest).getGwa())
            largest = left;

        if (right < n && arr.get(right).getGwa() < arr.get(largest).getGwa())
            largest = right;

        if (largest != i) {
            Student swap = arr.get(i);
            arr.set(i, arr.get(largest));
            arr.set(largest, swap);

            heapify(arr, n, largest);
        }
    }
}