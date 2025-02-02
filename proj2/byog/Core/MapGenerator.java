package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

public class MapGenerator implements Serializable {
    private static final long serialVersionUID = 45498234798734234L;
    
    private static final int WIDTH = Game.WIDTH;
    private static final int HEIGHT = Game.HEIGHT;
    Position playerPos;
    TETile[][] world;
    
    public MapGenerator(){
        world = new TETile[WIDTH][HEIGHT];
    }
    
    public void generateWorld(Random random) {
        setBackgroundAsWalls(world);  // fill the whole world with wall tiles.
        Queue<Room> rooms = generateRooms(world, random); // generate rooms.
        connectRooms(world, rooms, random); // connects rooms with hallways (no walls).
        removeWalls(world); // remove redundant walls.
        playerPos = addPlayer(world, random);
        addLockedDoor(world, random);
    }
    
    private Position addPlayer(TETile[][] world, Random rand) {
        while (true) {
            int x = RandomUtils.uniform(rand, 1, WIDTH - 2);
            int y = RandomUtils.uniform(rand, 1, HEIGHT - 2);
            if (world[x][y].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.PLAYER;
                return new Position(x, y);
            }
        }
    }
    
    private void addLockedDoor(TETile[][] world, Random rand) {
        while (true) {
            int x = RandomUtils.uniform(rand, 1, WIDTH - 2);
            int y = RandomUtils.uniform(rand, 1, HEIGHT - 2);
            if (world[x][y].equals(Tileset.WALL) && world[x][y + 1].equals(Tileset.FLOOR)
                    && world[x][y + 2].equals(Tileset.FLOOR) && Math.abs(x + y - playerPos.getX() - playerPos.getY()) > HEIGHT) {
                world[x][y] = Tileset.LOCKED_DOOR;
                break;
            }
        }
    }
    
    private void connectRooms(TETile[][] world, Queue<Room> rooms, Random random) {
        Queue<Room> roomsInOrder = new PriorityQueue<>();
        for (Room room : rooms) {
            roomsInOrder.add(room);
        }
        while (roomsInOrder.size() > 1) {
            Room room1 = roomsInOrder.poll();
            Room room2 = roomsInOrder.poll();
            Position pos1 = room1.randomGeneratePosInRoom(random);
            Position pos2 = room2.randomGeneratePosInRoom(random);
            addNakedHallway(world, pos1, pos2, random);
            roomsInOrder.add(room2);
        }
    }
    
    private void addNakedHallway
            (TETile[][] world, Position posA, Position posB, Random random) {
        if (posA.getX() == posB.getX()) { // vertical
            int x = posA.getX();
            int startY = Math.min(posA.getY(), posB.getY());
            int endY = Math.max(posA.getY(), posB.getY());
            for (int y = startY; y <= endY; y += 1) {
                world[x][y] = Tileset.FLOOR;
            }
        } else if (posA.getY() == posB.getY()) { // horizontal
            int y = posA.getY();
            int startX = Math.min(posA.getX(), posB.getX());
            int endX = Math.max(posA.getX(), posB.getX());
            for (int x = startX; x <= endX; x += 1) {
                world[x][y] = Tileset.FLOOR;
            }
        } else {
            boolean p = RandomUtils.bernoulli(random);
            Position inflection;
            if (p) {
                inflection = new Position(posA.getX(), posB.getY());
            } else {
                inflection = new Position(posB.getX(), posA.getY());
            }
            addNakedHallway(world, posA, inflection, random);
            addNakedHallway(world, posB, inflection, random);
        }
    }
    
    private void removeWalls(TETile[][] world) {
        int[][] neighbors = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT - 1; y += 1) {
                if (world[x][y].equals(Tileset.WALL)) {
                    int floorNeighbor = 0;
                    for (int[] neighbor : neighbors) {
                        int neighborX = x + neighbor[0];
                        int neighborY = y + neighbor[1];
                        if (neighborX >= 0 && neighborX < WIDTH
                                && neighborY >= 0 && neighborY < HEIGHT - 1
                                && world[neighborX][neighborY].equals(Tileset.FLOOR)) {
                            floorNeighbor += 1;
                        }
                    }
                    if (floorNeighbor == 0) {
                        world[x][y] = Tileset.NOTHING;
                    }
                }
            }
        }
    }
    
    private void setBackgroundAsWalls(TETile[][] world) {
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                if (j == HEIGHT - 1) {
                    world[i][j] = Tileset.NOTHING;
                } else {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }
    
    private Queue<Room> generateRooms(TETile[][] world, Random random) {
        Queue<Room> rooms = new LinkedList<>();
        Queue<BSSpace> bssQueue = new LinkedList<>();
        BSSpace root = new BSSpace(WIDTH, HEIGHT - 1, new Position(0, 0));
        bssQueue.add(root);
        int splitNum = 12 + RandomUtils.uniform(random, 10);
        while (splitNum > 0 && !bssQueue.isEmpty()) {
            BSSpace bssToSplit = bssQueue.remove();
            if (bssToSplit.split(random)) {
                bssQueue.add(bssToSplit.leftSpace);
                bssQueue.add(bssToSplit.rightSpace);
            } else {
                bssQueue.add(bssToSplit);
            }
            splitNum -= 1;
        }
        for (BSSpace bss : bssQueue) {
            bss.buildRoom(random);
            bss.room.addRoom(world);
            rooms.add(bss.room);
        }
        return rooms;
    }
}
