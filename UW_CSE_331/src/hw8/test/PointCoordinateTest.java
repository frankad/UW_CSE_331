package hw8.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hw5.test.CheckAsserts;
import hw8.PointCoordinate;

/**
 * The class tests the implementation of the class PointCoordinate, with 
 * different test cases. It tests the each method that are implemented in
 * PointCoordinate class.
 */
public class PointCoordinateTest {
	
	// point/pixel coordinates
	PointCoordinate pCoord1,pCoord2,pCoord3; 
	
	// epsilon value to check the precision, the amount of error to
	// allow when checking for equality of two doubles
	public static final double epsilon = 0.000000001;
	
	/**
	 * checks that Java asserts are enabled, and exits if not
	 */
	@Before
	public void testAssertsEnabled() {
		CheckAsserts.checkAssertsEnabled();
	}
	 
	@Before
	public void setUp(){					
		pCoord1 = new PointCoordinate(1903.7201, 1952.4322);
		pCoord2 = new PointCoordinate(1906.1864, 1939.0633);
		pCoord3 = new PointCoordinate(1897.9472, 1960.0194);			
	}	
	
	// Test the getX() method 
	@Test
	public void testGetX() {
		assertTrue (Math.abs(pCoord1.getX() - 1903.7201) < epsilon);
		assertTrue (Math.abs(pCoord2.getX() - 1906.1864) < epsilon);
	}
	
	// Test the getY() method
	@Test
	public void testGetY() {
		assertTrue (Math.abs(pCoord1.getY() - 1952.4322) < epsilon);
		assertTrue (Math.abs(pCoord2.getY() - 1939.0633) < epsilon);
	}
	
	// Test eqauls method with null, other point coordinate, and non point
	// coordinate object
	@Test
	public void testEqauls() {  
		assertFalse(pCoord1.equals(null));
		assertFalse(pCoord2.equals(null));
		assertFalse(pCoord1.equals("(abcd)"));
		assertFalse(pCoord1.equals(new PointCoordinate(1906.1864, 1939.0633)));
		assertTrue(pCoord1.equals(new PointCoordinate(1903.7201, 1952.4322)));
	} 
	
	// Test hashCode method
	@Test
	public void testHashCode() {
		assertEquals(pCoord1.hashCode(), (new PointCoordinate(1903.7201, 1952.4322)).hashCode());
	}
	
	// Test toString method
	@Test
	public void testToString() {
		assertEquals(pCoord1.toString(), "(1903.7201,1952.4322)");
		assertEquals(pCoord2.toString(), "(1906.1864,1939.0633)");	
	}
	
	// Test the getDirection method
	@Test
	public void testGetDirection() {
		assertEquals(pCoord1.getDirection(pCoord2), "N");
		assertEquals(pCoord1.getDirection(pCoord3), "SW");
		assertEquals(pCoord2.getDirection(pCoord3), "S");
	}	
}
