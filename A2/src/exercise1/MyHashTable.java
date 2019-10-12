package exercise1;

import java.util.NoSuchElementException;

import assignment2AADS.assignment2.A2HashTable;

public class MyHashTable<T> implements A2HashTable<T> {
  private final double MAX_LOAD;

  private T[] elements;
  private int size;
  private boolean hasRehashed = false;

  public boolean hasRehashed () { return hasRehashed; } // for junit testing

  @SuppressWarnings("unchecked")
  public MyHashTable (double maxLoad) {
    MAX_LOAD = maxLoad;
    elements = (T[]) new Object[11];
  }

  @Override
  public int getLengthOfArray () {
    return elements.length;
  }

  public int size() {
    return size;
  }

  @Override
  public void delete (T element) {
    int index = indexOf(element);
    if (index < 0) {
      throw new NoSuchElementException();
    }
    elements[index] = null;
    size--;
  }

  @Override
  public void insert (T element) {
    double currentLoad = (double) size / elements.length;
    int key = findFreeCell(element);
    if (currentLoad > MAX_LOAD || key < 0) {
      rehash();
      insert(element);
      return;
    }
    elements[key] = element;
    size++;
  }

  @Override
  public boolean contains(T element) {
    return indexOf(element) >= 0;
  }

  @SuppressWarnings("unchecked")
  private void rehash () {
    T[] oldArray = elements;
    int prime = findNextPrimeFrom(elements.length * 2 + 1); // finds the next prime number that is at least twice the size of the old array
    elements = (T[]) new Object[prime];

    size = 0;
    for (T element : oldArray) {
      if (element != null) {
        insert(element);
      }
    }
    hasRehashed = true;
  }

  public int indexOf (T element) {
    int hash = Math.abs(element.hashCode());
    int i = 0;
    int key;
    while (i < elements.length) {
      key = getKey(hash, i);
      if (key < 0 || elements[key] == null) {
        break;
      }
      if (elements[key] == element) {
        return key;
      }
      i++;
    }
    return -1;
  }

  private int findFreeCell (T element) {
    int hash = Math.abs(element.hashCode());

    int i = 0;
    int key;
    do {
      key = getKey(hash, i);
      if (i == elements.length) { // no empty cell could be found
        return -1;
      }
      i++;
    } while (elements[key] != null);

    return key;
  }

  private int getKey (int hash, int i) {
    return (hash + (int) Math.pow(i, 2)) % elements.length;
    // return (hash + (int) Math.pow(i, 2) + i) / 2 % elements.length;
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
    for (int i = 0; i < elements.length; i++) {
      str.append("[" + i + "] ");
      if (elements[i] != null) {
        str.append(elements[i]);
      }
      str.append("\n");
    }
    return str.toString();
  }

}
