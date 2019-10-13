package exercise3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class TestMyMeasure {
  private MyMeasure sut;

  @Before
  public void setup () {
    sut = new MyMeasure();
  }

  @Test
  public void testIsSameCollection () {
    int[] array1 = { 10, 1, 7, 10 };
    int[] array2 = { 1, 10, 7, 10 };
    assertTrue(sut.isSameCollection(array1, array2));
    
    array1 = new int[] { 10, 1, 7, 9 };
    array2 = new int[] { 1, 10, 7, 10 };
    assertFalse(sut.isSameCollection(array1, array2));
    
    array1 = new int[] { 1, 7, 10 };
    array2 = new int[] { 1, 10, 7, 7 };
    assertFalse(sut.isSameCollection(array1, array2));
  }

  @Test
  public void testMinDifferences () {
    int[] array1 = { 2, 5, 3, 9 };
    int[] array2 = { 15, 12, 1, 3 };

    int expected = 86;
    int actual = sut.minDifferences(array1, array2);

    assertEquals(expected, actual);
  }

  @Test
  public void testGetPercentileRange () {
    int[] arr = { 20, 16, 2, 4, 10, 6, 12, 8, 14, 18 };
    
    int[] expected = { 2 };
    int[] actual = sut.getPercentileRange(arr, 0, 10);
    assertEquals(Arrays.toString(expected), Arrays.toString(actual));
      
    expected = new int[] { 4, 6, 8, 10 }; // TODO does not need to be ordered
    actual = sut.getPercentileRange(arr, 10, 50);
    assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    
    expected = new int[] { 14 };
    actual = sut.getPercentileRange(arr, 60, 70);
    assertEquals(Arrays.toString(expected), Arrays.toString(actual));

    expected = new int[] { 2, 4, 6, 8, 10, 12, 14, 16, 18, 20 };
    actual = sut.getPercentileRange(arr, 0, 100);
    assertEquals(Arrays.toString(expected), Arrays.toString(actual));
  }
 
}