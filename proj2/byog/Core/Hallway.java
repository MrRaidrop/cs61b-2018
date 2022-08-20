package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;

class Hallway {
    position corner;
    position start;
    position end;
    int key;
    ArrayList<Hallway> hallways = new ArrayList<>();

    Hallway(position corner1, position start1, position end1, int key1) {
        this.corner = corner1;
        this.start = start1;
        this.end = end1;
        this.key = key1;
    }

    static position addColumHall(TETile[][] world1, position pos, int len) {
        for (int y = 0; y < len + 2; y++) {
            world1[pos.Xpos - 1][pos.Ypos + y] = Tileset.WALL;
            world1[pos.Xpos + 1][pos.Ypos + y] = Tileset.WALL;
        }
        return new position(pos.Xpos, pos.Ypos + len);
    }
    static position addRowHall(TETile[][] world1, position pos, int width) {
        for (int x = 0; x < width + 2; x++) {
            world1[pos.Xpos + x][pos.Ypos - 1] = Tileset.WALL;
            world1[pos.Xpos + x][pos.Ypos + 1] = Tileset.WALL;
        }
        return new position(pos.Xpos + width, pos.Ypos);
    }
    private static void fillColumHall(TETile[][] world, position p, int len) {
        for (int y = 0; y < len; y += 1) {
            world[p.Xpos][p.Ypos + y] = Tileset.FLOOR;
        }
    }
    private static void fillRowHall(TETile[][] world, position p, int width) {
        for (int x = 0; x < width; x += 1) {
            world[p.Xpos + x][p.Ypos] = Tileset.FLOOR;
        }
    }

    private static void fillLHall(TETile[][] world, Hallway hallway1) {
        switch (hallway1.key) {
            case 0: {
                fillRowHall(world, hallway1.start, hallway1.corner.Xpos - hallway1.start.Xpos + 1);
                fillColumHall(world, position.smallerY(hallway1.corner, hallway1.end),
                        Math.abs(hallway1.corner.Ypos - hallway1.end.Ypos) + 1);
                break;
            }
            case 1: {
                fillColumHall(world, hallway1.start, hallway1.corner.Ypos - hallway1.start.Ypos + 1);
                fillRowHall(world, position.smallerX(hallway1.corner, hallway1.end),
                        Math.abs(hallway1.corner.Xpos - hallway1.end.Xpos) + 1);
                break;
            }
            default:
                break;
        }
    }

    public static void fillLHallList(TETile[][] world, ArrayList<Hallway> HallList) {
        for (int i = 0; i < HallList.size(); i += 1) {
            fillLHall(world, HallList.get(i));
        }
    }

}
