package oy.tol.tra;

import java.util.Arrays;

public class Algorithms {
    public static int binarySearch(int key, Integer[] array, int low, int high) {  
        if (high >= low) {  
            int mid = low + (high - low) / 2;  
  
            // If the element is present at the middle itself  
            if (array[mid] == key) {  
                return mid;  
            }  
  
            // If element is smaller than mid, then it can only be present in left subarray  
            if (array[mid] > key) {  
                return binarySearch(key, array, low, mid - 1);  
            }  
  
            // Else the element can only be present in right subarray  
            return binarySearch(key, array, mid + 1, high);  
        }  
  
        // We reach here when element is not present in array  
        return -1;  
    }  
    public static void fastSort(Integer[] array) {  
        int n = array.length;  
        for (int i = 0; i < n - 1; i++) {  
            for (int j = 0; j < n - i - 1; j++) {  
                if (array[j].compareTo(array[j + 1]) > 0) {  
                    // Swap array[j+1] and array[j]  
                    Integer temp = array[j];  
                    array[j] = array[j + 1];  
                    array[j + 1] = temp;  
                }  
            }  
        }  
    }  
     public static void fastSort(String[] array) {  
        Arrays.sort(array, String.CASE_INSENSITIVE_ORDER);   
    }  
    public static int binarySearch(String key, String[] array, int fromIndex, int toIndex) {  
        return Arrays.binarySearch(array, fromIndex, toIndex, key);  
    }  
    public static <T> int linearSearch(T value, T[] array, int fromIndex, int toIndex) {  
        for (int index = fromIndex; index < toIndex; index++) {  
            if (value.equals(array[index])) {  
                return index;  
            }  
        }  
        return -1;  
    }  
  
   
    public static <T> int linearSearch(T value, T[] array) {  
        return linearSearch(value, array, 0, array.length);  
    }  
}
