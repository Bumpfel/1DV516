package exercise3;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DLLTest {
  
  private static MyIntegerDLL sut;
  private boolean debug = false;
  
  @Before
  public void setup () {
    sut = new MyIntegerDLL();
  }

  @Before
  public void printSeparator () {
    System.out.print(debug ? "----------------\n" : "");
  }

  @Test
  public void testInsertLeft () {
    sut.insertLeft(4);
    sut.insertLeft(5);
    sut.insertLeft(9);    
    
    String expected = "{ 9, 5, 4 }";
    assertEquals(expected, sut.toString());
  }

  @Test
  public void testInsertRight () {
    sut.insertRight(5);
    sut.insertRight(4);
    sut.insertRight(15);

    String expected = "{ 5, 4, 15 }";
    assertEquals(expected, sut.toString());
  }
  
  @Test
  public void testRemoveLeft () {
    testInsertLeft();
    int value = sut.removeLeft();
    int expected = 9;
    assertEquals(expected, value);

    value = sut.removeLeft();
    expected = 5;
    assertEquals(expected, value);
  }

  @Test
  public void testRemoveRight () {
    testInsertRight();
    int value = sut.removeRight();
    int expected = 15;
    assertEquals(expected, value);
  }

  @Test
  public void testFindMinimum () {
    sut.insertRight(5);
    sut.insertRight(4);
    sut.insertRight(15);

    Integer expected = 4;
    assertEquals(expected, sut.findMinimum());
  }
}