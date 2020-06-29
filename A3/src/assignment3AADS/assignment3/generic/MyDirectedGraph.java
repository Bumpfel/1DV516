package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class MyDirectedGraph<T> extends AbstractGraph<T> {
    
    /**
     * Kosaraju's algorithm
    */
    public List<List<T>> connectedComponents() {
        Stack<T> stack = new Stack<>();
        Set<T> visited = new HashSet<>();
        List<List<T>> connectedComponents = new ArrayList<>();
        
        // dfs traversal incl. adding to visited nodes to stack
        for(T vertex : adjacentVertices.keySet()) {
            if(!visited.contains(vertex)) {
                dfsKosaraju(vertex, visited, stack);
            }
        }
        
        // makes a transposed (inverted) graph of the current graph
        MyDirectedGraph<T> transposedGraph = createTransposedGraph();
        System.out.println(transposedGraph);
        visited.clear();
        
        System.out.println(stack);
        
        // traverse the transposed graph in reversed order compared to the first traversal
        while(!stack.empty()) {
            T vertex = stack.pop();
            if(!visited.contains(vertex)) {
                List<T> subgraph = new ArrayList<>();
                transposedGraph.dfsTransposedKosaraju(vertex, visited, subgraph);
                connectedComponents.add(subgraph);
            }
        }
        return connectedComponents;
    }

    private MyDirectedGraph<T> createTransposedGraph() {
        MyDirectedGraph<T> graph = new MyDirectedGraph<>();
        // add all vertices from old graph
        for(T vertex : adjacentVertices.keySet()) {
            graph.addVertex(vertex);
        }
        // add reversed edges
        for(T vertex : adjacentVertices.keySet()) {
            for(T adjacentVertex : adjacentVertices.get(vertex)) {
                graph.addEdge(adjacentVertex, vertex);
            }
        }

        return graph;
    }

}