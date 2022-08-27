import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class TrieNode {

    /**
     * The key stored in this node if any.
     */
    private String key;

    /**
     * the outgoing edges of this node, implemented as a sorted map of character to the child node.
     */
    private SortedMap<Character, TrieNode> edges;


    TrieNode addEdge(char c) {
        if (edges == null) {
            edges = new TreeMap<Character, TrieNode>();
        }
        TrieNode childNode = new TrieNode();
        edges.put(c, childNode);
        return childNode;
    }

    TrieNode getNodeByEdge(char c) {
        return (edges == null) ? null : edges.get(c);
    }

    TrieNode deleteEdge(char c) {
        return (edges == null) ? null : edges.remove(c);
    }

    Iterator<TrieNode> getChildren() {
        return (edges == null) ? null : edges.values().iterator();
    }

    void setKey(String key) {
        this.key = key;
    }

    String getKey() {
        return key;
    }

    public int getChildrenCnt() {
        return edges == null ? 0 : edges.size();
    }


}
