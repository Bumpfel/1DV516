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
    A2Direction[] array = new A2Direction[6];
    array[0] = A2Direction.LEFT;
    array[1] = A2Direction.DOWN;
    array[2] = A2Direction.DOWN;
    array[3] = A2Direction.RIGHT;
    array[4] = A2Direction.UP;
    array[5] = A2Direction.LEFT;
    
    sut = new MyItinerary(array);
  }

  private void alternateSetup () {
    ArrayList<A2Direction> arrList = new ArrayList<>();
    arrList.add(A2Direction.LEFT);
    arrList.add(A2Direction.DOWN);
    arrList.add(A2Direction.DOWN);
    arrList.add(A2Direction.RIGHT);
    arrList.add(A2Direction.DOWN);
    arrList.add(A2Direction.LEFT);
    arrList.add(A2Direction.UP);
    arrList.add(A2Direction.LEFT);
    arrList.add(A2Direction.UP);
    arrList.add(A2Direction.RIGHT);
    arrList.add(A2Direction.UP);

    sut = new MyItinerary(arrList.toArray(new A2Direction[arrList.size()]));
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
    // assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    
    // alternateSetup();
    expected = new int[] { 5, 8 };
    // actual = sut.getIntersections();
    // assertEquals(Arrays.toString(expected), Arrays.toString(actual));
  }

}