package hw8;

import java.io.*;
import java.util.Map;

import hw5.DirectGraph;
import hw6.MarvelParser.MalformedDataException;

/**
 * Parser utility to load the campus buildings and campus paths datasets.
 * 
 * Note: The code to Parse campus_buildings.dat and campus_paths.dat adapted
 * from hw6/MarvelParser.java file. 
 */
public class CampusDataParser {
	/**
	 * It reads the given campus buidlding data sets. Each line of the input
	 * data contains has four tab-separated fields such as a short name which
	 * is the abbreviated name of a building; long name which is the full name
	 * of a building; x and y coordinate of the location of the  building's
	 * entrance, example (shortName	longName	x	y). It accept two maps to be
	 * filled as parameters. 
	 *
	 * @param filename the file that will be to read, the campus building data file
	 * @param shortToFullName a map of the building's data, it maps building's short/
	 * 		  abbreviated name to full/long name. 
	 * @param shortNameToCoordinate a map of building's data, it maps building short/
	 *        abbreviated name to its locations coordinate.
	 * @requires the file(filename) should be well-formatted. Each line has four
	 * 			tab-separated fields/tokens.
	 * @modifies shortToFullName and shortNameToCoordinate
	 * @effects shortToFullName will have a map of the building short name to its full name.
	 * @effects the shortNameToCoordinate will have a map of the building short name to its
	 * 			coordinates.
	 * @throws Exception MalformedDataException if the file is not well-formed:
     *         each line contains exactly four tokens separated by three tab, or
     *         else starting with a # symbol to indicate a comment line.
	 */
	public static void buildingDataParser(String filename, Map <String,String> shortToFullName,
			Map <String, PointCoordinate> shortNameToCoordinate) throws Exception { 
		BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new FileReader(filename)); 
			 String inputLine;
		     while ((inputLine = reader.readLine()) != null) { 
		    	 
		    	// Ignore comment lines.
		        if (inputLine.startsWith("#")) {
		        	continue;
		        }
		        
		        // Parse the data, stripping out quotation marks and throwing
	            // an exception for malformed lines.
	            inputLine = inputLine.replace("\"", "");
	            String[] tokens = inputLine.split("\t");
	            if (tokens.length != 4) {
	                throw new MalformedDataException("Line should contain exactly three"
	                		+ " tabs: " + inputLine); 
	            }
	            
	            // from the current line get each tokens
	            String shortName = tokens[0];
	            String fullName = tokens[1];
	            double x = Double.parseDouble(tokens[2]);
	            double y = Double.parseDouble(tokens[3]);
	            
	            // Add the parsed data to shortToFullName that map from short to full name.
	            shortToFullName.put(shortName, fullName);
	            // Add the parsed data to the map that map from short to point coordinate.
	            shortNameToCoordinate.put(shortName, new PointCoordinate(x,y));       	
		     }
		} catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	    	if (reader != null) {
	    		try {
	                reader.close();
	            } catch (IOException e) {
	                System.err.println(e.toString());
	                e.printStackTrace(System.err);  
	            }
	        }
	    }
		
	}
	
	/**
	 * It reads the given campus path data sets/file. Thus, if the given file is not 
	 * empty, it will start with non-indented line with coordinates of start point,
	 * followed by indented lines. The non-indented line contains x and y coordinate
	 * of that point separated by comma(for instance x1,y1). The indented line formatted
	 * as, x and y coordinate of that point/node separated by comma, and followed by colon
	 * and the distance from the start point to this end point (for instance, x2,y2: dis12). 
	 * 
	 * @param filename the file that will be to read, the campus path data file
	 * @param NodeToPointCoordinate it maps the path point/node name to the coordinates 
	 *        of the point. 
	 * @param campusPaths the node that associated with its edge weight or the distnce from
	 *         start node to this end node;
	 * @requires the file(filename) should be well-formatted.
	 * @effects campusPaths will contain all nodes and edge/distance between nodes, that
	 * 			represnt the campus graph.
	 * @effects the NodeToPointCoordinate will have a map from each building location to
	 * 			its coordinate. "x,y" --> x,y 
	 * @modifies NodeToPointCoordinate and campusPaths, it fill both maps.
	 * @throws Exception MalformedDataException if the file is not well-formed:
     *         each line contains exactly four tokens separated by three tab, or
     *         else starting with a # symbol to indicate a comment line.
	 */	
	public static void pathDataParser(String filename, Map<String, PointCoordinate> NodeToPointCoordinate,  
			DirectGraph<String, Double> campusPaths) throws Exception {
		
		BufferedReader reader = null; 
		try {
			reader = new BufferedReader(new FileReader(filename));
			String start = "";
			String end = "";
			String inputLine; 
		    while ((inputLine = reader.readLine()) != null) {
		    	// Ignore comment lines.
		        if (inputLine.startsWith("#")) {
		        	continue;
		        }
		        
		        // Parse the data, stripping out quotation marks and throwing
 	            // an exception for malformed lines.
	            inputLine = inputLine.replace("\"", "");
	            // stripping out tab character 
	            inputLine = inputLine.replace("\t", "");
	            // split coordinates and distance/edge weight from indented lines
	            String[] tokens = inputLine.split(":");
	            
	            // array to process the coordinate of the point/location
	            String[] tokens2; 
	            // if the line is non-indented line
	            if (tokens.length == 1) {
	            	// get the starting node/location
	            	start = tokens[0];
	            	
	            	// if the path did not have this location/node, add it.
	            	if(!campusPaths.containsNode(start)){
	            		campusPaths.addNode(start); 
	            	}
	            	
	            	// if map did not contain the coordinate
	            	// of this location/node, process and add to it.
	            	if(!NodeToPointCoordinate.containsKey(start)){          
	            		// split and get the coordinate of the building location 
	            		tokens2 = start.split(",");
	            		double x = Double.parseDouble(tokens2[0]);
	            		double y = Double.parseDouble(tokens2[1]); 
	            		
	            		// set up the current start point/location coordinate
	            		// and add to the map. 
	            		PointCoordinate currentPoint = new PointCoordinate(x,y);
	            		NodeToPointCoordinate.put(start, currentPoint);		           		
	            	}
	                
	            } else if(tokens.length == 2) {  // we are at the indented line case
	            	// the first token value is the location of the end/destination building
	            	end = tokens[0];
	            	
	            	// the distance between the location, from start to this 
	            	// end node.
	            	double distance= Double.parseDouble(tokens[1]);
	            	
	            	// Add to this end/destination node/location if the graph 
	            	// did not contain. 
	            	if(!campusPaths.containsNode(end)){ 
	            		campusPaths.addNode(end);
	            	} 
	            	
	            	// add the starting and end/destination location/node
	            	// with the distance between them.
	            	campusPaths.addEdge(start, end, distance);
	            	
	            	// if this end node is not inside the map NodeToPointCoordinate
		            if(!NodeToPointCoordinate.containsKey(end)){
		            	// split and get the coordinate of the location/point
	            		tokens2 = end.split(",");
	            		double x = Double.parseDouble(tokens2[0]);
	            		double y = Double.parseDouble(tokens2[1]);
	            		
	            		// set up the current point/location coordinate and 
	            		// add to the map.
	            		PointCoordinate currentPoint = new PointCoordinate(x,y);
	            		NodeToPointCoordinate.put(end, currentPoint);	 	           		
	            	}
	            	
	            } else { 
	            	// if the file is malformed due to indentation of lines or others.
	            	throw new Exception("The file is malformed. "+ inputLine);
	            }	        	
		     }
		} catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                System.err.println(e.toString());
	                e.printStackTrace(System.err);
	            }
	        }
	    }
		
	}	
}
