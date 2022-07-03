public class SLList {
    private static class IntNode {
        public int item;
        public IntNode next;
        public IntNode(int item, IntNode next) {
            this.item = item;
            this.next = next;
        }
    }
    private IntNode first;
    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    /* takes in an integer x and inserts it at the given
    * position. If the position is after the end of the list,
    * insert the new node at the end.
    * For example, if the SLList is 5 → 6 → 2, insert(10, 1) results in 5 → 10 → 6 → 2.*/
    public void insert(int item, int position) {
        if (position == 0 || first == null) {
            addFirst(item);
            return;
        }
        IntNode current = first;
        while (current.next != null && position > 1) {
            current = current.next;
            position--;
        }
        IntNode newnode = new IntNode(item, current.next);
        current.next = newnode;
    }

    public void reverse() {
        first = reverseRecursiveHelper(first);
    }

    private IntNode reverseRecursiveHelper(IntNode front) {
        if (front == null || front.next == null) {
            return front;
        } else {
        IntNode reversed = reverseRecursiveHelper(front.next);
        front.next.next = front;
        front.next = null;
        return reversed;
        }
    }

    public int getFirst() {
        return first.item;
    }

    /** print out the SLList.*/
    public void printList() {
        IntNode cur = first;
        while (cur != null) {
            System.out.print(cur.item);
            System.out.print("->");
            cur = cur.next;
        }
        System.out.println("null");
    }

    public void addLast(int x) {
        IntNode p = first;

        /* Advance p to the end of the list. */
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }

    public static void main(String[] args) {
        SLList test = new SLList();
        test.addFirst(2);
        test.addFirst(6);
        test.addFirst(5);
        test.printList();
        //test.insert(10, 1);
        //test.printList();
        //test.insert(99,100);
        //test.printList();
        test.reverse();
        test.printList();
    }
}