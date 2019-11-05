package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO ska grafen klara flera edges mellan samma punkter så funkar det inte med hashmap

public abstract class AbstractGraph<T> implements A3Graph<T> {
    private HashMap<T, List<T>> adjacentVertices = new HashMap<>();
    private boolean isAcyclic = true;
    private boolean isConnected;
    private List<List<T>> connectedComponents = new ArrayList<>();

    public static void main(String[] args) {
        // AbstractGraph<Integer> graph = new MyUndirectedGraph<>();
        // int start = 1;
        // for(int i = start; i < 4; i ++) {
        //     graph.addVertex(i);
        //     if(i > start) {
        //         graph.addEdge(i - 1, i);
        //     }
        // }
        // graph.addVertex(4);
        // graph.connectedComponents();

        AbstractGraph<Integer> graph = new MyDirectedGraph<>();
        for(int i = 1; i < 4; i++) {
            graph.addVertex(i);
        }
        graph.addVertex(4);
        graph.addVertex(5);

        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 1);
        graph.addEdge(3, 2);
        graph.addVertex(5);
        System.out.println(graph.connectedComponents());
    }

    public int size() { return adjacentVertices.size(); }

    public void addVertex(T vertex) {
        adjacentVertices.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(T sourceVertex, T targetVertex) {
        List<T> sourceVertexEdges = adjacentVertices.get(sourceVertex);
        List<T> targetVertexEdges = adjacentVertices.get(targetVertex);

        if(sourceVertexEdges == null || targetVertexEdges == null) {
            throw new IllegalArgumentException("Cannot add edge " + sourceVertex + " - " + targetVertex + ". Vertex or vertices does not exist");
        }
        
        sourceVertexEdges.add(targetVertex);
        if(this instanceof MyUndirectedGraph) {
            targetVertexEdges.add(sourceVertex);
        }
    }

    private Set<T> recursiveTraversal(T vertex, Set<T> visitedVertices, List<T> stronglyConnectedVertices, T parent) {
        visitedVertices.add(vertex);

        if(stronglyConnectedVertices == null || (this instanceof MyDirectedGraph && adjacentVertices.get(vertex).size() == 0)) {
            stronglyConnectedVertices = new ArrayList<>();
            connectedComponents.add(stronglyConnectedVertices);
        }
        stronglyConnectedVertices.add(vertex);

        for(T connectedVertex : adjacentVertices.get(vertex)) {
            if(!connectedVertex.equals(parent)) {
                if(visitedVertices.contains(connectedVertex)) {
                    isAcyclic = false;
                } else {
                    recursiveTraversal(connectedVertex, visitedVertices, stronglyConnectedVertices, vertex);
                }
            }
        }
        isConnected = visitedVertices.containsAll(adjacentVertices.keySet());
        return visitedVertices;
    }
    
    private void analyzeGraph() {
        Set<T> allVisited = new HashSet<>(); // used to hold all visited vertices during this analysis
        Set<T> visited = new HashSet<>(); // used to hold visited vertices during one traversal
        connectedComponents.clear(); // clear list, so it doesn't add to potentially existing list

        for(T vertex : adjacentVertices.keySet()) { // loop through all vertices to allow exploring disconnected vertices
            if(allVisited.contains(vertex)) { // skip vertices that were already visited during this analysis
                continue;
            }
            visited = recursiveTraversal(vertex, new HashSet<>(), null, null);
            allVisited.addAll(visited);

            // convert set to list because the interface demands connectedComponents to return List<List<T>>
            // List<T> list = new ArrayList<>();
            // for(T element : visited) {
            //     list.add(element);
            // }
            // connectedComponents.add(list);
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


    public List<List<T>> connectedComponents() {
        analyzeGraph();
        return connectedComponents; // this list is not used anywhere else in the class, so not bothering with returning a copy
    }

    @Override
    public String toString() {
        return adjacentVertices.toString();
    }
    
}