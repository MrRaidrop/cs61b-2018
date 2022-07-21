package bearmaps;

import java.util.Comparator;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>{
    //private Comparator<Item> comparator;
    private Node[] pq;
    int size;
    Set<T> itemSet = new HashSet<>();

    public ArrayHeapMinPQ(int initCapacity) {
        pq = new ArrayHeapMinPQ.Node[initCapacity];
        size = 0;
    }
    /** for basic function test
    public static void main(String[] args) {
        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>(4);
        test.add("111", 0);
        test.add("222", 1);
        test.add("333", 2);
        test.add("444", 3);
        test.add("555", -1);
        test.add("666", -5);
        test.add("777", 2);
        test.add("888", 1);
        test.add("999", 1);
        System.out.println(test.contains("444"));
        System.out.println(test.getIndex("666"));
        System.out.println(test.getIndex("555"));
        System.out.println(test.getIndex("444"));
        String[] t = new String[1000];
        for (int i = 1; i < test.size + 1; i++) {
            t[i] = test.pq[i].item;
        }
        PrintHeapDemo.printFancyHeapDrawing(t);
        test.changePriority("666", 5);
        for (int i = 1; i < test.size + 1; i++) {
            t[i] = test.pq[i].item;
        }
        PrintHeapDemo.printFancyHeapDrawing(t);
    }*/



    @Override
    public void add(T item, double priority) {
        resizeCheck();
        Node cur = new Node(item, priority);
        if (contains(item)) {
            throw new IllegalArgumentException("do as josh said, I think it's reasonable though");

        }
        pq[size + 1] = cur;
        size++;
        swim(size);
        itemSet.add(item);
    }

    @Override
    public boolean contains(T item) {
        return itemSet.contains(item);
    }

    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return pq[1].item;
    }

    // @source https://algs4.cs.princeton.edu/24pq/MinPQ.java.html
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Node min = pq[1];
        exchange(1, size);
        size--;
        // exchange the smallest and the last node, and sink the last
        sink(1);
        pq[size + 1] = null;     // to avoid loitering and help with garbage collection
        resizeCheck();
        return min.item;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("no this item so you can't change it! ");
        }
        int index = getIndex(item);
        double usedPriority = pq[index].getPriority();
        pq[index].setPriority(priority);
        double cmp = priority - usedPriority;
        // if the priority is bigger, the Node go down, vice versa.
        if (cmp > 0) {
            sink(index);
            return;
        }
        if (cmp == 0) {
            return;
        }
        if (cmp < 0) {
            swim(index);
        }
    }
    private int getIndex(T item) {
        return getHelper(item, 1);
    }
    private int getHelper(T item, int k) {
        if (k > size) {
            return 0;
        }
        if (pq[k] == null) {
            return 0;
        }
        if (pq[k].item.equals(item)) {
            return k;
        }
        return getHelper(item, 2 * k) + getHelper(item, 2 * k + 1);
    }

    //@source: NaiveMinPQ
    private class Node implements Comparable<Node> {

        private T item;
        private double priority;

        Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
        T getItem() {
            return this.item;
        }
        double getPriority() {
            return this.priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }
        @Override
        public int compareTo(Node other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }
    }

    // save memory
    private void resizeCheck() {
        if (size >= pq.length - 1) {
            resize(pq.length * 5);
            return;
        }
        if (size <= (pq.length - 1) / 4) {
            resize(pq.length / 2);
        }
    }
    private void resize(int newCap) {
        Node[] temp = new ArrayHeapMinPQ.Node[newCap];
        for (int i = 1; i <= size; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    //@source https://algs4.cs.princeton.edu/24pq/MinPQ.java.html
    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exchange(k/2, k);
            k = k/2;
        }
    }
    private void sink(int k) {
        while (2*k <= size) {
            int j = 2*k;
            if (j < size && greater(j, j+1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            exchange(k, j);
            k = j;
        }
    }

    //judge if a node is greater than other
    private boolean greater(int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }

    //exchange two nodes
    private void exchange(int i, int j) {
        Node temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }


}
