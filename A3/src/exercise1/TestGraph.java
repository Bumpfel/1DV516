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
    }

    private void addElementsToGraph(int n) {
        int start = sut.size();
        for(int i = start; i < start + n; i ++) {
            sut.addVertex(i);
        }
    }

    // @Test
    // public void testaddVertex() {
    //     assertTrue(sut.size() == 0);
    //     sut.addVertex(1);
    //     assertTrue(sut.size() == 1);
    // }

    // @Test
    // public void testaddEdge() {
    //     sut.addVertex(vertex);
    //     sut.addEdge(sourceVertex, targetVertex);
    // }

    @Test
    public void testIsConnected() {
        addElementsToGraph(5);

        assertFalse(sut.isConnected());
        
        for(int i = 0; i < 4; i ++) {
            sut.addEdge(i, i + 1);
        }

        assertTrue(sut.isConnected());
    }

    @Test
    public void testIsAcyclic() {
        addElementsToGraph(5);
        
        for(int i = 0; i < 4; i ++) {
            sut.addEdge(i, i + 1);
        }
        assertTrue(sut.isAcyclic());
        System.out.println(sut);
        
        sut.addEdge(4, 1);
        assertFalse(sut.isAcyclic());
    }

    @Test
    public void testIsAcyclic2() {
        addElementsToGraph(5);
        
        for(int i = 0; i < 3; i ++) {
            sut.addEdge(i, i + 1);
        }
        addElementsToGraph(1);
        sut.addEdge(1, 4);
        assertTrue(sut.isAcyclic());
        System.out.println(sut);
    }
}