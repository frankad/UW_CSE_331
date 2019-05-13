package hw5.test;

import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import hw4.test.SpecificationTests;
import hw5.GraphEdges;

/** 
 * @author Fenathun reta
 * This class has a set of test methids for different cases to test the
 * class GraphEdges implementation.
 */
public final class GraphEdgesTest {
	
	private GraphEdges <String, String> eg1,eg2,eg3,eg4, eg5, eg6; 
	
	@Before
	public void setUp(){		
		
		eg1=new GraphEdges <String, String> ("n1", "e1");
		eg2=new GraphEdges <String, String> ("n2", "e2");
		eg3=new GraphEdges <String, String> ("n3", "e3");
		eg4=new GraphEdges <String, String> ("n4", "e4");
		eg5=new GraphEdges <String, String> ("n4", "e5");
		eg6=new GraphEdges <String, String> ("n4","e5");
					
	}
	/** 
	 * checks that Java asserts are enabled, and exits if not
	 */
	 @Before
	 public void testAssertsEnabled() {
		 SpecificationTests.checkAssertsEnabled();
	 }
	
	/*Test e getEndNode()  method*/
	@Test
	public void testGetEndNode() { 
	   assertEquals("n1", eg1.getEndNode());
	   assertEquals("n2", eg2.getEndNode());
	   assertEquals("n3", eg3.getEndNode());
	   assertEquals("n4", eg4.getEndNode());
	   assertEquals("n4", eg5.getEndNode());
	}
	
	/*Test getEdgeLable() method */
	@Test
	public void testGetEdge() {
	   assertEquals("e1",eg1.getEdgeLable());
	   assertEquals("e2",eg2.getEdgeLable());
	   assertEquals("e3",eg3.getEdgeLable());
	   assertEquals("e4",eg4.getEdgeLable());
	   assertEquals("e5",eg5.getEdgeLable());
	}
	
	/*Test toString() method */
	@Test
	public void testToString() {
	   assertEquals("n1(e1)",eg1.toString());
	   assertEquals("n2(e2)",eg2.toString());
	   assertEquals("n3(e3)",eg3.toString());
	   assertEquals("n4(e4)",eg4.toString());
	   assertEquals("n4(e5)",eg5.toString());   
	}
	
	/*Test equals() method */
	@Test
	public void testEqualGraphEdges() {
		assertFalse(eg1.equals(eg2)); 
		assertTrue(eg1.equals(eg1));
		assertTrue(eg2.equals(eg2));
		assertTrue(eg3.equals(eg3));
		assertFalse(eg4.equals("eg5"));
	}
	
	/*Test the hashCode() method*/
	@Test
	public void testHashCode() {
		assertEquals(eg1.hashCode(), eg1.hashCode());
		assertEquals(eg3.hashCode(), eg3.hashCode());
		assertEquals(eg5.hashCode(), eg6.hashCode());
		assertEquals(eg5.hashCode(), eg5.hashCode());		
	}
	
	/*Test the compareTo() method */
	@Test
	public void testComparteTo() { 
		assertTrue(eg1.compareTo(eg2) < 0);
		assertTrue(eg4.compareTo(eg5) < 0);
		assertTrue(eg1.compareTo(eg1) == 0);
		assertTrue(eg3.compareTo(eg3) == 0);
		assertFalse(eg1.compareTo(eg2) > 0);
		assertTrue(eg5.compareTo(eg2) > 0);
		assertTrue(eg2.compareTo(eg3) < 0);
		assertTrue(eg4.compareTo(eg5) < 0); 
	}
}
