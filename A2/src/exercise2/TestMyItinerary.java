package exercise2;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import assignment2AADS.assignment2.A2Direction;

public class TestMyItinerary {
  private MyItinerary sut;

  @Before
  public void setup () {
    ArrayList<A2Direction> list = new ArrayList<>();
    list.add(A2Direction.LEFT);
    list.add(A2Direction.DOWN);
    list.add(A2Direction.DOWN);
    list.add(A2Direction.RIGHT);
    list.add(A2Direction.UP);
    list.add(A2Direction.LEFT);
    
    sut = new MyItinerary(list.toArray(new A2Direction[list.size()]));
  }

  private void alternateSetup () {
    ArrayList<A2Direction> list = new ArrayList<>();
    list.add(A2Direction.LEFT);
    list.add(A2Direction.DOWN);
    list.add(A2Direction.RIGHT);
    list.add(A2Direction.DOWN);
    list.add(A2Direction.LEFT);
    list.add(A2Direction.UP);
    list.add(A2Direction.LEFT);
    list.add(A2Direction.UP);
    list.add(A2Direction.RIGHT);
    list.add(A2Direction.UP);

    sut = new MyItinerary(list.toArray(new A2Direction[list.size()]));
  }

  @Test
  public void testRotateRight () {
    A2Direction[] expected = new A2Direction[6];
    expected[0] = A2Direction.UP;
    expected[1] = A2Direction.LEFT;
    expected[2] = A2Direction.LEFT;
    expected[3] = A2Direction.DOWN;
    expected[4] = A2Direction.RIGHT;
    expected[5] = A2Direction.UP;

    assertEquals(Arrays.toString(expected), Arrays.toString(sut.rotateRight()));
  }

  @Test
  public void testHeight () {
    int expected = 2;
    int actual = sut.heightOfItinerary();
    assertEquals(expected, actual);
    
    alternateSetup();
    expected = 3;
    actual = sut.heightOfItinerary();
    assertEquals(expected, actual);
  }

  @Test
  public void testWidth () {
    int expected = 1;
    int actual = sut.widthOfItinerary();
    assertEquals(expected, actual);
    
    alternateSetup();
    expected = 2;
    actual = sut.widthOfItinerary();
    assertEquals(expected, actual);
  }

  @Test
  public void testIntersections () {
    int[] expected = { 5 };
    int[] actual = sut.getIntersections();
    assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    
    alternateSetup();
    expected = new int[] { 5, 8 };
    actual = sut.getIntersections();
    assertEquals(Arrays.toString(expected), Arrays.toString(actual));
  }

  @Test
  public void testIntersections2 () {
    ArrayList<A2Direction> list = new ArrayList<>();
    list.add(A2Direction.LEFT);
    list.add(A2Direction.DOWN);
    list.add(A2Direction.RIGHT);
    list.add(A2Direction.DOWN);
    list.add(A2Direction.LEFT);
    list.add(A2Direction.UP); //x 5
    list.add(A2Direction.LEFT);
    list.add(A2Direction.DOWN);
    list.add(A2Direction.RIGHT); //x 8
    list.add(A2Direction.DOWN);
    list.add(A2Direction.RIGHT);
    list.add(A2Direction.UP); //x 11
    list.add(A2Direction.RIGHT);
    list.add(A2Direction.UP);
    list.add(A2Direction.LEFT); //x 14

    sut = new MyItinerary(list.toArray(new A2Direction[list.size()]));
    int[] expected = { 5, 8, 11, 14 };
    int[] actual = sut.getIntersections();
    assertEquals(Arrays.toString(expected), Arrays.toString(actual));
  }

  
  @Test
  public void testIntList() {
    SimpleIntList list = new SimpleIntList();
    int expected = 0;
    int actual = list.size();
    assertEquals(expected, actual);
    expected = 14;
    for(int i = 0; i < expected; i++) {
      list.add(i);
    }
    actual = list.size();
    assertEquals(expected, actual);
  }

}
