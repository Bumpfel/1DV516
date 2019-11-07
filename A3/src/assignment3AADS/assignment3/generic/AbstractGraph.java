package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractGraph<T> implements A3Graph<T> {
    protected HashMap<T, List<T>> adjacentVertices = new HashMap<>();
    private boolean isAcyclic = true;
    private boolean isConnected;
    private List<List<T>> connectedComponents = new ArrayList<>();

    public int size() { return adjacentVertices.size(); }

    public HashMap<T, List<T>> getAdjacentVertices() { // for testing
        return adjacentVertices;
    }

    public void addVertex(T vertex) {
        adjacentVertices.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(T sourceVertex, T targetVertex) {
        List<T> sourceVertexEdges = adjacentVertices.get(sourceVertex);
        List<T> targetVertexEdges = adjacentVertices.get(targetVertex);

        if(sourceVertexEdges.contains(targetVertex) || targetVertexEdges.contains(sourceVertex)) {
            System.out.println("Rejecting duplicate edge " + sourceVertex + " - " + targetVertex);
            return;
        }

        if(sourceVertexEdges == null || targetVertexEdges == null) {
            throw new IllegalArgumentException("Cannot add edge " + sourceVertex + " - " + targetVertex + ". Vertex or vertices does not exist");
        }
        
        sourceVertexEdges.add(targetVertex);
        if(this instanceof MyUndirectedGraph) {
            targetVertexEdges.add(sourceVertex);
        }
    }

    private Set<T> depthFirstTraversal(T vertex, Set<T> visitedVertices, List<T> stronglyConnectedVertices, T parent) {
        visitedVertices.add(vertex); // add current vertex to visited

        // build connectedComponents lists. create and add new list if this call was not recursive,
        // or it's a directed graph and current vertex has no adjacent vertices
        if(stronglyConnectedVertices == null || (this instanceof MyDirectedGraph && adjacentVertices.get(vertex).size() == 0)) {
            stronglyConnectedVertices = new ArrayList<>();
            connectedComponents.add(stronglyConnectedVertices);
        }
        stronglyConnectedVertices.add(vertex);

        // traverse graph by visiting adjacent vertices (except the parent - where it came from (undirected graphs only))
        for(T adjacentVertex : adjacentVertices.get(vertex)) {
            if(!adjacentVertex.equals(parent)) {
                if(visitedVertices.contains(adjacentVertex)) { // vertex was already visited and not a parent. it's a cyclic graph
                    isAcyclic = false;
                } else {
                    depthFirstTraversal(adjacentVertex, visitedVertices, stronglyConnectedVertices, vertex); // go to next vertex
                }
            }
        }
        isConnected = visitedVertices.containsAll(adjacentVertices.keySet()); // graph is connected if all vertices in the graph was traversed from one root vertex
        return visitedVertices;
    }
    
    private void analyzeGraph() {
        Set<T> allVisited = new HashSet<>(); // used to hold all visited vertices during this analysis
        connectedComponents.clear(); // clear list, so it doesn't add to potentially existing list
        Set<T> visited = new HashSet<>(); // used to hold visited vertices during one traversal
        
        for(T vertex : adjacentVertices.keySet()) { // loop through all vertices to allow exploring disconnected vertices
            if(allVisited.contains(vertex)) { // skip vertices that were already visited during this analysis
                continue;
            }
            visited = depthFirstTraversal(vertex, new HashSet<>(), null, null);
            allVisited.addAll(visited);
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
