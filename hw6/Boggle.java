import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MaxPQ;

public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "words.txt";
    static Trie trie;
    
    public static Trie getTrie() {
        In in = new In(dictPath);
        trie = new Trie();
        while (!in.isEmpty()) {
            trie.addWord(in.readString());
        }
        return trie;
    }
    
    private static class LenComparator implements Comparator<String> {
        
        @Override
        public int compare(String o1, String o2) {
            if (o1.length() == o2.length()) {
                return o2.compareTo(o1);
            }
            return o1.length() - o2.length();
        }
    }
    
    /**
     * Solves a Boggle puzzle.
     *
     * @param k             The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     * The Strings are sorted in descending order of length.
     * If multiple words have the same length,
     * have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        // YOUR CODE HERE
        Board b = new Board(boardFilePath);
        trie = getTrie();
        MaxPQ<String> words = new MaxPQ<>(new LenComparator());
        
        for (int i = 0; i < b.row; i += 1) {
            for (int j = 0; j < b.col; j += 1) {
                List<Board.Node> marked = new ArrayList<>();
                Board.Node n = b.new Node(i, j);
                String prefix = n.ch.toString();
                marked.add(n);
                dfs(b, words, marked, n, prefix);
            }
        }
        return topK(words, k);
    }
    
    private static List<String> topK(MaxPQ<String> words, int k) {
        List<String> topK = new ArrayList<>();
        int i = 0;
        while (i < k && i < words.size()) {
            String word = words.delMax();
            if (!topK.contains(word)) {
                topK.add(word);
                i += 1;
            }
        }
        return topK;
    }
    
    private static void dfs(Board b, MaxPQ<String> words, List<Board.Node> marked, Board.Node n, String prefix) {
        if (!trie.containPrefix(prefix)) {
            marked.remove(n);
            return;
        }
        if (prefix.length() > 2) {
            if (trie.containWord(prefix)) {
                words.insert(prefix);
            }
        }
        List<Board.Node> neighbors = b.getNeighbors(n);
        for (Board.Node neighbor : neighbors) {
            if (!marked.contains(neighbor)) {
                n = neighbor;
                List<Board.Node> tempMarked = new ArrayList<>(marked);
                tempMarked.add(n);
                String prefixTemp = prefix;
                prefixTemp += n.ch.toString();
                dfs(b, words, tempMarked, n, prefixTemp);
            }
        }
    }
    
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> res = Boggle.solve(7, "smallBoard.txt");
        System.out.println(res);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
