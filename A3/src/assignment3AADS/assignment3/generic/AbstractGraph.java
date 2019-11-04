package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractGraph<T> implements A3Graph<T> {
    private HashMap<T, List<T>> adjacentVertices = new HashMap<>();
    // private List<List<T>> adjacentVertices = new List<>();
    private boolean isAcyclic = true;
    private boolean isConnected;

    public int size() { return adjacentVertices.size(); }

    public void addVertex(T vertex) {
        adjacentVertices.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(T sourceVertex, T targetVertex) {
        List<T> sourceVertexEdges = adjacentVertices.get(sourceVertex);
        List<T> targetVertexEdges = adjacentVertices.get(targetVertex);

        if(sourceVertexEdges == null || targetVertexEdges == null) {
            throw new IllegalArgumentException("Cannot add edge. Vertex does not exist");
        }
        
        sourceVertexEdges.add(targetVertex);
        if(this instanceof MyUndirectedGraph) {
            targetVertexEdges.add(sourceVertex);
        }
    }

    private Set<T> recursiveTraversal(T vertex, Set<T> visited, T parent) {
        visited.add(vertex);

        for(T connectedVertex : adjacentVertices.get(vertex)) {
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
        Set<T> visited = new HashSet<>();
        for(T vertex : adjacentVertices.keySet()) {
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


    public List<List<T>> connectedComponents() {
        List<List<T>> ret = new ArrayList<>();
        return ret;
    }

    @Override
    public String toString() {
        return adjacentVertices.toString();
    }
    
}