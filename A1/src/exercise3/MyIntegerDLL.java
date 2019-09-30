package exercise3;

import assignment1AADS.A1SequenceWithMinimum;

public class MyIntegerDLL implements A1SequenceWithMinimum {
  private Node head;
  private Node tail;

  public void insertLeft (Integer value) {
    Node n = new Node(value);

    if (tail == null) {
      tail = n;
    }
    
    if (head == null) {
      head = n;
    } else {
      head.prev = n;
      n.next = head;
      head = n;
    }
  }

  public void insertRight (Integer value) {
    Node n = new Node(value);

    if (head == null) {
      head = n;
    }

    if (tail == null) {
      tail = n;
    } else { 
      tail.next = n;
      n.prev = tail;
      tail = n;
    }
  }

  public Integer removeLeft () {
    int removeValue = head.value; 
    head = head.next;
    head.prev = null;
    return removeValue;
  }
  
	public Integer removeRight () {
    int removeValue = tail.value; 
    tail = tail.prev;
    tail.next = null;
    return removeValue;
  }
  
	public Integer findMinimum () {
    int minimum = head.value;
    Node n = head;
    while (n != null) {
      minimum = Math.min(minimum, n.value);
      n = n.next;
    }
    return minimum;
  }

  @Override
  public String toString () {
    StringBuilder str = new StringBuilder();
    str.append("{ ");
    Node n = head;
    while (n != null) {
      str.append(n.value + ", ");
      n = n.next;
    }
    str.delete(str.length() - 2, str.length());
    str.append(" }");
    return str.toString();
  }

  private class Node {
    private Node prev;
    private Node next;
    private int value;

    private Node (int _value) {
      value = _value;
    }
  }
}
