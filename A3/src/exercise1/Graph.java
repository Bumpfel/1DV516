package exercise1;

import assignment3AADS.assignment3.A3Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public abstract class Graph implements A3Graph {
    private HashMap<Vertex, List<Vertex>> vertices = new HashMap<>();
    private boolean isAcyclic = true;
    private boolean isConnected = false;

    public int size() { return vertices.size(); }

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
        
        System.out.println(graph);
    }

    public void addVertex(int vertex) {
        vertices.putIfAbsent(new Vertex(vertex), new ArrayList<>());
    }

    public void addEdge(int sourceVertex, int targetVertex) {
        List<Vertex> sourceVertexEdges = vertices.get(new Vertex(sourceVertex));
        List<Vertex> targetVertexEdges = vertices.get(new Vertex(targetVertex));

        if(sourceVertexEdges == null || targetVertexEdges == null) {
            throw new IllegalArgumentException("Cannot add edge. Vertex does not exist");
        }
        
        sourceVertexEdges.add(new Vertex(targetVertex));
        if(this instanceof MyUndirectedGraph) {
            targetVertexEdges.add(new Vertex(sourceVertex));
        }
    }

    private Set<Vertex> traverse(Vertex startVertex) {
        if(vertices.isEmpty()) {
            return null;
        }

        Set<Vertex> visited = new HashSet<>();
        Stack<Vertex> stack = new Stack<>();
        stack.push(startVertex);
        Vertex prev = null;
        while (!stack.isEmpty()) {
            Vertex vertex = stack.pop();
            // System.out.println(
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Vertex connectedVertex : vertices.get(vertex)) {
                    if(!connectedVertex.equals(prev)) {
                        System.out.println("going to " + connectedVertex + " from " + vertex + ". prev was " + prev);
                        stack.push(connectedVertex);
                    }
                    else {
                        System.out.println("stopping traversal to " + connectedVertex + " from " + vertex);
                    }
                }
                prev = vertex;
            } else {
                System.out.println("determined from vertex: " + vertex + " that graph is cyclic");
                isAcyclic = false;
            }
        }
        isConnected = visited.containsAll(vertices.keySet());
        return visited;
    }


    void recursiveTraversal(Vertex vertex, HashMap<Vertex, Boolean> visited, Vertex parent) {
        visited.put(vertex, true);

        
    }
    
    void analyzeGraph() {
        // Set<Vertex> visited = new HashSet<>();
        // int traversals = 0;
        // for(Vertex vertex : vertices.keySet()) {
        //     if(!visited.contains(vertex)) {
        //         System.out.println("## TRAVERSAL " + (++traversals) + ". starting on " + vertex);
        //         visited = traverse(vertex);
        //     }
        // }
        
        
        HashMap<Vertex, Boolean> visited = new HashMap<>();
        Vertex root = null;
        for(Vertex vertex : vertices.keySet()) {
            if(root == null) {
                root = vertex;
            }
            visited.put(vertex, false);
        }
        recursiveTraversal(root, visited, null);
    }



    public boolean isAcyclic() {
        analyzeGraph();
        return isAcyclic;
    }

    public boolean isConnected() {
        // in the case of MyDirectedGraph, isConnected() returns true if the graph is strongly connected
        // Set<Vertex> visitedVertices = traverse(false);
        // if(visitedVertices.size() < connectedVertices.size()) {
        //     return false;
        // }
        // return visitedVertices.containsAll(connectedVertices.keySet());

        analyzeGraph();
        return isConnected;
    }


    public List<List<Integer>> connectedComponents() {

        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return vertices.toString();
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