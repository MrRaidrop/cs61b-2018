public class LinkedListDeque<T> {
    private TNode sentinel;
    private int size;
    /* fundation of the List*/
    private class TNode {
        private TNode prev;
        private T item;
        private TNode next;

        TNode(T i, LinkedListDeque<T>.TNode p, LinkedListDeque<T>.TNode n) {
            item = i;
            prev = p;
            next = n;
        }

    }


    public LinkedListDeque() {
        size = 0;
        sentinel = new TNode((T) new Object(), null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }
    /**follow the auto-grader's guide.
    public LinkedListDeque(LinkedListDeque other) {
        size = 0;
        sentinelfront = new TNode((T) new Object(), null, null);
        sentinelback = new TNode((T) new Object(), null, null);
        sentinelfront.next = sentinelback;
        sentinelback.prev = sentinelfront;

        for (int i = 0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }
    }*/

    /** add a T item to the first of the Deque and return nothing.*/
    public void addFirst(T item) {
        TNode current = new TNode(item, sentinel, sentinel.next);
        sentinel.next.prev = current;
        sentinel.next = current;
        size += 1;
    }

    /** add a T item to the last of the Deque and return nothing.*/
    public void addLast(T item) {
        TNode current = new TNode(item, sentinel.prev, sentinel);
        sentinel.prev.next = current;
        sentinel.prev = current;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        return size == 0;
    }

    /** eturns the number of items in the deque */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last,
     * separated by a space. Once all the items have been printed,
     *  print out a new line.*/
    public void printDeque() {
        if (sentinel.next == sentinel) {
            return;
        }
        TNode cur = sentinel.next;
        while (cur.next != sentinel) {
            System.out.print(cur.item + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.*/
    public T removeFirst() {
        if (sizecheck()) {
            return null;
        }
        T res = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return res;
    }

    /**  Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.*/
    public T removeLast() {
        if (sizecheck()) {
            return null;
        }
        T res = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return res;
    }

    private boolean sizecheck() {
        return size == 0;
    }

    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!*/
    public T get(int index) {
        if (index > size || index < 0) {
            return null;
        }
        TNode cur = sentinel.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.item;
    }

    /** get the index th item use recursion.*/
    public T getRecursive(int index) {
        if (index > size || index < 0) {
            return null;
        }
        return getRecursicehelper(index, sentinel.next);
    }
    private T getRecursicehelper(int index, TNode cur) {
        if (index == 0) {
            return cur.item;
        }
        return getRecursicehelper(index - 1, cur.next);
    }
}

