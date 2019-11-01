package exercise1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public abstract class Graph {
    private HashMap<Integer, List<Integer>> connectedVertices = new HashMap<>(); // TODO kanske ha Vertex istället för Integer om fördel. blir lite mer omanvändarbart
    private Integer someVertex; // save a reference to have somewhere to start

    public static void main(String[] args) throws Exception {
        MyDirectedGraph graph = new MyDirectedGraph();
        
        for(int i = 0; i < 5; i ++) {
            graph.addVertex(i);
        }
    }

    public void addVertex(int vertex) {
        if(someVertex == null) {
            someVertex = vertex;
        }
        connectedVertices.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(int sourceVertex, int targetVertex) {
        if(sourceVertex >= connectedVertices.size() || targetVertex >= connectedVertices.size() ||
         (connectedVertices.get(sourceVertex) == null && connectedVertices.get(targetVertex) == null)) {
            throw new IllegalArgumentException("Cannot add edge");
        }

        connectedVertices.get(sourceVertex).add(targetVertex);
        connectedVertices.get(targetVertex).add(sourceVertex);
    }

    private Set<Integer> depthFirstTraversal() { 
        if(connectedVertices.isEmpty()) {
            return null;
        }

        Set<Integer> visited = new LinkedHashSet<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(someVertex);
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Integer connectedVertex : connectedVertices.get(vertex)) {
                    stack.push(connectedVertex);
                }
            }
        }
        return visited;
    }


    public boolean isAcyclic() {
        return true;
    }

    public boolean isConnected() {
        // in the case of MyDirectedGraph, isConnected() returns true if the graph is strongly connected
        Set<Integer> visitedVertices = depthFirstTraversal();
        for(Integer vertex : connectedVertices.keySet()) {
            if(!visitedVertices.contains(vertex)) {
                return false;
            }
        }
        return true;
    }


    private List<List<Integer>> connectedComponents() {

        return new ArrayList<>();
    }

    // Implement in each class a method connectedComponents() that returns a List of Lists of integers (List<List<Integer>> connectedComponents()) that returns the nodes in each connected component of the graph (in the case of MyDirectedGraph, in each strongly connected component). Next figure shows examples of what connectedComponents() should return for directed and for undirected graphs. 

    class Vertex { // TODO ta bort om inte används
        int value;
        boolean wasVisited = false;

        Vertex(int vertexValue) {
            value = vertexValue;
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