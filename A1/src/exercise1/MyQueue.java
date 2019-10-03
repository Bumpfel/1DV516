package exercise1;

public class MyQueue<T> {
  private Node head;
  private Node tail;
  private int size;

  public T getHeadValue () {
    return head.value;
  }
  public T getTailValue () {
    return tail.value;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public boolean containsOneElement() {
    return size == 1;
  }

  public void add(T value) {
    Node newNode = new Node(value);

    if (isEmpty()) {
      head = newNode;
      tail = newNode;
    } else {
      tail.next = newNode;
      tail = newNode;
    }
    size++;
  }

  public T poll() {
    Node removedNode = head;
    if (containsOneElement()) {
      head = null;
      tail = null;
    } else {
      head = head.next;
    }
    size--;
    return removedNode.value;
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

  private class Node {
    private Node next;
    private T value;

    private Node(T _value) {
      value = _value;
    }
  }
}