package hw5.test;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set; 
import org.junit.Before;
import org.junit.Test;

import hw4.test.SpecificationTests;
import hw5.*;
/**
 * @author Fenathun reta
 * This class has a set of test methids for different cases to test the
 * class DirectGraph  implementation.
 * 
 */
@SuppressWarnings("rawtypes") 
public final class DirectGraphTest { 
	// construct a new graphs 
	private DirectGraph <String, String> dg1, dg2, dg3, dg4, dg5, dgTest, dgTest2, dgTest3; 
	// construct a new edges of the graph
	private GraphEdges ge1, ge2, ge3, ge4, ge5, ge6;  
	
	/** 
	 * checks that Java asserts are enabled, and exits if not
	 */
	 @Before
	 public void testAssertsEnabled() {
		 SpecificationTests.checkAssertsEnabled();
	 } 
	@SuppressWarnings("unchecked")  
	@Before
	public void setUp() { 
		// a graph no node and edge 
		dg1 = new DirectGraph <String, String> ();
		dgTest = new DirectGraph <String, String> ();
		// a agraph with only one node
		dg2 = new DirectGraph<String, String> (); 
		dg2.addNode("n1");
		
		// a graph with one node and zero edge, connected to itsef
		dg3 = new DirectGraph<String, String> ();
		dg3.addNode("n1");
		dg3.addEdge("n1", "n1", "e1"); 
			
		// add nodes
		dg4 = new DirectGraph<String, String> ();
		dg4.addNode("n1");
		dg4.addNode("n2");
		dg4.addNode("n3");
		dg4.addNode("n4");
		
		// add edges betweeen nodes
		dg4.addEdge("n1", "n1", "e1");
		dg4.addEdge("n1", "n2", "e2");		
		dg4.addEdge("n1", "n3", "e3");
		dg4.addEdge("n2", "n3", "e4");
		dg4.addEdge("n4", "n1", "e5");		
		dg4.addEdge("n4", "n3", "e6");		
			
		// children node with edges
		ge1 = new GraphEdges("n1", "e1");
		ge2 = new GraphEdges("n2", "e2"); 
		ge3 = new GraphEdges("n3", "e3");
		ge4 = new GraphEdges("n1", "e5");
		ge5 = new GraphEdges("n3", "e6");
		ge6 = new GraphEdges("n3", "e4");
		
		dg5 = new DirectGraph<String, String> ();
		
		// for size and children test.
		dgTest3 = new DirectGraph<String, String> ();
		dgTest3.addNode("n1");
		dgTest3.addNode("n2");
		dgTest3.addNode("n3");
		dgTest3.addNode("n4");
		dgTest3.addEdge("n1", "n2", "e1");
		dgTest3.addEdge("n1", "n3", "e3");
		dgTest3.addEdge("n1", "n4", "e2");
		dgTest3.addEdge("n2", "n4", "e4");
		dgTest3.addEdge("n3", "n4", "e5");
		dgTest3.addEdge("n3", "n3", "e6");
		dgTest3.addEdge("n2", "n2", "e7");
	}
	
	/*Test the constructor when created isEmpty() */
	@Test
	public void constructorTest() {
		DirectGraph<String, String> grp = new DirectGraph<String, String>();
		assertTrue(grp.isEmpty());
		assertTrue(dg1.isEmpty()); 
		assertTrue(dg5.isEmpty()); 
		
	} 	
	/*Test the size of the graph when constructed */
	@Test
	public void TestZeroSizeAfterConstructed() { 
		assertEquals(0, dg1.size()); 
	}
	
	/*Test addNode methods and check after adding only one node*/
	@Test
	public void addOneNodeandsize() { 
		dg1.addNode("n1");
		dg3.addNode("n1");
		assertEquals("node n1 added to the graph", 1, dg1.getAllNodes().size());
		assertEquals("node n1 added to the graph", 1, dg2.getAllNodes().size());
		assertEquals("node n1 added to the graph", 1, dg3.getAllNodes().size());
		assertFalse(dg1.isEmpty());
		assertFalse(dg2.isEmpty());
		
	}
	
