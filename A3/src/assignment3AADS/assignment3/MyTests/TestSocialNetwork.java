package assignment3AADS.assignment3.MyTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import assignment3AADS.assignment3.generic.MySocialNetwork;

public class TestSocialNetwork {
    private MySocialNetwork<Integer> sut;

    @Test
    public void testNumberOfPeopleAtFriendshipDistance() {
        sut = createExampleGraph();
        
        int expected = 9;
        int actual = sut.numberOfPeopleAtFriendshipDistance(5, 1);
        assertEquals(expected, actual);

        expected = 4;
        actual = sut.numberOfPeopleAtFriendshipDistance(5, 2);
        assertEquals(expected, actual);
        
        expected = 1;
        actual = sut.numberOfPeopleAtFriendshipDistance(5, 3);
        assertEquals(expected, actual);

    }
    
    public void testFurthestDistanceInFriendshipRelationships () {
        sut = createExampleGraph();


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