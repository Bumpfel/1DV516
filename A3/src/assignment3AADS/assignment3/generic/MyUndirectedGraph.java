package assignment3AADS.assignment3.generic;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import assignment3AADS.assignment3.generic.MyUndirectedGraph;

public class MyUndirectedGraph<T> extends AbstractGraph<T> {
    private Stack<Edge> removedEdges = new Stack<>();

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
            
            List<T> path = depthFirstEulerTraversal(startVertex);
 
            for(int i = 1; i < path.size() ; i ++) {
                T vertex = path.get(i);
                if(adjacentVertices.get(vertex).size() > 0) { // has untraversed paths
                    List<T> path2 = depthFirstEulerTraversal(vertex);
                    path.remove(vertex);
                    path.addAll(i, path2);
                }
            }
            System.out.println();

            restoreGraph();
            return path;
        }
        return null;
    }

    private List<T> depthFirstEulerTraversal(T root) {
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

}