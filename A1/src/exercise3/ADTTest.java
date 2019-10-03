package exercise3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ADTTest {
  
  private static SequenceWithMinimum sut;
  private boolean debug = false;
  
  @Before
  public void setup () {
    sut = new SequenceWithMinimum();
  }

  @Before
  public void printSeparator () {
    System.out.print(debug ? "----------------\n" : "");
  }

  @Test
  public void testToString () {
    String expected = "{  }";
    String actual = sut.toString();
    assertEquals(expected, actual);
  }

  @Test
  public void testInsertLeft () {
    sut.insertLeft(4);
    sut.insertLeft(5);
    sut.insertLeft(9);
    
    String expected = "{ 9, 5, 4 }";
    String actual = sut.toString();
    assertEquals(expected, actual);
  }

  @Test
  public void testInsertRight () {
    sut.insertRight(5);
    sut.insertRight(4);
    sut.insertRight(15);
    
    String expected = "{ 5, 4, 15 }";
    String actual = sut.toString();
    assertEquals(expected, actual);
  }
  
  @Test
  public void testRemoveLeft () {
    sut.insertLeft(4);
    sut.insertLeft(5);
    sut.insertLeft(9);
    
    int actual = sut.removeLeft();
    int expected = 9;
    assertEquals(expected, actual);

    actual = sut.removeLeft();
    expected = 5;
    assertEquals(expected, actual);
  }

  @Test
  public void testRemoveRight () {
    sut.insertRight(5);
    sut.insertRight(4);
    sut.insertRight(15);

    int actual = sut.removeRight();
    int expected = 15;
    
    actual = sut.removeRight();
    expected = 4;
    assertEquals(expected, actual);
  }

  @Test
  public void testFindMinimum () {
    sut.insertRight(5);
    sut.insertRight(4);
    sut.insertRight(9);

    Integer expected = 4;
    assertEquals(expected, sut.findMinimum());

    sut.insertRight(9);
    sut.insertRight(1);

    expected = 1;
    assertEquals(expected, sut.findMinimum());
  }

  @Test
  public void testVarious () {
    sut.insertRight(9);
    sut.insertRight(1);

    Integer expected = 1;
    assertEquals(expected, sut.findMinimum());

    sut.removeRight();

    sut.insertRight(5);
    sut.insertRight(4);
    sut.insertRight(9);

    expected = 4;
    assertEquals(expected, sut.findMinimum());
  }

  @Test
  public void testIsEmpty () {
    testInsertRight();
    assertTrue(!sut.isEmpty());
    System.out.println(sut);
    while(!sut.isEmpty()) {
      sut.removeLeft();
    }
    assertTrue(sut.isEmpty());
  }
}
