package oy.tol.tra;  
  

  
public class QueueImplementation<E> implements QueueInterface<E> {  
  
    private Object [] itemArray; 
    private int initialCapacity;  
    private int currentSize;  
    private int head = 0;
    private int tail = 0;

    private static final int DEFAULT_QUEUE_SIZE = 10;
    public QueueImplementation() throws QueueAllocationException{
        this(DEFAULT_QUEUE_SIZE);
    }


  
    public QueueImplementation(int initialCapacity)throws QueueAllocationException {  
        if (initialCapacity<2){
            throw  new QueueAllocationException("Capacity must greater than 2");
        }
        itemArray = new Object[initialCapacity];  
        this.initialCapacity = initialCapacity;  
        this.currentSize = 0;  
    }  
  
    @Override  
    public int capacity() {  
        return initialCapacity;  
    }  
   
  
    @Override  
    public void enqueue(E element) throws QueueAllocationException, NullPointerException {  
        if (element == null){
            throw new NullPointerException("The element to enqueue can't be null");
        }
        
        if (currentSize >= initialCapacity){
            try {
                int newCapacity = 2 * initialCapacity;
                Object [] newArray = new Object[newCapacity];
                int i = 0;
               
                while (i<currentSize){
                    
                    if (head+i<initialCapacity){
                        newArray[i] = itemArray[head+i];
                    }else {
                   
                        newArray[i] = itemArray[i-(initialCapacity-head)];
                    }
                    i++;
                }
                itemArray = newArray;
                initialCapacity = newCapacity;
                
                head = 0;
               
                tail = currentSize;
            } catch (OutOfMemoryError e) {
                throw new QueueAllocationException("Failed to allocate more room for the stack.");
            }
        }
       
        itemArray[tail] = element;
        if (tail == initialCapacity-1){
            tail = 0;
        }else {
            tail = tail+1;
        }
        currentSize++;
    }

    
  
    @SuppressWarnings("unchecked")
    @Override  
    public E dequeue() throws QueueIsEmptyException {  
        if (isEmpty()) {  
            throw new QueueIsEmptyException("Queue is empty");  
        }  
        Object dequeueElement = itemArray[head];
        itemArray[head] = null;
        if (head == initialCapacity-1){
            head = 0;
        }else {
            head = head+1;
        }
        currentSize--;
        return (E) dequeueElement;
    }  
  
    @SuppressWarnings("unchecked")
    @Override  
    public E element() throws QueueIsEmptyException {  
        if (head == tail && currentSize != initialCapacity) {  
            throw new QueueIsEmptyException("Queue is empty");  
        }  
       
        Object abb = itemArray[head];
        return (E) abb;
    }  
  
    @Override  
    public int size() {  
        return currentSize;  
    }  
  
    @Override  
    public boolean isEmpty() {  
        if (head == tail && currentSize != initialCapacity){
            return true;
        }
        else return false;
    }  
  
    @Override  
    public void clear() {  
        for (int i=0;i <initialCapacity;i++){
            itemArray[i] = null;
        }
        head = 0;
        tail = 0;
        currentSize = 0;
    }  
    @Override  
    public String toString() {  
       
        StringBuilder builder = new StringBuilder("[");
        int i = 0;
        while (i<currentSize){
            if (head+i<initialCapacity){
                builder.append(itemArray[head+i].toString());
            }else {
                builder.append(itemArray[i-(initialCapacity-head)].toString());
            }
            if (i < currentSize-1) {
                builder.append(", ");
            }
            i++;
        }
        builder.append("]");
        return builder.toString();
    }
}

