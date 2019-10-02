package exercise3;

import assignment1AADS.A1SequenceWithMinimum;

public class MyIntegerDLL implements A1SequenceWithMinimum {
  private Node head;
  private Node tail;
  private Node minimumNode;
  private boolean alwaysRunSearch = false;

  public void setLegacyMode(boolean enabled) { alwaysRunSearch = enabled; }

  public boolean isEmpty () {
    return head == null;
  }

  public void insertLeft (Integer value) {
    Node newNode = new Node(value);
    setMinimum(newNode);
    
    if (isEmpty()) {
      firstInsertion(newNode);
    } else {
      head.prev = newNode;
      newNode.next = head;
      head = newNode;
    }
  }

  public void insertRight (Integer value) {
    Node newNode = new Node(value);
    setMinimum(newNode);

    if (isEmpty()) {
      firstInsertion(newNode);
    } else {
      tail.next = newNode;
      newNode.prev = tail;
      tail = newNode;
    }
  }

  public Integer removeLeft () {
    Node removedNode = head;
    if(head.next == null) {
      lastRemoval();
    } else {
      head = head.next;
      head.prev = null;
    }
    decideMinimumNode(removedNode);
    return removedNode.value;
  }
  
	public Integer removeRight () {
    Node removedNode = tail;
    if(tail.prev == null) {
      lastRemoval();
    } else {
      tail = tail.prev;
      tail.next = null;
    }
    decideMinimumNode(removedNode);
    return removedNode.value;
  }

  public Integer findMinimum () {
    if (alwaysRunSearch)
      return findNewMinimum().value;
    return minimumNode.value;
  }

  private int findNewMinCount = 0;
  public int getFindNewMinCount() { return findNewMinCount; }

	private Node findNewMinimum () {
    findNewMinCount++;
    Node smallestFound = head;
    Node n = head;
    while (n != null) {
      if (n.value < smallestFound.value) {
        smallestFound = n;
      }
      n = n.next;
    }
    return smallestFound;
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
    if (!isEmpty()) {
      str.delete(str.length() - 2, str.length());
    }
    str.append(" }");
    return str.toString();
  }

  // private methods that reduce code duplication
  
  private void setMinimum (Node newNode) {
    if(minimumNode == null || newNode.value < minimumNode.value) {
      minimumNode = newNode;
    }
  }

  private void firstInsertion(Node node) {
    head = node;
    tail = node;
  }

  private void lastRemoval() {
    head = null;
    tail = null;
  }

  private void decideMinimumNode (Node removedNode) {
    if (removedNode == minimumNode && !alwaysRunSearch) {
      minimumNode = findNewMinimum();
    }
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
