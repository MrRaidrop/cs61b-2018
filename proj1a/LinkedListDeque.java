public class LinkedListDeque<T> {
    private TNode sentinelfront;
    private TNode sentinelback;
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
        sentinelfront = new TNode((T) new Object(), null, null);
        sentinelback = new TNode((T) new Object(), null, null);
        sentinelfront.next = sentinelback;
        sentinelback.prev = sentinelfront;
    }

    public LinkedListDeque(LinkedListDeque other) {
        size = 0;
        sentinelfront = new TNode((T) new Object(), null, null);
        sentinelback = new TNode((T) new Object(), null, null);
        sentinelfront.next = sentinelback;
        sentinelback.prev = sentinelfront;

        for (int i=0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }
    }

    /** add a T item to the first of the Deque and return nothing.*/
    public void addFirst(T item) {
        TNode current = new TNode(item, sentinelfront, sentinelfront.next);
        TNode temp = sentinelfront.next;

        sentinelfront.next = current;
        size += 1;
    }

    /** add a T item to the last of the Deque and return nothing.*/
    public void addLast(T item) {
        TNode current = new TNode(item, sentinelback.prev, sentinelback);
        sentinelback.prev.next = current;
        sentinelback.prev = current;
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
        if (sentinelfront.next == sentinelback) {
            return;
        }
        TNode cur = sentinelfront.next;
        while (cur.next != sentinelback) {
            System.out.print(cur.item + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.*/
    public T removeFirst() {
        T res = get(1);
        sentinelfront.next.next.prev = sentinelfront;
        sentinelfront.next = sentinelfront.next.next;
        size -= 1;
        return res;
    }

    /**  Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.*/
    public T removeLast() {
        T res = get(size);
        sentinelback.prev.prev.next = sentinelfront;
        sentinelback.prev = sentinelfront.prev.prev;
        size -= 1;
        return res;
    }

    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!*/
    public T get(int index) {
        if (index > size || index <= 0) {
            return null;
        }
        TNode cur = sentinelfront;
        int S = size;
        while (cur != sentinelback) {
            if (S == 0) {
                return cur.item;
            }
            S -= 1;
            cur = cur.next;
        }
        return null;
    }
}

