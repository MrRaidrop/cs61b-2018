
public class ArrayDeque<T> {
    private T[] items;
    private int size;

    /**
     * Creates an empty list.
     */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }
    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[other.items.length];
        size = other.size;
        System.arraycopy(other.items, 0, items, 0, size);
    }

    /**
     * Resizes the underlying array to the target capacity.
     */
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 1, size);
        items = a;
    }

    /** check the size of the array, if it reaches the capacity resize it.
     * if its usage factor is lower than 25%, resize it.*/
    private void capacitycheck() {
        if (size == items.length) {
            resize(size * 2);
        }
        if (items.length >= 16) {
            if (size < items.length / 4) {
                resize(items.length / 2);
            }
        }
    }

    /**
     * add a T item to the first of the Deque and return nothing.
     */
    public void addFirst(T i) {
        capacitycheck();
        T[] A = (T[]) new Object[items.length + 1];
        A[0] = i;
        System.arraycopy(items, 0, A, 1, size);
        items = A;
        size += 1;
    }

    /**
     * add a T item to the last of the Deque and return nothing.
     */
    public void addLast(T x) {
        capacitycheck();
        items[size] = x;
        size = size + 1;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * eturns the number of items in the deque
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last,
     * separated by a space. Once all the items have been printed,
     * print out a new line.
     */
    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            System.out.println(items[i] + " ");
        }
        System.out.println();
    }

    /**
     * Deletes item from front of the list and
     * returns deleted item.
     */
    public T removeFirst() {
        T res = items[0];
        T[] A = (T[]) new Object[items.length - 1];
        items[0] = null;
        size = size - 1;
        System.arraycopy(items, 1, A, 0, size);
        items = A;
        return res;
    }


    /**
     * Deletes item from back of the list and
     * returns deleted item.
     */
    public T removeLast() {
        T res = items[size - 1];
        items[size - 1] = null;
        size = size - 1;
        return res;
    }

    /**
     * Gets the index th item in the list (0 is the front).
     */
    public T get(int index) {
        if (index <= 0 || index > size) {
            return null;
        }
        return items[index - 1];
    }

/**
 public T[] insert(T item, int position) {
 if (position > size) {
 addLast(item);
 return items;
 }
 T[] A = (T[]) new Object[items.length];
 System.arraycopy(items, 0, A, 0, position);
 A[position] = item;
 System.arraycopy(items, position, A, position+1, size-position);
 size = size + 1;
 items = A;
 return A;
 }

 public void reverse() {
 for(int i=0; i < size/2; i++) {
 T temp = items[i];
 items[i] = items[size-i-1];
 items[size-1-i] = temp;
 }
 }
 */
}



