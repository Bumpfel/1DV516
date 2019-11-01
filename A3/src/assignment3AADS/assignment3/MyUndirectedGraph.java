package assignment3AADS.assignment3;

import java.util.List;

public class MyUndirectedGraph implements A3Graph {

    @Override
    public void addVertex(int vertex) {
	// TODO Auto-generated method stub

    }

    @Override
    public void addEdge(int sourceVertex, int targetVertex) {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean isConnected() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isAcyclic() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public List<List<Integer>> connectedComponents() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean hasEulerPath() {
	// TODO Auto-generated method stub
	return A3Graph.super.hasEulerPath();
    }

    @Override
    public List<Integer> eulerPath() {
	// TODO Auto-generated method stub
	return A3Graph.super.eulerPath();
    }
    
    

}
