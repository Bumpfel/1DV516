package tests;



import assignment2AADS.assignment2.A2HashTable;
import assignment2AADS.assignment2.MyHashTable;

class TestHashTable {

    private static final int NUM_INSERTIONS = 10000;
    private static final double MAX_LOAD = 0.75;



    private MyHashTable<Integer> createTable() {
	return new MyHashTable<Integer>(MAX_LOAD);
    }
    
    
    
    public void testInsertSimple() {
	A2HashTable<Integer> table = createTable();
	
	for(int i=0; i<NUM_INSERTIONS; i++) {
	    table.insert(i);
	}
	
    }   
}
