import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import edu.princeton.cs.algs4.In;

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
        List<String> res = new ArrayList<>();
        for (int i = 0; i < b.row; i += 1) {
            for (int j = 0; j < b.col; j += 1) {
                List<Board.Node> marked = new ArrayList<>();
                Board.Node n = b.new Node(i, j);
                String prefix = n.ch.toString();
                marked.add(n);
                dfs(k, b, res, marked, n, prefix);
            }
        }
        res.sort(new resComparator());
        res = getFirstK(res, k);
        return res;
    }
    
    private static List<String> getFirstK(List<String> res, int k) {
        List<String> FirstK = new ArrayList<>();
        for (int i = 0; i < k && i < res.size(); i += 1) {
            FirstK.add(res.get(i));
        }
        return FirstK;
    }
    
    private static class resComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.length() != s2.length() ? s2.length() - s1.length() : s1.compareTo(s2);
        }
    }
    
    private static void dfs(int k, Board b, List<String> res, List<Board.Node> marked, Board.Node n, String prefix) {
        if (!trie.containPrefix(prefix)) {
            marked.remove(n);
            return;
        }
        if (prefix.length() > 2) {
            if (trie.containWord(prefix) && !res.contains(prefix)) {
                res.add(prefix);
            }
        }
        List<Board.Node> neighbors = b.getNeighbors(n);
        for (Board.Node neighbor : neighbors) {
            if (!marked.contains(neighbor)) {
                n = neighbor;
                List<Board.Node> tempMarked = new ArrayList<>();
                tempMarked.addAll(marked);
                tempMarked.add(n);
                String prefixTemp = prefix;
                prefixTemp += n.ch.toString();
                dfs(k, b, res, tempMarked, n, prefixTemp);
            }
        }
    }
    
    public static void main(String[] args) {
        Boggle g = new Boggle();
        List<String> res = g.solve(7, "exampleBoard.txt");
        System.out.println(res);
    }
}
