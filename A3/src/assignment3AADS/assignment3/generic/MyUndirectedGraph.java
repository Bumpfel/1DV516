package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import assignment3AADS.assignment3.generic.MyUndirectedGraph;

public class MyUndirectedGraph<T> extends AbstractGraph<T> {

    public static void main(String[] args) {
        // MyUndirectedGraph<Integer> graph = buildExerciseGraph();
        MyUndirectedGraph<Integer> graph = buildMyGraph();

        // System.out.println(graph);
        graph.eulerPath();
    }

    private void removeEdge(T vertex1, T vertex2) {
        adjacentVertices.get(vertex1).remove(vertex2);
        adjacentVertices.get(vertex2).remove(vertex1);
        // System.out.println("removing edges between " + vertex1 + " and " + vertex2);
        // System.out.println(this);
    }

    // private void removeEdges(List<T> path) {
    //     for(T edges : path) {
    //         for(int i = 0; i < 2 - 1; i++) {
    //             removeEdge(edges.get(0), edges.get(1));
    //         }
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
            HashMap<T, List<T>> adjacentVerticesCopy = new HashMap<>(adjacentVertices);
            // get vertex with uneven degree to start on 
            T startVertex = null;
            for(T vertex : adjacentVertices.keySet()) {
                if(adjacentVertices.get(vertex).size() % 2 == 1) {
                    startVertex = vertex;
                }
            }
            
            List<T> path = depthFirstTraversalIterative(startVertex);
            // removeEdges(path);

            int firstPathsize = path.size();
            System.out.println("first path " + path);
            for(int i = 0; i < firstPathsize ; i ++) {
                T vertex = path.get(i);
                if(adjacentVertices.get(vertex).size() > 0) { // has untraversed paths
                    System.out.println(vertex + " has untraversed paths " + adjacentVertices.get(vertex));
                    List<T> path2 = depthFirstTraversalIterative(vertex);
                    // removeEdges(path2);
                    path.remove(vertex);
                    path.addAll(path2);
                    System.out.println(path2);
                }
            }
            System.out.println("final path " + path);
            
            adjacentVertices = adjacentVerticesCopy; // restore original
            return path;
        }
        return null;
    }

    private List<T> depthFirstTraversalIterative(T root) {
        List<T> visited = new LinkedList<>();
        Stack<T> visitedEdges = new Stack<>();
        Stack<T> visitNext = new Stack<>();
        visitNext.push(root);
        while (!visitNext.isEmpty()) {
            if(!visitedEdges.empty()) {
                removeEdge(visitedEdges.pop(), visitedEdges.pop());
            }
            T vertex = visitNext.pop();
            visited.add(vertex);
            if(adjacentVertices.get(vertex).isEmpty()) {
                break;
            }
            for (T adjacentVertex : adjacentVertices.get(vertex)) {
                // if (!visited.contains(vertex) && !visited.contains(adjacentVertex)) {
                    // List<T> list = new ArrayList<>();
                    // list.add(vertex);
                    // list.add(adjacentVertex);
                    visitNext.push(adjacentVertex);
                    // removeEdge(vertex, adjacentVertex);
                    visitedEdges.push(vertex);
                    visitedEdges.push(adjacentVertex);
                // }
            }
        }
        return visited;
    }

    // List<T> visitedDFS = new LinkedList<>();

    // private void dfs(T vertex) {
    //     visitedDFS.add(vertex);
    //     for(T adjacentVertex : adjacentVertices.get(vertex)) {
    //         if(!visitedDFS.contains(adjacentVertex)) {
    //             dfs(adjacentVertex);
    //         }
    //     }
    // }



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



}