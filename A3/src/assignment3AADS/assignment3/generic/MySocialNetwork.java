package assignment3AADS.assignment3.generic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MySocialNetwork<T> extends MyUndirectedGraph<T> implements A3SocialNetwork<T> {
    private int mFurthestDistanceInFriendshipRelationships;

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
                        if(hasEnoughCommonFriends(root, adjacentVertex, requiredCommonFriends)) {
                            verticesAtDistance.add(adjacentVertex);
                        }
                    }
                }
            }
        }
        mFurthestDistanceInFriendshipRelationships = currentLevel;
        return verticesAtDistance;
    }


    private boolean hasEnoughCommonFriends(T person1, T person2, int requiredCommonFriends) {
        for(T friendOfPerson1 : adjacentVertices.get(person1)) {
            if(adjacentVertices.get(person2).contains(friendOfPerson1)) {
                requiredCommonFriends --;
                if(requiredCommonFriends == 0) {
                    break;
                }
            }
        }
        return requiredCommonFriends <= 0;
    }
}
