package oy.tol.tra;

/**
 * This class instantiates different types of stacks implementing the {@code StackInterface} interface.
 * <p>
 
 * objects in the this task.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class StackFactory {

   private StackFactory() {
   }

   public static StackInterface<Integer> createIntegerStack() {
      return new StackImplementation<>();
   }

   /**
    * Creates an instance of StackImplementation for Integer type.
    * @param capacity Number of elements the stack can hold.
    * @return The stack object.
    */
   public static StackInterface<Integer> createIntegerStack(int capacity) {
      return new StackImplementation<>(capacity);
   }

   /**
    * Instantiates a stack of Characters using the stack default constructor.
  
    * @return The stack implementation holding Characters.
    */
   public static StackInterface<Character> createCharacterStack() {
       return new StackImplementation<>();
   }

   /**
    * Instantiates a stack of Characters using the stack default constructor.
   
    * @return The stack implementation holding Characters.
    */
    public static StackInterface<Character> createCharacterStack(int capacity) {

      return new StackImplementation<>(capacity);
   }
}
