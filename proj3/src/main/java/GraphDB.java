import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB implements AStarGraph<Long>{
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     *
     * @param dbPath Path to the XML file to be parsed.
     */

    KdTree nodesTree = new KdTree(); // for nearest finding
    HashMap<Long, Point> activeNodes = new HashMap<>(); // nodes on the valid ways
    HashMap<Long, Point> nodes = new HashMap<>(); // all node on the map

    HashMap<Long, Point> locations = new HashMap<>(); // all node on the map
    HashMap<String, ArrayList<Point>> locationsWithName = new HashMap<>();
    HashMap<String, String> nameToDirtyName = new HashMap<>();
    Trie t = new Trie();
    HashMap<Long, ArrayList<Long>> adjNodes = new HashMap<>(); // all adj nodes
    HashMap<Long, ArrayList<WeightedEdge<Long>>> adjEdge = new HashMap<>();

    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        Iterator<HashMap.Entry<Long, ArrayList<Long>>> it = adjNodes.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<Long, ArrayList<Long>> entry = it.next();
            if (entry.getValue().isEmpty()) {
                nodes.remove(entry.getKey());
//                cleanCount++;
                it.remove();
            }

        }
//        System.out.println("all locations num:"+locations.size());
//        System.out.println("add node count:"+nodecount);
//
//        System.out.println("node size after clean:"+nodes.size());
//        System.out.println("adjacent node num:"+adjcount);
//
//        System.out.println("clean times:"+cleanCount);
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     *
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        return nodes.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     *
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        validateVertex(nodes.get(v).id);
        return adjNodes.get(v);
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        return nodesTree.nearest(lon, lat).id;
    }

    /**
     * Gets the longitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        validateVertex(v);
        return nodes.get(v).getX();
    }

    // @source https://blog.csdn.net/qq_45698833/article/details/116036624
    void validateVertex(long v) {
        if (!nodes.containsKey(v)) {
            throw new IllegalArgumentException("Vertex " + v + "is not in the graph");
        }
    }
    public List<String> getLocationsByPrefix(String pre) {
        List<String> cleanNames = t.searchPrefix(pre);
        List<String> res = new ArrayList<>();
        for (String c : cleanNames) {
            nameToDirtyName.get(c);
        }
        return res;
    }
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> res = new LinkedList<>();
        if (!locations.containsKey(locationName)) {
            return res;
        }
        for (Point p : locationsWithName.get(locationName)) {
            res.add(getNameNodeAsMap(p.getId()));
        }
        return res;
    }
    public Map<String, Object> getNameNodeAsMap(long id) {
        Point n = locations.get(id);
        Map<String, Object> res = new HashMap<>();
        res.put("id", n.id);
        res.put("lat", n.y);
        res.put("lon", n.x);
        res.put("name", n.name);

        return res;


    }


    String getWayName(long v, long w) {
        validateVertex(v);
        validateVertex(w);
        ArrayList<WeightedEdge<Long>> vEdges = adjEdge.get(v);
        ArrayList<WeightedEdge<Long>> wEdges = adjEdge.get(w);
        for (WeightedEdge vE : vEdges) {
            for (WeightedEdge wE : wEdges) {
                if (vE.other(v) == w && wE.other(w) == v) {
                    return vE.getName();
                }
            }
        }
        return null;
    }

    void addNode(long id, double lon, double lat) {
        Point n = new Point(id, lon, lat);
//        nodecount++;
        nodes.put(n.id, n);
        adjNodes.put(n.id, new ArrayList<>());
        adjEdge.put(n.id, new ArrayList<>());
        locations.put(n.id, n);
    }
    void addLocationsWithName(String name, long ID) {
        if (!locationsWithName.containsKey(name)) {
            ArrayList<Point> curList = new ArrayList<>();
            curList.add(locations.get(ID));
            locationsWithName.put(name, curList);
        } else {
            locationsWithName.get(name).add(locations.get(ID));
        }
    }

    /**
     * Gets the latitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        validateVertex(v);
        return nodes.get(v).getY();
    }

    void addWay(ArrayList<Point> curNodes, String name, long wayID) {
        ArrayList<WeightedEdge<Long>> curEdges = new ArrayList<>();
        for (int i = 0, j = 1; j < curNodes.size();i++, j++) {
            Point lastNode = curNodes.get(i);
            Point curNode= curNodes.get(j);
            addEdge(lastNode.id, curNode.id, name);
        }
        for (Point p : curNodes) {
            activeNodes.put(p.id, p);
        }
    }

    void addEdge(Long v, Long w, String wayName) {
        validateVertex(nodes.get(v).id);
        validateVertex(nodes.get(w).id);
        adjNodes.get(v).add(nodes.get(w).id);
        adjNodes.get(w).add(nodes.get(v).id);
        double weight = distance(v, w);
        adjEdge.get(v).add(new Edge(v, w, weight, wayName));
        adjEdge.get(w).add(new Edge(w, v, weight, wayName));
        nodesTree.insert(locations.get(v));
        nodesTree.insert(locations.get(w));
    }

    @Override
    public ArrayList<WeightedEdge<Long>> neighbors(Long v) {
        return adjEdge.get(v);
    }

    @Override
    public double estimatedDistanceToGoal(Long s, Long goal) {
        return distance(
                closest(nodes.get(s).getX(), nodes.get(s).getY()), goal);
    }

    class Edge implements WeightedEdge<Long> {
        long v;
        long w;
        double weight;
        String name = null;


        Edge(long v, long w, double weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        Edge(long v, long w, double weight, String name) {
            this.v = v;
            this.w = w;
            this.weight = weight;
            this.name = name;
        }

        @Override
        public Long from() {
            return nodes.get(v).id;
        }

        @Override
        public Long to() {
            return nodes.get(w).id;
        }

        @Override
        public double weight() {
            return weight;
        }

        void setName(String name1) {
            this.name = name1;
        }

        @Override
        public String getName() {
            return name;
        }
        @Override
        public long other(long x) {
            if (x == v) {
                return w;
            }
            if (x == w) {
                return v;
            }
            throw new NoSuchElementException();
        }

        public double getWeight() {
            return weight;
        }

    }


    class Way {
        long id;
        String name;
        ArrayList<WeightedEdge<Long>> edges;


        Way(long id) {
            this.id = id;
        }

        void setName(String name1) {
            this.name = name1;
        }

        void setEdges(ArrayList<WeightedEdge<Long>> edges1) {
            this.edges = edges1;
        }
    }

}
