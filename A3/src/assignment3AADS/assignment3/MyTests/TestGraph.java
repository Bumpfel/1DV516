package assignment3AADS.assignment3.MyTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;

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
    public void testHasEulerPath() {
        // Euler graph from example in assignment
        sut = buildValidEulerGraph();
        assertTrue(sut.hasEulerPath());

        // Same graph but without vertex 5 and connected edges (not an euler path)
        sut = buildInvalidEulerGraph();
        assertFalse(sut.hasEulerPath());
    }


    @Test
    public void testEulerPaths() {
        sut = buildValidEulerGraph();

        List<Edge> graphEdges = mapEdges();
        List<Edge> eulerEdges = mapEdgesEulerPath(sut.eulerPath());
        assertTrue(eulerEdges.containsAll(graphEdges));
        
        graphEdges.add(new Edge(5, 4));
        assertFalse(eulerEdges.containsAll(graphEdges));
        
        sut = buildValidEulerGraph2();
        
        graphEdges = mapEdges();
        eulerEdges = mapEdgesEulerPath(sut.eulerPath());
        assertTrue(eulerEdges.containsAll(graphEdges));
    }


    private List<Edge> mapEdges() {
        List<Edge> allEdges = new ArrayList<>();
        HashSet<Integer> addedVertices = new HashSet<>();

        HashMap<Integer, List<Integer>> adjacentVertices = sut.getAdjacentVertices(); 
        for(Integer vertex : adjacentVertices.keySet()) {
            for(Integer adjacentVertex : adjacentVertices.get(vertex)) {
                addedVertices.add(vertex);
                if(addedVertices.contains(adjacentVertex)) { // skip duplicates
                    continue;
                }
                allEdges.add(new Edge(vertex, adjacentVertex));
            }
        }
        return allEdges;
    }


    private List<Edge> mapEdgesEulerPath(List<Integer> path) {
        List<Edge> allEdges = new ArrayList<>();

        for(int i = 0; i < path.size() - 1; i ++) {
            allEdges.add(new Edge(path.get(i), path.get(i + 1)));
        }
        return allEdges;
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


    private static MyUndirectedGraph<Integer> buildValidEulerGraph2() {       
        MyUndirectedGraph<Integer> graph = new MyUndirectedGraph<>();
        for(int i = 1; i <= 7; i ++) {
            graph.addVertex(i);
        }
        graph.addEdge(1, 2);
        graph.addEdge(1, 6);

        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 6);
        
        graph.addEdge(3, 4);
      
        graph.addEdge(4, 5);
        graph.addEdge(4, 7);

        graph.addEdge(5, 7);

        graph.addEdge(6, 7);

        return graph;
    }


    class Edge {
        Integer from;
        Integer to;
        boolean visited = false;

        Edge(Integer _from, Integer _to) {
            from = _from;
            to = _to;
        }

        @Override
        public String toString() {
            return from + "-" + to;
        }

        @Override
        public boolean equals(Object o) {
            try {
                // @SuppressWarnings("unchecked")
                Edge other = (Edge) o;
                return (from.equals(other.from) && to.equals(other.to)) || (from.equals(other.to) && to.equals(other.from));
            }
            catch(ClassCastException e) {
                return false;
            }
        }
    }

}