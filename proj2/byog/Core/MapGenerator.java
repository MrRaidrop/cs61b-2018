package byog.Core;


import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class MapGenerator {
    int mapwidth;
    int maplength;
    int maxRoomNum;
    private static final int minroomsize = 2;
    private static final int maxroomsize = 10;
    private static position[][] allPos;
    ArrayList<Square> squares;
    private int numberofrooms;
    ArrayList<Hallway> hallways;
    long Seed;
    Random RANDOM;
    private int numOfUnblock = 0;
    private int numOfblocked = 0;
    private position playerPos;
    private position goalPos;



    // reference: https://zhuanlan.zhihu.com/p/27381213
    public MapGenerator(int mapwidth1, int maplength1, int maxRoomNum1, TETile[][] world1, long seed) {
        mapwidth = mapwidth1;
        maplength = maplength1;
        maxRoomNum = maxRoomNum1;
        RANDOM = new Random(seed);
        for (int x = 0; x < mapwidth; x += 1) {
            for (int y = 0; y < maplength; y += 1) {
                world1[x][y] = Tileset.GRASS;
            }
        }
        generateSquares(world1);
        generateHalls(world1, squares);
        generateSquaresAgain(world1);
        getAllPos(world1);
        setPlayerAndGoal(world1);

    }

    void getAllPos(TETile[][] world1) {
        allPos = new position[mapwidth][maplength];
        for (int x = 0; x < mapwidth; x += 1) {
            for (int y = 0; y < maplength; y += 1) {
                if (world1[x][y].equals(Tileset.WALL)) {
                    position cur = new position(x, y, true);
                    // set damage wall here after, using randomness
                    allPos[x][y] = cur;
                    numOfblocked++;
                }
                if (world1[x][y].equals(Tileset.FLOOR)) {
                    position cur = new position(x, y, false);
                    // set damage floor here
                    allPos[x][y] = cur;
                    numOfUnblock++;
                }
                // add something after maybe
            }
        }
    }
    void setDamageFloor() {

    }

    position getGoalPos(TETile[][] world1) {
        return goalPos;
    }
    void setPlayerAndGoal(TETile[][] world1) {
        int pp = RandomUtils.uniform(RANDOM, 1, numOfUnblock);
        int ld = RandomUtils.uniform(RANDOM, 1, numOfblocked);
        boolean goalSeted = false;

        for (int x = 0; x < mapwidth; x += 1) {
            for (int y = 0; y < maplength; y += 1) {
                if (world1[x][y].equals(Tileset.WALL)) {
                    ld--;
                    if (ld <= 0 && !goalSeted &&
                            ((world1[x - 1][y].equals(Tileset.WALL) &&
                                    world1[x + 1][y].equals(Tileset.WALL)) ||
                                    (world1[x][y - 1].equals(Tileset.WALL) &&
                                            world1[x][y + 1].equals(Tileset.WALL)))) {
                        world1[x][y] = Tileset.LOCKED_DOOR;
                        goalPos = new position(x, y);
                        goalSeted = true;
                    }
                }
                if (world1[x][y].equals(Tileset.FLOOR)) {
                    pp--;
                    if (pp == 0) {
                        world1[x][y] = Tileset.PLAYER;
                        playerPos = new position(x, y);
                    }
                }
                // add something after maybe
            }
        }
    }

    //generate 6 to given numbers of rooms with restricted size, and draw it.
    void generateSquares(TETile[][] world1) {
        squares = new ArrayList<Square>();
        numberofrooms = RandomUtils.uniform(RANDOM, 6, maxRoomNum);
        //generate rooms of random number from 6 to given max number
        while (squares.size() < numberofrooms) {
            Square S1 = oneSquareGenerator(maxroomsize, minroomsize);
            if (overlapcheck(S1, squares)) {
                S1.SquareWallRoom = position.SquarePositionGenerator
                        (S1.x1 , S1.x2, S1.y2, S1.y1, true);
                S1.SquareRoom = position.SquarePositionGenerator
                        (S1.x1 + 1, S1.x2 - 1, S1.y2 + 1, S1.y1 - 1, false);
                squares.add(S1);
                S1.drawSquareWallFirst(world1);
                S1.drawSquare(world1, Tileset.FLOOR);
            }
        }
        squares = sortSquares(squares);
    }

    void generateSquaresAgain(TETile[][] world1) {
        for (Square s : squares) {
            s.drawSquare(world1, Tileset.FLOOR);
        }
    }

    private void generateHalls(TETile[][] world1, List<Square> squares1) {
        hallways = new ArrayList<>();
        for (int i = 0; i < squares1.size() - 1; i++) {
            hallways.add(drawLWay(world1, squares1.get(i), squares1.get(i + 1)));
        }
        Hallway.fillLHallList(world1, hallways);
    }
    //sort square by centre position
    private ArrayList<Square> sortSquares(ArrayList<Square> squares1) {
        ArrayList<Square> res = new ArrayList<>();
        int count = squares1.size();
        while (count > 0) {
            int i = findXYminSquare(squares1);
            res.add(squares1.get(i));
            squares1.remove(i);
            count--;
        }
        return res;
    }
    private static int findXYminSquare(ArrayList<Square> squares1) {
        Square temp = squares1.get(0);
        int res = 0;
        for (int i = 0; i < squares1.size(); i++) {
            int x = temp.getCentre().Xpos + temp.getCentre().Ypos;
            int y = squares1.get(i).getCentre().Xpos + squares1.get(i).getCentre().Ypos;
            if (x > y) {
                temp = squares1.get(i);
                res = i;
            }
        }
        return res;
    }
    // helper method: use random starting position to generate a Square
    Square oneSquareGenerator(int maxl, int minl) {
        //randomx1 is the left X of the square
        int randomx1 = RandomUtils.uniform(RANDOM, 5, mapwidth - maxl - 5);
        //randomy1 is the Down Y of the square
        int randomy1 = RandomUtils.uniform(RANDOM, maxl + 5, maplength - 5);
        int onesidelen = RandomUtils.uniform(RANDOM, minl, maxl);
        int anothersidelen = RandomUtils.uniform(RANDOM, minl, maxl);
        position p1 = new position(randomx1, randomy1, false);
        position p2 = new position(randomx1 + onesidelen, randomy1 - anothersidelen, false);
        return new Square(p1, p2);
    }

    position randomInsidePos(Square S1) {
        int insideX = RandomUtils.uniform(RANDOM, 0, S1.row) / 2 + S1.getCentre().Xpos;
        int insideY = RandomUtils.uniform(RANDOM, 0, S1.column) / 2 + S1.getCentre().Ypos;
        return new position(insideX, insideY);
    }
    Hallway drawLWay(TETile[][] world1, Square S1, Square S2) {
        position p2 = randomInsidePos(S2);
        position p1 = randomInsidePos(S1);
        int key = RandomUtils.uniform(RANDOM, 0, 2);
        switch (key) {
            case 0: {  //draw Row way first.
                position RowStart = position.smallerX(p1, p2);
                position RowCorner = Hallway.addRowHall(world1, RowStart, Math.abs(p2.Xpos - p1.Xpos));
                position ColumStart = position.smallerY(RowCorner, position.largerX(p1, p2));
                Hallway.addColumHall(world1, ColumStart, Math.abs(p2.Ypos - p1.Ypos));
                addCorner(world1, RowCorner);
                return new Hallway(RowCorner, RowStart, position.largerX(p1, p2), 0);
            }
            case 1: { //draw Colum way first.
                position ColumStart = position.smallerY(p1, p2);
                position ColumCorner = Hallway.addColumHall(world1, ColumStart, Math.abs(p2.Ypos - p1.Ypos));
                position RowStart = position.smallerX(ColumCorner, position.largerY(p1, p2));
                Hallway.addRowHall(world1, RowStart, Math.abs(p2.Xpos - p1.Xpos));
                addCorner(world1, ColumCorner);
                return new Hallway(ColumCorner, ColumStart, position.largerY(p1, p2), 1);
            }
            default: {
                return null;
            }
        }
    }
        static void addCorner(TETile[][] world1, position corner) {
        for (int i = corner.Xpos - 1; i < corner.Xpos + 1; i++) {
            for (int j = corner.Ypos - 1; j < corner.Ypos + 1; j++) {
                world1[i][j] = Tileset.WALL;
            }
        }
        world1[corner.Xpos][corner.Ypos] = Tileset.FLOOR;
    }

    // helper method: judge if two line cross or not
    private static boolean CrossLine(int left, int right, int y, int top, int bottom, int x) {
        return (top < y) && (bottom > y) && (left < x) && (right > x);
    }
    // helper method: judge if two square overlap or not
    private static boolean overlap(Square S1, Square S2) {
        if (S2.y2 > S1.y1 || S1.y2 > S2.y1 || S1.x2 < S2.x1 || S2.x2 < S1.x1) {
            return false;
        }
        return true;
    }
    //helper method: judge if the given square overlap to any square in the square list
    private static boolean overlapcheck(Square S1, List<Square> squares) {
        if (squares.size() == 0) {
            return true;
        }
        for (Square cur : squares) {
            if (overlap(cur, S1)) {
                return false;
            }
        }
        return true;
    }
}
