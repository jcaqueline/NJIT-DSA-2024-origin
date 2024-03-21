package oy.tol.tra;  
  
import java.util.LinkedList;  
  
public class QueueImplementation<E> implements QueueInterface<E> {  
  
    private LinkedList<E> queue;  
    private int initialCapacity;  
    private int currentSize;  
  
    public QueueImplementation(int initialCapacity) {  
        if (initialCapacity < 0) {  
            throw new IllegalArgumentException("Initial capacity cannot be negative");  
        }  
        this.initialCapacity = initialCapacity;  
        this.queue = new LinkedList<>();  
        this.currentSize = 0;  
    }  
  
    @Override  
    public int capacity() {  
        return initialCapacity;  
    }  
    private void expandCapacity() {  
       
        initialCapacity *= 2;  
       
        LinkedList<E> newQueue = new LinkedList<>(queue);  
        queue = newQueue;  
    }  
  
    @Override  
    public void enqueue(E element) throws QueueAllocationException, NullPointerException {  
        if (element == null) {  
            throw new NullPointerException("Element cannot be null");  
        }  
        if (currentSize >= initialCapacity) {  
            expandCapacity(); 
        }  
        queue.addLast(element);  
        currentSize++;  
    }  
  
    @Override  
    public E dequeue() throws QueueIsEmptyException {  
        if (isEmpty()) {  
            throw new QueueIsEmptyException("Queue is empty");  
        }  
        return queue.removeFirst();  
    }  
  
    @Override  
    public E element() throws QueueIsEmptyException {  
        if (isEmpty()) {  
            throw new QueueIsEmptyException("Queue is empty");  
        }  
        return queue.getFirst();  
    }  
  
    @Override  
    public int size() {  
        return queue.size();  
    }  
  
    @Override  
    public boolean isEmpty() {  
        return queue.isEmpty();  
    }  
  
    @Override  
    public void clear() {  
        queue.clear();  
    }  
    @Override  
    public String toString() {  
       
        return queue.toString();  
    }  
}
