package assignment3AADS.assignment3.MyTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Linearly connects added vertices
     */
    private void connectVertices() {
        for(int i = 0; i < sut.size() - 1; i ++) {
            sut.addEdge(i, i + 1);
        }
    }

    @Test
    public void testIsConnected() {
        addElementsToGraph(5);

        assertFalse(sut.isConnected());
        
        connectVertices();
        assertTrue(sut.isConnected());
        
        sut.addVertex(100);
        assertFalse(sut.isConnected());
    }

    @Test
    public void testIsAcyclic() {
        addElementsToGraph(5);
        
        connectVertices();
        assertTrue(sut.isAcyclic());
        
        sut.addEdge(4, 1);
        // System.out.println(sut);
        assertFalse(sut.isAcyclic());
    }

    @Test
    public void testIsAcyclic2() {
        addElementsToGraph(5);
        
        connectVertices();
        
        sut.addVertex(100);
        sut.addEdge(100, 1);
        
        // System.out.println(sut);
        assertTrue(sut.isAcyclic());
    }

    @Test
    public void testConnectedComponents() {
        List<List<Integer>> expected = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        expected.add(list1);
        List<Integer> list2 = new ArrayList<>();
        list2.add(5);
        expected.add(list2);
        
        for(int i = 0; i < 4; i++) {
            sut.addVertex(i);
            list1.add(i);
        }

        connectVertices();
        sut.addVertex(4);

        // System.out.println(expected);
        // System.out.println("-----");
        // System.out.println(sut.connectedComponents());

        List<List<Integer>> actual = sut.connectedComponents();

        assertEquals(expected, actual);
    }
}