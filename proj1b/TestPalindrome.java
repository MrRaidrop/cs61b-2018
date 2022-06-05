import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        //assertTrue(palindrome.isPalindromebyRecursion(""));
        assertTrue(palindrome.isPalindrome("c"));
        //assertTrue(palindrome.isPalindromebyRecursion("c"));
        assertFalse(palindrome.isPalindrome("revolver"));
        //assertFalse(palindrome.isPalindromebyRecursion("revolver"));
        assertTrue(palindrome.isPalindrome("rotator"));
        //assertTrue(palindrome.isPalindromebyRecursion("rotator"));
        assertFalse(palindrome.isPalindrome("Noon"));
        //assertFalse(palindrome.isPalindromebyRecursion("Noon"));
        String testbig = "";
        for (int i = 0; i < 1000; i++) {
            testbig = testbig + "nin";
        }
        assertTrue(palindrome.isPalindrome(testbig));
        //assertTrue(palindrome.isPalindromebyRecursion(testbig));
    }

    @Test
    public void testisPalidromecc() {
        assertTrue(palindrome.isPalindrome("chrysid", offByOne));
        assertFalse(palindrome.isPalindrome("noon", offByOne));
        assertTrue(palindrome.isPalindrome("n", offByOne));
        assertTrue(palindrome.isPalindrome("flake", offByOne));
    }
}