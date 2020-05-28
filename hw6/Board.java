import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

/**
 * Board class
 *
 * @author GongQiong
 */
public class Board {
    private char[][] board;
    int row;
    int col;
    private int[][] direction = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    
    class Node {
        Character ch;
        int rowIndex;
        int colIndex;
        
        public Node(int row, int col) {
            this.rowIndex = row;
            this.colIndex = col;
            this.ch = board[row][col];
        }
        
        @Override
        public boolean equals(Object other) {
            if (other == null || other.getClass() != this.getClass()) {
                return false;
            }
            Node otherNode = (Node) other;
            return otherNode.ch == ch && otherNode.rowIndex == rowIndex && otherNode.colIndex == colIndex;
        }
    }
    
    public Board(String boardFilePath) {
        In in = new In(boardFilePath);
        String[] boardLines = in.readAllLines();
        row = boardLines.length;
        col = boardLines[0].length();
        board = new char[row][col];
        for (int i = 0; i < row; i += 1) {
            for (int j = 0; j < col; j += 1) {
                board[i][j] = boardLines[i].charAt(j);
            }
        }
    }
    
    public List<Node> getNeighbors(Node n) {
        List<Node> neighbors = new ArrayList<>();
        int rowIndex = n.rowIndex;
        int colIndex = n.colIndex;
        if (rowIndex < 0 || rowIndex >= row || colIndex < 0 || colIndex >= col) {
            throw new IllegalArgumentException("Should pass legal node");
        }
        for (int[] dir : direction) {
            int rowIndexNeighbor = rowIndex + dir[0];
            int colIndexNeighbor = colIndex + dir[1];
            if (rowIndexNeighbor >= 0 && rowIndexNeighbor < row
                    && colIndexNeighbor >= 0 && colIndexNeighbor < col) {
                neighbors.add(new Node(rowIndexNeighbor, colIndexNeighbor));
            }
        }
        return neighbors;
    }
}
