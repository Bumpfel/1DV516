package assignment2AADS.assignment2;

import java.util.ArrayList;

public class MyHashTable<T> implements A2HashTable<T> {
  private final double MAX_LOAD;
  // private Object[] elements;

  private T[] elements;
  // private Node[] elements;
  private int size;
  
  @SuppressWarnings("unchecked")
  public MyHashTable(double maxLoad) {
    MAX_LOAD = maxLoad;
    elements = (T[]) new Object[11];
    // elements = (Node[]) new Object[11];
  }

  // public MyHashTable(int initialCapacity, double _maxLoad) {
  //   if(initialCapacity < 0) {
  //     throw new IllegalArgumentException();
  //   }
    
  //   elements = new Object[initialCapacity];
  //   maxLoad = _maxLoad;
  // }

  public static void main(String[] args) {
    MyHashTable<Object> sut = new MyHashTable<>(.75);

    // sut.insert(1);

    // sut.insert(obj1);
    // System.out.println("Contains " + obj2 + ": " + sut.contains(obj2));


    String obj1 = new String("din");
    String obj2 = new String("din");

    for(int i = 65; i < 200; i++) {
      sut.insert((char) i);
    }

    sut.insert("hej");
    sut.insert("på");
    sut.insert("dig");
    sut.insert("lille");
    sut.insert("sociopat");
    sut.insert(",");
    sut.insert("ge");
    sut.insert("mig");
    // sut.insert("din");
    // sut.insert("din");
    sut.insert("din");
    sut.insert(obj1);
    sut.insert(obj2);
    sut.insert("min");
    sut.insert("uppgift");
    sut.insert("!");

    // sut.insert("bla");
    // sut.insert("bla");
    // sut.insert("bla");
    // sut.insert("bla");
    // sut.insert("bla");
    // sut.insert("bla");
    // sut.insert("bla");
    // sut.insert("bla");
    // sut.insert("bla");
    // sut.insert("bla");

    // System.out.println();
    // System.out.println("EVERYTHING INSERTED. PRINTING TABLE");
    // System.out.println(sut.toString());

    Object searchObj = obj2;
    System.out.println("Contains " + searchObj + ": " + sut.contains(searchObj));

    // int prime = 11;
    // for(int i = 0; i < 7; i++) {
    //   prime = sut.findNextPrime(prime);
    //   System.out.println(prime);
    // }    
  }

  @Override
  public void insert(T element) {
    double currentLoad = (double) size / elements.length;
    // Node n = new Node(element);
    int key = findFreeCell(element);
    if (currentLoad > MAX_LOAD || key < 0) {
      System.out.println("-### Exceeded max load, OR did not find empty cell. Re-hashing");
      System.out.println(size + ", " + elements.length + " == " + currentLoad);
      rehash();
      insert(element);
      return;
    }
    
    elements[key] = element;
    size ++;
  }

  @Override
  public void delete(T element) {
    int index = indexOf(element);
    elements[index] = null;
  }

  @Override
  public boolean contains(T element) {
    return indexOf(element) >= 0;
  }

  public int indexOf (T element) {
    int hash = element.hashCode();
    int i = 0;
    int key;
    while (i < elements.length) {
      key = getKey(hash, i);
      if (key < 0 || elements[key] == null) {
        break;
      }
      if (elements[key] == element) {
        System.out.println("found after " + (i + 1) + " iterations");
        return key;
      }
      i ++;
    }
    return -1;
  }

  @Override
  public int getLengthOfArray() {
    return elements.length;
  }

  @SuppressWarnings("unchecked")
  private void rehash () {
    T[] oldArray = elements;
    int nextPrime = findNextPrimeFrom(elements.length * 2 + 1);
    elements = (T[]) new Object[nextPrime];

    size = 0;
    for (T obj : oldArray) {
      if (obj != null) {
        insert(obj);
      }
    }
    System.out.println("REHASH DONE, NEW SIZE " + elements.length + "\n" + this);
  }

  private int findFreeCell(T obj) {
    int hash = Math.abs(obj.hashCode());
    // int hash = hashCode & 0x7FFFFFFF;

    int i = 0;
    int key;
    // System.out.println("----- " + obj + " " + hash + " --------");
    do {
      key = getKey(hash, i);
      // key = (hash + (int) Math.pow(i, 2) + i) / 2 % elements.length;

      // System.out.println("checking key " + key);
      // if (Math.pow(i, 2) >= elements.length) {
      if (i == elements.length) { // no empty cell could be found
        // System.out.println("###### COULD NOT FIND EMPTY CELL");
        return -1;
      }
      i++;
    }
    while(elements[key] != null);

    return key;
  }

  private int getKey (int hash, int i) {
    return (hash + (int) Math.pow(i, 2)) % elements.length;
  }

  private int findNextPrimeFrom(int n) {
    if (isPrime(n)) {
      return n;
    }
    return findNextPrimeFrom(n + 1);
  }
 
  private boolean isPrime(int n) {
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
    for (int i = 0; i < elements.length; i ++) {
      str.append("[" + i + "] ");
      if (elements[i] != null) {
         str.append(elements[i]);
        }
        str.append("\n");
    }
    return str.toString();
  }

  // class Node {
  //   T element;
  //   int key;

  //   Node (T _element, int _key) {
  //     element = _element;
  //     key = _key;
  //   }

  //   @SuppressWarnings("unchecked")
  //   @Override
  //   public boolean equals (Object o) {
  //     Node obj = (Node) o;
  //     return element == obj && key == obj.key;
  //     // return element.equals((T) o);
  //   }

  //   @Override
  //   public int hashCode () {
  //     return element.hashCode();
  //   }
    
  // }

}