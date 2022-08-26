package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import org.junit.Test;

import java.awt.*;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 50;
    private int stringCount = 0;
    private int maxRoomNum = 18;
    private HashMap<String, Date> fileSavedTime = new HashMap<>();

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        drawStartUI();
        switch (getFirstChar()) {
            case 'n': {
                startGame();
                break;
            }
            case 'l': {
                loadGame();
                break;
            }
            case 's': {
                SelectAnotherGame();
                break;
            }
            case 'q': {
                quitGame();
                break;
            }
        }

    }
    @Test
    public void test() {
        playWithKeyboard();
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
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < input.length(); i++) {
            stringCount++;
            switch (Character.toLowerCase(input.charAt(i))) {
                case 'n': {
                    String rest = input.substring(i + 1);
                    long seed = readSeed(rest);

                    rest = SpreadTheWheatFromTheChaff(input.substring(stringCount));

                    startGame(world, rest, seed);

                    stringCount = 0;
                    break;
                }
                case 'l': {
                    loadGame(SpreadTheWheatFromTheChaff(input.substring(i + 1)));
                    break;
                }
                case 'q': {
                    System.exit(0);
                    break;

                }
            }
        }
        return world;
    }

    // after read the new game char, read the seed info
    private long readSeed(String input) {
        input = input.toLowerCase(Locale.ROOT);
        String pre = input.substring(0, 1);
        long res = Long.parseLong(pre);
        stringCount++;
        for (int i = 1; i < input.length(); i++) {
            stringCount++;
            if (Character.isDigit(input.charAt(i)) && Character.isDigit(input.charAt(i - 1))) {
                res = res * 10 + Integer.parseInt(String.valueOf(input.charAt(i)));
            } else if (!Character.isDigit(input.charAt(i))) {
                break;
            }
        }
        return res;
    }

    // make the rest of the input string valid and tight, with only 'wasd' and 'q',
    // remove the ':' before the valid ":q"
    private String SpreadTheWheatFromTheChaff(String input) {
        String res = "";
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'w' ||
                    input.charAt(i) == 'a' ||
                    input.charAt(i) == 's' ||
                    input.charAt(i) == 'd') {
                res += input.charAt(i);
            } else if (input.charAt(i) == 'q' && i != 0) {
                if (input.charAt(i- 1) == ':') {
                    res += ':';
                    res += 'q';
                    break;
                }
            }
        }
        return res;
    }

    private void startGame() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        long seed = getSeedInput();
        StdDraw.clear();
        MapGenerator mg = new MapGenerator(WIDTH, HEIGHT, maxRoomNum, world, seed);

        play(world);
    }
    private void startGame(TETile[][] world, String input, long seed) {
        StdDraw.clear();
        MapGenerator mg = new MapGenerator(WIDTH, HEIGHT, maxRoomNum, world, seed);

        play(world, input);
    }

    private void startAnotherGame(String input) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        long seed = getSeedInput();
        StdDraw.clear();
        MapGenerator mg = new MapGenerator(WIDTH, HEIGHT, maxRoomNum, world, seed);

        playAnother(world, input);
    }

    private long getSeedInput() {
        long res = 0;
        drawSeedInput(res);

        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = Character.toLowerCase(StdDraw.nextKeyTyped());
            if (Character.isDigit(c)) {
                res = res * 10 + Integer.parseInt(String.valueOf(c));
                drawSeedInput(res);
            }
            if (!Character.isDigit(c)) {
                break;
            }
        }
        return res;
    }



    private char getFirstChar() {
        char c;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            c = Character.toLowerCase(StdDraw.nextKeyTyped());
            if (c == 'n' || c == 'l' || c == 'q' || c == 's') {
                break;
            }
        }
        return c;
    }
    private TETile[][] loadGame() {
        String file = "savefile.txt";
        return loadAnotherGame(file);
    }
    private TETile[][] loadGame(String input) {
        TETile[][] finalWorldFrame;
        finalWorldFrame = getSavedGame();
        play(finalWorldFrame, input);
        return finalWorldFrame;
    }

    private TETile[][] loadAnotherGame(String input) {
        TETile[][] finalWorldFrame;
        finalWorldFrame = getSavedGame(input);
        playAnother(finalWorldFrame, input);
        return finalWorldFrame;
    }

    private void SelectAnotherGame() {
        drawSelecttUI();
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }

            char c = StdDraw.nextKeyTyped();

            if (c == '1' || c == '2' || c == '3' || c == 'q') {
                if(c == 'q') {
                    System.exit(0);
                }
                String inputFile = "savefile" + c + ".txt";
                SaveLoadOrDelete(inputFile);
                break;
            }
        }
    }

    private void SaveLoadOrDelete(String inputFile) {
        drawSLD(inputFile);
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = Character.toLowerCase(StdDraw.nextKeyTyped());
            if (c == 's' || c == 'l' || c == 'q') {
                if (c == 's') {
                    startAnotherGame(inputFile);
                    break;
                } else if (c == 'q') {
                    quitGame();
                } else {
                    loadAnotherGame(inputFile);
                    break;
                }
            }
        }
    }


    private position getInitPlayerPos(TETile[][] finalWorldFrame) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (finalWorldFrame[x][y].equals(Tileset.PLAYER)) {
                    return new position(x, y);
                }
            }
        }
        return new position();
    }

    private void play(TETile[][] finalWorldFrame) {
        String file = "savefile.txt";
        playAnother(finalWorldFrame, file);
    }



    private void playAnother(TETile[][] finalWorldFrame, String file) {
        StdDraw.disableDoubleBuffering();
        StdDraw.enableDoubleBuffering();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);
        Player.setPos(getInitPlayerPos(finalWorldFrame));
        char pre = 'c';
        while(true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = Character.toLowerCase(StdDraw.nextKeyTyped());
            if (c == 'q' && pre == ':') {
                saveAnotherGame(finalWorldFrame, file);
                System.exit(0);
            }
            switch (c) {
                case 'w': {
                    Player.walkUp(finalWorldFrame);
                    ter.renderFrame(finalWorldFrame);
                    break;
                }
                case 's': {
                    Player.walkDown(finalWorldFrame);
                    ter.renderFrame(finalWorldFrame);
                    break;
                }
                case 'a': {
                    Player.walkLeft(finalWorldFrame);
                    ter.renderFrame(finalWorldFrame);
                    break;
                }
                case 'd': {
                    Player.walkRight(finalWorldFrame);
                    ter.renderFrame(finalWorldFrame);
                    break;
                }
                default: {
                    pre = c;
                }
            }

        }
    }
    private void play(TETile[][] finalWorldFrame, String input) {
        StdDraw.disableDoubleBuffering();
        StdDraw.enableDoubleBuffering();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);
        Player.setPos(getInitPlayerPos(finalWorldFrame));
        char pre = 'c';
        for (int  i = 0; i < input.length(); i++) {
            char c = Character.toLowerCase(input.charAt(i));
            if (c == 'q' && pre == ':') {
                saveGame(finalWorldFrame);
                System.exit(0);
            }
            switch (c) {
                case 'w': {
                    Player.walkUp(finalWorldFrame);
                    ter.renderFrame(finalWorldFrame);
                    break;
                }
                case 's': {
                    Player.walkDown(finalWorldFrame);
                    ter.renderFrame(finalWorldFrame);
                    break;
                }
                case 'a': {
                    Player.walkLeft(finalWorldFrame);
                    ter.renderFrame(finalWorldFrame);
                    break;
                }
                case 'd': {
                    Player.walkRight(finalWorldFrame);
                    ter.renderFrame(finalWorldFrame);
                    break;
                }
                default: {
                    pre = c;
                }
            }

        }
    }

    private TETile[][] getSavedGame() {
        String file = "savefile.txt";
        return getSavedGame(file);
    }


    private TETile[][] getSavedGame(String input) {
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(input));
            finalWorldFrame = (TETile[][]) in.readObject();
            Player.setPos((position) in.readObject());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return finalWorldFrame;
    }



    private void quitGame() {
        System.exit(0);
    }



    private void saveGame(TETile[][] finalWorldFrame) {
        String file = "savefile.txt";
        saveAnotherGame(finalWorldFrame, file);
    }

    private void saveAnotherGame(TETile[][] finalWorldFrame , String file) {
        Date cur = new Date();
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            switch (file) {
                case "savefile.txt": {
                    ObjectOutputStream recordInfo = new ObjectOutputStream
                            (new FileOutputStream("record.txt"));
                    recordInfo.writeObject(cur);
                    break;
                }
                case "savefile1.txt": {
                    ObjectOutputStream recordInfo = new ObjectOutputStream
                            (new FileOutputStream("record1.txt"));
                    recordInfo.writeObject(cur);
                    break;
                }
                case "savefile2.txt": {
                    ObjectOutputStream recordInfo = new ObjectOutputStream
                            (new FileOutputStream("record2.txt"));
                    recordInfo.writeObject(cur);
                    break;
                }
                case "savefile3.txt": {
                    ObjectOutputStream recordInfo = new ObjectOutputStream
                            (new FileOutputStream("record3.txt"));
                    recordInfo.writeObject(cur);
                    break;
                }
            }
            out.writeObject(finalWorldFrame);
            out.writeObject(Player.getPos());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void drawStartUI() {
        initializeCanvas();


        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, 3 * HEIGHT / 4, "CS61B: THE GAME");
        Font smallFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(smallFont);
        String record = "record.txt";
        File file = new File(record);
        if (file.exists()) {
            Date cur = readDateAndDrawInRecord("savefile.txt");
            // that's how I wrote this function.
            StdDraw.text(WIDTH / 2, HEIGHT / 2, "The last time you saved this game: ");
            StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, cur.toString());
        }
        StdDraw.text(WIDTH / 2, HEIGHT / 4 + 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4 - 2, "Select Another Game Record (S)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4 - 4, "Quit (Q)");

        StdDraw.show();
    }

    static void drawEnd() {
        StdDraw.clear();
        StdDraw.setCanvasSize(WIDTH * 16, (HEIGHT + 1) * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT + 1);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, 3 * HEIGHT / 4, "Congratulation! But you actually did nothing.");


        StdDraw.show();
    }


    private void drawSelecttUI() {
        StdDraw.clear();
        initializeCanvas();
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, 3 * HEIGHT / 4, "CS61B: THE GAME");

        Font smallFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH / 2, HEIGHT / 4 + 2, "Record 1 Press (1)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4, "Record 2 Press (2)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4 - 2, "Record 3 Press (3)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4 - 4, "Quit (q)");


        StdDraw.show();
    }
    private void drawSLD(String input) {
        StdDraw.clear();
        initializeCanvas();
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, 3 * HEIGHT / 4, "Select your action to this record");
        readDateAndDrawInRecord(input);
        StdDraw.show();
    }

    private Date readDateAndDrawInRecord(String input) {
        try {
            switch (input) {
                case "savefile.txt": {
                    File file = new File("record.txt");
                    if (file.exists()) {
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream("record.txt"));
                        Date cur = (Date) in.readObject();
                        return cur;
                    }
                    break;
                }
                case "savefile1.txt": {
                    File file = new File("record1.txt");
                    if (file.exists()) {
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream("record1.txt"));
                        Date cur = (Date) in.readObject();
                        drawSLDHelperExist(cur);
                    } else {
                        drawSLDHelperNotExist();
                    }
                    break;
                }
                case "savefile2.txt": {
                    File file = new File("record2.txt");
                    if (file.exists()) {
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream("record2.txt"));
                        Date cur = (Date) in.readObject();
                        drawSLDHelperExist(cur);
                    } else {
                        drawSLDHelperNotExist();
                    }
                    break;
                }
                case "savefile3.txt": {
                    File file = new File("record3.txt");
                    if (file.exists()) {
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream("record3.txt"));
                        Date cur = (Date) in.readObject();
                        drawSLDHelperExist(cur);
                    } else {
                        drawSLDHelperNotExist();
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    private void drawSLDHelperExist(Date cur) {

        Font smallFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "This record saved lastly at: ");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, cur.toString());
        StdDraw.text(WIDTH / 2, HEIGHT / 4 + 2, "Start new game (s)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4 - 2, "Quit (q)");
    }
    private void drawSLDHelperNotExist() {
        Font smallFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "This record is new.");
        StdDraw.text(WIDTH / 2, HEIGHT / 4, "Start new game (s)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4 - 2, "Quit (q)");
    }



    private void drawSeedInput(long res) {
        if (res == 0) {
            StdDraw.clear();
            initializeCanvas();
            Font font = new Font("Monaco", Font.BOLD, 60);
            StdDraw.setFont(font);
            StdDraw.text(WIDTH / 2, 3 * HEIGHT / 4, "Please input a seed: ");
            StdDraw.show();
        } else {
            StdDraw.clear();
            initializeCanvas();
            Font font = new Font("Monaco", Font.BOLD, 60);
            StdDraw.setFont(font);
            StdDraw.text(WIDTH / 2, 3 * HEIGHT / 4, "Please input a seed: ");
            StdDraw.text(WIDTH / 2, 1 * HEIGHT / 4, String.valueOf(res));
            StdDraw.show();
        }
    }


    private void initializeCanvas() {
        StdDraw.setCanvasSize(WIDTH * 16, (HEIGHT + 1) * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT + 1);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);
    }
}
