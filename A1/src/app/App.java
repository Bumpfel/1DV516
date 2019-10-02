package app;

import java.util.Random;

import exercise3.MyIntegerDLL;

public class App {
    public static void main(String[] args) throws Exception {
        testPerformance(true);
        System.out.println("---------------------");
        testPerformance(false);
        // testLoadPerformance();
    }
    
    private static void testPerformance (boolean legacyMode) {
        System.out.println("## Running performance test with legacy mode " + (legacyMode ? "on" : "off") + " ##");

        MyIntegerDLL adt = new MyIntegerDLL();
        adt.setLegacyMode(legacyMode);
        int insertions = 10000;

        Random rand = new Random();
        for(int i = 0; i < insertions; i++) {
            adt.insertLeft(rand.nextInt(insertions));
        }
        
        long timestamp, timeTaken = 0;
        while(!adt.isEmpty()) {
            timestamp = System.currentTimeMillis();
            adt.findMinimum();
            timeTaken += System.currentTimeMillis() - timestamp;
            adt.removeRight();
        }
        System.out.println("findMinimum() time: " + timeTaken + " ms");
        System.out.println("find newMinimum() ran " + adt.getFindNewMinCount() + " times");
    }


    private static void testLoadPerformance () {
        MyIntegerDLL tree = new MyIntegerDLL();
        int max = 10000;
        int findOperations = Math.min(10000, max);

        Random rand = new Random();
        for(int i = 0; i < max; i++) {
            tree.insertLeft(rand.nextInt(max));
        }
        
        int temp;
        long timestamp, timeTaken = 0;
        for(int i = 0; i < findOperations; i++) {
            timestamp = System.currentTimeMillis();
            tree.findMinimum();
            timeTaken += System.currentTimeMillis() - timestamp;
            temp = tree.removeRight();
            tree.insertRight(temp);
        }
        System.out.println("findMinimum() time: " + timeTaken + " ms");
        System.out.println("find newMinimum() ran " + tree.getFindNewMinCount() + " times");
    }
}