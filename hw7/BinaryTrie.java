import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.MinPQ;

public class BinaryTrie implements Serializable {
    private Node trie;
    
    private class Node implements Comparable<Node>, Serializable {
        private final Character ch;
        private final int freq;
        private Node left;
        private Node right;
        
        public Node(Character ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }
        
        @Override
        public int compareTo(Node other) {
            return this.freq - other.freq;
        }
    }
    
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<>();
        for (Character c : frequencyTable.keySet()) {
            int freq = frequencyTable.get(c);
            if (freq > 0) {
                pq.insert(new Node(c, freq, null, null));
            }
        }
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        trie = pq.delMin();
    }
    
    public Match longestPrefixMatch(BitSequence querySequence) {
        int i = 0;
        BitSequence seq = new BitSequence();
        Node n = trie;
        while (n.ch == '\0') {
            if (querySequence.bitAt(i) == 0) {
                seq = seq.appended(0);
                n = n.left;
            } else {
                seq = seq.appended(1);
                n = n.right;
            }
            i += 1;
        }
        Match res = new Match(seq, n.ch);
        return res;
    }
    
    public Map<Character, BitSequence> buildLookupTable() {
        BitSequence seq = new BitSequence();
        Map<Character, BitSequence> res = new HashMap<>();
        buildLookupTablehelper(trie, res, seq);
        return res;
    }
    
    private void buildLookupTablehelper(Node n, Map<Character, BitSequence> res, BitSequence seq) {
        if (n.isLeaf()) {
            res.put(n.ch, seq);
            return;
        }
        buildLookupTablehelper(n.left, res, seq.appended(0));
        buildLookupTablehelper(n.right, res, seq.appended(1));
    }
}
