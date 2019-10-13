package exercise1;

import java.util.NoSuchElementException;

import assignment2AADS.assignment2.A2HashTable;

public class MyHashTable<T> implements A2HashTable<T> {
  private final double MAX_LOAD;

  private T[] mElements;
  private int mSize;
  private boolean mHasRehashed = false;

  public boolean hasRehashed () { return mHasRehashed; } // for junit testing

  @SuppressWarnings("unchecked")
  public MyHashTable (double maxLoad) {
    MAX_LOAD = maxLoad;
    mElements = (T[]) new Object[11];
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
    int index = indexOf(element);
    if (index < 0) {
      throw new NoSuchElementException();
    }
    mElements[index] = null;
    mSize--;
  }

  @Override
  public void insert (T element) {
    double currentLoad = (double) mSize / mElements.length;
    int key = findFreeCell(element);
    if (currentLoad > MAX_LOAD || key < 0) {
      rehash();
      insert(element);
      return;
    }
    mElements[key] = element;
    mSize++;
  }

  @Override
  public boolean contains(T element) {
    return indexOf(element) >= 0;
  }

  @SuppressWarnings("unchecked")
  private void rehash () {
    T[] oldArray = mElements;
    int prime = findNextPrimeFrom(mElements.length * 2 + 1); // finds the next prime number that is at least twice the size of the old array
    mElements = (T[]) new Object[prime];

    mSize = 0;
    for (T element : oldArray) {
      if (element != null) {
        insert(element);
      }
    }
    mHasRehashed = true;
  }

  public int indexOf (T element) {
    int hash = Math.abs(element.hashCode());
    int i = 0;
    int key;
    while (i < mElements.length) {
      key = getKey(hash, i);
      if (key < 0 || mElements[key] == null) {
        break;
      }
      if (mElements[key].equals(element)) {
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
      if (i == mElements.length) { // no empty cell could be found
        return -1;
      }
      i++;
    } while (mElements[key] != null);

    return key;
  }

  private int getKey (int hash, int i) {
    return (hash + (int) Math.pow(i, 2)) % mElements.length;
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
    for (int i = 0; i < mElements.length; i++) {
      str.append("[" + i + "] ");
      if (mElements[i] != null) {
        str.append(mElements[i]);
      }
      str.append("\n");
    }
    return str.toString();
  }

}
