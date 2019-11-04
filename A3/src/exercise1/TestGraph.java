package exercise1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestGraph {
    private Graph sut;

    @Before
    public void setup() { 
        // sut = new MyUndirectedGraph();
        sut = new MyDirectedGraph();
    }

    private void addElementsToGraph(int n) {
        int start = sut.size();
        for(int i = start; i < start + n; i ++) {
            sut.addVertex(i);
        }
    }

    @Test
    public void tempTest() {
        sut.addVertex(1);
        sut.addVertex(2);
        sut.addEdge(1, 2);
        System.out.println(sut);
    }

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
        
        sut.addEdge(4, 1);
        assertFalse(sut.isAcyclic());
        System.out.println(sut);
    }

    @Test
    public void testIsAcyclic2() {
        addElementsToGraph(5);
        
        for(int i = 0; i < 4; i ++) {
            sut.addEdge(i, i + 1);
        }
        
        sut.addVertex(100);
        sut.addEdge(100, 1);
        
        assertTrue(sut.isAcyclic());
        System.out.println(sut);
    }
}