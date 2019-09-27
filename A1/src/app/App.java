package app;

import exercise1.MyIntegerBST;

public class App {
    public static void main(String[] args) throws Exception {
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
}