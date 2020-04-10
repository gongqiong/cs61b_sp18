package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[][] tiles;
    private int width;
    private int estimatedDistanceToGoal=-1;
    private Queue<WorldState> neighbors = new Queue<>();
    
    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j.
     */
    public Board(int[][] tiles) {
        width = tiles.length;
        this.tiles = new int[width][width];
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < width; j += 1) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }
    
    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     */
    public int tileAt(int i, int j) {
        if (i < 0 || i > width - 1 || j < 0 || j > width - 1) {
            throw new IndexOutOfBoundsException("tiles should be an N-by-N array.");
        }
        return tiles[i][j];
    }
    
    /**
     * Returns the board size N
     */
    public int size() {
        return width;
    }
    
    /**
     * Returns the neighbors of the current board
     */
    @Override
    public Iterable<WorldState> neighbors() {
        if (neighbors.size()>0){
            return neighbors;
        }
        int[][] tilesCopy = new int[width][width];
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < width; j += 1) {
                tilesCopy[i][j] = tileAt(i, j);
            }
        }
        int blankRow = -1;
        int blankCol = -1;
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < width; j += 1) {
                if (tileAt(i, j) == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < width; j += 1) {
                if (Math.abs(i - blankRow) + Math.abs(j - blankCol) - 1 == 0) {
                    tilesCopy[blankRow][blankCol] = tilesCopy[i][j];
                    tilesCopy[i][j] = 0;
                    Board neighbor = new Board(tilesCopy);
                    neighbors.enqueue(neighbor);
                    tilesCopy[i][j] = tilesCopy[blankRow][blankCol];
                    tilesCopy[blankRow][blankCol] = 0;
                }
            }
        }
        return neighbors;
    }
    
    /**
     * Hamming estimate: The number of tiles in the wrong position.
     */
    public int hamming() {
        estimatedDistanceToGoal = 0;
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < width; j += 1) {
                if (tileAt(i, j) != 0 && tileAt(i, j) != 3 * i + j + 1) {
                    estimatedDistanceToGoal += 1;
                }
            }
        }
        return estimatedDistanceToGoal;
    }
    
    /**
     * Manhattan estimate: The sum of the Manhattan distances
     * (sum of the vertical and horizontal distance)
     * from the tiles to their goal positions.
     */
    public int manhattan() {
        estimatedDistanceToGoal = 0;
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < width; j += 1) {
                int actualValue = tileAt(i, j);
                if (actualValue != 0) {
                    int x = (actualValue-1)/width;
                    int y = actualValue - width * x - 1;
                    estimatedDistanceToGoal += Math.abs(x - i) + Math.abs(y - j);
                }
            }
        }
        return estimatedDistanceToGoal;
    }
    
    /**
     * Estimated distance to goal. This method should simply return
     * the results of manhattan() when submitted t Gradescope.
     */
    @Override
    public int estimatedDistanceToGoal() {
        if (estimatedDistanceToGoal>=0){
            return estimatedDistanceToGoal;
        }
        estimatedDistanceToGoal= manhattan();
        return estimatedDistanceToGoal;
    }
    
    /**
     * Returns true if this board's tile values are the same position as y's
     */
    public boolean equals(Board y) {
        if (this == y){
            return true;
        }
        if (y == null || y.getClass()!=this.getClass()){
            return false;
        }
        Board yBoard = (Board) y;
        if (width != yBoard.width) {
            return false;
        }
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < width; j += 1) {
                if (tileAt(i, j) != yBoard.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
    
}
