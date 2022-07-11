import org.junit.Test;
import java.util.Random;

public class testUnionFind {
    @Test
    public void testUnionFind() {
        int n = 100000000;
        UnionFind abab = new UnionFind(n);
        int i = 0;
        for (; i < n / 2; i++) {
            abab.connect(i, i + 1);
        }
        for (; i < 3 * n / 4; i++) {
            abab.connect(i, i + 1);
        }
        for (; i < n - 100; i++) {
            abab.connect(i, i + 1);
        }
        double startTime=System.currentTimeMillis(); //获取开始时间
        abab.connect(0, 3*n/5);
        for (int x = 0; x < 3*n/4; x++) {
            abab.isConnected(3*n/4-x, x);
        }
        for (int x = 0; x < 3*n/4; x++) {
            abab.isConnected(3*n/4-x, x);
        }
        double endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    }
}
