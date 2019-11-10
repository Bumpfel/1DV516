package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import assignment3AADS.assignment3.generic.MyUndirectedGraph;

public class MyUndirectedGraph<T> extends AbstractGraph<T> {
    private Stack<Edge> removedEdges = new Stack<>();
    private T oddVertex;

    private void removeEdge(Edge edge) {
        adjacentVertices.get(edge.from).remove(edge.to);
        adjacentVertices.get(edge.to).remove(edge.from);
    }

    @Override
    public boolean isConnected() {
        Set<T> visited = new HashSet<>();
        dfs(firstAddedVertex, visited);
        return visited.size() == adjacentVertices.size();
    }

    public List<List<T>> connectedComponents() {
        Set<T> allVisited = new HashSet<>(); // used to hold all visited vertices during this analysis
        List<List<T>> connectedComponents = new ArrayList<>();
        
        for(T vertex : adjacentVertices.keySet()) { // loop through all vertices
            if(allVisited.contains(vertex)) { // skip vertices that were already visited
                continue;
            }
            Set<T> visited = new HashSet<>();
            dfs(vertex, visited);
            connectedComponents.add(new ArrayList<>(visited));
            allVisited.addAll(visited);
        }
        return connectedComponents;
    }

    @Override
    public boolean hasEulerPath() {
        int oddDegreeVertices = 0;
        for(T vertex : adjacentVertices.keySet()) {
            List<T> list = adjacentVertices.get(vertex);
            if(list.size() % 2 == 1) {
                oddDegreeVertices ++;
                oddVertex = vertex;
            }
        }
        if(isEulerCircuit(oddDegreeVertices) || isEulerPath(oddDegreeVertices)) {
            return isConnected();
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
            T startVertex = oddVertex == null ? firstAddedVertex : oddVertex;
            
            List<T> path = new ArrayList<>();
            path.add(startVertex);
 
            for(int i = 0; i < path.size() ; i ++) {
                T vertex = path.get(i);
                if(adjacentVertices.get(vertex).size() > 0) { // has untraversed paths
                    List<T> partialPath = dfsEuler(vertex);
                    // splice partial path into path
                    path.remove(vertex);
                    path.addAll(i, partialPath);
                }
            }
            System.out.println();

            restoreGraph();
            return path;
        }
        return null;
    }

    private List<T> dfsEuler(T root) {
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
            if(adjacentVertices.get(vertex).isEmpty())  {
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
        public int hashCode() {
            return from.hashCode() + to.hashCode();
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