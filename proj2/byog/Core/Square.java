package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
/** Square class stores information of the four corner status of positions
 * and have method of drawing itself without wall and finding connectors.
 */
class Square {
    position cornerLU;
    position cornerRD;
    position cornerLD;
    position cornerRU;
    boolean connected; // if the Square is connected or not
    int x1;
    int x2;
    int y1;
    int y2;
    int column; //the length of vertical side
    int row;   //the length of across side
    List<position> SquareWallRoom = new ArrayList<position>();
    List<position> SquareRoom = new ArrayList<position>();
    Square(position LeftUp, position RightDown) {
        cornerRD = RightDown;
        cornerLU = LeftUp;
        cornerLD = new position(cornerLU.Xpos, cornerRD.Ypos, false);
        cornerRU = new position(cornerRD.Xpos, cornerLU.Ypos, false);
        column = Math.abs(LeftUp.Ypos - RightDown.Ypos);
        row = Math.abs(RightDown.Xpos - LeftUp.Xpos);
        x1 = cornerLD.Xpos; // x1 is the smaller x
        x2 = cornerRD.Xpos; // x2 is the bigger x
        y1 = cornerLU.Ypos; // y1 is the bigger y
        y2 = cornerLD.Ypos; // y2 is the smaller y
    }
    position getCentre() {
        return new position((x1 + x2) / 2, (y1 + y2) / 2, false);
    }
    void drawSquare(TETile[][] world1, TETile t) {
        for (int x = x1; x < x2; x++) {
            for (int y = y2; y < y1; y++) {
                world1[x][y] = t;
            }
        }
    }
    void drawSquareWallFirst(TETile[][] world1) {
        for (int x = x1 - 1; x < x2 + 1; x++) {
            for (int y = y2 - 1; y < y1 + 1; y++) {
                world1[x][y] = Tileset.WALL;
            }
        }
    }




    /**
    List<Connector> findConnectors(TETile[][] world, int width, int height) {
        List<Connector> res = new ArrayList<>();
        for (int i = cornerLD.getX(); i <= cornerRU.getX(); i++) {
            position p1 = new position(i, cornerLD.getY(), false);
            Connector.addConnectableDirection(res, world, Tileset.FLOOR, Direction.DOWN, p1, width,
                    height);
            position p2 = new position(i, cornerRU.getY(), false);
            Connector.addConnectableDirection(res, world, Tileset.FLOOR, Direction.UP, p2, width,
                    height);
        }
        for (int j = cornerLD.getY(); j <= cornerRU.getY(); j++) {
            position p1 = new position(cornerLD.getX(), j, false);
            Connector.addConnectableDirection(res, world, Tileset.FLOOR, Direction.LEFT, p1, width,
                    height);
            position p2 = new position(cornerRU.getX(), j, false);
            Connector.addConnectableDirection(res, world, Tileset.FLOOR, Direction.RIGHT, p2, width,
                    height);
        }
        return res;
    }
    void setConnected(boolean TorF) {
        connected = TorF;
    }
    boolean getConnected() {
        return connected;
    }*/
}
