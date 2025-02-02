package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
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
    private boolean playWithKeyboard = true;
    
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
                // if a world has been saved before, we first load it.
                MapGenerator mapG = loadMapGenerator();
                
                takeAction(mapG);
                break;
            case "q":
                System.exit(0);
            default:
                menuType(2);
                playGame(getUserInput(1));
        }
    }
    
    private MapGenerator loadMapGenerator() {
        File f = new File("./mapGenerator.ser");
        try {
            if (f.exists()) {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                MapGenerator mapG = (MapGenerator) os.readObject();
                return mapG;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("class not found");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        return new MapGenerator();
    }
    
    private void saveMapGenerator(MapGenerator mapG) {
        File f = new File("./mapGenerator.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(mapG);
            os.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
    
    private void startNewGame(Long seed) {
        rand = new Random(seed);
        MapGenerator mapG = new MapGenerator();
        mapG.generateWorld(rand);
        takeAction(mapG);
    }
    
    private void takeAction(MapGenerator mapG) {
        ter.initialize(WIDTH, HEIGHT);
        
        while (!gameOver) {
            ter.renderFrame(mapG.world);
            tileInfo(mapG.world);
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            String action = getUserInput(1);
            if (action.equals(":") && getUserInput(1).equals("q")) {
                saveMapGenerator(mapG);
                System.exit(0);
            }
            mapG.playerPos = move(mapG.world, action, mapG.playerPos);
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
        if (playWithKeyboard) {
            ter.renderFrame(world);
            winTheGame();
        }
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
                StdDraw.text(width / 2, height / 4,
                        "Press your choice correctly: N, L or Q");
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
        playWithKeyboard = false;
        char action = input.charAt(0);
        MapGenerator mapG = new MapGenerator();
        switch (action) {
            case 'N':
                long seed = Long.parseLong(input.split("[a-zA-Z]")[1]);
                String moves = input.split("[\\d]+")[1];
                mapG = playNewGame(seed);
                mapG = move(mapG, moves);
                break;
            case 'L':
                mapG = loadMapGenerator();
                mapG = move(mapG, input);
                break;
            default:
        }
        return mapG.world;
    }
    
    private MapGenerator move(MapGenerator mapG, String moves) {
        boolean saveTheGame = false;
        if (moves.split(":").length != 1) {
            moves = moves.split(":")[0];
            saveTheGame = true;
        }
        for (int i = 0; i < moves.length(); i += 1) {
            String moveStep = "";
            moveStep += moves.charAt(i);
            mapG.playerPos = move(mapG.world, moveStep.toLowerCase(), mapG.playerPos);
        }
        if (saveTheGame) {
            saveMapGenerator(mapG);
        }
        return mapG;
    }
    
    private MapGenerator playNewGame(Long seed) {
        Random rand = new Random(seed);
        MapGenerator mapG = new MapGenerator();
        mapG.generateWorld(rand);
        return mapG;
    }
}
