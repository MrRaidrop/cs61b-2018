public class Palindrome {
    /**turn a given string into a Deque.*/
    public Deque<Character> wordToDeque(String word) {
        int wordlength = word.length();
        if (wordlength == 0 || word == null) {
            return null;
        }
        Deque<Character> res = new ArrayDeque<>();
        for(int i = 0; i < wordlength; i++) {
            res.addLast(word.charAt(i));
        }
        return res;
    }

    /** take in a String and return if the String is a Palindrome or not.*/
    public boolean isPalindrome(String word) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        Deque d = wordToDeque(word);
        while (d.size() > 1) {
            if (d.removeFirst() != d.removeLast()) {
                return false;
            }
        }
        return true;
    }

    /** take in a String and return if the String is a Palindrome or not by recursion.
    public boolean isPalindromebyRecursion(String word) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        Deque d = wordToDeque(word);
        if (isPalindromebyRecursionhelper(d) == 0) {
            return true;
        }else {
            return false;
        }
    }
    private int isPalindromebyRecursionhelper(Deque d) {
        if (d.size() <= 1) {
            return 0;
        } else if (d.removeFirst() == d.removeLast()) {
            return isPalindromebyRecursionhelper(d);
        }else {
            return 1;
        }
    }*/

    /** The method will return true if the word is a palindrome according to the
     * character comparison test provided by the CharacterComparator passed in as argument cc.*/
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        Deque<Character> d = wordToDeque(word);
        while (d.size() > 1) {
            if (!cc.equalChars(d.removeFirst(), d.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
