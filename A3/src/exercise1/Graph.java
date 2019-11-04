package exercise1;

// TODO höll på med sist: måste hitta ett hållbart sätt att veta om en undirected array är acyclic. 
// använde tidigare ett visited-fält i vertex-klassen, men det blev dumt

import assignment3AADS.assignment3.A3Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public abstract class Graph implements A3Graph {
    private HashMap<Vertex, List<Vertex>> connectedVertices = new HashMap<>();
    private Vertex someVertex; // save a reference to have somewhere to start
    
    public int size() { return connectedVertices.size(); }

    public static void main(String[] args) throws Exception {
        MyDirectedGraph graph = new MyDirectedGraph();
        
        for(int i = 0; i < 5; i ++) {
            graph.addVertex(i);
        }

        graph.analyzeGraph();
    }

    public void addVertex(int vertex) {

        if(someVertex == null) {
            someVertex = new Vertex(vertex);
        }
        connectedVertices.putIfAbsent(new Vertex(vertex), new ArrayList<>());
    }

    public void addEdge(int sourceVertex, int targetVertex) {
        List<Vertex> sourceVertexEdges = connectedVertices.get(new Vertex(sourceVertex));
        List<Vertex> targetVertexEdges = connectedVertices.get(new Vertex(targetVertex));

        if(sourceVertexEdges == null || targetVertexEdges == null) {
            throw new IllegalArgumentException("Cannot add edge. Vertex does not exist");
        }
        
        sourceVertexEdges.add(new Vertex(targetVertex));
        if(this instanceof MyUndirectedGraph) {
            targetVertexEdges.add(new Vertex(sourceVertex));
        }
    }

    private Set<Vertex> traverse(boolean stopOnCyclic) {
        if(connectedVertices.isEmpty()) {
            return null;
        }

        Set<Vertex> visited = new HashSet<>();
        Set<Vertex> visitedTwice = new HashSet<>();
        Stack<Vertex> stack = new Stack<>();
        stack.push(someVertex);
        while (!stack.isEmpty()) {
            Vertex vertex = stack.pop();
            System.out.println("visiting " + vertex);
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Vertex connectedVertex : connectedVertices.get(vertex)) {
                    stack.push(connectedVertex);
                }
            } else if(stopOnCyclic ) {
                if(this instanceof MyDirectedGraph) {
                    throw new IllegalArgumentException();
                } else if(this instanceof MyUndirectedGraph) {
                    if(visitedTwice.contains(vertex)) {
                        throw new IllegalArgumentException();
                    }
                    visitedTwice.add(vertex);
                }
            }
        }
        return visited;
    }


    private boolean isCyclic;
    private boolean isConnected = false;

    public void analyzeGraph() {
        
        while(true) {
            HashSet<Vertex> visited = new HashSet<>();
            for(Vertex vertex : connectedVertices.keySet()) {
                visited.add(vertex);
            }
            if(visited.containsAll(connectedVertices.keySet())) {
                System.out.println("contains all");
                isConnected = true;
                break;
            }

        }
    }


    public boolean isAcyclic() {
        // TODO måste traversera alla vertices (kan finnas de som ej är anslutna med starten)
        Set<Vertex> visitedVertices = traverse(false);


        try {
            traverse(true);
        } catch(IllegalArgumentException e) {
            return false;
        }
        return true;
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
        return connectedVertices.toString();
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