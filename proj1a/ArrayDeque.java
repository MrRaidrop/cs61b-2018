public class ArrayDeque<T> {
    private T[] items;
    //how many items in items
    private int size;
    private int head;
    private int tail;

    /**
     * Creates an empty list.
     */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }
    /**follow the auto-grader's guide.
     public ArrayDeque(ArrayDeque other) {
     items = (T[]) new Object[other.items.length];
     size = other.size;
     System.arraycopy(other.items, 0, items, 0, size);
     }*/

    /**
     * Resizes the underlying array to the target capacity.
     */

    // 扩容为原来的2倍
    private void doubleCapacity() {
        assert head == tail;
        int p = head;
        int n = items.length;
        int r = n - p; // number of elements to the right of p
        int newCapacity = n << 1;
        if (newCapacity < 0)
            throw new IllegalStateException("Sorry, deque too big");
        Object[] a = new Object[newCapacity];
        // 既然是head和tail已经重合了，说明tail是在head的左边。
        System.arraycopy(items, p, a, 0, r); // 拷贝原数组从head位置到结束的数据
        System.arraycopy(items, 0, a, r, p); // 拷贝原数组从开始到head的数据
        items = (T[]) a;
        head = 0; // 重置head和tail为数据的开始和结束索引
        tail = n;
    }

    /**
     * add a T item to the first of the Deque and return nothing.
     */
    public void addFirst(T t) {
        if (t == null)
            throw new NullPointerException();
        // 本来可以简单地写成head-1，但如果head为0，减1就变为-1了，
        // 和elements.length - 1进行与操作就是为了处理这种情况，这时结果为elements.length - 1。
        items[head = (head - 1) & (items.length - 1)] = t;
        if (head == tail) {
            doubleCapacity();// head和tail不可以重叠
        }
    }

    /**
     * add a T item to the last of the Deque and return nothing.
     */
    public void addLast(T t) {
        if (t == null)
            throw new NullPointerException();
        // tail位置是空的，把元素放到这。
        items[tail] = t;
        // 和head的操作类似，为了处理临界情况 (tail为length - 1时)，和length - 1进行与操作，结果为0。
        if ((tail = (tail + 1) & (items.length - 1)) == head) {
            doubleCapacity();
        }
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return head == tail;
    }

    /**
     * returns the number of items in the deque
     */
    public int size() {
        return (tail - head) & (items.length - 1);
    }

    public T removeFirst() {
        T x = pollFirst();
        if (x == null)
            return null;
        return x;
    }

    private T pollFirst() {
        int h = head;
        T result = items[h]; // Element is null if deque empty
        if (result == null)
            return null;
        // 表明head位置已为空
        items[h] = null;     // Must null out slot
        head = (h + 1) & (items.length - 1); // 处理临界情况（当h为elements.length - 1时），与后的结果为0。
        return result;
    }

    public T removeLast() {
        T x = pollLast();
        if (x == null) {
            return null;
        }
        return x;
    }

    private T pollLast() {
        int t = (tail - 1) & (items.length - 1); // 处理临界情况（当tail为0时），与后的结果为elements.length - 1。
        T result = items[t];
        if (result == null)
            return null;
        items[t] = null;
        tail = t; // tail指向的是下个要添加元素的索引。
        return result;
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
     * Gets the index th item in the list (0 is the front).
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return (T) items[index];
    }
    private T getFirst() {
        T x = items[head];
        if (x == null)
            return null;
        return x;
    }
    private T getLast() {
        // 处理临界情况（当tail为0时），与后的结果为elements.length - 1。
        T x = items[(tail - 1) & (items.length - 1)];
        if (x == null)
            return null;
        return x;
    }


}



