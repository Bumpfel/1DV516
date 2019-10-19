package exercise1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class TestMyHashTable {
  private MyHashTable<String> sut;
  
  @Before
  public void setup () {
    sut = new MyHashTable<>(.75);
  }

  @Test
  public void testInsert () {
    assertTrue(sut.size() == 0);

    String obj1 = new String("str1");
    String obj2 = new String("str2");
    String obj3 = new String("str3");

    sut.insert(obj1);
    sut.insert(obj2);
    sut.insert(obj3);

    assertTrue(sut.size() == 3);
  }

  @Test  
  public void testGetLengthOfArray () {
    int initialArraySize = sut.getLengthOfArray();
    assertTrue(initialArraySize > sut.size());

    for(int i = 0; i < initialArraySize * sut.MAX_LOAD + 1; i++) {
      sut.insert("" + i);
    }
    assertTrue(sut.getLengthOfArray() > initialArraySize);
  }
  
  @Test 
  public void testContains () {
    sut.insert("test");

    assertFalse(sut.contains("newString"));
    assertTrue(sut.contains("test"));
  }
  
  @Test(expected = NoSuchElementException.class)
  public void testDelete () {
    String str1 = new String("somethingUnique");
    String str2 = new String("somethingElseUnique");
    sut.insert(str1);
    sut.insert(str2);
    int sizeBeforeRemoval = sut.size();
    sut.delete(str1);

    assertFalse(sut.contains(str1));
    assertTrue(sut.contains(str2));
    assertTrue(sut.size() == sizeBeforeRemoval - 1);
    
    sut.delete("doesnotexist");
  }
}