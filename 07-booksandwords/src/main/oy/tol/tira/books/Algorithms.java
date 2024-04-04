package oy.tol.tira.books;

import java.util.function.Predicate;

public class Algorithms {
    //The generic method for reversing an array: reverse
    public static <T> void reverse(T [] array) {
        int i = 0;
        while (i < array.length/2) {
            swap(array,i,array.length-i-1);
            i++;
        }
    }

    //Bubble sort is replaced by quick sort!!!
    //The generic method for sorting an array: sort (bubble sort)
    public static <T extends Comparable<T>> void sort(T [] array) {
        for (int i=0;i<array.length-1;i++){
            for (int j=0;j<array.length-1-i;j++){
                if (array[j].compareTo(array[j+1]) > 0) {
                    swap(array,j,j+1);
                }

            }
        }
    }

    public static <E extends Comparable<E>> void fastSort(E[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        quickSortHoare(array, 0, array.length - 1);
    }

    public static <E extends Comparable<E>> void quickSortHoare(E[] array, int begin, int end) {
        if (begin < end) {
            int pivotIndex = hoarePartition(array, begin, end);
            quickSortHoare(array, begin, pivotIndex);
            quickSortHoare(array, pivotIndex + 1, end);
        }
    }

    private static <E extends Comparable<E>> int hoarePartition(E[] array, int begin, int end) {
        E pivot = array[begin];
        int i = begin - 1;
        int j = end + 1;
        while (true) {
            do {
                i++;
            } while (array[i].compareTo(pivot) < 0);

            do {
                j--;
            } while (array[j].compareTo(pivot) > 0);

            if (i >= j) {
                return j;
            }
            swap(array, i, j);
        }
    }

    //The generic method used to swap two elements of an array: swap
    public static <T> void swap(T[] array,int i,int j){
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static <T extends Comparable<T>> int binarySearch(T aValue, T [] fromArray, int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            return -1;
        }

        int middle = (fromIndex+toIndex)/2;

        if (aValue.compareTo(fromArray[middle]) == 0) {
            return middle;
        }
        else if (aValue.compareTo(fromArray[middle]) < 0) {
            return binarySearch(aValue, fromArray, fromIndex, middle - 1);
        }
        else {
            return binarySearch(aValue, fromArray, middle + 1, toIndex);
        }

    }
    public static <T> int partitionByRule(T[] array, int count, Predicate<T> rule) {
        // Find first element rules applies to.
        // Index of that element will be in variable index.
        int index = 0;
        for (; index < count; index++) {
            if (rule.test(array[index])) {
                break;
            }
        }
        // If went to the end, nothing was selected so quit here.
        if (index >= count) {
            return count;
        }
        // Then start finding not selected elements starting from next from index.
        // If the element is not selected, swap it with the selected one.
        int nextIndex = index + 1;
        // Until end of array reached.
        while (nextIndex != count) {
            if (!rule.test(array[nextIndex])) {
                swap(array, index, nextIndex);
                // If swapping was done, add to index since now it has non-selected element.
                index++;
            }
            nextIndex++;
        }
        return index;
    }



}