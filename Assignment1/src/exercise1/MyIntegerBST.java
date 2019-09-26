import assignment1AADS.A1Tree;
import jdk.nashorn.internal.ir.BinaryNode;

public class MyIntegerBST implements A1Tree {
	private BinaryNode<Integer> root;

	public static void main (String[] args) {
		MyIntegerBST tree = new MyIntegerBST();

		tree.insert(10);
		tree.insert(7);
		tree.insert(20);
		tree.insert(4);
		tree.insert(9);
		tree.insert(25);
		tree.insert(8);

		// tree.printByLevels();

		System.out.println(tree.mostSimilarValue(9));
	}

	public void insert (Integer value) {
		root = insert(value, root);
	}

	private BinaryNode<Integer> insert (final Integer value, BinaryNode<Integer> node) {
		if (node == null) {
			return new BinaryNode(value);
		}

		if (value < node.element) {
			node.left = insert(value, node.left);
		} else if (value > node.element) {
			node.right = insert(value, node.right);
		}

		return node;
	}

	public Integer mostSimilarValue (Integer value) {

		return mostSimilarTo(value, root);
	}

	private Integer mostSimilarTo(Integer value, BinaryNode<Integer> node) {
		if (value == node.element) { // TODO add base case +-1 ?
			return node.element;
		}

		BinaryNode<Integer> closestMatch;
		if (node == root) {
			closestMatch = root.element; // TODO osnyggt
		}


		int nodeComparison = Math.abs(node.element - value);
		// System.out.println(nodeComparison);
		int leftComparison = node.left != null ? Math.abs(node.left.element - value) : 999; // TODO bad
		// System.out.println(leftComparison);
		int rightComparison = node.right != null ? Math.abs(node.right.element - value) : 999;
		// System.out.println(rightComparison);
		// System.out.println("---");
		
		if (node.left != null) {
			return mostSimilarTo(value, node.left);
		}
		if (node.right != null) {
			return mostSimilarTo(value, node.right);
		}
		return closestMatch;
	}
	
	public int getTreeHeight() {
		if (root == null) {
			return 0;
		}	else {
			return root.getHeight();
		}
	}


	public void printByLevels() {
		int height = getTreeHeight();
		System.out.println(height);
		for (int i = 0; i < height; i++) {
			System.out.print("Depth " + i + ": ");
			printLevel(root, i);
			System.out.println();
		}
	} 

	private void printLevel(BinaryNode<Integer> node, int depth) {
		if (node == null) {
			return;
		}
		if (depth == 0) {
			System.out.print(node.element + " ");
		}
		else if (depth > 0) {
			printLevel(node.left, depth - 1);
			printLevel(node.right, depth - 1);
		}
	}
	
	private class BinaryNode<T> {
		private T element;
		private BinaryNode<T> left;
		private BinaryNode<T> right;
		
		private BinaryNode (T _element) {
			element = _element;
		}

		private	int getHeight () {
			int leftHeight = 0;
			int rightHeight = 0;

			if (left != null) {
				leftHeight = left.getHeight();
			}
			if (right != null) {
				rightHeight = right.getHeight();
			}

			return 1 + Math.max(leftHeight, rightHeight);
		}
	}
}
