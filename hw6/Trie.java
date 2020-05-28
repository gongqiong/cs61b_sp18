import java.util.HashMap;
import java.util.Map;

public class Trie {
    private Node root = new Node(' ');
    
    private class Node {
        Character ch;
        boolean isEnd;
        Map<Character, Node> children = new HashMap<>();
        
        public Node(Character ch) {
            this.ch = ch;
            isEnd = false;
        }
    }
    
    // add a word to the trie.
    public void addWord(String s) {
        Node n = root;
        for (int i = 0; i < s.length(); i += 1) {
            char c = s.charAt(i);
            if (!n.children.containsKey(c)) {
                n.children.put(c, new Node(c));
            }
            n = n.children.get(c);
        }
        n.isEnd = true;
    }
    
    // check if trie contains word s.
    public boolean containWord(String s) {
        Node n = root;
        for (int i = 0; i < s.length(); i += 1) {
            char c = s.charAt(i);
            if (!n.children.containsKey(c)) {
                return false;
            } else {
                n = n.children.get(c);
            }
        }
        return n.isEnd;
    }
    
    // check if trie contains prefix s.
    public boolean containPrefix(String s) {
        Node n = root;
        for (int i = 0; i < s.length(); i += 1) {
            char c = s.charAt(i);
            if (!n.children.containsKey(c)) {
                return false;
            } else {
                n = n.children.get(c);
            }
        }
        return true;
    }
}
