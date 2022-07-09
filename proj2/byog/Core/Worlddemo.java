package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class Worlddemo {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;
    public static void drawFlower(int x1, int x2, int y2, int y1,TETile[][] world1) {
        for (int x = x1; x < x2; x++) {
            for (int y = y2; y < y1; y++) {
                world1[x][y] = Tileset.FLOWER;
            }
        }
    }
    public static void drawWall(int x1, int x2, int y2, int y1,TETile[][] world1) {
        for (int x = x1; x < x2; x++) {
            for (int y = y2; y < y1; y++) {
                world1[x][y] = Tileset.WALL;
            }
        }
    }


    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.WALL;
            }
        }
        MapGenerator map1 = new MapGenerator(100, 50, 14, world);
        // draws the world to the screen
        ter.renderFrame(world);
    }

}