package oy.tol.tra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class KeyValueHashTable<K extends Comparable<K>, V> implements Dictionary<K, V> {

    // This should implement a hash table.
  // This should implement a hash table.

  private Pair<K, V>[] values = null;
  private int count = 0;
  private int collisionCount = 0;
  private int maxProbingSteps = 0;
  private int reallocationCount = 0;
  private static final double LOAD_FACTOR = 0.45;
  private static final int DEFAULT_CAPACITY = 20;

  public KeyValueHashTable(int capacity) throws OutOfMemoryError {
      ensureCapacity(capacity);
  }

  public KeyValueHashTable() throws OutOfMemoryError {
      ensureCapacity(DEFAULT_CAPACITY);
  }

  @Override
  public Type getType() {
      return Type.HASHTABLE;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void ensureCapacity(int capacity) throws OutOfMemoryError {
      if (capacity < DEFAULT_CAPACITY) {
          capacity = DEFAULT_CAPACITY;
      }
      // Assuming capacity means the count of elements to add, so multiplying by fill factor.
      values = (Pair<K, V>[]) new Pair[(int) ((double) capacity * (1.0 + LOAD_FACTOR))];
      reallocationCount = 0;
      count = 0;
      collisionCount = 0;
      maxProbingSteps = 0;
  }

  @Override
  public int size() {
      
      return count;
  }

  
  @Override
  public String getStatus() {
      StringBuilder builder = new StringBuilder();
      builder.append(String.format("Hash table load factor is %.2f%n", LOAD_FACTOR));
      builder.append(String.format("Hash table capacity is %d%n", values.length));
      builder.append(String.format("Current fill rate is %.2f%%%n", (count / (double)values.length) * 100.0));
      builder.append(String.format("Hash table had %d collisions when filling the hash table.%n", collisionCount));
      builder.append(String.format("Hash table had to probe %d times in the worst case.%n", maxProbingSteps));
      builder.append(String.format("Hash table had to reallocate %d times.%n", reallocationCount));
      return builder.toString();
  }

  @Override
  public boolean add(K key, V value) throws IllegalArgumentException, OutOfMemoryError {
      if (null == key || value == null) throw new IllegalArgumentException("Person or phone number cannot be null");
      // Checks if the LOAD_FACTOR has been exceeded --> if so, reallocates to a bigger hashtable.
      if (((double)count * (1.0 + LOAD_FACTOR)) >= values.length) {
          reallocate((int)((double)(values.length) * (1.0 / LOAD_FACTOR)));
      }
      int hashCode = key.hashCode();
      int index = calculateIndexByHC(hashCode,key);
      if(index == -1){
          return false;
      }
      if (values[index]==null){
          count++;
      }
      values[index] = new Pair<>(key, value);

      return true;
  }

  @Override
  public V find(K key) throws IllegalArgumentException {
      // Remember to check for null.
      if (null==key) throw new IllegalArgumentException("Person to find cannot be null");
      // Must use same method for computing index as add method
      int hashCode = key.hashCode();
      int index = getIndexByHC(hashCode,key);
      if (index == -1){
          return null;
      }
      return values[index].getValue();
  }

  @Override
  @java.lang.SuppressWarnings({"unchecked"})
  public Pair<K,V> [] toSortedArray() {
      List<Pair<K, V>> sortedList = new ArrayList<>(count);  
    for (Pair<K, V> pair : values) {  
        if (pair != null) {  
            sortedList.add(pair);  
        }  
    }  
    Pair<K, V>[] sortedArray = sortedList.toArray(new Pair[0]); 
    Arrays.sort(sortedArray, Comparator.comparing(Pair::getKey)); 
    return sortedArray;  
    }

  @SuppressWarnings("unchecked")
  private void reallocate(int newSize) throws OutOfMemoryError {
      if (newSize < DEFAULT_CAPACITY) {
          newSize = DEFAULT_CAPACITY;
      }
      reallocationCount++;
      Pair<K, V>[] oldPairs = values;
      this.values = (Pair<K, V>[]) new Pair[(int)((double)newSize * (1.0 + LOAD_FACTOR))];
      count = 0;
      collisionCount = 0;
      maxProbingSteps = 0;
      for (int index = 0; index < oldPairs.length; index++) {
          if (oldPairs[index] != null) {
              add(oldPairs[index].getKey(), oldPairs[index].getValue());
          }
      }
  }

  @Override
  public void compress() throws OutOfMemoryError {
      int newCapacity = (int)(count * (1.0 / LOAD_FACTOR));
          if (newCapacity < values.length) {
                reallocate(newCapacity);
          } 
  }

  private int calculateIndexByHC(int hashCode,K key){
      int index = Math.abs(hashCode) % values.length;

      int start = index;
      while (values[index] != null && !values[index].getKey().equals(key)) {
          index = (index + 1) % values.length;
          if (index == start) {
              return -1;
          }
      }
      return index;
  }

  private int getIndexByHC(int hashCode,K key){
      int index = Math.abs(hashCode) % values.length;

      int start = index;
      while (values[index] == null || !values[index].getKey().equals(key)) {
          index = (index + 1) % values.length;
          if (index == start) {
              return -1;
          }
      }
      return index;
  }
}
