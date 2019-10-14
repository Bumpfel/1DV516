package exercise2;

import assignment2AADS.assignment2.A2Direction;
import assignment2AADS.assignment2.A2Itinerary;
import exercise1.MyHashTable;
import tools.SimpleIntList;

public class MyItinerary implements A2Itinerary<A2Direction> {

  private final A2Direction[] mDirections;
  private int[] mIntersections;
  private int mWidth = -1;
  private int mHeight = -1;
  
  public MyItinerary (A2Direction[] array) {
    mDirections = array;
  }

  @Override
  public int widthOfItinerary() { 
    if(mWidth == -1) // only calculate once
      mWidth = calcSize(A2Direction.LEFT, A2Direction.RIGHT);
    return mWidth;
  }
  
  @Override
  public int heightOfItinerary() { 
    if(mHeight == -1) // only calculate once
      mHeight = calcSize(A2Direction.UP, A2Direction.DOWN);
    return mHeight;
  }

  @Override
  public int[] getIntersections() { 
    if(mIntersections == null) // only calculate once
      mIntersections = calcIntersections();
    return mIntersections;
  }

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
    Coordinate coord = new Coordinate(hPos, vPos);
    coords.insert(coord);
    
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
