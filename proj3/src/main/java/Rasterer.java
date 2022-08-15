import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    public static final int TILE_SIZE = 256;
    private static final double LONG_WIDTH = Math.abs(ROOT_LRLON - ROOT_ULLON);
    private static final double LAT_HEIGHT = Math.abs(ROOT_ULLAT - ROOT_LRLAT);
    public static final double Root_lonDPP = Math.abs(ROOT_ULLON - ROOT_LRLON) / TILE_SIZE;

    public Rasterer() {
    }
    private int getDepth(double ullon, double lrlon, double width) {
        double desiredLonDPP = (lrlon - ullon) / width;
        double n = Math.log(LONG_WIDTH / (desiredLonDPP * TILE_SIZE)) / Math.log(2.0);
        int depth = (int) Math.ceil(n);
        return Math.min(7, depth);
    }
    private int[] getWhich(double lon, double lat, int depth) {
        double sclaeLon = Math.abs(ROOT_LRLON - ROOT_ULLON) / Math.pow(2, depth);
        double lonPos = Math.abs(lon - ROOT_ULLON);
        double scaleLat = Math.abs(ROOT_LRLAT - ROOT_ULLAT) / Math.pow(2, depth);
        double latPos = Math.abs(lat - ROOT_ULLAT);
        int resLon = (int) (Math.floor(lonPos / sclaeLon));
        int resLat = (int) (Math.floor(latPos / scaleLat));
        if (lon > ROOT_LRLON) {
            resLon = (int) (Math.pow(2, depth) - 1);
        }
        if (lon < ROOT_ULLON) {
            resLon = 0;
        }
        if (lat > ROOT_ULLAT) {
            resLat = 0;
        }
        if (lat < ROOT_LRLAT) {
            resLat = (int) (Math.pow(2, depth) - 1);
        }
        return new int[] {resLon, resLat};
    }

    private String[][] toDisplay(int[] ul, int[] lr, int depth) {
        int lonNum = lr[0] - ul[0] + 1;
        int latNum = lr[1] - ul[1] + 1;
        StringBuilder temp = new StringBuilder();
        String[][] res = new String[latNum][lonNum];
        for (int lat = ul[1], i = 0; i < latNum; lat++, i++) {
            for (int lon = ul[0], j = 0; j < lonNum; lon++, j++)
                res[i][j] = "d" + depth + "_x" + lon + "_y" + lat + ".png";
        }
        return res;
    }

    @Test
    public void test() {
        double ullon = -122.30410170759153;
        double lrlon = -122.2104604264636;
        double ullat = 37.870213571328854;
        double lrlat = 37.8318576119893;
        double w = 1085;
        double h = 556;
        double lonDPP = Math.abs(ullon - lrlon) / w;
        int depth = getDepth(ullon, lrlon, w);
        System.out.println(depth);

        int[] ultest = getWhich(ullon, ullat, depth);
        System.out.println(Arrays.toString(ultest));
        int[] lrtest = getWhich(lrlon, lrlat, depth);
        System.out.println(Arrays.toString(lrtest));
        String[][] test = toDisplay(ultest, lrtest, depth);
        for (String[] ss : test) {
            for (String s : ss) {
                System.out.print(s + " ");
            }
            System.out.println();
        }


    }



    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        double ullon = params.get("ullon");
        double lrlon = params.get("lrlon");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        double w = params.get("w");
        double h = params.get("h");
        int depth = getDepth(ullon, lrlon, w);
        int[] ulPos = getWhich(ullon, ullat, depth);
        int[] lrPos = getWhich(lrlon, lrlat, depth);
        String[][] grid = toDisplay(ulPos, lrPos, depth);
        double widthPerPic = LONG_WIDTH / Math.pow(2.0, depth);
        double heightPerPic = LAT_HEIGHT / Math.pow(2.0, depth);
        int xMin = (int) (Math.floor((ullon - ROOT_ULLON) / widthPerPic)) ;
        int xMax = (int) (Math.floor((lrlon - ROOT_ULLON) / widthPerPic)) ;
        int yMin = (int) (Math.floor((ROOT_ULLAT - ullat) / heightPerPic)) ;
        int yMax = (int) (Math.floor((ROOT_ULLAT - lrlat) / heightPerPic)) ;

        double xLeftBounding = ROOT_ULLON + xMin * widthPerPic;
        double xRightBounding = ROOT_ULLON + (xMax + 1) * widthPerPic;
        double yUpperBouding = ROOT_ULLAT - yMin * heightPerPic;
        double yLowerBouding = ROOT_ULLAT - (yMax + 1) * heightPerPic;
        // corner situation
        if (ullon < ROOT_ULLON) {
            xMin = 0;
            xLeftBounding = ROOT_ULLON;
        }
        if (lrlon > ROOT_LRLON) {
            xMax = (int) Math.pow(2, depth) - 1;
            xRightBounding = ROOT_LRLON;
        }
        if (ullat > ROOT_ULLAT) {
            yMin = 0;
            yUpperBouding = ROOT_ULLAT;
        }
        if (lrlat < ROOT_LRLAT) {
            yMax = (int) Math.pow(2, depth) - 1;
            yLowerBouding = ROOT_LRLAT;
        }
        /**
        String[][] render_grid = new String[yMax - yMin + 1][xMax - xMin + 1];
        for (int i = yMin; i <= yMax; i++) {
            for (int j = xMin; j <= xMax; j++) {
                render_grid[i - yMin][j - xMin] = "d" + depth + "_x" + j + "_y" + i + ".png";
            }
        }*/

        Map<String, Object> results = new HashMap<>();

        results.put("query_success", false);
        if (lrlat >= ROOT_ULLAT || lrlon <= ROOT_ULLON
                || ullat <= ROOT_LRLAT || ullon >= ROOT_LRLON
                || ullon >= lrlon || ullat <= lrlat) {
            results.put("render_grid", null);
            results.put("raster_ul_lon", 0);
            results.put("raster_ul_lat", 0);
            results.put("raster_lr_lon", 0);
            results.put("raster_lr_lat", 0);
            results.put("depth", 0);
        }
        else {

            results.put("render_grid", grid);
            results.put("raster_ul_lon", xLeftBounding);
            results.put("raster_ul_lat", yUpperBouding);
            results.put("raster_lr_lon", xRightBounding);
            results.put("raster_lr_lat", yLowerBouding);

            results.put("depth", depth);
            results.put("query_success", true);


        }
        return results;

    }

}