	/*addition of duplicated node to the same graph does not effect the size of the graph*/
	@Test
	public void addDuplicatedNode() {
		dgTest.addNode("n1");
		dgTest.addNode("n1");
		dgTest.addNode("n1");
		dgTest.addNode("n1");
		dgTest.addNode("n1");
		assertEquals("node n1 added 5 times to the graph", 1, dgTest.getAllNodes().size());
	}
	
	/* addition of multiple nodes*/
	@Test
	public void sizeAfterAdditionOfMultipleNodes() {
		dgTest.addNode("n2");
		dgTest.addNode("n3");
		dgTest.addNode("n4");
		dgTest.addNode("n5");
		assertEquals("4 nodes are added to graph", 4, dgTest.getAllNodes().size());
		assertEquals("4 nodes are added to graph", 4, dg4.getAllNodes().size());
		assertFalse(dgTest.isEmpty());			
	}
	
	/*Test containsNode method, check if the graph has the given node*/
	@Test
	public void testContainsNodeAfterAddition() {
		dgTest.addNode("n6");
		Set<String> expectedOutput = new HashSet<String>();
		expectedOutput = dgTest.getAllNodes();
		assertEquals(expectedOutput, dgTest.getAllNodes()); 
		assertTrue(dgTest.containsNode("n6"));
		dgTest.addNode("n7");
		assertTrue(dgTest.containsNode("n7"));		
		assertFalse(dg4.containsNode("A"));  
	}
	
	/*Test checkEdgeExist between nodes, if the nodes are connected by edges*/
	@Test
	public void testEdgeExistbetweenNodes() {
		assertFalse(dg4.checkEdgeExist("n1","n6"));    
		assertTrue(dg4.checkEdgeExist("n4","n1"));
		assertFalse(dg4.checkEdgeExist("n4","n2"));
		assertTrue(dg4.checkEdgeExist("n4","n3"));
		
		// test directed ness of the edge
		assertTrue(dg4.checkEdgeExist("n1","n2"));
		assertFalse(dg4.checkEdgeExist("n2","n1"));
		assertTrue(dg4.checkEdgeExist("n1","n3"));
		assertFalse(dg4.checkEdgeExist("n3","n3")); 
		
		
		assertFalse(dg4.checkEdgeExist("n4","n2")); 
		assertFalse(dg4.checkEdgeExist("n2","n5")); 
		
		// test self edge case 
		assertTrue(dg4.checkEdgeExist("n1","n1")); 
		assertTrue(dgTest3.checkEdgeExist("n2","n2"));		
		assertTrue(dgTest3.checkEdgeExist("n3","n3"));
	}
	
	
	/*Test removeNode from the graph*/
	@Test
	public void testRemoveNodes() {
		dg1.reomevNode("n1");
		dg2.reomevNode("n1"); 
		assertTrue(dg1.isEmpty());	
	    assertTrue(dg2.isEmpty());  
	    dg1.addNode("n1"); 
	    assertFalse(dg1.isEmpty());
	    dg2.addNode("n1");
	    dg2.addNode("n2");
	    assertFalse(dg2.isEmpty());
	    dg2.reomevNode("n2");
	    assertFalse(dg2.isEmpty());
	}
	
	/*Test addEdges from the graph*/
	@Test
	public void testAddEdgesOfGraph() { 
		dg5.addNode("n1");
		dg5.addNode("n2"); 
		dg5.addEdge("n1", "n2", "e1");
		assertEquals(1, dg5.edgeLablesFromStart("n1", "n2").size());
		dg5.addEdge("n1", "n2", "e2"); 
		assertEquals(2, dg5.edgeLablesFromStart("n1", "n2").size());
		
		// test multiple edges between the same nodes with the same edge label
		dg5.addEdge("n1", "n2", "e2");
		dg5.addEdge("n1", "n2", "e2"); 
		dg5.addEdge("n1", "n2", "e2");
		assertEquals(2, dg5.edgeLablesFromStart("n1", "n2").size()); 
	}

	
	/*Test addEdge by adding dublicated edges between the same nodes */
	@Test
	public void testAddDupicatedEdges() { 
		dg5.addNode("n1"); 
		dg5.addNode("n2");
		dg5.addEdge("n1", "n2", "e1");
		dg5.addEdge("n1", "n2", "e1");
		dg5.addEdge("n1", "n2", "e1");
		assertEquals(1, dg5.edgeLablesFromStart("n1", "n2").size());
	}
	
