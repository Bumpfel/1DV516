package assignment3AADS.assignment3.MyTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import assignment3AADS.assignment3.generic.MySocialNetwork;

public class TestSocialNetwork {
    private MySocialNetwork<Integer> sut;

    @Before
    public void setup() {
        sut = createTestGraph();
    }

    @Test
    public void testNumberOfPeopleAtFriendshipDistance() {
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
    
    @Test
    public void testFurthestDistanceInFriendshipRelationships () {
        int expected = 3;
        int actual = sut.furthestDistanceInFriendshipRelationships(5);
        assertEquals(expected, actual);

        expected = 5;
        actual = sut.furthestDistanceInFriendshipRelationships(13);
        assertEquals(expected, actual);

        expected = 4;
        actual = sut.furthestDistanceInFriendshipRelationships(7);
        assertEquals(expected, actual);
    }

    @Test
    public void testPossibleFriends() {
        // test friends of 5
        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(10);
        List<Integer> actual = sut.possibleFriends(5);

        assertEquals(expected.size(), actual.size());
        for(Integer n : expected) {
            assertTrue(actual.contains(n));
        }

        // test friends of 10
        expected.clear();
        expected.add(5);
        actual = sut.possibleFriends(10);

        assertEquals(expected.size(), actual.size());
        for(Integer n : expected) {
            assertTrue(actual.contains(n));
        }

        // test friends of 13
        expected.clear();
        actual = sut.possibleFriends(13);

        assertEquals(expected.size(), actual.size());
        for(Integer n : expected) {
            assertTrue(actual.contains(n));
        }
    }


    private static MySocialNetwork<Integer> createTestGraph() {
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