package exercise1;

import java.util.NoSuchElementException;

import assignment2AADS.assignment2.A2HashTable;

public class MyHashTable<T> implements A2HashTable<T> {
  private static final int NO_FREE_CELL_FOUND = -1, ELEMENT_NOT_FOUND = -1, ELEMENT_ALREADY_EXISTS = -2;

  public final double MAX_LOAD;

  private Object[] mElements;
  private int mSize;

  public MyHashTable (double maxLoad) {
    MAX_LOAD = maxLoad;
    // mElements = new Object[11]; // prime sized
    mElements = new Object[8]; // must be a power of two with current key algorithm
  }
  
  @Override
  public int getLengthOfArray () {
    return mElements.length;
  }
  
  public int size() {
    return mSize;
  }
  
  @Override
  public void delete (T element) {
    int index = search(element, true);
    if (index == ELEMENT_NOT_FOUND) {
      throw new NoSuchElementException();
    }
    mElements[index] = new DeletedNode();
    mSize--;
  }

  @Override
  public void insert (T element) {
    double currentLoad = (double) mSize / mElements.length;
    int freeCell = -1;
    if (currentLoad < MAX_LOAD) {
      freeCell = search(element, false);
    }
    if (currentLoad > MAX_LOAD || freeCell == NO_FREE_CELL_FOUND) {
      rehash();
      insert(element);
      return;
    }
    if (freeCell == ELEMENT_ALREADY_EXISTS) {
      return;
    }
    mElements[freeCell] = element;
    mSize++;
  }

  @Override
  public boolean contains(T element) {
    return search(element, true) >= 0;
  }

  @SuppressWarnings("unchecked")
  private void rehash () {
    Object[] oldArray = mElements;
    // int prime = findNextPrimeFrom(mElements.length * 2 + 1); // finds the next prime number that is at least twice the size of the old array
    // mElements = (T[]) new Object[prime];
    
    mElements = (T[]) new Object[mElements.length * 2];

    mSize = 0;
    for (Object element : oldArray) {
      if (element != null && !(element instanceof MyHashTable.DeletedNode)) {
        insert((T) element);
      }
    }
  }

  /**
   * Searches for a free cell for the element or the element itself. 
   * In searching for a free cell it also checks whether the table already contains an equals object
   * @param element
   * @param searchForElement decides whether the method should search for a free cell or the element itself
   * @return
   */
  private int search (T element, boolean searchForElement) {
    int hash = Math.abs(element.hashCode());
    int i = 0;

    while (i < mElements.length) {
      // int index = (hash + (int) Math.pow(i, 2)) % mElements.length; // can visit half of the cells (rounded up) if array length is a prime
      int index = (hash + (int) Math.pow(i, 2) + i) / 2 % mElements.length; // can visit all cells if array length is a power of 2
      if (mElements[index] == null) {
        return searchForElement ? ELEMENT_NOT_FOUND : index;
      }
      if (!searchForElement && mElements[index] instanceof MyHashTable.DeletedNode) {
        return index;
      }
      if(mElements[index].equals(element)) {
        return searchForElement ? index : ELEMENT_ALREADY_EXISTS;
      }
      i++;
    }
    return -1;
  }

  @Override
  public String toString () {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < mElements.length; i++) {
      str.append("[" + i + "] ");
      if (mElements[i] != null) {
        str.append(mElements[i]);
      }
      str.append("\n");
    }
    return str.toString();
  }



  // not used
  private int findNextPrimeFrom (int n) {
    return isPrime(n) ? n : findNextPrimeFrom(n + 1);
  }
  private boolean isPrime (int n) {
    for (int i = 2; i < n; i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }

  

  class DeletedNode {
    @Override
    public String toString () {
      return "DELETED";
    }
  }

}
