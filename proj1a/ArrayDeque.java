public class ArrayDeque<T> implements Deque<Item>{
        /** array to save data.*/
        private T[] items;
        /** size of the deque. */
        private int size;

        /** size of the array. */
        private int length;

        /** front index. */
        private int front;

        /** back index. */
        private int back;

        /** constructor of ArrayDeque. */
        public ArrayDeque() {
            items = (T[]) new Object[8];
            size = 0;
            length = 8;
            front = 4;
            back = 4;
        }

        /** return if the deque is empty.
         * @return true if the deque is empty.
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /** return the size of the deque. */
        public int size() {
            return size;
        }

        /** return the "index - 1".
         * @param index index
         */
        private int minusOne(int index) {
            if (index == 0) {
                return length - 1;
            }
            return index - 1;
        }

        /** return the "index + 1".
         * @param index index
         */
        private int plusOne(int index, int module) {
            index %=  module;
            if (index == module - 1) {
                return 0;
            }
            return index + 1;
        }
        private void sizecheck() {
            if (size == length - 1) {
                grow();
            }
            if (length >= 16 && length / size >= 4) {
                shrink();
            }
        }

        private void grow() {
            T[] cur = (T[]) new Object[length * 2];
            int frontptr = front;
            int lengthptr = length;
            while (frontptr != back) {
                cur[lengthptr] = cur[frontptr];
                frontptr = plusOne(frontptr, length);
                lengthptr = plusOne(lengthptr, length * 2);
            }
            front = length;
            back = lengthptr;
            items = cur;
            length *= 2;
        }

        private void shrink() {
            T[] cur = (T[]) new Object[length / 2];
            int ptr1 = front;
            int ptr2 = length / 4;
            while (ptr1 != back) {
                cur[ptr2] = items[ptr1];
                ptr1 = plusOne(ptr1, length);
                ptr2 = plusOne(ptr2, length / 2);
            }
            front = length / 4;
            back = ptr2;
            items = cur;
            length = length / 2;
        }

        /** add one item at the front of the deque.
         * @param item the item we want to add
         */
        public void addFirst(T item) {
            sizecheck();
            front = minusOne(front);
            items[front] = item;
            size++;
        }

        /** add one item at the end of the deque.
         * @param item item we want to add
         */
        public void addLast(T item) {
            sizecheck();
            items[back] = item;
            back = plusOne(back, length);
            size++;
        }

        /** remove the first item.
         * @return the removed first item
         */
        public T removeFirst() {
            if (size == 0) {
                return null;
            }
            sizecheck();
            T res = items[front];
            front = plusOne(front, length);
            size--;
            return res;
        }

        /** remove the last item.
         * @return the removed last item
         */
        public T removeLast() {
            if (size == 0) {
                return null;
            }
            sizecheck();
            back = minusOne(back);
            size--;
            return items[back];
        }

        /** return the item indexed at index.
         * @param index index
         */
        public T get(int index) {
            if (index >= size) {
                return null;
            }
            int ptr = front;
            for (int i = 0; i < index; i++) {
                ptr = plusOne(ptr, length);
            }
            return items[ptr];
        }

        /** print the entire deque from front to end. */
        public void printDeque() {
            int ptr = front;
            while (ptr != back) {
                System.out.print(items[ptr] + " ");
                ptr = plusOne(ptr, length);
            }
        }

}


