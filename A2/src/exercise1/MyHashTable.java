package exercise1;

import java.util.NoSuchElementException;

import assignment2AADS.assignment2.A2HashTable;

// TODO fixa kaoset med Object och Node. kräver många castings.
// TODO objekt (noder ska inte refereas till som "element")
// Allows for duplicates
public class MyHashTable<T> implements A2HashTable<T> {
  private final double MAX_LOAD;

  private Object[] mElements;
  // private T[] mElements;
  private int mSize;
  private boolean mHasRehashed = false;

  public boolean hasRehashed () { return mHasRehashed; } // for junit testing

  @SuppressWarnings("unchecked")
  public MyHashTable (double maxLoad) {
    MAX_LOAD = maxLoad;
    // mElements = (T[]) new Object[11];
    mElements = new Object[11];
  }
  
  @Override
  public int getLengthOfArray () {
    return mElements.length;
  }
  
  public int size() {
    return mSize;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public void delete (T element) {
    int index = indexOf(element);
    if (index < 0) {
      throw new NoSuchElementException();
    }
    // TODO can't nullify. contains won't find subsequent elements, unless it continues to search no matter if it finds null objects
    // mElements[index] = (T) new Object(); 
    mElements[index] = new Node(NodeStatus.DELETED);
    mSize--;
  }

  @Override
  public void insert (T element) {
    double currentLoad = (double) mSize / mElements.length;
    int key = -1;
    if (currentLoad < MAX_LOAD) {
      key = findFreeCell(element);
    }
    if (currentLoad > MAX_LOAD || key < 0) {
      rehash();
      insert(element);
      return;
    }
    mElements[key] = new Node(element);
    mSize++;
  }

  @Override
  public boolean contains(T element) {
    return indexOf(element) >= 0;
  }

  @SuppressWarnings("unchecked")
  private void rehash () {
    // T[] oldArray = mElements;
    Object[] oldArray = mElements;
    int prime = findNextPrimeFrom(mElements.length * 2 + 1); // finds the next prime number that is at least twice the size of the old array
    mElements = (T[]) new Object[prime];

    mSize = 0;
    for (Object element : oldArray) {
      Node node = (Node) element;
      if (element != null && !node.deleted) {
        if (node.deleted) {
          System.err.println("skipping deleted object " + element);
        }
        insert((T) element);
      }
    }
    mHasRehashed = true;
  }

  public int indexOf (T element) {
    int hash = Math.abs(element.hashCode());
    int i = 0;
    int key;
    while (i < Math.ceil(mElements.length / 2)) {
      key = getKey(hash, i);
      Node node = (Node) mElements[key];
      if (key < 0 || mElements[key] == null) {
        break;
      }
      // if (mElements[key].equals(element)) {
      if (node.element != null && node.element.equals(element)) {
        return key;
      }
      i++;
    }
    return -1;
  }

  @SuppressWarnings("unchecked")
  private int findFreeCell (T element) {
    int hash = Math.abs(element.hashCode());

    int i = 0;
    int key;
    Node node;
    do {
      key = getKey(hash, i);
      if (i == Math.ceil(mElements.length / 2)) { // no empty cell could be found
        return -1;
      }
      i++;
      node = (Node) mElements[key];
    } while (mElements[key] != null && !node.deleted);

    return key;
  }

  private int getKey (int hash, int i) {
    return (hash + (int) Math.pow(i, 2)) % mElements.length;
  }

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

  private enum NodeStatus { DELETED };
  
  class Node {
    T element;
    boolean deleted = false;

    Node (T _element) {
      element = _element;
    }
    
    Node (NodeStatus status) {
      deleted = true;
    }

    @Override
    public boolean equals (Object other) {
      if(other instanceof MyHashTable.Node) {
        Node node = (Node) other;
        return element.equals(node.element);
      }
      return false;
    }
    
    @Override
    public String toString () {
      return deleted ? "DELETED" : element.toString();
    }
  }

}
