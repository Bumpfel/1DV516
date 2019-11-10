package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public abstract class AbstractGraph<T> implements A3Graph<T> {
    protected Map<T, List<T>> adjacentVertices = new HashMap<>();
    private boolean isAcyclic = true;
    protected T firstAddedVertex;

    public int size() { return adjacentVertices.size(); }

    public Map<T, List<T>> getAdjacentVertices() { // for testing
        return adjacentVertices;
    }

    public void addVertex(T vertex) {
        if(firstAddedVertex == null) {
            firstAddedVertex = vertex;
        }
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

    protected void dfs(T vertex, Set<T> visited) {
        recursiveDepthFirstTraversal(vertex, visited, null, null, null);
    }


    protected void dfsKosaraju(T vertex, Set<T> visited, Stack<T> stack) {
        recursiveDepthFirstTraversal(vertex, visited, stack, null, null);
    }
    
    protected void dfsTransposedKosaraju(T vertex, Set<T> visited, List<T> subgraph) {
        recursiveDepthFirstTraversal(vertex, visited, null, subgraph, null);
    }



    private void recursiveDepthFirstTraversal(T vertex, Set<T> visited, Stack<T> sccStack, List<T> subgraph, T parent) {
        visited.add(vertex); // add current vertex to visited
        if(subgraph != null) { // used by kosarajus' algorithm
            subgraph.add(vertex);
        }

        // traverse graph by visiting adjacent vertices (except the parent - where it came from (undirected graphs only))
        for(T adjacentVertex : adjacentVertices.get(vertex)) {
            if(!adjacentVertex.equals(parent)) {
                if(visited.contains(adjacentVertex)) { // vertex was already visited and not a parent. it's a cyclic graph
                    isAcyclic = false;
                } else {
                    recursiveDepthFirstTraversal(adjacentVertex, visited, sccStack, subgraph, vertex); // visit next vertex
                }
            }
        }
        if(sccStack != null) { // used by Kosarajus' algorithm (directed graphs)
            sccStack.push(vertex);
        }
    }
  
    
    /**
     * Returns true if graph is connected
     */
    private boolean analyzeGraph() {
        Set<T> allVisited = new HashSet<>(); // used to hold all visited vertices during this analysis
        Set<T> visited = null;

        for(T vertex : adjacentVertices.keySet()) { // loop through all vertices to allow exploring disconnected vertices
            if(allVisited.contains(vertex)) { // skip vertices that were already visited during this analysis
                continue;
            }
            visited = new HashSet<>(); // used to hold visited vertices during one traversal
            dfs(vertex, visited);
            allVisited.addAll(visited);
        }
        int visitedVertices = visited == null ? 0 : visited.size();
        return visitedVertices == adjacentVertices.size();
    }


    public boolean isAcyclic() {
        analyzeGraph();
        return isAcyclic;
    }

    public boolean isConnected() {
        return analyzeGraph();
    }

    @Override
    public String toString() {
        return adjacentVertices.toString();
    }



}
