package exercise1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BSTTest {
  
  private static MyIntegerBST sut;
  private boolean debug = false;
  
  @BeforeClass
  public static void setup () {
    System.out.println("setup()");
    sut = new MyIntegerBST();
    sut.insert(10);
		sut.insert(7);
		sut.insert(20);
		sut.insert(4);
		sut.insert(9);
		sut.insert(25);
		sut.insert(8);
  }

  @Before
  public void printSeparator () {
    System.out.print(debug ? "----------------\n" : "");
  }

  @Test
  public void testPrintLevels () {
    System.out.print(debug ? "testPrintLevels()\n" : "");
    sut.printByLevels();
    String actual = Arrays.toString(sut.testPrintArr);
    String expected = "[10, 7, 20, 4, 9, 25, 8]";
    assertEquals(expected, actual.trim());
  }

  @Test
  public void testSimilarValue () {
    System.out.print(debug ? "testSimilarValue()\n" : "");

    assertEquals(sut.mostSimilarValue(18), Integer.valueOf(20));
    assertEquals(sut.mostSimilarValue(21), Integer.valueOf(20));
    assertEquals(sut.mostSimilarValue(24), Integer.valueOf(25));
    assertEquals(sut.mostSimilarValue(1), Integer.valueOf(4));
    assertEquals(sut.mostSimilarValue(9), Integer.valueOf(9));
    assertTrue(sut.mostSimilarValue(15) == Integer.valueOf(10) || sut.mostSimilarValue(15) == Integer.valueOf(20));
  }
}