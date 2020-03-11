package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    
    private static class Position {
        int x;
        int y;
        
        public Position(int x_co, int y_co) {
            x = x_co;
            y = y_co;
        }
    }
    
    /**
     * Computes the width of row i for a size x hexagon.
     *
     * @param x The size of the hex.
     * @param i The row number where i= 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth(int x, int i) {
        if (i >= x) {
            i = 2 * x - 1 - i;
        }
        return x + 2 * i;
    }
    
    /**
     * Computes relative x coordinate of the leftmost tile in the ith row of a hexagon.
     * Assuming that the bottom row has an x-coordinate of zero.
     *
     * @param x The size of the hex.
     * @param i The row number where i= 0 is the bottom row.
     * @return
     */
    public static int hexRowOffset(int x, int i) {
        if (i >= x) {
            i = 2 * x - 1 - i;
        }
        return x - i;
    }
    
    /**
     * Adds a row of the same tile.
     *
     * @param tiles the world to draw on.
     * @param p     the leftmost position of the row
     * @param width the number of the tiles wide to draw
     * @param t     the tile to draw
     */
    public static void addRow(TETile[][] tiles, Position p, int width, TETile t) {
        for (int i = 0; i < width; i += 1) {
            tiles[p.x + i][p.y] = t;
        }
    }
    
    /**
     * Adds a hexagon to the world.
     *
     * @param tiles the world to draw on.
     * @param p     the leftmost position of the row
     * @param x     the size of the hex.
     * @param t     the tile to draw
     */
    public static void addHexagon(TETile[][] tiles, Position p, int x, TETile t) {
        if (x < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        for (int i = 0; i < 2 * x; i += 1) {
            int thisRowY = p.y + i;
            int xRowStart = p.x + hexRowOffset(x, i);
            Position rowStartP = new Position(xRowStart, thisRowY);
            addRow(tiles, rowStartP, hexRowWidth(x, i), t);
        }
    }
    
    /**
     * Computes relative y coordinate of the leftmost tile in the ith col of a tessellation.
     * Assuming that the leftmost column has an y-coordinate of zero.
     *
     * @param x The size of the hex.
     * @param i The column number where i= 0 is the leftmost column.
     * @return
     */
    public static int hexTesColOffset(int x, int i) {
        if (i >= x) {
            i = 2 * x - 2 - i;
        }
        return i * x;
    }
    
    /**
     * Drawing a tessellation of Hexagons.
     *
     * @param tiles the world to draw on.
     * @param p     the leftmost position of the row
     * @param x     the size of the hex.
     */
    public static void addColHexTex(TETile[][] tiles, Position p, int x, int col_num) {
        for (int i = 0; i < col_num; i += 1) {
            Position newP = new Position(p.x, p.y + 2 * x * i);
            TETile t = randomTile();
            addHexagon(tiles, newP, x, t);
        }
    }
    
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0:
                return Tileset.WALL;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.MOUNTAIN;
            default:
                return Tileset.TREE;
        }
    }
    
    /**
     * Computes the number of hexagon for ith column.
     *
     * @param x
     * @param i
     * @return
     */
    public static int hexTesColNum(int x, int i) {
        if (i >= x) {
            i = 2 * x - 2 - i;
        }
        return x + i;
    }
    
    public static void addHexTes(TETile[][] tiles, Position p, int x) {
        for (int i = 0; i < 2 * x - 1; i += 1) {
            int thisColY = p.y - hexTesColOffset(x, i);
            int thisColX = p.x + 2 * i * x - i;
            Position newP = new Position(thisColX, thisColY);
            addColHexTex(tiles, newP, x, hexTesColNum(x, i));
        }
    }
    
    
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        Position p = new Position(0, 20);
        addHexTes(tiles, p, 3);
        ter.renderFrame(tiles);
    }
}
