package oy.tol.tra;

import java.util.Arrays;

/**
 * An implementation of the StackInterface.
 * <p>
 
 * 
 * Note that you need to implement construtor(s) for your concrete StackImplementation, which
 * allocates the internal Object array for the Stack:
 * - a default constructor, calling the StackImplementation(int size) with value of 10.
 * - StackImplementation(int size), which allocates an array of Object's with size.
 *  -- remember to maintain the capacity and/or currentIndex when the stack is manipulated.
 * @param <T>
 */
public class StackImplementation<E> implements StackInterface<E> {

   private Object [] itemArray;
   private int capacity;
   private int currentIndex = -1;
   private static final int DEFAULT_STACK_SIZE = 10;
   private int size;
  



   /**
    * Allocates a stack with a default capacity.
    * @throws StackAllocationException
    */
   public StackImplementation() throws StackAllocationException {
      this(DEFAULT_STACK_SIZE);
      
   }

   /** TODO: Implement so that
    * - if the size is less than 2, throw StackAllocationException
    * - if the allocation of the array throws with Java exception,
    *   throw StackAllocationException.
    * @param capacity The capacity of the stack.
    * @throws StackAllocationException If cannot allocate room for the internal array.
    */
   @SuppressWarnings("unchecked")
   public StackImplementation(int capacity) throws StackAllocationException {
      if (capacity < 2) {  
         throw new StackAllocationException("Capacity must be at least 2");  
     }  
     
     this.capacity = capacity; 
     this.itemArray = (E[]) new Object[capacity];
     this.size = 0;
     this.currentIndex = -1;
      
   }
   
   @Override
   public int capacity() {
     
      return  this.capacity; 
      
   }
  
   @SuppressWarnings({ "unchecked", "unused" })
   private void resize()throws StackAllocationException {  
      
      capacity *= 2; 
      E[] tempArray = (E[]) new Object[capacity]; 
      if (tempArray == null) {  
         throw new StackAllocationException("Failed to allocate memory for stack expansion");  
     }   
      System.arraycopy(itemArray, 0, tempArray, 0, size);  
      itemArray = tempArray;    
     
  }  
   @Override
   public void push(E element) throws StackAllocationException, NullPointerException {
      if (element == null) {  
         throw new NullPointerException("Cannot push null element into the stack");  
     }  
     if (size == capacity) {  
         resize();  
     }  
     itemArray[++currentIndex] = element;  
     size++;  
     
               
   }

   @SuppressWarnings("unchecked")
   @Override
   public E pop() throws StackIsEmptyException {
      if (currentIndex < 0) {  
        throw new StackIsEmptyException("Expecting to get StackIsEmptyException when popping from empty stack.");  
    }  
    E item = (E) itemArray[currentIndex]; 
    itemArray[currentIndex] = null;  
    currentIndex--;                   
    size--;                 
    return item;  
      
   }

   @SuppressWarnings("unchecked")
   @Override
   public E peek() throws StackIsEmptyException {
      if (currentIndex < 0) {    
         throw new StackIsEmptyException("Expecting to get StackIsEmptyException when peeking from empty stack");    
     }  
     return (E) itemArray[currentIndex];  
   }

   @Override
   public int size() {
     
      return currentIndex+1;
   }

   
   @Override
   public void clear() {
     
      currentIndex = -1;
      
   }

   @Override
   public boolean isEmpty() {
      if (currentIndex==-1){
         return true;
      }
      return false;
      
      
   }

   @Override
   public String toString() {
      if (isEmpty()) {  
         return "[]";  
     }  
      StringBuilder builder = new StringBuilder("[");
      for (var index = 0; index <= currentIndex; index++) {
         builder.append(itemArray[index].toString());
         if (index < currentIndex) {
            builder.append(", ");
         }
      }
      builder.append("]");
      return builder.toString();
   }
}
