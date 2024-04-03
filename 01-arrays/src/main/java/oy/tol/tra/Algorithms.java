package oy.tol.tra;


public class Algorithms{
   
    
    public static <T> void reverse(T [] array) {
        int i = 0;
        while (i < array.length/2) {
            swap(array,i,array.length-i-1);
            i++;
        }
    }


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
	public static <T> void swap(T[] array,int i,int j){
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
