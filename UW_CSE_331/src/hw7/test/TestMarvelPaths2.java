package hw7.test;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Before;
import org.junit.Test;

import hw5.DirectGraph;
import hw5.GraphEdges;
import hw7.MarvelPaths2;

/**
 * 
 * @author Fentahun Reta
 * This class has a set of test methids for different cases to test the
 * class MarvelPaths2  implementation.
 *
 */
public class TestMarvelPaths2 {
	//private static final int TIMEOUT = 2000;
	private DirectGraph<String, Double> testGraph; 

	@Before
	public void setUp() throws Exception {
		testGraph = new DirectGraph<String, Double> (); 
		testGraph.addNode("A");
		testGraph.addNode("B");
		testGraph.addNode("C");
		testGraph.addNode("D"); 
		testGraph.addNode("E");
		testGraph.addNode("F");
		testGraph.addEdge("A", "B", 0.5);
		testGraph.addEdge("B", "C", 0.6);
		testGraph.addEdge("C", "D", 0.5);
		testGraph.addEdge("D", "E", 1.4);
		testGraph.addEdge("E", "F", 1.5);
		testGraph.addEdge("A", "D", 1.9);
		testGraph.addEdge("A", "F", 2.2);
	}
	
	/* TestThrowException for findMinimumCostPath method
	 * Test node that passed to find the minimum cost path between two nodes,
	 * but the starting node "X" is not part of the graph.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testStartNodeNotInsideGraph() {
		MarvelPaths2.findMinimumCostPath(testGraph,"X","A");		
	}
	
	/* TestThrowException for findMinimumCostPath method
	 * Test node that passed to find the minimum cost path between two nodes,
	 * but the ending node "X" is not part of the graph  
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEndNodeNotInsideGraph() {
		MarvelPaths2.findMinimumCostPath(testGraph,"A","X");
	}
	
	/* TestThrowException for findMinimumCostPath method
	 * Test node that passed to find the minimum cost path between two nodes,
	 * but both nodes (X and Y) are not part of the graph  
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAllNodesNotInsideGraph(){
		MarvelPaths2.findMinimumCostPath(testGraph,"X","Y");
	} 
	
	/** test the graphBuilding() method 
	 * It test the method to build a graph from the given file marvelPath2Test.stv
	 * @throws Exception 
	 */
	@Test
	public void testGraphBuilding() throws Exception {
		testGraph = MarvelPaths2.graphBuilding("src/hw7/data/marvelPaths2Test.tsv");
		Set<String> setOfNodes = testGraph.getAllNodes(); 
		Set<String> actual = new TreeSet<String>();
		for(String nd: setOfNodes) {
			actual.add(nd);
		}
		Set<String> expected = new TreeSet<String>(); 
		expected.add("A");
		expected.add("B");
		expected.add("C");
		expected.add("D");
		assertEquals(expected,actual);
	}
	
	/*TestfindMinimumCostPath
	 * It test the method to find the minimum cost path, findMinimumCostPath()
	 */
	@Test
	public void testfindMinimumCostPath() {
		List<GraphEdges<String, Double>> actual = new ArrayList<GraphEdges<String, Double>>();
		actual.add(new GraphEdges <String, Double>("A", 0.0));
		actual.add(new GraphEdges <String, Double>("B", 0.5));
		actual.add(new GraphEdges <String, Double>("C", 1.1));
		actual.add(new GraphEdges <String, Double>("D", 1.6));
		assertEquals(actual,MarvelPaths2.findMinimumCostPath(testGraph, "A", "D"));
		
	}
	
	/* test when there is no path betweeen two nodes in the graph*/
	@Test
	public void testNoPathBetweenTwoNodes(){ 
		testGraph.addNode("G");
		testGraph.addNode("H");
		assertEquals(null, MarvelPaths2.findMinimumCostPath(testGraph, "G", "H"));  
	}
	
	/* it test the minimum cost to move from one node to an other. So, there might be an other
	 * costly path label, but it choose the minimum cost or the cheapest cost.
	 */
	@Test
	public void testToChooseTheMinimumCostPathLabel() { 
		testGraph.addEdge("A", "B", 3.0);
		testGraph.addEdge("B", "C", 2.0);
		List<GraphEdges<String, Double>> paths = MarvelPaths2.findMinimumCostPath(testGraph, "A", "E");		
		double expectedCost = 3.0; 
		double actualCost = 0.0;
		for (GraphEdges<String, Double> eg : paths) {
			actualCost = eg.getEdgeLable(); 
		} 
		assertTrue(expectedCost == actualCost); 
		
	}

}
