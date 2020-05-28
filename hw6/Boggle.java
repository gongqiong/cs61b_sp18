import java.util.*;

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
        Set<String> res = new HashSet<>();
        for (int i = 0; i < b.row; i += 1) {
            for (int j = 0; j < b.col; j += 1) {
                List<Board.Node> marked = new ArrayList<>();
                Board.Node n = b.new Node(i, j);
                String prefix = n.ch.toString();
                marked.add(n);
                dfs(b, res, marked, n, prefix);
            }
        }
        String[] topKRes = topK(res, k);
        for (int i = 0; i<k; i+=1){
            String temp = topKRes[0];
            topKRes[0] = topKRes[k-1-i];
            topKRes[k-1-i] = temp;
            sink(0, topKRes, k-1-i);
        }
        List<String> topK = new ArrayList<>(Arrays.asList(topKRes));
        return topK;
    }
    
    private static String[] topK(Set<String> res, int k) {
        int i = 0;
        String[] topK = new String[k];
        for (String s : res) {
            if (i == 0) {
                topK[0] = s;
            } else if (i < k) {
                topK[i] = s;
                swim(i, topK);
            } else {
                if (!less(s, topK[0])) {
                    topK[0] = s;
                    sink(0, topK,k);
                }
            }
            i += 1;
        }
        return topK;
    }
    
    private static void sink(int i, String[] sList,int N) {
        while (2 * i + 1 < N) {
            int j = 2 * i + 1;
            if (j < N - 1 && less(sList[j + 1], sList[j])) {
                j += 1;
            }
            if (less(sList[i], sList[j])) {
                break;
            }
            String temp = sList[j];
            sList[j] = sList[i];
            sList[i] = temp;
            i = j;
        }
    }
    
    private static void swim(int i, String[] sList) {
        int j = (i - 1) / 2;
        if (i > 0 && !less(sList[j], sList[i])) {
            String temp = sList[j];
            sList[j] = sList[i];
            sList[i] = temp;
            swim(j, sList);
        }
    }
    
    private static boolean less(String s1, String s2) {
        if (s1.length() == s2.length()) {
            return s1.compareTo(s2) > 0;
        }
        return s1.length() < s2.length();
    }
    
    private static void dfs(Board b, Set<String> res, List<Board.Node> marked, Board.Node n, String prefix) {
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
                dfs(b, res, tempMarked, n, prefixTemp);
            }
        }
    }
    
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Boggle g = new Boggle();
        List<String> res = g.solve(7, "smallBoard.txt");
        System.out.println(res);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
