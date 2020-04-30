package lab11.graphs;

import java.util.LinkedList;

/**
 * @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private boolean findTheCycle;
    private int cyclePoint;
    
    public MazeCycles(Maze m) {
        super(m);
        s = 0;
        findTheCycle = false;
        distTo[s] = 0;
        edgeTo[s] = s;
        cyclePoint = -1;
    }
    
    @Override
    public void solve() {
        // TODO: Your code here!
        dfsFindCycle(s);
        drawTheCycle(cyclePoint);
    }
    
    // Helper methods go here
    private void dfsFindCycle(int v) {
        if (findTheCycle) {
            return;
        }
        marked[v] = true;
        for (int i : maze.adj(v)) {
            if (marked[i] && i != edgeTo[v]) {
                findTheCycle = true;
                cyclePoint = i;
                edgeTo[i] = v;
            } else if (!marked[i]) {
                edgeTo[i] = v;
                distTo[i] = distTo[v] + 1;
                dfsFindCycle(i);
                if (findTheCycle) {
                    return;
                }
            }
        }
    }
    
    private void drawTheCycle(int c) {
        
        if (!findTheCycle) {
            for (int i = 0; i < maze.V(); i += 1) {
                if (marked[i]) {
                    edgeTo[i] = Integer.MAX_VALUE;
                }
            }
            announce();
        } else {
            LinkedList<Integer> cyclePoints = new LinkedList<>();
            cyclePoints.add(c);
            int v = edgeTo[c];
            while (v != c) {
                cyclePoints.add(v);
                v = edgeTo[v];
            }
            for (int i = 0; i < maze.V(); i += 1) {
                if (marked[i] && !cyclePoints.contains(i)) {
                    edgeTo[i] = Integer.MAX_VALUE;
                }
            }
            announce();
        }
    }
}

