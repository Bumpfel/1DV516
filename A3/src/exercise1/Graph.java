package exercise1;

import assignment3AADS.assignment3.A3Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Graph implements A3Graph {
    private HashMap<Vertex, List<Vertex>> adjacentVertices = new HashMap<>();
    private boolean isAcyclic = true;
    private boolean isConnected;

    public int size() { return adjacentVertices.size(); }

    public static void main(String[] args) throws Exception {
        Graph graph = new MyUndirectedGraph();
        // Graph graph = new MyDirectedGraph();
        
        for(int i = 0; i < 5; i ++) {
            graph.addVertex(i);
        }
        
        for(int i = 0; i < 4; i ++) {
            graph.addEdge(i, i + 1);
        }
        
        graph.addVertex(100);
        graph.addEdge(100, 1);

        graph.isAcyclic();

        // graph.connectedComponents();
    }

    public void addVertex(int vertex) {
        adjacentVertices.putIfAbsent(new Vertex(vertex), new ArrayList<>());
    }

    public void addEdge(int sourceVertex, int targetVertex) {
        List<Vertex> sourceVertexEdges = adjacentVertices.get(new Vertex(sourceVertex));
        List<Vertex> targetVertexEdges = adjacentVertices.get(new Vertex(targetVertex));

        if(sourceVertexEdges == null || targetVertexEdges == null) {
            throw new IllegalArgumentException("Cannot add edge. Vertex does not exist");
        }
        
        sourceVertexEdges.add(new Vertex(targetVertex));
        if(this instanceof MyUndirectedGraph) {
            targetVertexEdges.add(new Vertex(sourceVertex));
        }
    }

    private Set<Vertex> recursiveTraversal(Vertex vertex, Set<Vertex> visited, Vertex parent) {
        visited.add(vertex);

        for(Vertex connectedVertex : adjacentVertices.get(vertex)) {
            if(!connectedVertex.equals(parent)) {
                if(visited.contains(connectedVertex)) {
                    isAcyclic = false;
                } else {
                    recursiveTraversal(connectedVertex, visited, vertex);
                }
            }
        }
        isConnected = visited.containsAll(adjacentVertices.keySet());
        return visited;
    }
    
    private void analyzeGraph() {
        Set<Vertex> visited = new HashSet<>();
        for(Vertex vertex : adjacentVertices.keySet()) {
            if(visited.contains(vertex)) {
                continue;
            }
            visited = recursiveTraversal(vertex, new HashSet<>(), null);
        }
    }



    public boolean isAcyclic() {
        analyzeGraph();
        return isAcyclic;
    }

    public boolean isConnected() {
        analyzeGraph();
        return isConnected;
    }


    public List<List<Integer>> connectedComponents() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return adjacentVertices.toString();
    }
    // Implement in each class a method connectedComponents() that returns a List of Lists of integers (List<List<Integer>> connectedComponents()) that returns the nodes in each connected component of the graph (in the case of MyDirectedGraph, in each strongly connected component). Next figure shows examples of what connectedComponents() should return for directed and for undirected graphs. 

    class Vertex {
        int value;

        Vertex(int vertexValue) {
            value = vertexValue;
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof Vertex) {
                Vertex otherObject = (Vertex) o;
                return value == otherObject.value;
            }
            return false;
        }

        @Override
        public int hashCode() {
            Integer iValue = value;
            return iValue.hashCode();
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    class Edge {
        int source;
        int target;

        Edge(int _source, int _target) {
            source = _source;
            target = _target;
        }
    }
    
}