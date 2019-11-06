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
        sut = new MyUndirectedGraph<>(); // do not change type
    }

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
    public void testIsConnected() {
        addElementsToGraph(5);

        assertFalse(sut.isConnected());
        
        connectVertices();
        assertTrue(sut.isConnected());
        
        sut.addVertex(100);
        assertFalse(sut.isConnected());

        sut.addEdge(100, 1);
        if(sut instanceof MyUndirectedGraph) {
            assertTrue(sut.isConnected());
            sut = new MyDirectedGraph<>();
            testIsConnected();
        } else {
            assertFalse(sut.isConnected());
        }
    }

    @Test
    public void testIsAcyclic1() {
        addElementsToGraph(5);
        connectVertices();
        assertTrue(sut.isAcyclic());
        
        sut.addEdge(4, 1);
        assertFalse(sut.isAcyclic());

        if(sut instanceof MyUndirectedGraph) {
            sut = new MyDirectedGraph<>();
            testIsAcyclic1();
        }
    }

    @Test
    public void testIsAcyclic2() {
        addElementsToGraph(5);
        connectVertices();
        sut.addVertex(100);
        sut.addEdge(100, 1);
        assertTrue(sut.isAcyclic());

        if(sut instanceof MyUndirectedGraph) {
            sut = new MyDirectedGraph<>();
            testIsAcyclic2();
        }
    }

    @Test
    public void testIsAcyclic3() {
        for(int i = 1; i <= 5; i++) {
            sut.addVertex(i);
        }

        sut.addEdge(1, 3);
        sut.addEdge(1, 4);
        sut.addEdge(2, 1);
        sut.addEdge(3, 2);

        System.out.println(sut);

        assertFalse(sut.isAcyclic());

        if(sut instanceof MyUndirectedGraph) {
            sut = new MyDirectedGraph<>();
            testIsAcyclic3();
        }
    }

    @Test
    public void testIsAcyclic4() {
        for(int i = 1; i <= 5; i++) {
            sut.addVertex(i);
        }

        sut.addEdge(1, 3);
        sut.addEdge(1, 4);
        sut.addEdge(2, 3);
        sut.addEdge(3, 2);

        System.out.println(sut);

        
        if(sut instanceof MyUndirectedGraph) {
            assertFalse(sut.isAcyclic());
            sut = new MyDirectedGraph<>();
            testIsAcyclic4();
        } else {
            assertTrue(sut.isAcyclic());
        }
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
        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < actual.size(); i ++) {
            assertEquals(expected.get(i).size(), actual.get(i).size());
            assertTrue(actual.get(i).containsAll(expected.get(i)));
        }
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

        list2.add(4);
        list3.add(5);
        
        for(int i = 1; i < 4; i++) {
            sut.addVertex(i);
            list1.add(i);
        }
        sut.addVertex(4);
        sut.addVertex(5);

        sut.addEdge(1, 3);
        sut.addEdge(1, 4);
        sut.addEdge(2, 1);
        sut.addEdge(3, 2);
        sut.addVertex(5);

        List<List<Integer>> actual = sut.connectedComponents();

        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < actual.size(); i ++) {
            assertEquals(expected.get(i).size(), actual.get(i).size());
            assertTrue(actual.get(i).containsAll(expected.get(i)));
        }
    }

    @Test
    public void testhasEulerPath() {
        // Euler graph from example in assignment
        sut = buildValidEulerGraph();
        assertTrue(sut.hasEulerPath());

        // Same graph but without vertex 5 and connected edges (not an euler path)
        sut = buildInvalidEulerGraph();
        assertFalse(sut.hasEulerPath());
    }


    @Test
    public void testEulerPath() {
        sut = buildValidEulerGraph();

        List<Integer> path = sut.eulerPath();
        // TODO finns för många kombinationer för att jämföra med ett förväntat resultat.
        // försök köra en traversal och kolla så att alla edges har blivit traverserade en gång
    }

    private MyUndirectedGraph<Integer> buildValidEulerGraph() {
        MyUndirectedGraph<Integer> graph = new MyUndirectedGraph<>();

        for(int i = 1; i <= 5; i ++) {
            graph.addVertex(i);
        }
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(1, 5);
        
        graph.addEdge(3, 2);
        graph.addEdge(3, 4);
        graph.addEdge(3, 5);
        
        graph.addEdge(2, 4);

        return graph;
    }

    private MyUndirectedGraph<Integer> buildInvalidEulerGraph() {
        MyUndirectedGraph<Integer> graph = new MyUndirectedGraph<>();

        for(int i = 1; i <= 4; i ++) {
            graph.addVertex(i);
        }
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        
        graph.addEdge(3, 2);
        graph.addEdge(3, 4);
        
        graph.addEdge(2, 4);

        return graph;
    }

}