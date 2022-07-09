import org.junit.Test;

public class testUnionFind {
    @Test
    public void testUnionFind() {
        UnionFind aba = new UnionFind(10);
        System.out.println(aba.isConnected(0, 3));
        aba.connect(0 , 1);
        aba.connect(1 , 2);
        aba.connect(2 , 3);
        System.out.println(aba.sizeOf(3));
        System.out.println(aba.find(3));
        System.out.println(aba.parent(3));
        System.out.println(aba.isConnected(0, 3));
        aba.connect(0 , 2);
        System.out.println(aba.parent(2));
    }
}
