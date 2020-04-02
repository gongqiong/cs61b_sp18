package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;


public class Game {
    public static Random rand;
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final int width = 40;
    private static final int height = 40;
    private boolean gameOver = false;
    private boolean winTheGame = false;
    private String record = "";
    
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        StdDraw.setCanvasSize(width * 16, height * 16);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        menuType(1);
        playGame(getUserInput(1));
        
    }
    
    private void playGame(String input) {
        switch (input) {
            case "n":
                menuType(3);
                Long seed = Long.parseLong(getUserInput(20));
                startNewGame(seed);
                break;
            case "l":
                break;
            case "q":
                break;
            default:
                menuType(2);
                playGame(getUserInput(1));
        }
    }
    
    private void startNewGame(Long seed) {
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        rand = new Random(seed);
        MapGenerator mapG = new MapGenerator();
        Position playerPos = mapG.generateWorld(world, rand);
        
        while (!gameOver) {
            ter.renderFrame(world);
            tileInfo(world);
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            playerPos = move(world, getUserInput(1), playerPos);
            if (winTheGame) {
                winTheGame();
                break;
            }
        }
        
    }
    
    private void winTheGame() {
        if (winTheGame) {
            StdDraw.pause(500);
            StdDraw.clear(Color.black);
            Font font = new Font("monaco", Font.BOLD, 30);
            StdDraw.setFont(font);
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WIDTH / 2, HEIGHT / 2, "Congratulations! You win the game!");
            StdDraw.show();
        }
    }
    
    private Position move(TETile[][] world, String action, Position playerPos) {
        int x = playerPos.getX();
        int y = playerPos.getY();
        if (action.equals("w")) {
            if (world[x][y + 1].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.FLOOR;
                world[x][y + 1] = Tileset.PLAYER;
                playerPos = new Position(x, y + 1);
            }
        }
        if (action.equals("a")) {
            if (world[x - 1][y].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.FLOOR;
                world[x - 1][y] = Tileset.PLAYER;
                playerPos = new Position(x - 1, y);
            }
            if (world[x - 1][y].equals(Tileset.LOCKED_DOOR)) {
                world[x][y] = Tileset.FLOOR;
                world[x - 1][y] = Tileset.UNLOCKED_DOOR;
                playerPos = new Position(x - 1, y);
                winTheGame = true;
            }
        }
        if (action.equals("s")) {
            if (world[x][y - 1].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.FLOOR;
                world[x][y - 1] = Tileset.PLAYER;
                playerPos = new Position(x, y - 1);
            }
            if (world[x][y - 1].equals(Tileset.LOCKED_DOOR)) {
                world[x][y] = Tileset.FLOOR;
                world[x][y - 1] = Tileset.UNLOCKED_DOOR;
                playerPos = new Position(x, y - 1);
                winTheGame = true;
                
            }
        }
        if (action.equals("d")) {
            if (world[x + 1][y].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.FLOOR;
                world[x + 1][y] = Tileset.PLAYER;
                playerPos = new Position(x + 1, y);
            }
            if (world[x + 1][y].equals(Tileset.LOCKED_DOOR)) {
                world[x][y] = Tileset.FLOOR;
                world[x + 1][y] = Tileset.UNLOCKED_DOOR;
                playerPos = new Position(x + 1, y);
                winTheGame = true;
            }
        }
        ter.renderFrame(world);
        winTheGame();
        return playerPos;
    }
    
    private void tileInfo(TETile[][] world) {
        Font font = new Font("monaco", Font.PLAIN, 16);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        String tile = world[x][y].description();
        StdDraw.textLeft(1, HEIGHT - 1, tile);
        StdDraw.show();
    }
    
    private String getUserInput(int n) {
        String res = "";
        while (res.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = StdDraw.nextKeyTyped();
            if (res.length() > 1 && c == 's') {
                break;
            }
            res += String.valueOf(c);
        }
        res.toLowerCase();
        return res;
    }
    
    private void menuType(int i) {
        basicMenu();
        switch (i) {
            case 1:
                StdDraw.text(width / 2, height / 4, "Press your choice please");
                break;
            case 2:
                StdDraw.text(width / 2, height / 4, "Press your choice correctly: N, L or Q");
                break;
            case 3:
                StdDraw.text(width / 2, height / 4,
                        "Please input a seed and ended with 'S' to start the game. ");
        }
        StdDraw.show();
    }
    
    private void basicMenu() {
        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.enableDoubleBuffering();
        Font smallFont = new Font("Monaco", Font.PLAIN, 20);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(width / 2, 3 * height / 4, "CS61B: THE GAME");
        StdDraw.setFont(smallFont);
        StdDraw.text(width / 2, height / 2, "New Game (N)");
        StdDraw.text(width / 2, height / 2 - 2, "Load Game (L)");
        StdDraw.text(width / 2, height / 2 - 4, "Quit (Q)");
    }
    
    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        char action = input.charAt(0);
        long seed = Long.parseLong(input.split("[a-zA-Z]")[1]);
        Random random = new Random(seed);
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        MapGenerator.generateWorld(finalWorldFrame, random);
        return finalWorldFrame;
    }
}
