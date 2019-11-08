package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MySocialNetwork<T> extends MyUndirectedGraph<T> implements A3SocialNetwork<T> {

    public static void main(String[] args) {
        MySocialNetwork<Integer> graph = createExampleGraph();

        // System.out.println(graph);
        graph.numberOfPeopleAtFriendshipDistance(5, 2);
    }

    @Override
    public int numberOfPeopleAtFriendshipDistance(T vertex, int distance) {

        List<T> nodes = breadthFirstTraversal(vertex, distance);
        System.out.println(nodes);
        return nodes.size();
    }

    @Override
    public int furthestDistanceInFriendshipRelationships(T vertex) {
        breadthFirstTraversal(vertex, -1);
        return maxDistance;
    }

    @Override
    public List<T> possibleFriends(T vertex) {
    	return null;
    }


    int maxDistance;

    List<T> breadthFirstTraversal(T root, final int DISTANCE) {
        Set<T> visited = new LinkedHashSet<>();
        List<T> verticesAtDistance = new ArrayList<>();
        Queue<T> queue = new LinkedList<>();
        queue.add(root);
        visited.add(root);
        int currentLevel = 0;
        int iteration = 0;
        int verticesAtLevel = queue.size();
        System.out.println();
        System.out.println("-- Searching for vertices at level " + DISTANCE);
        while (!queue.isEmpty()) {
            if(iteration == verticesAtLevel) {
                if(currentLevel == DISTANCE) {
                    System.out.println("done. terminating search");
                    break;
                }
                verticesAtLevel = queue.size();
                iteration = 0;
                currentLevel ++;
                maxDistance = currentLevel;
                System.out.println("-- Level " + currentLevel + " - " + verticesAtLevel + " neighbour(s) --");
            }
            
            System.out.println("visited " + queue.peek() + " at level " + currentLevel);

            T vertex = queue.poll();

            for (T adjacentVertex : adjacentVertices.get(vertex)) {
                if (!visited.contains(adjacentVertex)) {
                    visited.add(adjacentVertex);
                    queue.add(adjacentVertex);
                    if(currentLevel + 1 == DISTANCE) {
                        verticesAtDistance.add(adjacentVertex);
                    }
                }
            }
            iteration ++;
        }
        return verticesAtDistance;
    }

    private static MySocialNetwork<Integer> createExampleGraph() {
        MySocialNetwork<Integer> graph = new MySocialNetwork<>();
        for(int i = 1; i <= 15; i ++) {
            graph.addVertex(i);
        }
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(1, 5);
        
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        
        graph.addEdge(3, 4);
        graph.addEdge(3, 6);
        graph.addEdge(3, 13);

        graph.addEdge(4, 5);
        
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        graph.addEdge(5, 8);
        graph.addEdge(5, 9);
        graph.addEdge(5, 12);
        graph.addEdge(5, 15);

        graph.addEdge(6, 14);

        graph.addEdge(7, 10);
        graph.addEdge(7, 11); 

        graph.addEdge(8, 10);
        
        graph.addEdge(9, 10);

        return graph;
    }

}
