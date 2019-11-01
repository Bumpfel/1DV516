package exercise1;

import java.util.ArrayList;
import java.util.List;

public abstract class _Graph {
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();
    private boolean isAcyclic = true;

    public boolean isAcyclic() {
        return isAcyclic;
    }

    public static void main(String[] args) throws Exception {
        MyDirectedGraph graph = new MyDirectedGraph();
        
        for(int i = 0; i < 5; i ++) {
            graph.addVertex(i);
        }
    }

    public void addVertex(int vertex) {
        vertices.add(new Vertex());
    }

    public void addEdge(int sourceVertex, int targetVertex) {
        if(sourceVertex >= vertices.size() || targetVertex >= vertices.size() ||
         (vertices.get(sourceVertex) == null && vertices.get(targetVertex) == null)) {
            throw new IllegalArgumentException("Cannot add edge");
        }

        // set visited for the vertex
        if(edges.isEmpty()) {
            vertices.get(sourceVertex).wasVisited = true;
        }

        if(vertices.get(targetVertex).wasVisited) {
            isAcyclic = false;
        } else {
            vertices.get(targetVertex).wasVisited = true;
        }
        edges.add(new Edge(sourceVertex, targetVertex));
    }

    public boolean isConnected() {
        ArrayList<Integer> visitedVertices = new ArrayList<>();

        // in the case of MyDirectedGraph, isConnected() returns true if the graph is strongly connected
        return true;
    }


    private List<List<Integer>> connectedComponents() {

        return new ArrayList<>();
    }

    // Implement in each class a method connectedComponents() that returns a List of Lists of integers (List<List<Integer>> connectedComponents()) that returns the nodes in each connected component of the graph (in the case of MyDirectedGraph, in each strongly connected component). Next figure shows examples of what connectedComponents() should return for directed and for undirected graphs. 

    class Vertex {
        // int vertex;
        boolean wasVisited = false;
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