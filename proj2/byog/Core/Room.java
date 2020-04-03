package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class Room implements Comparable<Room>, Serializable {
    private static final long serialVersionUID = 123123123123123L;
    
    private Position pos;
    private int width;
    private int height;
    
    Room(Position pos, int width, int height) {
        this.pos = pos;
        this.width = width;
        this.height = height;
    }
    
    Position randomGeneratePosInRoom(Random random) {
        int x = pos.getX() + RandomUtils.uniform(random, 1, width + 1);
        int y = pos.getY() + RandomUtils.uniform(random, 1, height + 1);
        return new Position(x, y);
    }
    
    void addRoom(TETile[][] world) {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[pos.getX() + x + 1][pos.getY() + y + 1] = Tileset.FLOOR;
            }
        }
    }
    
    @Override
    public int compareTo(Room r) {
        return this.pos.getX() + this.pos.getY() - r.pos.getX() - r.pos.getY();
    }
}
