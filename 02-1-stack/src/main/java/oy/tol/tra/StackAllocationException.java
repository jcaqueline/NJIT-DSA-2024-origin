package oy.tol.tra;

/**
 * An exception thrown by StackInterface implementations if the allocation for the
 * stack data structure fails.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class StackAllocationException extends RuntimeException {
   /**
    * Constructor for the exception.
    * @param message The message visible to developer or user.
    * @param e 
    */
  

   public StackAllocationException(String string) {
      super(string);
   } 
}
