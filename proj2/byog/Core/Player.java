package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class Player {
    int health;
    ArrayList<Equipment> EqList;
    private static position pos;

    private static position goalPos = getGoalPos();

    public static position getPos() {
        return pos;
    }

    public static void setPos(position p) {
        pos = p;
    }

    public static void walkLeft(TETile[][] world, TERenderer ter) {
        position newPos = new position(pos.getX() - 1, pos.getY());
        walk(world, newPos, ter);
    }

    public static void walkRight(TETile[][] world, TERenderer ter) {
        position newPos = new position(pos.getX() + 1, pos.getY());
        walk(world, newPos, ter);
    }


    public static void walkUp(TETile[][] world, TERenderer ter) {
        position newPos = new position(pos.getX(), pos.getY() + 1);
        walk(world, newPos, ter);
    }

    public static void walkDown(TETile[][] world, TERenderer ter) {
        position newPos = new position(pos.getX(), pos.getY() - 1);
        walk(world, newPos, ter);
    }

    private static void walk(TETile[][] world, position newPos, TERenderer ter) {
        if (newPos.awayfrom(goalPos) == 0) {
            Game.drawEnd();
        }
        if (newPos.isTile(world, Tileset.FLOOR)) {
            pos.drawTile(world, Tileset.FLOOR);
            newPos.drawTile(world, Tileset.PLAYER);
            ter.renderFrame(world);
            pos = newPos;
        }
    }

    public static void autoWalk(TETile[][] world, position goalThisTime, int w, int h, TERenderer ter) {
        AstarSolver solver = new AstarSolver(world, pos, goalThisTime, w, h);
        List<position> positions = solver.getSolution();
        for (position p : positions) {
            if (!p.equals(pos)) {

                walk(world, p, ter);
                ter.renderFrame(world);
                StdDraw.pause(200);
            }
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
