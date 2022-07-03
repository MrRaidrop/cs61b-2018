package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 60;
    private static final long SEED = 241231873;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // g is the length of one side of a hex.
        int g = 3;
        // xoffset innitialize.
        int xoffsetinit = 5;
        //how big the map would be.
        int scale = 5;
        drawhexmap(g, xoffsetinit, 6, world);
        // draws the world to the screen
        ter.renderFrame(world);
    }

    private static TETile randomTile(int tileNum) {
        switch (tileNum) {
            case 0: return Tileset.WATER;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.SAND;
            case 4: return Tileset.LOCKED_DOOR;
            case 5: return Tileset.MOUNTAIN;
            case 6: return Tileset.TREE;
            case 7: return Tileset.FLOOR;
            case 8: return Tileset.PLAYER;
            default: return Tileset.NOTHING;
        }
    }

    public static void drawhexmap(int g, int xoffsetinit, int scale, TETile[][] world){
        int i = 3;
        int xoffset = xoffsetinit;
        while (i <= scale) {
            drawRandomHex(i, g, xoffset, world);
            xoffset += 2 * g - 1;
            i++;
        }
        while (i > 2) {
            drawRandomHex(i, g, xoffset, world);
            xoffset += 2 * g - 1;
            i--;
        }

    }

    public static void drawRandomHex(int n, int g,int xoffset, TETile[][] world) {
        int basis = HEIGHT / 2;
        int ystart = basis - g * n;
        for (int i = 0; i < n; i++) {
            addHexagon(g, xoffset, ystart, world);
            ystart += 2 * g;
        }
    }

    public static void addHexagon(int g, int xoffset, int y, TETile[][] world) {
        int tileNum = RANDOM.nextInt(9);
        getdownrow(g, g, world, xoffset, y + g, 0, tileNum);
        getuprow(g, g, world, xoffset, y, 0, tileNum);
    }
    public static void getdownrow(int g, int n, TETile[][] thisworld, int xoffset, int y, int count, int tilenum) {
        if (count == g) {
            return;
        }
        int blank = g - n;
        int allrow = g + 2 * (g - 1);
        for (int x = xoffset + blank; x < xoffset + allrow - blank; x++) {
            thisworld[x][y] = randomTile(tilenum);
        }
        getdownrow(g, n - 1, thisworld, xoffset, y + 1, count + 1, tilenum);
    }
    public static void getuprow(int g, int n, TETile[][] thisworld, int xoffset, int y, int count, int tilenum) {
        if (count == g) {
            return;
        }
        int blank = g - count - 1;
        int allrow = g + 2 * (g - 1);
        for (int x = xoffset + blank; x < xoffset + allrow - blank; x++) {
            thisworld[x][y] = randomTile(tilenum);
        }
        getuprow(g,n + 1, thisworld, xoffset, y + 1, count + 1, tilenum);
    }
}
