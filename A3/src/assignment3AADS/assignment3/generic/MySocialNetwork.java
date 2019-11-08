package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MySocialNetwork<T> extends MyUndirectedGraph<T> implements A3SocialNetwork<T> {
    private int mFurthestDistanceInFriendshipRelationships;

    public static void main(String[] args) {
        MySocialNetwork<Integer> graph = createExampleGraph();

        graph.possibleFriends(5);
        // graph.numberOfPeopleAtFriendshipDistance(5, 2);
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

    private List<T> breadthFirstTraversal(T root, int distance, int requiredCommonFriends) {
        Set<T> visited = new HashSet<>();
        List<T> verticesAtDistance = new ArrayList<>();
        Queue<T> queue = new LinkedList<>();
        queue.add(root);
        visited.add(root);
        int currentLevel = 0;
        int verticesAtLevel = queue.size();
        
        for(int iteration = 0; !queue.isEmpty(); iteration ++) {
            // level switch
            if(iteration == verticesAtLevel) {
                if(currentLevel == distance) {
                    break;
                }
                verticesAtLevel = queue.size();
                iteration = 0;
                currentLevel ++;
            }
            T vertex = queue.poll();

            // iterate through adjacent vertices of current vertex, add unvisited adjavent vertices to queue
            for (T adjacentVertex : adjacentVertices.get(vertex)) {
                if (!visited.contains(adjacentVertex)) {
                    visited.add(adjacentVertex);
                    queue.add(adjacentVertex);
                    // at correct level (queue is populated before level is increased)
                    if(currentLevel + 1 == distance) {
                        int commonFriends = requiredCommonFriends > 0 ? countCommonFriends(root, adjacentVertex) : 0;
                        
                        if(commonFriends >= requiredCommonFriends) {
                            verticesAtDistance.add(adjacentVertex);
                        }
                    }
                }
            }
        }
        mFurthestDistanceInFriendshipRelationships = currentLevel;
        return verticesAtDistance;
    }


    private int countCommonFriends(T person1, T person2) {
        int commonFriends = 0;

        for(T friendOfPerson1 : adjacentVertices.get(person1)) {
            if(adjacentVertices.get(person2).contains(friendOfPerson1)) {
                commonFriends ++;
            }
        }
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
