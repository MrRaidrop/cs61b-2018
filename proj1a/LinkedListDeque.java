public class LinkedListDeque<T> {
    private TNode sentinel_front;
    private TNode sentinel_back;
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
        sentinel_front = new TNode((T) new Object(), null, null);
        sentinel_back = new TNode((T) new Object(), null, null);
        sentinel_front.next = sentinel_back;
        sentinel_back.prev = sentinel_front;
    }

    /** add a T item to the first of the Deque and return nothing.*/
    public void addFirst(T item) {
        TNode current = new TNode(item, sentinel_front, sentinel_front.next);
        TNode temp = sentinel_front.next;

        sentinel_front.next = current;
        size += 1;
    }

    /** add a T item to the last of the Deque and return nothing.*/
    public void addLast(T item) {
        TNode current = new TNode(item, sentinel_back.prev, sentinel_back);
        sentinel_back.prev.next = current;
        sentinel_back.prev = current;
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
        if (sentinel_front.next == sentinel_back) {
            return;
        }
        TNode cur = sentinel_front.next;
        while (cur.next != sentinel_back) {
            System.out.print(cur.item + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.*/
    public T removeFirst() {
        T res = get(1);
        sentinel_front.next.next.prev = sentinel_front;
        sentinel_front.next = sentinel_front.next.next;
        size -= 1;
        return res;
    }

    /**  Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.*/
    public T removeLast() {
        T res = get(size);
        sentinel_back.prev.prev.next = sentinel_front;
        sentinel_back.prev = sentinel_front.prev.prev;
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
        TNode cur = sentinel_front;
        int S = size;
        while (cur != sentinel_back) {
            if (S == 0) {
                return cur.item;
            }
            S -= 1;
            cur = cur.next;
        }
        return null;
    }


}
