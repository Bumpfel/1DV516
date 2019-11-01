package assignment3AADS.assignment3.generic.tests;


import assignment3AADS.assignment3.generic.MySocialNetwork;

public class TestSocialNetwork {

    private static final String[] names = {"Alice","Bob", "Carol", "David", "Eve", "Grace", "Heidi", "Ivan", "Mallory", "Mark", "Neo", "Trent",};
    private MySocialNetwork<String> createSocialNetwork(){
	return new MySocialNetwork<String>();
    }
    

     
     
     public void testInsertSimple() {
	 MySocialNetwork<String> network = createSocialNetwork();
 	
 	for(int i=0; i<names.length; i++) {
 	   network.addVertex(names[i]);
 	}
 	
 	//Create circle of friends
 	for(int i=0; i<names.length-1; i++) {
  	   network.addEdge(names[i],  names[i+1]);
  	}
 	network.addEdge(names[names.length], names[0]);
 	
 	network.numberOfPeopleAtFriendshipDistance(names[0], 2);
 	
 	
     }   
    
}