	/*Test the edge remove method removeEdge(start, ende, edge) */
	@Test
	public void testRemovedEdges() {
		dgTest3.removeEdge("n1", "n2", "e1");
		assertFalse(dgTest3.checkEdgeExist("n1", "n2"));
		dgTest3.addEdge("n1", "n2", "e1");
		dg5.removeEdge("n1", "n2", "e2");
		assertEquals(0, dg5.edgeLablesFromStart("n1", "n2").size());
		dg5.addNode("n3");
		dg5.addNode("n4");
		dg5.addEdge("n3", "n4", "e3"); 
		assertEquals(1, dg5.edgeLablesFromStart("n3", "n4").size()); 
		
	}
	/*Test getAllNodes in the graph*/
	@Test
	public void testGetAllNodesInTheGraph() {
		dgTest2 = new DirectGraph<String, String>(); 
		assertEquals("[]",  dgTest2.getAllNodes().toString()); 
		assertEquals("[n1]", dg2.getAllNodes().toString());
		assertEquals("[n1]", dg3.getAllNodes().toString());
		assertEquals("[n1, n2, n3, n4]", dg4.getAllNodes().toString()); 
		assertEquals("[n1, n2, n3, n4]", dgTest3.getAllNodes().toString());  		
	}
	
	/*Test edgesOfNode the edge associated withe the destination node starting from some node, GraphEdges
	 * <destination node, edege>*/
	@Test
	public void testEdgesOfNodes() {
		Set <GraphEdges> expectedN1 = new HashSet <GraphEdges>();  
		expectedN1.add(ge1); 
		expectedN1.add(ge2);
		expectedN1.add(ge3);
		assertEquals(expectedN1, dg4.edgesOfNode("n1"));  
		Set <GraphEdges> expectedN4 = new HashSet <GraphEdges>();
		expectedN4.add(ge4);
		expectedN4.add(ge5);
		assertEquals(expectedN4, dg4.edgesOfNode("n4")); 
		Set <GraphEdges> expectedN3 = new HashSet <GraphEdges>();
		assertEquals(expectedN3, dg4.edgesOfNode("n3"));
		Set <GraphEdges> expectedN2 = new HashSet <GraphEdges>();
		expectedN2.add(ge6);
		assertEquals(expectedN2, dg4.edgesOfNode("n2"));
	}
	
	/*Test the lable between two nodes edgeLablesFromStart (start, end) */
	public void egdeglablesFromStartToEndNodes() {
		assertEquals(1, dg4.edgeLablesFromStart("n1", "n1").size());
		assertEquals(1, dg4.edgeLablesFromStart("n1", "n2").size());
		assertEquals(1, dg4.edgeLablesFromStart("n1", "n3").size());
		assertEquals(0, dg4.edgeLablesFromStart("n1", "n4").size());
		assertEquals(1, dg4.edgeLablesFromStart("n4", "n1").size());
		assertEquals(0, dg4.edgeLablesFromStart("n2", "n4").size());
	}
	
	/*Test the children of a given node, getChildren(Start node)*/
	@Test 
	public void testgetChildrenOfNode() {
		
		Set<String> expected = new HashSet<String>();
		expected.add("n2");
		expected.add("n3");
		expected.add("n4");
		assertEquals(expected, dgTest3.getChildren("n1")); 
	}
	
	/*Test the size of the graph size() */
	@Test
	public void testSizeOfTheGraph() {
		 assertEquals(4, dg4.size());
		 assertEquals(4, dgTest3.size());  
		 
        
	}
	
	
}
