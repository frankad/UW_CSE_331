package hw8.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import hw5.DirectGraph;
import hw5.test.CheckAsserts;
import hw8.CampusDataParser;
import hw8.PointCoordinate;

/**
 * Ths class tests the implementation of the class CampusDataParser, with 
 * different test cases. It tests the each method that are implemented in
 * CampusDataParser class.
 */

public class CampusDataParserTest {
	
	// short name to full name 
	private Map<String,String> nameShortToFull;
	// short name to coordinate point
	private Map<String, PointCoordinate> shortNameToCoord;
	// location to coordinate
	private Map<String, PointCoordinate> nodeToCoord;
	// node associated with edge weight/physical length
	private DirectGraph<String, Double> campusRoute;
	
	/**
	 * checks that Java asserts are enabled, and exits if not
	 */
	@Before
	public void testAssertsEnabled() {
		CheckAsserts.checkAssertsEnabled();
	}
	
	@Before
	public void setUp(){	
		nameShortToFull = new HashMap<String, String>();
		shortNameToCoord = new HashMap <String, PointCoordinate>();
		nodeToCoord = new HashMap<String, PointCoordinate>();
		campusRoute = new DirectGraph<String, Double>(); 
	}	
	
	/* Test the buildingDataParser method for the case when empty building data file is passed*/
	@Test
	public void testParseEmptyBuildingData() throws Exception {
		CampusDataParser.buildingDataParser ("src/hw8/data/emptyCampusBuilding.dat", 
				nameShortToFull, shortNameToCoord);  
		assertEquals (new HashMap<String, String>(), nameShortToFull);
		assertEquals (new HashMap<String, PointCoordinate>(), shortNameToCoord);   
	}
	
	/* Test the pathDataParser method for the case when empty data file is passed*/
	@Test
	public void testEmptyPathDataParser() throws Exception {
		CampusDataParser.pathDataParser ("src/hw8/data/emptyCampusPath.dat", 
				nodeToCoord, campusRoute);   
		assertEquals (new HashMap<String, PointCoordinate>(), nodeToCoord);  
		assertTrue(campusRoute.isEmpty());
		assertEquals(new DirectGraph<String, Double>(), campusRoute);
	}
	
	/* Test the buildingDataParser method for the case only two buildings inside
	 * the data file passed, dataWithTwoBuilding.dat */
	@Test
	public void testBuildingDataParserTwoBuildingData() throws Exception {
		CampusDataParser.buildingDataParser("src/hw8/data/dataWithTwoBuilding.dat",  
				nameShortToFull, shortNameToCoord);
		Map<String, String> names = new HashMap<String, String>();
		Map<String, PointCoordinate> nameToCoord = new HashMap<String, PointCoordinate>();
		names.put("BA",	"Building A");
		names.put("BB",	"Building B");
		nameToCoord.put("BA",	new PointCoordinate(2.0, 0.0));
		nameToCoord.put("BB",	new PointCoordinate(2.0, 6.0));
		assertEquals(names, nameShortToFull);
		assertEquals(nameToCoord, shortNameToCoord); 
	}
	
	/* Test the pathDataParser method for the case only two buildings inside
	 * the path file passed, pathsWithTwoData.dat*/
	@Test
	public void testpathDataParserTwoBuildingData() throws Exception {
		CampusDataParser.pathDataParser("src/hw8/data/pathsWithTwoData.dat", 
				nodeToCoord, campusRoute);
		Map<String, PointCoordinate> locationTocoord =  
				new HashMap<String, PointCoordinate>();
		DirectGraph<String, Double> pathGraph = new DirectGraph<String, Double>();		
		locationTocoord.put("2.0,0.0", new PointCoordinate(2.0, 0.0));
		locationTocoord.put("2.0,6.0", new PointCoordinate(2.0, 6.0));
		pathGraph.addNode("2.0,0.0");
		pathGraph.addNode("2.0,6.0");
		pathGraph.addEdge("2.0,0.0", "2.0,6.0", 6.0);
		pathGraph.addEdge("2.0,6.0", "2.0,0.0", 6.0);
		assertEquals(locationTocoord, nodeToCoord); 
		assertEquals(pathGraph.getAllNodes(), campusRoute.getAllNodes()); 		
		
		//String  path = pathGraph.toString().trim().replaceAll("\\r\\n", "\n");
		//path = path.replaceAll("\\r", "\n");
		//String cpath = campusRoute.toString().trim().replaceAll("\\r\\n", "\n");
		//cpath = cpath.replaceAll("\\r", "\n");
		
		assertEquals(pathGraph, campusRoute); 
	}
	
	/* Test the buildingDataParser method for the case when the passed data file has 
	 * a comment line inside the data, the comment starts with "#".
	 */
	@Test
	public void testbuildingDataParserHasComments() throws Exception {
		CampusDataParser.buildingDataParser( 
				"src/hw8/data/buildDataWithComments.dat", 
				nameShortToFull, shortNameToCoord);
		Map<String, String> names = new HashMap<String, String>();
		Map<String, PointCoordinate> nameToCoord = new HashMap<String, PointCoordinate>();
		names.put("GB",	"Garage GB");
		names.put("PB",	"Parking PB");
		names.put("MB",	"Market MB");
		nameToCoord.put("GB",	new PointCoordinate(4.0, 8.0));
		nameToCoord.put("PB",	new PointCoordinate(6.0, 12.0));
		nameToCoord.put("MB",	new PointCoordinate(8.0, 16.0)); 
		assertEquals(names, nameShortToFull);
		assertEquals(nameToCoord, shortNameToCoord);  
	}
	
