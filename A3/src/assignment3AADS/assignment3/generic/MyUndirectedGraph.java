package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import assignment3AADS.assignment3.generic.MyUndirectedGraph;

public class MyUndirectedGraph<T> extends AbstractGraph<T> {
    // private Map<Vertex, List<T>> adjacentVertices = new HashMap<>();

    public static void main(String[] args) {
        MyUndirectedGraph<Integer> graph = buildExerciseGraph();
        
        graph.eulerPath();
    }

    // @Override
    // public void addVertex(T label) {
    //     adjacentVertices.put(new Vertex(label), new ArrayList<>());
    // }

    // @Override
    // public void addEdge(T sourceVertex, T targetVertex) {
    //     List<T> sourceVertexEdges = adjacentVertices.get(sourceVertex);
    //     List<T> targetVertexEdges = adjacentVertices.get(targetVertex);

    //     if(sourceVertexEdges == null || targetVertexEdges == null) {
    //         throw new IllegalArgumentException("Cannot add edge " + sourceVertex + " - " + targetVertex + ". Vertex or vertices does not exist");
    //     }
        
    //     sourceVertexEdges.add(targetVertex);
    //     if(this instanceof MyUndirectedGraph) {
    //         targetVertexEdges.add(sourceVertex);
    //     }
    // }




    @Override
    public boolean hasEulerPath() {
        int oddDegreeVertices = 0;
        for(T vertex : adjacentVertices.keySet()) {
            List<T> list = adjacentVertices.get(vertex);
            if(list.size() % 2 == 1) {
                oddDegreeVertices ++;
            }
        }
        if(oddDegreeVertices == 2) {
            return isConnected(); // TODO current implementation does not have great time complexity 
        }
        return false;
    }

    @Override
    public List<T> eulerPath() {
        if(hasEulerPath()) {

            // while(true) {
                T root = (T) adjacentVertices.keySet().toArray()[0];
                LinkedHashSet<T> path = depthFirstTraversal(root);
                System.out.println(path);
                for(T vertex : path) {
                    // graph.addEdge
                }
            // }
            return null; // TODO real path
        }
        return null;
    }

    private LinkedHashSet<T> depthFirstTraversal(T root) {
        LinkedHashSet<T> visited = new LinkedHashSet<T>();
        Stack<T> stack = new Stack<T>();
        stack.push(root);
        while (!stack.isEmpty()) {
            T vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (T adjacentVertex : adjacentVertices.get(vertex)) {
                    stack.push(adjacentVertex);
                }
            }
        }
        return visited;
    }

    private void dfs(Vertex v) {
        v.visited = true;
        for(T adjacentVertex : adjacentVertices.get(v.element)) {
            if(!new Vertex(adjacentVertex).visited) {
                dfs(new Vertex(adjacentVertex));
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

    class Vertex {
        T element;
        boolean visited;

        Vertex(T _element) {
            element = _element;
        }

        @Override
        public boolean equals(Object o) {
            try {
                @SuppressWarnings("unchecked")
                Vertex other = (Vertex) o;
                return element.equals(other.element);
            }
            catch(ClassCastException e) {
                return false;
            }
        }
    }

    class Edge {
        T from;
        T to;
        boolean visited = false;

        Edge(T _from, T _to) {
            from = _from;
            to = _to;
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

}