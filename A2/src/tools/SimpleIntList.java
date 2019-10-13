package tools;

public class SimpleIntList {
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