package oy.tol.tra;


public class Algorithms{
   
    
    public static <T extends Comparable<T>> void sort(T [] array){
        if(null != array && array.length > 1) {
			int len = array.length;
			boolean isSwaped = true;
			T temp = null;
			for(int i=0;i<len && isSwaped;i++) {
				isSwaped = false;
				for(int j=0;j<len-1-i;j++) {
					if(array[j].compareTo(array[j+1]) > 0) {
						temp = array[j];
						array[j] = array[j+1];
						array[j+1] = temp;
						isSwaped = true;
					}
				}
			}
		}

    }
    public static <T> void reverse(T [] array) {
        int i = 0;
        while ( i< array.length/2) {
            T temp = array[i];
            array[i] = array[array.length-i-1];
            array[array.length-i-1] = temp;
            i++;
        }
    }

}
