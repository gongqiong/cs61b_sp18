import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    
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
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("cat"));
    }
    
    @Test
    public void testisPalindromeByOne() {
        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("a", obo));
        assertFalse(palindrome.isPalindrome("aa", obo));
        assertTrue(palindrome.isPalindrome("flake", obo));
        assertFalse(palindrome.isPalindrome("noon", obo));
    }
    
    @Test
    public void testisPalindromeByN() {
        OffByN obn5 = new OffByN(5);
        assertTrue(palindrome.isPalindrome("", obn5));
        assertTrue(palindrome.isPalindrome("a", obn5));
        assertFalse(palindrome.isPalindrome("aa", obn5));
        assertTrue(palindrome.isPalindrome("aaf", obn5));
        assertFalse(palindrome.isPalindrome("fafh", obn5));
    }
}
