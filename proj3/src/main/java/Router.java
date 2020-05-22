import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    
    private static class Vertex implements Comparable<Vertex> {
        long id;
        double disFromS;
        double disToT;
        
        Vertex(long id, double disFromS, double disToT) {
            this.id = id;
            this.disFromS = disFromS;
            this.disToT = disToT;
        }
        
        @Override
        public int compareTo(Vertex v) {
            Double priority = disFromS + disToT;
            Double vPriority = v.disFromS + v.disToT;
            return priority.compareTo(vPriority);
        }
    }
    
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     *
     * @param g       The graph to use.
     * @param stlon   The longitude of the start location.
     * @param stlat   The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        Map<Long, Double> bestDis = new HashMap<>();
        Map<Long, Long> bestRout = new HashMap<>();
        PriorityQueue<Vertex> fringe = new PriorityQueue<>();
        List<Long> marked = new ArrayList<>();
        
        long s = g.closest(stlon, stlat);
        long t = g.closest(destlon, destlat);
        bestDis.put(s, 0.0);
        Vertex sVertex = new Vertex(s, bestDis.get(s), g.distance(s, t));
        fringe.add(sVertex);
        while (!fringe.isEmpty()) {
            long v = fringe.remove().id;
            marked.add(v);
            if (v == t) {
                break;
            }
            for (long w : g.adjacent(v)) {
                if (marked.contains(w)) {
                    continue;
                }
                double dis = bestDis.get(v) + g.distance(v, w);
                if (bestDis.get(w) == null || (bestDis.get(w) != null && bestDis.get(w) > dis)) {
                    bestDis.put(w, dis);
                    bestRout.put(w, v);
                    Vertex wVertex = new Vertex(w, dis, g.distance(w, t));
                    fringe.add(wVertex);
                }
            }
        }
        LinkedList<Long> res = new LinkedList<>();
        res.add(t);
        long curr = t;
        while (curr != s) {
            curr = bestRout.get(curr); //FIXME NullPointerException
            res.addFirst(curr);
        }
        return res; // DONE FIXME
    }
    
    /**
     * Create the list of directions corresponding to a route on the graph.
     *
     * @param g     The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        //看起来简单实际怎么这么复杂！啊！
        List<NavigationDirection> res = new ArrayList<>();
        long lastNode = route.remove(0);
        double dis = 0;
        List<String> preWay = g.getWay(lastNode);
        String lastWay = null;
        int direction = 0;
        boolean startNewWay = true;
        for (int i = 0; i < route.size(); i += 1) {
            long v = route.get(i);
            List<String> way = g.getWay(v);
            if (startNewWay) {
                lastWay = getSameWay(preWay, way);
            }
            if (way.contains(lastWay)) {
                startNewWay = false;
                dis += g.distance(v, lastNode);
            } else {
                startNewWay = true;
                addNavigationDirection(direction, lastWay, dis, res);
                direction = getDirection(g, v, lastNode); //TODO special case
                dis = g.distance(v, lastNode);
            }
            if (i == route.size() - 1) {
                addNavigationDirection(direction, lastWay, dis, res);
            }
            lastNode = v;
            preWay = way;
        }
        return res; // FIXME
    }
    
    private static void addNavigationDirection(int direction, String lastWay,
                                               double dis, List<NavigationDirection> res) {
        NavigationDirection s = new NavigationDirection();
        s.direction = direction;
        s.way = lastWay;
        s.distance = dis;
        res.add(s);
    }
    
    private static String getSameWay(List<String> w1, List<String> w2) {
        for (int i = 0; i < w1.size(); i += 1) {
            String way = w1.get(i);
            if (w2.contains(way)) {
                return way;
            }
        }
        return null;
    }
    
    private static int getDirection(GraphDB g, long v, long w) {
        double degree = g.bearing(v, w);
        if (degree > 100) {
            return 7;
        } else if (degree > 30) {
            return 4;
        } else if (degree > 15) {
            return 3;
        } else if (degree > -15) {
            return 1;
        } else if (degree > -30) {
            return 2;
        } else if (degree > -100) {
            return 5;
        } else {
            return 6;
        }
    }
    
    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {
        
        /**
         * Integer constants representing directions.
         */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;
        
        /**
         * Number of directions supported.
         */
        public static final int NUM_DIRECTIONS = 8;
        
        /**
         * A mapping of integer values to directions.
         */
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];
        
        /**
         * Default name for an unknown way.
         */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }
        
        /**
         * The direction a given NavigationDirection represents.
         */
        int direction;
        /**
         * The name of the way I represent.
         */
        String way;
        /**
         * The distance along this way I represent.
         */
        double distance;
        
        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }
        
        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }
        
        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         *
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }
                
                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }
        
        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                        && way.equals(((NavigationDirection) o).way)
                        && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
