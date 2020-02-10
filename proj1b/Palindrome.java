public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> res = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            res.addLast(word.charAt(i));
        }
        return res;
    }

    /* Josh Hog told me that Don’t use the get method of Deque.
       That will just make things unnecessarily complicated.
       So I will try to write another recursive method.

    public boolean isPalindrome(String word) {
        int len = word.length();
        if (len == 0 || len == 1) {
            return true;
        }
        for (int i = 0; i < len / 2; i++) {
            if (word.charAt(len-1 - i) != wordToDeque(word).get(i)) {
                return false;
            }
        }
        return true;
    }
     */

    /*recursion*/
    public boolean isPalindrome(String word) {
        Deque d = wordToDeque(word);
        return isPalindromeHelper(d);
    }

    private boolean isPalindromeHelper(Deque d) {
        if (d.size() <= 1) {
            return true;
        } else if (d.removeFirst() != d.removeLast()) {
            return false;
        } else {
            return isPalindromeHelper(d);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque d = wordToDeque(word);
        return isPalindromeHelper(d, cc);
    }

    private boolean isPalindromeHelper(Deque d, CharacterComparator cc) {
        if (d.size() <= 1) {
            return true;
        } else if (!cc.equalChars((char) d.removeFirst(), (char) d.removeLast())) {
            return false;
        } else {
            return isPalindromeHelper(d, cc);
        }
    }
}
