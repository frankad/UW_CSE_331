package hw8.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import hw5.GraphEdges;
import hw5.test.CheckAsserts;
import hw8.PathFindingModel;
import hw8.PointCoordinate;

/**
 * Ths class tests the implementation of the class PathFindingModel, with 
 * different test cases. It tests the each method that are implemented in
 * PathFindingModel class.
 */
public class PathFindingModelTest { 
	
	// initialize file path and construct PathFindingModel model
	private PathFindingModel model1; 
	
	/**
	 *  checks that Java asserts are enabled, and exits if not
	 */
	@Before
	public void testAssertsEnabled() { 
		CheckAsserts.checkAssertsEnabled();
	}
	
	@Before
	public void setUp() throws Exception{ 
		model1 = new PathFindingModel("src/hw8/data/emptyCampusBuilding.dat", 
				"src/hw8/data/emptyCampusPath.dat");
	}
		
	// It test the construction of the PathFindingModel when the building data file path is 
	// and the tha path dara file passed correctly
	@Test 
	public void testConstructModelCorrectDataFilePassed() throws Exception {
		new PathFindingModel("src/hw8/data/appartmentComplexes.dat", 
				"src/hw8/data/appartmentComplexPaths.dat");
	}		
		
	// It test the construction of the PathFindingModel when the building data file is 
	// null.
	@Test(expected = IllegalArgumentException.class) 
	public void testConstructModelWithNullBuildingDataFile() throws Exception {
		new PathFindingModel(null, "src/hw8/data/appartmentComplexPaths.dat");
	}
		
	// It test the construction of the PathFindingModel when the path data file is 
	// null.
	@Test(expected = IllegalArgumentException.class) 
	public void testConstructPathFindingModelWithNullPathDataFile() throws Exception {
		new PathFindingModel("src/hw8/data/appartmentComplexes.dat", null);
	}
		
	// It test the construction of the PathFindingModel when both data file 
	// passed are null. 
	@Test(expected = IllegalArgumentException.class) 
	public void testPathFindingModelWithBothNullFile() throws Exception {
		new PathFindingModel(null, null); 
	}
	// Test the PathFindingModel construction when both the building data and the
	// path data passed are empty.
	@Test
	public void testPathFindingModelWithEmptyData() throws Exception {
		new PathFindingModel("src/hw8/data/emptyCampusBuilding.dat", 
				"src/hw8/data/emptyCampusPath.dat" );
	}
		
	// Test the getBuildingShortToFullName method when both the building data and the
	// path data passed are empty.
	@Test
	public void testGetBuildingShortToFullNamEmptyBuildings() throws Exception {
		assertEquals(new HashMap<String, String>(), model1.getBuildingShortToFullName());
	}		
		
	// Test getBuildingShortToFullName method a map that return short name to
	// full name wen empty data file passed.
	@Test
	public void testGetBuildingShortToFullName() {		
		assertEquals("{}", model1.getBuildingShortToFullName().toString());		
	}
		
	// Test getBuildingShortToFullName method using a data that has only two buildings 
	@Test
	public void testGetBuildingShortToFullNameWithTwoBuildingData() throws Exception {
		PathFindingModel model = new PathFindingModel("src/hw8/data/dataWithTwoBuilding.dat",
				"src/hw8/data/pathsWithTwoData.dat"); 
		Map<String, String> shortToFull = new HashMap <String, String>();
		shortToFull.put("BA", "Building A");
		shortToFull.put("BB", "Building B");
		assertEquals(shortToFull, model.getBuildingShortToFullName());  
	}
		
	// Test getShortNameToCoordinate method using building data file 
	// dataWithTwoBuilding and a path file pathsWithTwoData.
	@Test
	public void testGetShortNameToCoordinate() throws Exception{
		PathFindingModel model = new PathFindingModel("src/hw8/data/dataWithTwoBuilding.dat",
				"src/hw8/data/pathsWithTwoData.dat"); 
		Map<String, PointCoordinate> shortcoord = new HashMap<String, PointCoordinate>();
		shortcoord.put("BA", new PointCoordinate(2.0, 0.0));
		shortcoord.put("BB", new PointCoordinate(2.0, 6.0));
		assertEquals(shortcoord, model.getShortNameToCoordinate());  
	}
		
