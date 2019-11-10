package assignment3AADS.assignment3.MyTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import assignment3AADS.assignment3.generic.AbstractGraph;
import assignment3AADS.assignment3.generic.MyDirectedGraph;
import assignment3AADS.assignment3.generic.MyUndirectedGraph;

public class TestGraph {
    private AbstractGraph<Integer> sut;

    private void addElementsToGraph(int n) {
        int start = sut.size();
        for(int i = start; i < start + n; i ++) {
            sut.addVertex(i);
        }
    }

    /**
     * Linearly connects vertices
     */
    private void connectVertices() {
        for(int i = 0; i < sut.size() - 1; i ++) {
            sut.addEdge(i, i + 1);
        }
    }

    /**
     * Linearly connects vertices in specified range
     */
    private void connectVertices(int start, int end) {
        for(int i = start; i < end; i ++) {
            sut.addEdge(i, i + 1);
        }
    }


    @Test
    public void testIsConnectedUndirected() {
        sut = new MyUndirectedGraph<>();
        checkIsConnected();
    }

    @Test
    public void testIsConnectedDirected() {
        sut = new MyDirectedGraph<>();
        checkIsConnected();
    }

    private void checkIsConnected() {
        addElementsToGraph(5);

        assertFalse(sut.isConnected());
        
        connectVertices();
        assertTrue(sut.isConnected());
        
        sut.addVertex(100);
        assertFalse(sut.isConnected());

        sut.addEdge(100, 1);

        if(sut instanceof MyUndirectedGraph) {
            assertTrue(sut.isConnected());
        } else {
            assertFalse(sut.isConnected());
        }
    }

    @Test
    public void testIsConnectedDirected2() {
        sut = new MyDirectedGraph<>();

        addElementsToGraph(4);
        sut.addEdge(0, 1);
        sut.addEdge(1, 2);
        sut.addEdge(1, 3);

        assertTrue(sut.isConnected());
    }

    @Test
    public void testIsAcyclicUndirected1() {
        sut = new MyUndirectedGraph<>();
        acyclicTest1();
    }
    @Test
    public void testIsAcyclicDirected1() {
        sut = new MyDirectedGraph<>();
        acyclicTest1();
    }
    private void acyclicTest1() {
        addElementsToGraph(5);
        connectVertices();
        assertTrue(sut.isAcyclic());
        
        sut.addEdge(4, 1);
        assertFalse(sut.isAcyclic());
    }


    @Test
    public void testIsAcyclicUndirected2() {
        sut = new MyUndirectedGraph<>();
        acyclicTest2();
    }
    @Test
    public void testIsAcyclicDirected2() {
        sut = new MyDirectedGraph<>();
        acyclicTest2();
    }
    private void acyclicTest2() {
        addElementsToGraph(5);
        connectVertices();
        sut.addVertex(100);
        sut.addEdge(100, 1);
        assertTrue(sut.isAcyclic());
    }


    @Test
    public void testIsAcyclicUndirected3() {
        sut = new MyUndirectedGraph<>();
        acyclicTest3();
    }
    @Test
    public void testIsAcyclicDirected3() {
        sut = new MyDirectedGraph<>();
        acyclicTest3();
    }
    private void acyclicTest3() {
        for(int i = 1; i <= 5; i++) {
            sut.addVertex(i);
        }

        sut.addEdge(1, 3);
        sut.addEdge(1, 4);
        sut.addEdge(2, 1);
        sut.addEdge(3, 2);

        assertFalse(sut.isAcyclic());
    }


    @Test
    public void testConnectedComponentsUndirected() {
        sut = new MyUndirectedGraph<>();

        List<List<Integer>> expected = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        expected.add(list1);
        List<Integer> list2 = new ArrayList<>();
        list2.add(5);
        expected.add(list2);
        
        for(int i = 1; i < 5; i++) {
            sut.addVertex(i);
            list1.add(i);
        }

        connectVertices(1, 4);
        sut.addVertex(5);

        List<List<Integer>> actual = sut.connectedComponents();
        assertTrue(actual.containsAll(expected));
    }


    @Test
    public void testConnectedComponentsDirected() {
        sut = new MyDirectedGraph<>();
        
        List<List<Integer>> expected = new ArrayList<>();
        
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();

        expected.add(list1);
        expected.add(list2);
        expected.add(list3);
  
        for(int i = 1; i < 4; i++) {
            sut.addVertex(i);
            list1.add(i);
        }

        list2.add(4);
        list3.add(5);
      
        sut.addVertex(4);
        sut.addVertex(5);

        sut.addEdge(1, 3);
        sut.addEdge(1, 4);
        sut.addEdge(2, 1);
        sut.addEdge(3, 2);

        sut.addVertex(5);

        List<List<Integer>> actual = sut.connectedComponents();
        assertTrue(actual.containsAll(expected));
    }

    @Test
    public void testConnectedComponentsDirected2() {
        sut = new MyDirectedGraph<>();
        
        List<List<Integer>> expected = new ArrayList<>();
        
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();

        expected.add(list1);
        expected.add(list2);
        expected.add(list3);

        for(int i = 0; i < 3; i++) {
            sut.addVertex(i);
            list1.add(i);
        }

        list2.add(3);
        list3.add(4);
       
        sut.addVertex(3);
        sut.addVertex(4);

        sut.addEdge(0, 2);
        sut.addEdge(0, 3);
        sut.addEdge(1, 0);
        sut.addEdge(2, 1);
        sut.addEdge(3, 4);
        
        List<List<Integer>> actual = sut.connectedComponents();
        assertTrue(actual.containsAll(expected));
    }
}