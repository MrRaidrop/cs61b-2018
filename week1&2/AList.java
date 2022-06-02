
public class AList<Item> {
    private Item[] items;
    private int size;

    /** Creates an empty list. */
    public AList() {
        items = (Item[]) new Object[100];
        size = 0;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    /** Inserts X into the back of the list. */
    public void addLast(Item x) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[size] = x;
        size = size + 1;
    }

    /** Returns the item from the back of the list. */
    public Item getLast() {
        return items[size - 1];
    }
    /** Gets the ith item in the list (0 is the front). */
    public Item get(int i) {
        return items[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    public Item[] insert(Item item, int position) {
        if (position > size) {
            addLast(item);
            return items;
        }
        Item[] a = (Item[]) new Object[items.length];
        System.arraycopy(items, 0, a, 0, position);
        a[position] = item;
        System.arraycopy(items, position, a, position+1, size-position);
        size = size + 1;
        items = a;
        return a;
    }

    public void reverse() {
        for(int i=0; i < size/2; i++) {
            Item temp = items[i];
            items[i] = items[size-i-1];
            items[size-1-i] = temp;
        }
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public Item removeLast() {
        Item x = getLast();
        items[size - 1] = null;
        size = size - 1;
        return x;
    }
} 