	// Test the getCoordOfBuilding method, it returns the coordination of
	// the building when buidling location ("x,y") is passsed. it returns
	// PointCoordinate (x,y).
	@Test
	public void testGetCoordOfBuilding() throws Exception{
		PathFindingModel model = new PathFindingModel("src/hw8/data/dataWithTwoBuilding.dat",
				"src/hw8/data/pathsWithTwoData.dat"); 
		Map<String, PointCoordinate> shortcoord = new HashMap<String, PointCoordinate>();
		shortcoord.put("2.0,0.0", new PointCoordinate(2.0, 0.0));
		shortcoord.put("2.0,6.0", new PointCoordinate(2.0, 6.0));
		assertEquals(shortcoord.get("2.0,0.0"), model.getCoordOfBuilding("2.0,0.0"));
		assertEquals(shortcoord.get("2.0,6.0"), model.getCoordOfBuilding("2.0,6.0"));
	}
		
	// Test the findShortestRoute method  with differnt cases, when there is a path
	// between buildings, when there is many way to go, and when there is no paths
	@Test 
	public void testFindShortestRoute () throws Exception {
		PathFindingModel model = new PathFindingModel("src/hw8/data/appartmentComplexes.dat", 
						"src/hw8/data/appartmentComplexPaths.dat"); 
		List<GraphEdges<String, Double>> actual = new ArrayList<GraphEdges<String, Double>>();
		actual.add(new GraphEdges <String, Double>("2.0,2.0", 0.0));
		actual.add(new GraphEdges <String, Double>("5.0,6.0", 5.0 ));
		actual.add(new GraphEdges <String, Double>("8.0,6.0", 8.0));
			
		List<GraphEdges<String, Double>> actual2 = new ArrayList<GraphEdges<String, Double>>();
		actual2.add(new GraphEdges <String, Double>("2.0,6.0", 0.0));
		actual2.add(new GraphEdges <String, Double>("5.0,6.0", 3.0));
		actual2.add(new GraphEdges <String, Double>("8.0,6.0", 6.0));
			
		List<GraphEdges<String, Double>> actual3 = new ArrayList<GraphEdges <String, Double>>();
		actual3.add(new GraphEdges <String, Double>("2.0,2.0", 0.0));
		actual3.add(new GraphEdges <String, Double>("5.0,6.0", 5.0));
		actual3.add(new GraphEdges <String, Double>("5.0,2.0", 9.0));
			
		String coordAA = "2.0,2.0";
		String coordAB = "5.0,2.0";
		String coordAC = "2.0,6.0";
		String coordAE = "8.0,6.0"; 
	
		assertEquals(actual, model.findShortestRoute(coordAA, coordAE));
		assertEquals(actual2, model.findShortestRoute(coordAC, coordAE));
		assertEquals(actual3,model.findShortestRoute(coordAA, coordAB));
		
		// when the no path exists from start to end, No path from AB to AA
		assertEquals(null, model.findShortestRoute(coordAB, coordAA));
	}
	
	// test the getBuildingFullName method by passing the building short name to
	// get full name of the building/appartment complex.
	@Test
	public void testGetFullNameOfAppartementFromShortName() throws Exception { 
		// using appartment complex data
		PathFindingModel model1 = new PathFindingModel("src/hw8/data/appartmentComplexes.dat", 
				"src/hw8/data/appartmentComplexPaths.dat");
			
		assertEquals("Appartment A", model1.getBuildingFullName("AA"));
		assertEquals("Appartment B", model1.getBuildingFullName("AB"));
		assertEquals("Appartment C", model1.getBuildingFullName("AC"));
			
		// using campus data
		PathFindingModel model2 = new PathFindingModel("src/hw8/data/campus_buildings.dat",
				"src/hw8/data/campus_paths.dat");
		assertEquals("Bagley Hall (East Entrance)", model2.getBuildingFullName("BAG"));
		assertEquals("Paul G. Allen Center for Computer Science & Engineering", 
				model2.getBuildingFullName("CSE"));
		assertEquals("Loew Hall", model2.getBuildingFullName("LOW"));
	} 
}
