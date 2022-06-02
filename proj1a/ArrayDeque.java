
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

    /**
     * Resizes the underlying array to the target capacity.
     */
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 1, size);
        items = a;
    }

    private void capacitycheck() {
        if (size == items.length) {
            resize(size * 2);
        } else if (items.length >= 16) {
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
        System.arraycopy(items, 1, A, 0, size);
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
        for (Item i : items) {
            System.out.println(i + " ");
        }
        System.out.println();
    }

    /**
     * Deletes item from front of the list and
     * returns deleted item.
     */
    public T removeFirst() {
        T x = items[0];
        T[] A = (T[]) new Object[items.length - 1];
        items[0] = null;
        size = size - 1;
        System.arraycopy(items, 1, A, 0, size);
        return x;
    }


    /**
     * Deletes item from back of the list and
     * returns deleted item.
     */
    public T removeLast() {
        T x = items[size - 1];
        items[size - 1] = null;
        size = size - 1;
        return x;
    }

    /**
     * Gets the ith item in the list (0 is the front).
     */
    public T get(int i) {
        return items[i];
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



