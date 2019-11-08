package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MySocialNetwork<T> extends MyUndirectedGraph<T> implements A3SocialNetwork<T> {
    private int mFurthestDistanceInFriendshipRelationships;
    private List<T> mPossibleFriends;

    public static void main(String[] args) {
        MySocialNetwork<Integer> graph = createExampleGraph();

        // System.out.println(graph);
        graph.numberOfPeopleAtFriendshipDistance(5, 2);
    }

    @Override
    public int numberOfPeopleAtFriendshipDistance(T vertex, int distance) {
        return breadthFirstTraversal(vertex, distance, 0).size();
    }

    @Override
    public int furthestDistanceInFriendshipRelationships(T vertex) {
        breadthFirstTraversal(vertex, -1, 0);
        return mFurthestDistanceInFriendshipRelationships;
    }

    @Override
    public List<T> possibleFriends(T vertex) {
        return breadthFirstTraversal(vertex, 2, 3);
    }

    List<T> breadthFirstTraversal(T root, final int DISTANCE, int requiredCommonFriends) {
        Set<T> visited = new LinkedHashSet<>();
        List<T> verticesAtDistance = new ArrayList<>();
        Queue<T> queue = new LinkedList<>();
        queue.add(root);
        visited.add(root);
        int currentLevel = 0;
        int iteration = 0;
        int verticesAtLevel = queue.size();
        // System.out.println();
        // System.out.println("-- Searching for vertices at level " + DISTANCE);
        while (!queue.isEmpty()) {
            if(iteration == verticesAtLevel) {
                if(currentLevel == DISTANCE) {
                    // System.out.println("done. terminating search");
                    break;
                }
                verticesAtLevel = queue.size();
                iteration = 0;
                currentLevel ++;
                // System.out.println("-- Level " + currentLevel + " - " + verticesAtLevel + " neighbour(s) --");
            }
            
            // System.out.println("visited " + queue.peek() + " at level " + currentLevel);

            T vertex = queue.poll();

            for (T adjacentVertex : adjacentVertices.get(vertex)) {
                if (!visited.contains(adjacentVertex)) {
                    visited.add(adjacentVertex);
                    queue.add(adjacentVertex);
                    if(currentLevel + 1 == DISTANCE) {
                        int commonFriends = 0;
                        if(requiredCommonFriends > 0) {
                            commonFriends = countCommonAdjacentFriends(root, adjacentVertex);
                        }
                        if(commonFriends >= requiredCommonFriends) {
                            verticesAtDistance.add(adjacentVertex);
                        }
                    }
                }
            }
            iteration ++;
        }
        mFurthestDistanceInFriendshipRelationships = currentLevel;
        return verticesAtDistance;
    }


    private int countCommonAdjacentFriends(T person1, T person2) {
        // List<T> commonFriends = new ArrayList<>();
        int commonFriends = 0;

        for(T friendOfPerson1 : adjacentVertices.get(person1)) {
            if(adjacentVertices.get(person2).contains(friendOfPerson1)) {
                // commonFriends.add(friendOfPerson1);
                commonFriends ++;
            }
        }
        System.out.println(person1 + " has " + commonFriends + " common friends with " + person2);
        return commonFriends;
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
