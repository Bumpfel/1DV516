package exercise2;

import assignment2AADS.assignment2.A2Direction;
import assignment2AADS.assignment2.A2Itinerary;
import exercise1.MyHashTable;

public class MyItinerary implements A2Itinerary<A2Direction> {

  private final A2Direction[] mDirections;
  private final int[] mIntersections;
  private final int mWidth;
  private final int mHeight;
  
  public MyItinerary (A2Direction[] array) {
    mDirections = array;
    
    mWidth = calcSize(A2Direction.LEFT, A2Direction.RIGHT);
    mHeight = calcSize(A2Direction.UP, A2Direction.DOWN);

    mIntersections = calcIntersections();
  }

  @Override
  public int widthOfItinerary() { return mWidth; }
  
  @Override
  public int heightOfItinerary() { return mHeight; }

  @Override
  public int[] getIntersections() { return mIntersections; }

  /**
   * Returns an array of directions of the itinerary rotated right
   * Does not modify the original. To use these values as the starting point, create a new itinerary with the returned array
   */
  @Override
  public A2Direction[] rotateRight() {
    A2Direction[] rotatedDirections = new A2Direction[mDirections.length];
    A2Direction[] directionArr = A2Direction.values();
    for(int i = 0; i < mDirections.length; i++) {
      int n = (mDirections[i].ordinal() + 1) % directionArr.length;
      rotatedDirections[i] = directionArr[n];
    }
    return rotatedDirections;
  }

  private int calcSize (A2Direction dir1, A2Direction dir2) {
    int max = 0;
    int min = 0;
    int position = 0;
    for (A2Direction direction : mDirections) {
      if (direction == dir1) {
        position++;
        max = Math.max(max, position);
      } else if (direction == dir2) {
        position--;
        min = Math.min(min, position);
      }
    }
    return max - min;
  }

  private int[] calcIntersections () {
    MyHashTable<Coordinate> coords = new MyHashTable<>(.5);

    int hPos = 0;
    int vPos = 0;
    Coordinate coord;
    SimpleIntList intersectionIndices = new SimpleIntList();

    int index = 0;
    for(A2Direction direction : mDirections) {
      if(direction == A2Direction.UP)
        vPos ++;
      else if(direction == A2Direction.DOWN)
        vPos --;
      else if(direction == A2Direction.LEFT)
        hPos --;
      else if(direction == A2Direction.RIGHT)
        hPos ++;

      coord = new Coordinate(hPos, vPos);
      if (coords.contains(coord)) {
        intersectionIndices.add(index);
      }
      coords.insert(coord);
      index ++;
    }

    return intersectionIndices.toArray();
  }

  class Coordinate {
    Integer x;
    Integer y;

    Coordinate (int _x, int _y) {
      x = _x;
      y = _y;
    }

    @Override
    public int hashCode () {
      return x.hashCode() + y.hashCode();
    }

    @Override
    public boolean equals (Object o) {
      if(o instanceof Coordinate) {
        Coordinate otherCoord = (Coordinate) o;
        return x == otherCoord.x && y == otherCoord.y;
      }
      return false;
    }

    @Override
    public String toString() {
      return x + ", " + y;
    }
  }
}

class SimpleIntList {
  private int[] list = new int[10];
  private int size = 0;

  public int size () { return size; }

  private void resize() {
    int[] tempList = list;
    list = new int[list.length * 2];
    
    for (int i = 0; i < tempList.length; i++) {
      list[i] = tempList[i];
    }
  }
  
  public void add(int n) {
    if(size == list.length) {
      resize();
    }
    list[size] = n;
    size ++;
  }

  public int[] toArray() {
    int[] copy = new int[size];
    for (int i = 0; i < size; i++) {
      copy[i] = list[i];
    }
    return copy;
  }
}