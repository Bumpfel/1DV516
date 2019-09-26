import assignment1AADS.A1Tree;

public class MyIntegerBST implements A1Tree {
	private BinaryNode<Integer> root;

	public static void main (String[] args) {
		// System.out.print("test");
		MyIntegerBST tree = new MyIntegerBST();

		tree.insert(10);
		tree.insert(11);
		tree.insert(12);
		tree.insert(9);
	}
	
	public void insert (Integer value) {
		insert(value, root);
	}

	private void insert (final Integer value, BinaryNode<Integer> node) {
		if (node == null) {
			root = new BinaryNode(value);
			System.out.println(value + " inserted as root");
		} else {
			BinaryNode newNode = new BinaryNode<>(value);
			
			if (value < Integer.parseInt(root.element.toString())) {
				if (node.left == null) {
					node.left = newNode;
					System.out.println(value + " inserted as left of " + node.element);
				} else {
					insert(value, node.left);
				}
			} else {
				if(node.right == null) {
					node.right = newNode;
					System.out.println(value + " inserted as right of " + node.element);
				} else {
					insert(value, node.right);
				}
			}
		}
	}
	
	public Integer mostSimilarValue (Integer value) {
		
		return -1;
	}
	
	public void printByLevels () {
		
	}
	
}

class BinaryNode<T> {
	T element;
	BinaryNode<T> left;
	BinaryNode<T> right;
	
	BinaryNode(T value) {
		element = value;
	}
}