package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Solver {
    
    private class SearchNode {
        private WorldState state;
        private int moves;
        private Integer priority;
        private SearchNode prev;
        
        private SearchNode(WorldState state, SearchNode preState) {
            this.state = state;
            this.prev = preState;
            if (preState == null) {
                moves = 0;
            } else {
                moves = preState.moves + 1;
            }
            priority = moves + state.estimatedDistanceToGoal();
        }
    }
    
    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode left, SearchNode right) {
            return left.priority.compareTo(right.priority);
        }
    }
    
    private int moves;
    private Stack<WorldState> paths;
    
    /**
     * Constructor which solves the puzzle, computing everything necessary for
     * moves() and solution() to not have to solve the problem again.
     * Solves the puzzle using the A* algorithm. Assumes a solution exists.
     *
     * @param initial
     */
    public Solver(WorldState initial) {
        MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
        SearchNode currentNode = new SearchNode(initial, null);
        
        while (!currentNode.state.isGoal()) {
            for (WorldState neighbor : currentNode.state.neighbors()) {
                if (currentNode.prev == null || !neighbor.equals(currentNode.prev.state)) {
                    pq.insert(new SearchNode(neighbor, currentNode));
                }
            }
            currentNode = pq.delMin();
        }
        
        moves = currentNode.moves;
        paths = new Stack<>();
        while (currentNode != null) {
            paths.push(currentNode.state);
            currentNode = currentNode.prev;
        }
    }
    
    
    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     *
     * @return
     */
    public int moves() {
        return moves;
    }
    
    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     *
     * @return
     */
    public Iterable<WorldState> solution() {
        List<WorldState> solution = new LinkedList<>();
        while (!paths.isEmpty()) {
            solution.add(paths.pop());
        }
        return solution;
    }
}
