/**
 * add a third public method to the Palindrome class.
 * will return true if the word is a palindrome according to the character comparison
 * test provided by the CharacterComparator passed in as argument cc.
 */
public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        return x - y == 1 || x - y == -1;
    }
}
