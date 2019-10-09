package exercise1;

import assignment1AADS.A1Tree;

public class MyIntegerBST implements A1Tree {
	private BinaryNode<Integer> root;
	// for test purposes
	int[] testPrintArr = new int[7];
	private int testPointer = 0;

	public static void main(String[] args) {
		MyIntegerBST tree = new MyIntegerBST();
		tree.insert(4);
		tree.insert(10);
		tree.insert(2);
		tree.insert(12);
		tree.insert(8);
		tree.insert(9);
		tree.printByLevels();
	}

	public void insert (Integer value) {
		root = insert(value, root);
	}

	private BinaryNode<Integer> insert (final Integer value, BinaryNode<Integer> node) {
		if (node == null) {
			BinaryNode<Integer> newNode = new BinaryNode<>(value);
			
			return newNode;
		}

		if (value < node.element) {
			node.left = insert(value, node.left);
			node.height = node.left.height + 1;
		} else if (value > node.element) {
			node.right = insert(value, node.right);
			node.height = node.right.height + 1;
		}
		
		// auto balancing
		// If this node becomes unbalanced, then there 
		// are 4 cases Left Left Case 
		if (getNodeBalance(node) > 1 && value < node.left.element) 
				return rightRotate(node);

		// Right Right Case 
		if (getNodeBalance(node) < -1 && value > node.right.element) 
				return leftRotate(node); 

		// Left Right Case 
		if (getNodeBalance(node) > 1 && value > node.left.element) { 
				node.left = leftRotate(node.left); 
				return rightRotate(node); 
		} 

		// Right Left Case
		if (getNodeBalance(node) < -1 && value < node.right.element) { 
				node.right = rightRotate(node.right); 
				return leftRotate(node); 
		}
		return node;
	}

	private int getNodeBalance (BinaryNode<Integer> node) {
		int leftHeight = node.left != null ? node.left.height + 1 : 0;
		int rightHeight = node.right != null ? node.right.height + 1 : 0;

		return leftHeight - rightHeight;
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
				str.append(node.element + "(" + node.height + "), ");

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

	private int getNodeHeight(BinaryNode<Integer> node) {
		if (node == null)
			return 0;
		return node.height;
	}
	
	private void updateChildrenHeights (BinaryNode<Integer> node) {
		node.height = Math.max(getNodeHeight(node.left), getNodeHeight(node.right)) + 1;
	}

	private BinaryNode<Integer> rightRotate(BinaryNode<Integer> y) {
		System.out.println("right rotation of " + y.element);
		BinaryNode<Integer> x = y.left; 
		BinaryNode<Integer> T2 = x.right;

		// Perform rotation 
		x.right = y; 
		y.left = T2; 

		updateChildrenHeights(x);
		updateChildrenHeights(y);

		// Return new root 
		return x; 
}

private BinaryNode<Integer> leftRotate(BinaryNode<Integer> x) {
	System.out.println("left rotation of " + x.element);
	BinaryNode<Integer> y = x.right; 
	BinaryNode<Integer> T2 = y.left; 

		// Perform rotation 
		y.left = x; 
		x.right = T2; 

		updateChildrenHeights(x);
		updateChildrenHeights(y);

		// Return new root 
		return y; 
} 
	
	private class BinaryNode<T> {
		private T element;
		private BinaryNode<T> left;
		private BinaryNode<T> right;
		private int height = 0;
		
		private BinaryNode (T _element) {
			element = _element;
		}
	}
}
