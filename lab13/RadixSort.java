import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int max = Integer.MIN_VALUE;
        for (String s : asciis) {
            if (s.length() > max) {
                max = s.length();
            }
        }
        String[] res = asciis.clone();
        for (int i = max - 1; i >= 0; i--) {
            sortHelperLSD(res, i);
        }
        return res;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int base = 256;
        int[] counts = new int[base + 1];
        for (String s : asciis) {
            int cur = index >= s.length() ? 0 : s.charAt(index) + 1;
            counts[cur]++;
        }
        int[] starts = new int[base + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }
        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            String s = asciis[i];
            int c = index >= s.length() ? 0 : s.charAt(index) + 1;
            int place = starts[c];
            sorted[place] = s;
            starts[c]++;
        }

        for (int i = 0; i < asciis.length; i += 1) {
            asciis[i] = sorted[i];
        }
    }

    public static long StringtoLong(String s) {
        long res = 0;
        for (int i = s.length() - 1, j = 0; i >= 0; i--, j++) {
            res += s.charAt(i) * Math.pow(256, j);
        }
        return res;
    }
    public static String LongtoString(long a) {
        Stack<Character> chars= new Stack<>();
        while(a > 1) {
            char c = (char) (a % 256);
            chars.push(c);
            a = a / 256;
        }
        String res = "";
        while(!chars.empty()) {
            res += chars.pop();
        }
        return res;
    }


    public static void main(String[] args) {
        String[] strings = new String[] {"abfc", "bafaa", "cbafafa"};
        String[] res = sort(strings);
        for (String s : res) {
            System.out.println(s);
        }
    }


    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
