package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final int width = 40;
    private static final int height = 40;
    private Random rand;
    
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
        input.toLowerCase();
        switch (input) {
            case "n":
                menuType(3);
                Long seed = Long.parseLong(getUserInput(100));
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
        mapG.generateWorld(world, rand);
        Font font = new Font("monaco", Font.PLAIN,16);
        StdDraw.setFont(font);
        String[] tile = {"locked door", "wall", "floor", "you"};
        
        StdDraw.text(1,height,tile[0]);
        ter.renderFrame(world);
    }
    
    private String getUserInput(int n) {
        String res = "";
        while (res.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = StdDraw.nextKeyTyped();
            if (c == 's') {
                break;
            }
            res += String.valueOf(c);
        }
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
        input.toLowerCase();
        char action = input.charAt(0);
        long seed = Long.parseLong(input.split("[a-z]")[1]);
        Random random = new Random(seed);
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        MapGenerator.generateWorld(finalWorldFrame, random);
        return finalWorldFrame;
    }
}
