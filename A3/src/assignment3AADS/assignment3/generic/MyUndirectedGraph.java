package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import assignment3AADS.assignment3.generic.MyUndirectedGraph;

public class MyUndirectedGraph<T> extends AbstractGraph<T> {
    private Stack<Edge> removedEdges = new Stack<>();

    public static void main(String[] args) {
        // MyUndirectedGraph<Integer> graph = buildExerciseGraph();
        // MyUndirectedGraph<Integer> graph = buildMyGraph();
        MyUndirectedGraph<Integer> graph = buildComplexGraph();

        System.out.println(graph);
        graph.eulerPath();
        
        System.out.println(graph);
        graph.eulerPath();
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

    private void removeEdge(T vertex1, T vertex2) {
        adjacentVertices.get(vertex1).remove(vertex2);
        adjacentVertices.get(vertex2).remove(vertex1);
    }

    private void removeEdge(Edge edge) {
        adjacentVertices.get(edge.from).remove(edge.to);
        adjacentVertices.get(edge.to).remove(edge.from);
    }

    @Override
    public boolean hasEulerPath() {
        int oddDegreeVertices = 0;
        for(T vertex : adjacentVertices.keySet()) {
            List<T> list = adjacentVertices.get(vertex);
            if(list.size() % 2 == 1) {
                oddDegreeVertices ++;
            }
        }
        if(isEulerCircuit(oddDegreeVertices) || isEulerPath(oddDegreeVertices)) {
            return isConnected(); // TODO current implementation does not have great time complexity
        }
        return false;
    }

    private boolean isEulerCircuit (int oddDegreeVertices) {
        return oddDegreeVertices == 0 && adjacentVertices.size() % 2 == 0;
    }
    
    private boolean isEulerPath (int oddDegreeVertices) {
        return oddDegreeVertices == 2;
    }


    @Override
    public List<T> eulerPath() {
        if(hasEulerPath()) {
            // get start vertex (any for circuit, vertex with uneven degree for path)
            T startVertex = adjacentVertices.keySet().iterator().next();
            for(T vertex : adjacentVertices.keySet()) {
                if(adjacentVertices.get(vertex).size() % 2 == 1) {
                    startVertex = vertex;
                }
            }
            
            List<T> path = eulerTraversal(startVertex);

            // System.out.println("first path " + path);
            for(int i = 1; i < path.size() ; i ++) {
                T vertex = path.get(i);
                // System.out.println("checking for untravelled edges at index " + i + " (" + vertex + ")");
                if(adjacentVertices.get(vertex).size() > 0) { // has untraversed paths
                    // System.out.println(vertex + " has untraversed paths " + adjacentVertices.get(vertex));
                    List<T> path2 = eulerTraversal(vertex);
                    path.remove(vertex);
                    path.addAll(i, path2);
                    // System.out.println("inserting new path " + path2 + " at index " + i);
                    // System.out.println("new path " + path);
                }
            }
            // System.out.println("final path " + path);
            // System.out.println();

            restoreGraph();
            return path;
        }
        return null;
    }

    private List<T> eulerTraversal(T root) {
        List<T> visited = new LinkedList<>();
        Stack<Edge> visitedEdges = new Stack<>();
        Stack<T> visitNext = new Stack<>();

        visitNext.push(root);
        while (!visitNext.isEmpty()) {
            if(!visitedEdges.empty()) {
                Edge edge = visitedEdges.pop();
                removeEdge(edge);
                removedEdges.push(edge);
            }
            T vertex = visitNext.pop();
            visited.add(vertex);
            if(adjacentVertices.get(vertex).isEmpty()) {
                break;
            }
            for (T adjacentVertex : adjacentVertices.get(vertex)) {
                visitNext.push(adjacentVertex);
                visitedEdges.push(new Edge(vertex, adjacentVertex));
            }
        }
        return visited;
    }

    private void restoreGraph() {
        for(Edge edge : removedEdges) {
            addEdge(edge.from, edge.to);
        }
        removedEdges.clear();
    }
    
    

    class Edge {
        T from;
        T to;
    
        Edge(T _from, T _to) {
            from = _from;
            to = _to;
        }
    
        @Override
        public String toString() {
            return from + "-" + to;
        }
    
        @Override
        public boolean equals(Object o) {
            try {
                @SuppressWarnings("unchecked")
                Edge other = (Edge) o;
                return (from.equals(other.from) && to.equals(other.to)) || (from.equals(other.to) && to.equals(other.from));
            }
            catch(ClassCastException e) {
                return false;
            }
        }
    }
    



    private static MyUndirectedGraph<Integer> buildExerciseGraph() {
        MyUndirectedGraph<Integer> graph = new MyUndirectedGraph<>();
        for(int i = 1; i <= 5; i ++) {
            graph.addVertex(i);
        }
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(1, 5);
        
        graph.addEdge(3, 2);
        graph.addEdge(3, 4);
        graph.addEdge(3, 5);
        
        graph.addEdge(2, 4);

        return graph;
    }

    private static MyUndirectedGraph<Integer> buildMyGraph() {       
        MyUndirectedGraph<Integer> graph = new MyUndirectedGraph<>();
        for(int i = 1; i <= 7; i ++) {
            graph.addVertex(i);
        }
        graph.addEdge(1, 2);
        graph.addEdge(1, 6);

        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 6);
        
        graph.addEdge(3, 4);
      
        graph.addEdge(4, 5);
        graph.addEdge(4, 7);

        graph.addEdge(5, 7);

        graph.addEdge(6, 7);

        return graph;
    }

    private static MyUndirectedGraph<Integer> buildComplexGraph() {       
        MyUndirectedGraph<Integer> graph = new MyUndirectedGraph<>();
        for(int i = 1; i <= 12; i ++) {
            graph.addVertex(i);
        }
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);

        graph.addEdge(2, 3);
        graph.addEdge(2, 8);
        
        graph.addEdge(3, 4);
        graph.addEdge(3, 6);
        graph.addEdge(3, 7);
        graph.addEdge(3, 9);
        
        graph.addEdge(4, 5);
        graph.addEdge(4, 7);
        graph.addEdge(4, 10);
        graph.addEdge(4, 11);

        graph.addEdge(5, 10);

        graph.addEdge(6, 9);
        
        graph.addEdge(7, 9);
        graph.addEdge(7, 10);

        graph.addEdge(8, 9);

        graph.addEdge(9, 10);
        graph.addEdge(9, 12);
        
        graph.addEdge(10, 11);
        graph.addEdge(10, 12);
        return graph;
    }

}