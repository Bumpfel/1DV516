package assignment3AADS.assignment3.MyTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import assignment3AADS.assignment3.generic.AbstractGraph;
import assignment3AADS.assignment3.generic.MyDirectedGraph;
import assignment3AADS.assignment3.generic.MyUndirectedGraph;

public class TestGraph {
    private AbstractGraph<Integer> sut;

    @Before
    public void setup() { 
        sut = new MyUndirectedGraph<Integer>();
        // sut = new MyDirectedGraph<Integer>();
    }

    private void addElementsToGraph(int n) {
        int start = sut.size();
        for(int i = start; i < start + n; i ++) {
            sut.addVertex(i);
        }
    }

    @Test
    public void testIsConnected() {
        addElementsToGraph(5);

        assertFalse(sut.isConnected());
        
        for(int i = 0; i < 4; i ++) {
            sut.addEdge(i, i + 1);
        }
        assertTrue(sut.isConnected());
        
        sut.addVertex(100);
        assertFalse(sut.isConnected());
    }

    @Test
    public void testIsAcyclic() {
        addElementsToGraph(5);
        
        for(int i = 0; i < 4; i ++) {
            sut.addEdge(i, i + 1);
        }
        assertTrue(sut.isAcyclic());
        
        sut.addEdge(4, 1);
        // System.out.println(sut);
        assertFalse(sut.isAcyclic());
    }

    @Test
    public void testIsAcyclic2() {
        addElementsToGraph(5);
        
        for(int i = 0; i < 4; i ++) {
            sut.addEdge(i, i + 1);
        }
        
        sut.addVertex(100);
        sut.addEdge(100, 1);
        
        // System.out.println(sut);
        assertTrue(sut.isAcyclic());
    }
}