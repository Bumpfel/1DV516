package assignment3AADS.assignment3.MyTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import assignment3AADS.assignment3.generic.MyUndirectedGraph;

public class TestEuler {
    private MyUndirectedGraph<Integer> sut;

    @Test
    public void testHasEulerPath() {
        // Euler graph from example in assignment
        sut = buildValidEulerGraph();
        assertTrue(sut.hasEulerPath());

        // Same graph but without vertex 5 and connected edges (not an euler path)
        sut = buildInvalidEulerGraph();
        assertFalse(sut.hasEulerPath());
    }


    /** 
     * Maps all available edges in the graph, and all the edges travelled by eulerPath(), and compares the two
    */
    @Test
    public void testEulerPaths() { 
        // test assignment example graph
        List<Edge> graphEdges = null;
        List<Edge> eulerEdges = null;

        for(int i = 0; i < 10000; i ++) {
            sut = buildValidEulerGraph();
            graphEdges = mapAllEdges();
            eulerEdges = mapEdgesEulerPath();
            assertTrue(eulerEdges.containsAll(graphEdges) && eulerEdges.size() == graphEdges.size());
        }
    
        // add a random edge to assert it has not been travelled
        graphEdges.add(new Edge(5, 4));
        assertFalse(eulerEdges.containsAll(graphEdges));
        
        // test morecomplex graph from book
        for(int i = 0; i < 10000; i ++) {
            sut = buildValidComplexEulerGraph();
            graphEdges = mapAllEdges();
            eulerEdges = mapEdgesEulerPath();
            assertTrue(eulerEdges.containsAll(graphEdges) && eulerEdges.size() == graphEdges.size());
        }
    }

    
    private List<Edge> mapAllEdges() {
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


    private List<Edge> mapEdgesEulerPath() {
        List<Integer> path = sut.eulerPath();
        List<Edge> allEdges = new ArrayList<>();

        for(int i = 0; i < path.size() - 1; i ++) {
            allEdges.add(new Edge(path.get(i), path.get(i + 1)));
        }
        return allEdges;
    }

    private MyUndirectedGraph<Integer> buildValidEulerGraph() {
        MyUndirectedGraph<Integer> graph = new MyUndirectedGraph<>();

        ArrayList<Integer> vertices = new ArrayList<>();
        for(int i = 1; i <= 7; i ++) {
            vertices.add(i);
        }
        Collections.shuffle(vertices);

        for(Integer vertex : vertices) {
            graph.addVertex(vertex);
        }

        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(new Edge(1, 2));
        edges.add(new Edge(1, 3));
        edges.add(new Edge(1, 4));
        edges.add(new Edge(1, 5));
        
        edges.add(new Edge(3, 2));
        edges.add(new Edge(3, 4));
        edges.add(new Edge(3, 5));
        
        edges.add(new Edge(2, 4));


        edges.add(new Edge(2, 6));
        edges.add(new Edge(4, 6));
        edges.add(new Edge(6, 7));
        edges.add(new Edge(4, 7));
        
        Collections.shuffle(edges);

        for(Edge edge : edges) {
            graph.addEdge(edge.to, edge.from);
        }

        // graph.addEdge(1, 2);
        // graph.addEdge(1, 3);
        // graph.addEdge(1, 4);
        // graph.addEdge(1, 5);
        
        // graph.addEdge(3, 2);
        // graph.addEdge(3, 4);
        // graph.addEdge(3, 5);
        
        // graph.addEdge(2, 4);

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
        
        graph.addEdge(2, 4);

        graph.addEdge(2, 2);
        graph.addEdge(3, 4);

        return graph;
    }


    // scrambled order
    private static MyUndirectedGraph<Integer> buildValidComplexEulerGraph() {
        MyUndirectedGraph<Integer> graph = new MyUndirectedGraph<>();

        ArrayList<Integer> vertices = new ArrayList<>();

        for(int i = 1; i <= 12; i ++) {
            vertices.add(i);
        }
        Collections.shuffle(vertices);

        for(Integer vertex : vertices) {
            graph.addVertex(vertex);
        }

        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(new Edge(1, 3));
        edges.add(new Edge(1, 4));

        edges.add(new Edge(2, 3));
        edges.add(new Edge(2, 8));
        
        edges.add(new Edge(3, 4));
        edges.add(new Edge(3, 6));
        edges.add(new Edge(3, 7));
        edges.add(new Edge(3, 9));
        
        edges.add(new Edge(4, 5));
        edges.add(new Edge(4, 7));
        edges.add(new Edge(4, 10));
        edges.add(new Edge(4, 11));

        edges.add(new Edge(5, 10));

        edges.add(new Edge(6, 9));
        
        edges.add(new Edge(7, 9));
        edges.add(new Edge(7, 10));
        
        edges.add(new Edge(8, 9));

        edges.add(new Edge(9, 10));
        edges.add(new Edge(9, 12));

        edges.add(new Edge(10, 11));
        edges.add(new Edge(10, 12));

        Collections.shuffle(edges);

        for(Edge edge : edges) {
            graph.addEdge(edge.from, edge.to);
        }
        return graph;
    }
}

class Edge {
    Integer from;
    Integer to;

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
            Edge other = (Edge) o;
            return (from.equals(other.from) && to.equals(other.to)) || (from.equals(other.to) && to.equals(other.from));
        }
        catch(ClassCastException e) {
            return false;
        }
    }
}