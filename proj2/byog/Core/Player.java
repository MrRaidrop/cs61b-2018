package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Player {
    int health;
    ArrayList<Equipment> EqList;
    private static position pos;
    private static position goal = getGoalPos();

    public static position getPos() {
        return pos;
    }

    public static void setPos(position p) {
        pos = p;
    }

    public static void walkLeft(TETile[][] world) {
        position newPos = new position(pos.getX() - 1, pos.getY());
        walk(world, newPos);
    }

    public static void walkRight(TETile[][] world) {
        position newPos = new position(pos.getX() + 1, pos.getY());
        walk(world, newPos);
    }


    public static void walkUp(TETile[][] world) {
        position newPos = new position(pos.getX(), pos.getY() + 1);
        walk(world, newPos);
    }

    public static void walkDown(TETile[][] world) {
        position newPos = new position(pos.getX(), pos.getY() - 1);
        walk(world, newPos);
    }

    private static void walk(TETile[][] world, position newPos) {
        if (newPos.distance(goal) == 0) {
            System.exit(0);
        }
        if (newPos.isTile(world, Tileset.FLOOR)) {
            pos.drawTile(world, Tileset.FLOOR);
            newPos.drawTile(world, Tileset.PLAYER);
            pos = newPos;
        }
    }

    private static position getGoalPos() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("goalPos.txt"));
            position goalPos = (position) in.readObject();
            in.close();
            return goalPos;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new position(999, 999);
    }

}