	/* Test the pathDataParser method for the case when the passed data file 
	 * has a comment line inside the data, the comment starts with "#".
	 */
	@Test
	public void testPathDataParserHasComments() throws Exception {
		CampusDataParser.pathDataParser( "src/hw8/data/pathDataWithComments.dat", 
				nodeToCoord, campusRoute); 
		Map<String, PointCoordinate> locationTocoord =  
				new HashMap<String, PointCoordinate>();
		DirectGraph<String, Double> pathGraph = new DirectGraph<String, Double>();	
		locationTocoord.put("2.0,2.0", new PointCoordinate(2.0,2.0));
		locationTocoord.put("2.0,6.0", new PointCoordinate(2.0,6.0));
		locationTocoord.put("5.0,6.0", new PointCoordinate(5.0,6.0));
		pathGraph.addNode("2.0,2.0");
		pathGraph.addNode("2.0,6.0");
		pathGraph.addNode("5.0,6.0"); 
		
		pathGraph.addEdge("2.0,2.0", "2.0,6.0", 4.0);
		pathGraph.addEdge("2.0,6.0", "2.0,2.0", 4.0);
		pathGraph.addEdge("2.0,6.0", "5.0,6.0", 3.0);
		pathGraph.addEdge("2.0,2.0", "5.0,6.0", 5.0);
		pathGraph.addEdge("5.0,6.0", "2.0,2.0", 5.0);
		assertEquals(locationTocoord, nodeToCoord); 
		assertEquals(pathGraph.getAllNodes(), campusRoute.getAllNodes());
		assertEquals(pathGraph, campusRoute);
	}
	
	/* Test the buildingDataParser method for the case with five buildings 
	 * inside the data file passed, appartmentComplexes.dat.
	 */
	@Test
	public void testBuildingDataParserAptComplex() throws Exception {
		CampusDataParser.buildingDataParser(
				"src/hw8/data/appartmentComplexes.dat", 
				nameShortToFull, shortNameToCoord);
		Map<String, String> names =  
				new HashMap<String, String>();
		Map<String, PointCoordinate> nameToCoord =
				new HashMap<String, PointCoordinate>();
		names.put("AA",	"Appartment A");
		names.put("AB",	"Appartment B");
		names.put("AC",	"Appartment C");
		names.put("AD",	"Appartment D");
		names.put("AE",	"Appartment E");
		nameToCoord.put("AA", new PointCoordinate(2.0, 2.0));
		nameToCoord.put("AB", new PointCoordinate(5.0, 2.0));
		nameToCoord.put("AC", new PointCoordinate(2.0, 6.0));
		nameToCoord.put("AD", new PointCoordinate(5.0, 6.0));
		nameToCoord.put("AE", new PointCoordinate(8.0, 6.0));
		assertEquals(names, nameShortToFull);
		assertEquals(nameToCoord, shortNameToCoord); 
	}
	/* Test the pathDataParser method for the case with five paths
	 * inside the data file passed, appartmentComplexPaths.dat */	
	@Test
	public void testPathDataParserAptComplex() throws Exception {
		CampusDataParser.pathDataParser( "src/hw8/data/appartmentComplexPaths.dat", 
				nodeToCoord, campusRoute); 
		Map<String, PointCoordinate> locationTocoord =  
				new HashMap<String, PointCoordinate>();
		DirectGraph<String, Double> pathGraph = new DirectGraph<String, Double>();
		locationTocoord.put("2.0,2.0", new PointCoordinate(2.0, 2.0));
		locationTocoord.put("5.0,2.0", new PointCoordinate(5.0, 2.0));
		locationTocoord.put("2.0,6.0", new PointCoordinate(2.0, 6.0));
		locationTocoord.put("5.0,6.0", new PointCoordinate(5.0, 6.0));
		locationTocoord.put("8.0,6.0", new PointCoordinate(8.0, 6.0));
		
		pathGraph.addNode("2.0,2.0");
		pathGraph.addNode("5.0,2.0");
		pathGraph.addNode("2.0,6.0");
		pathGraph.addNode("5.0,6.0");
		pathGraph.addNode("8.0,6.0");
		
		pathGraph.addEdge("2.0,2.0", "2.0,6.0", 4.0);
		pathGraph.addEdge("2.0,2.0", "5.0,6.0", 5.0);
		pathGraph.addEdge("2.0,2.0", "8.0,6.0", 12.0);
		
		pathGraph.addEdge("2.0,6.0", "5.0,6.0", 3.0);	
		pathGraph.addEdge("2.0,6.0", "5.0,2.0", 5.0);	
		pathGraph.addEdge("2.0,6.0", "2.0,2.0", 4.0);
		
		pathGraph.addEdge("5.0,6.0", "8.0,6.0", 3.0);
		pathGraph.addEdge("5.0,6.0", "5.0,2.0", 4.0);
		pathGraph.addEdge("5.0,6.0", "2.0,2.0", 5.0);
		
		pathGraph.addEdge("5.0,2.0", "8.0,6.0", 5.0);
		
		assertEquals(locationTocoord, nodeToCoord);
		assertEquals(pathGraph.getAllNodes(), campusRoute.getAllNodes());
		assertEquals(pathGraph, campusRoute);
	}
}


