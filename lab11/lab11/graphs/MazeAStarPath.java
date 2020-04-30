package lab11.graphs;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import edu.princeton.cs.algs4.MinPQ;

/**
 * @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    Queue<Integer> fringe;
    
    
    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        fringe = new LinkedList<>();
    }
    
    /**
     * Estimate of the distance from v to the target.
     */
    private int h(int v) {
        int vx = maze.toX(v);
        int vy = maze.toY(v);
        return maze.N() * 2 - vx - vy;
    }
    
    /**
     * Finds vertex estimated to be closest to target.
     */
    
    private int findMinimumUnmarked() {
        int min = Integer.MAX_VALUE;
        int res = -1;
        for (int v : fringe) {
            if (h(v) < min) {
                min = h(v);
                res = v;
            }
        }
        return res;
        /* You do not have to use this method. */
    }
    
    /*
    private class VertexComparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex a, Vertex b){
            return a.priority.compareTo(b.priority);
        }
    }
    
     */
    
    /**
     * Performs an A star search from vertex s.
     */
    private void astar(int s) {
        // TODO
        fringe.add(s);
        while (!fringe.isEmpty()) {
            s = findMinimumUnmarked();
            fringe.remove(s);
            marked[s] = true;
            announce();
            if (s == t) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
            for (int w : maze.adj(s)) {
                if (!marked[w]) {
                    fringe.add(w);
                    edgeTo[w] = s;
                    distTo[w] = distTo[s] + 1;
                }
            }
        }
    }
    
   
    /*
    private void fillAllVertexes(MinPQ<Vertex> fringe){
        for (int i= 0; i<maze.V();i+=1){
            fringe.insert(new Vertex(i));
        }
    }
    
    private class Vertex{
        int v;
        Integer priority;
        public Vertex(int v){
            this.v= v;
            priority = distTo[v]+h(v);
        }
    }
    
     */
    
    @Override
    public void solve() {
        astar(s);
    }
    
}

