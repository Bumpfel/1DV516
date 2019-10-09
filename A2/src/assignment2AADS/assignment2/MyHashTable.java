package assignment2AADS.assignment2;

public class MyHashTable<T> implements A2HashTable<T> {
  private Object[] elements;
  // private T[] nodes = new T[11];
  private int size;
  private double maxLoad;

  public MyHashTable(double _maxLoad) {
    maxLoad = _maxLoad;
    elements = new Object[11];
  }

  // public MyHashTable(int initialCapacity, double _maxLoad) {
  //   if(initialCapacity < 0) {
  //     throw new IllegalArgumentException();
  //   }
    
  //   elements = new Object[initialCapacity];
  //   maxLoad = _maxLoad;
  // }

  public static void main(String[] args) {
    MyHashTable<String> sut = new MyHashTable<>(.75);
    sut.insert("test");
    sut.insert("test");
    sut.insert("test");
  }

  @Override
  public void insert(T element) {
    double currentLoad = (double) size / elements.length;
    if(currentLoad > maxLoad) {
      rehash();
      return;
    }
    
    size ++;

    Node n = new Node(element);
    int key = n.hashCode();
    elements[key] = element;
  }

  @Override
  public void delete(T element) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean contains(T element) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int getLengthOfArray() {
    return elements.length;
  }

  private void rehash () {

  }


  private class Node {
    private T value;

    public Node (T _value) {
      value = _value;
    }

    @Override
    public boolean equals (Object o) {
      return this.equals(o);
    }

    @Override
    public int hashCode () {
      int hash = value.hashCode();

      int key;
      int i = 0;
      do {
        i++;
        key = (hash + (int) Math.pow(i, 2)) % elements.length;
      }
      while(elements[key] != null);
  
      return key;
    }
  }

}
