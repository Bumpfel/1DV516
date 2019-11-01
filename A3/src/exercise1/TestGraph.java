package exercise1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestGraph {
    private static Graph sut;

    @BeforeClass
    public static void setup() {
        sut = new MyUndirectedGraph();

        for(int i = 0; i < 5; i ++) {
            sut.addVertex(i);
        }
    }

    @Test
    public void testSmt() {
        
    }

    @Test
    public void testIsConnected() {
        assertFalse(sut.isConnected());
        
        for(int i = 0; i < 4; i ++) {
            sut.addEdge(i, i + 1);
        }

        assertTrue(sut.isConnected());
    }
}