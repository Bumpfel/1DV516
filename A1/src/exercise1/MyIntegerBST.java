package exercise1;

import assignment1AADS.A1Tree;

public class MyIntegerBST implements A1Tree {
	private BinaryNode<Integer> root;
	// for test purposes
	int[] testPrintArr = new int[7];
	private int testPointer = 0;

	public static void main(String[] args) {
		MyIntegerBST tree = new MyIntegerBST();
		tree.printByLevels();
	}

	public void insert (Integer value) {
		root = insert(value, root);
	}

	private BinaryNode<Integer> insert (final Integer value, BinaryNode<Integer> node) {
		if (node == null) {
			return new BinaryNode<Integer>(value);
		}

		if (value < node.element) {
			node.left = insert(value, node.left);
		} else if (value > node.element) {
			node.right = insert(value, node.right);
		}

		return node;
	}

	public Integer mostSimilarValue (Integer value) {
		return mostSimilarTo(value, root, null);
	}

	private Integer mostSimilarTo(final Integer value, BinaryNode<Integer> node, BinaryNode<Integer> closestMatch) {
		if(node == null) {
			return closestMatch.element;
		}

		// compare closestmatch with current node
		if (closestMatch == null || (Math.abs(closestMatch.element - value) > Math.abs(node.element - value))) {
			closestMatch = node;
		}

		if (value < node.element) {
			return mostSimilarTo(value, node.left, closestMatch); // continue searching in left node
		}
		if (value > node.element) {
			return mostSimilarTo(value, node.right, closestMatch); // continue searching in right node
		}

		return closestMatch.element; // exact match
	}

	public void printByLevels () {
		if (root == null)
			return;
		
		MyQueue<BinaryNode<Integer>> q = new MyQueue<BinaryNode<Integer>>();
		q.add(root);

		while (!q.isEmpty()) {
			int nodesOnThisLevel = q.size(); // since child nodes are added to the same queue, we must keep track of where the level ends
			StringBuilder str = new StringBuilder();

			while (nodesOnThisLevel > 0) {
				BinaryNode<Integer> node = q.poll();
				str.append(node.element + ", ");

				testPrintArr[testPointer++] = node.element;

				if (node.left != null)
					q.add(node.left);
				if (node.right != null)
					q.add(node.right);
				nodesOnThisLevel--;
			}
			if (str.length() >= 2) {
				str.setLength(str.length() - 2);
			}
			System.out.println(str);
		}
	}
	
	private class BinaryNode<T> {
		private T element;
		private BinaryNode<T> left;
		private BinaryNode<T> right;
		
		private BinaryNode (T _element) {
			element = _element;
		}
	}
}
