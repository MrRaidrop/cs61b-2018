import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Trie {
    private final TrieNode root;

    /**
     * Creates a new empty TRIE object.
     */
    public Trie() {
        root = new TrieNode();
    }


    /**
     * Inserts the specified key into this Trie object.
     * @param key
     */
    public void insert(String key) {
        TrieNode currNode = root;
        for (char c : key.toCharArray()) {
            TrieNode child = currNode.getNodeByEdge(c);
            if (child == null) {
                currNode = currNode.addEdge(c);
            } else {
                currNode = child;
            }
        }
        currNode.setKey(key);
    }

    /**
     * Returns all the keys in the Trie which start with the specified prefix.
     * @param prefix
     * @return
     */
    public List<String> searchPrefix(String prefix) {
        TrieNode currNode = root;
        for (char c : prefix.toCharArray()) {
            TrieNode child = currNode.getNodeByEdge(c);
            if (child == null) {
                return Collections.emptyList();
            } else {
                currNode = child;
            }
        }
        List<String> matches = new ArrayList<String>();
        preorderTraverse(currNode, matches);
        return matches;
    }

    /**
     * Delete all children below the specified prefix.
     * @param prefix
     * @return true if successful; false if prefix not found.
     */
    public boolean deletePrefix(String prefix) {
        TrieNode currNode = root;
        TrieNode prevNode = root;
        char delFrom = ' ';
        for (char c : prefix.toCharArray()) {
            TrieNode child = currNode.getNodeByEdge(c);
            delFrom = c;
            if (child == null) {
                return false;
            } else {
                prevNode = currNode;
                currNode = child;
            }
        }
        prevNode.deleteEdge(delFrom);
        return true;
    }

    /**
     * Does preorder traversal of Trie and add retrieved keys in the specified results list.
     * @param currNode
     * @param results
     */
    private void preorderTraverse(TrieNode currNode, List<String> results) {
        if (currNode.getKey() != null) {
            results.add(currNode.getKey());
        }
        Iterator<TrieNode> children = currNode.getChildren();
        if (children != null) {
            while (children.hasNext()) {
                preorderTraverse(children.next(), results);
            }
        }
    }

    /**
     * Does preorder traversal of Trie and print all keys.
     */
    public void traverse() {
        preorderTraverse(root);
    }
    private void preorderTraverse(TrieNode root) {
        if (root.getKey() != null) {
            System.out.println(root.getKey());
        }
        Iterator<TrieNode> children = root.getChildren();
        if (children != null) {
            while (children.hasNext()) {
                preorderTraverse(children.next());
            }
        }
    }
    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("vino");
        t.insert("vinod");
        t.insert("vin");
        t.insert("jyo");
        t.insert("jyotsna");
        t.insert("jyot");
        t.insert("jyots");
        t.insert("jyotsn");
        t.insert("joe");

        System.out.println(t.searchPrefix("vin"));
        System.out.println(t.searchPrefix("j"));
        System.out.println(t.searchPrefix("jy"));
        System.out.println(t.searchPrefix("joe"));

        System.out.println(t.searchPrefix("bhalblah"));

        t.deletePrefix("vino");
        System.out.println(t.searchPrefix("v"));

        t.traverse();
    }

}
