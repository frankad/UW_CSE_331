package hw8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hw5.DirectGraph;
import hw5.GraphEdges;
import hw7.MarvelPaths2;

/**
 *  The class PathFindingModel represents the model to find the path/route
 *  using the campus  building and campus path data. It handles the data and 
 *  contains the major logic and computation of the program. It finds the 
 *  shortest walking route between buildings using Dijkstra's algorithm to
 *  in the graph. The edge weights represent the physical length of the 
 *  route segment. 
 *  
 *  @specfield nameShortToFull: Map<String, String> 
 *  		building's short name associated with its full name
 *  @specfield shortNameToCoord
 *  		building's short name associated with its coordination/location
 *  @specfield dircGraph,  a direct graph with edge weight a physical length of
 *         the possible route with the associated distance on campus.
 *  @specfield pathNodeToCoord
 *  		building location associated with its coordinate.
 */
public class PathFindingModel {
	
	// Represntation invariant(RI):
	//			nameShortToFull != null and shortNameToCoord != null
	//			pathNodeToCoord != null and dircGraph != null
	//          In each case the key and value should not be null.
	//
	// Abstract Function (AF):
	//	       AF = this is a PathFindingModel model such that
	//		   model.buuilding shortNameToFull = this.nameShortToFull
	//		   mmodel.building shortNameToCoordinate = this.shortNameToCoord
	//         model.buildingLocationToCoordinate = this.pathNodeToCoord
	//		   model.dircGraph = this.dircGraph
	
	// A map thats maps building abbreviated name to its full name
	private Map<String, String> nameShortToFull;
	
	// A map that maps building abbreviated name to its coordination.
	private Map<String, PointCoordinate> shortNameToCoord;
	
	// A map that hold building location to its coordinates
	private Map<String, PointCoordinate> pathNodeToCoord;
	
	// graph that holds all the paths and its associated distance.
	// the building is node and the edge weight is physical length.
	private DirectGraph<String, Double> dircGraph;  
	
	/**
	 * Construct the PathFindingModel, the campus graph with edge weight is 
	 * physical length.
	 * 
	 * @param buildingDataFile the building file that has data of campus buildings.
	 * @param pathDataFile the path data file that has data of campus path.
	 * @requires buildingDataFile != null and pathDataFile != null
	 * @requires the buildingDataFile and pathDataFile file should be well-formatted.
	 * @effects  
	 * @throws Exception if the format of the files do not well-formatted.
	 */
	public PathFindingModel(String buildingDataFile, String pathDataFile) throws Exception {
		// verify if the building or paths data file passed is not null 
		if(buildingDataFile == null) {
			throw new IllegalArgumentException
			("building data file should not be null"); 
		} 
		
		if(pathDataFile == null) { 
			throw new IllegalArgumentException
			("paths data fiel should not be null");   
		}
		
		// instantiate the variables 
		nameShortToFull = new HashMap<String, String>(); 
		shortNameToCoord = new HashMap<String, PointCoordinate>(); 
		pathNodeToCoord = new HashMap<String, PointCoordinate>();
		dircGraph = new DirectGraph<String, Double>();  
	    //	
		CampusDataParser.buildingDataParser(buildingDataFile, nameShortToFull, shortNameToCoord);
		CampusDataParser.pathDataParser(pathDataFile, pathNodeToCoord, dircGraph);
		checkRep();
	}
	
	/**
	 * It returns a map that has building abbreviated/short name
	 * as key and full name as a value, shortName --> FullName.
	 *  
	 * @return a map contains buildings abbreviated/short name 
	 * 			to its full name
	 */
	public Map<String, String> getBuildingShortToFullName() { 
		checkRep(); 
		return new HashMap<String,String>(nameShortToFull);  
	}
	
	/**
	 * It returns a map that has buildings abbreviated/short name to
	 * its coordinates. shortName--> x,y
	 * 
	 * @return a map that contain buildings abbreviated/short name
	 * 		  to its coordinate.
	 */	
	public Map<String, PointCoordinate> getShortNameToCoordinate() {
		checkRep(); 
		return new HashMap<String, PointCoordinate>(shortNameToCoord); 
	}
	
	/**
	 * It return the coordination of the given building/node location, "x,y"
	 * @param location the location of the building in the campus map/graph,
	 * 		 it is a string form of the pixel/point coordination.
	 * @return the coordination of the given building location in the
	 * 		   campus. 
	 */
	public PointCoordinate getCoordOfBuilding(String location) {
		checkRep(); 
		if(location.equals(null)) { 
			throw new IllegalArgumentException
			("the building name should not be null"); 
		}
		if(!pathNodeToCoord.containsKey(location)) {
			throw new IllegalArgumentException
			("the building name should be in the graph");
		}
		return pathNodeToCoord.get(location);	 	
	}
	
	/**
	 * It finding the shortest walking route between two buildings. This 
	 * is done by using Dijkstra's algorithm to find a shortest path in  
	 * the graph, where edge weights represent the physical length of a
	 * route segment. 
	 * 
	 * @param start the start building/node to find the shortes path
	 * @param end the end/destination building/node to find the path
	 * @return the shortest walking route between two buildings, other  
	 *         wise it return null if there is no path exists from 
	 *         start to end building.    
	 */
	public List<GraphEdges<String, Double>> findShortestRoute (String start, 
			String end) { 
		checkRep();
		return MarvelPaths2.findMinimumCostPath(dircGraph, start, end); 
	}
	
	/**
	 * Returns the full name of the building when the abbreviated 
	 * name of the building passed.
	 * @param shortName the abbreviated name of the building in side the campus
	 * @requires shortName != null and buildingName must contain inside the 
	 * 			campus (nameShortToFull)
	 * @return the full name of the building that found inside the campus
	 */
	public String getBuildingFullName(String shortName) {
		checkRep();
		return nameShortToFull.get(shortName);  
	}
	
	/**
	 * Check if the representation of the invariant holds.
	 */
	private void checkRep(){
		assert (shortNameToCoord != null): "map shortNameToCoord"
				+ " should not be null";
		assert (pathNodeToCoord != null):  "map pathNodeToCoord "
				+ "should not be null"; 
		assert (nameShortToFull != null):  "map nameShortToFull "
				+ "should not be null";
		assert (dircGraph != null): "the graph should not be null";
	}
}
