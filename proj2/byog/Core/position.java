package byog.Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class position implements Serializable {
    int Xpos;
    int Ypos;
    boolean blocked;
    boolean damage;


    public position(int x, int y, boolean blockedornot) {
        this.Xpos = x;
        this.Ypos = y;
        this.blocked = blockedornot;
        this.damage = false;
    }

    public position() {
        this.blocked = false;
        this.damage = false;
    }

    public position(int x, int y) {
        this.Xpos = x;
        this.Ypos = y;
        this.blocked = false;
        this.damage = false;
    }

    public int getX() {
        return Xpos;
    }

    public int getY() {
        return Ypos;
    }
    //auto path-finding algorithm wish i can implement it
    //public static void findpath(position start, position end) {}

    public static List<position> SquarePositionGenerator(int x1, int x2, int y1, int y2, boolean blocked1) {
        List<position> Linepos = new ArrayList<position>();
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                Linepos.add(new position(x, y, blocked1));
            }
        }
        return Linepos;
    }

    public double awayfrom(position p2) {
        int x = (Xpos - p2.Xpos) * (Xpos - p2.Xpos);
        int y = (Ypos - p2.Ypos) * (Ypos - p2.Ypos);
        return Math.sqrt(x + y);
    }

    public void setXpos(int xpos) {
        this.Xpos = xpos;
    }

    public void setYpos(int ypos) {
        this.Ypos = ypos;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setDamage(boolean damage) {
        this.damage = damage;
    }

    public void drawTile(TETile[][] world1, TETile t) {
        world1[Xpos][Ypos] = t;
    }

    public boolean isTile(TETile[][] world1, TETile t) {
        return world1[Xpos][Ypos].equals(t);
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }
        position other = (position) obj;
        if (other.Xpos == this.Xpos && other.Ypos == other.Ypos) {
            return true;
        }
        return false;
    }

    public static position smallerX(position p1, position p2) {
        if (p1.Xpos > p2.Xpos) {
            return p2;
        }
        return p1;
    }

    public static position smallerY(position p1, position p2) {
        if (p1.Ypos > p2.Ypos) {
            return p2;
        }
        return p1;
    }

    public static position largerX(position p1, position p2) {
        if (p1.Xpos > p2.Xpos) {
            return p1;
        }
        return p2;
    }

    public static position largerY(position p1, position p2) {
        if (p1.Ypos > p2.Ypos) {
            return p1;
        }
        return p2;
    }

    @Override
    public int hashCode() {
        int res = Xpos * 100 + Ypos;
        return res;
    }


    List<position> getReachableNeighbour(TETile[][] world, int width, int height) {
        List<position> res = new ArrayList<>();
        if (Xpos + 1 < width && world[Xpos + 1][Ypos].equals(Tileset.FLOOR)) {
            res.add(new position(Xpos + 1, Ypos));
        }
        if (Xpos - 1 >= 0 && world[Xpos - 1][Ypos].equals(Tileset.FLOOR)) {
            res.add(new position(Xpos - 1, Ypos));
        }
        if (Ypos + 1 < height && world[Xpos][Ypos + 1].equals(Tileset.FLOOR)) {
            res.add(new position(Xpos, Ypos + 1));
        }
        if (Ypos - 1 >= 0 && world[Xpos][Ypos - 1].equals(Tileset.FLOOR)) {
            res.add(new position(Xpos, Ypos - 1));
        }
        return res;
    }
}
