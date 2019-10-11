package assignment2AADS.assignment2;

public class MyHashTable<T> implements A2HashTable<T> {
  private final double MAX_LOAD;
  // private Object[] elements;

  private T[] elements;
  private int size;
  
  @SuppressWarnings("unchecked")
  public MyHashTable(double _maxLoad) {
    MAX_LOAD = _maxLoad;
    elements = (T[]) new Object[11];
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
    sut.insert("hej");
    sut.insert("på");
    sut.insert("dig");
    sut.insert("din");
    sut.insert("lille");
    sut.insert("sociopat");
    sut.insert(",");
    sut.insert("ge");
    sut.insert("mig");
    sut.insert("min");
    sut.insert("uppgift");
    sut.insert("!");
    System.out.println();
    System.out.println("EVERYTHING INSERTED. PRINTING TABLE");
    System.out.println(sut.toString());

    // int prime = 11;
    // for(int i = 0; i < 7; i++) {
    //   prime = sut.findNextPrime(prime);
    //   System.out.println(prime);
    // }    
  }

  @Override
  public void insert(T element) {
    double currentLoad = (double) size / elements.length;
    if(currentLoad > MAX_LOAD) {
      System.out.println("-### Exceeded max load, re-hashing");
      System.out.println(size + ", " + elements.length + " == " + currentLoad);
      rehash();
      insert(element);
      return;
    }

    Node n = new Node(element);
    int key = n.hashCode();
    if(key < 0) {
      System.out.println("Did not find empty cell, re-hashing");
      rehash();
      insert(element);
      return;
    }
    
    elements[key] = element;
    size ++;
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

  @SuppressWarnings("unchecked")
  private void rehash () {
    T[] oldArray = elements;
    int nextPrime = findNextPrime(elements.length * 2);
    elements = (T[]) new Object[nextPrime];

    size = 0;
    for(T o : oldArray) {
      if(o != null) {
        insert(o);
      }
    }
    System.out.println("REHASH DONE, NEW SIZE " + elements.length + "\n" + this);
  }

  private int findNextPrime(int n) {
    n ++;
    if (isPrime(n)) {
      return n;
    }
    return findNextPrime(n);
  }
 
  private boolean isPrime(int n) {
    for(int i = 2; i < n; i++) {
      if(n % i == 0) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString () {
    StringBuilder str = new StringBuilder();
    for(int i = 0; i < elements.length; i ++) {
      str.append("[" + i + "] ");
      if (elements[i] != null) {
         str.append(elements[i]);
        }
        str.append("\n");
    }
    return str.toString();
  }

  class Node {
    T value;

    Node (T _value) {
      value = _value;
    }

    @Override
    public boolean equals (Object o) {
      return value.equals(o.toString());
    }

    @Override
    public int hashCode () {
      int hash = Math.abs(value.hashCode());

      int key;
      int i = 0;
      System.out.println("----- " + value + " " + hash + " --------");
      do {
        key = (hash + (int) Math.pow(i, 2)) % elements.length;
        // key = (hash + (int) Math.pow(i, 2) + i) / 2 % elements.length;

        System.out.println("checking key " + key);
        // if (Math.pow(i, 2) >= elements.length) {
        if (i == elements.length) { // no empty cell could be found
          System.out.println("###### COULD NOT FIND EMPTY CELL");
          return -1;
        }
        i++;
      }
      while(elements[key] != null);
  
      return key;
    }
  }

}
