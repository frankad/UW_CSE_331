package hw8;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import hw5.GraphEdges; 

/**
 * This class is the view and controller to find the path in the campus
 * based on the campus building and path data set. It has main method that 
 * allow the user to interact in the command line to get information from 
 * the model by calling appropriate methods from view.
 */
public class PathFindingControllerView {
	
	/**
	 * It displays list of the buildings' names in the campus, the 
	 * abbreviatedand name followed by tab separated full name
	 * 
	 * @param model model of the PathFindingModel
	 * @requires model != null
	 * @effect print all the building names in the campus, the 
	 * 		abbreviatedand followed by full name
	 */
	public static void listBuildings(PathFindingModel model) {
		// get a map of the shortName to fullName
		Map<String,String> buildingNames = model.getBuildingShortToFullName();
		
		// TreeSet is used to sort the keys/short name in lexicographic order
		Set<String> sortedShortName = new TreeSet<String>(buildingNames.keySet());
		
		// String representation of each building short name followed by
		// tab separted its full name in lexicographic order
		String result = "Buildings:\n"; 
		
		// prepare tab separated short name followed by full
		// name of the building for print.
		for(String shortName : sortedShortName) {
			String fullName = buildingNames.get(shortName);
			result +="\t" + shortName + ": "+ fullName + "\n";
		}
		System.out.println(result);	
	}
	
	/**
	 * It print out the shortes path/route to travel from starting building
	 * to some ending/destination building
	 *  
	 * @param model the PathFindingModel
	 * @param start the starting building for the route
	 * @param end   the end/destination  building of the route
	 * @effects it prints the shortest path from the starting building
	 * 			 to the end/destination building in the campus direct graph.
	 */
	public static void shortestRoute(PathFindingModel model, String start, 
			String end) {
		// the model, starting and ending location/node must not ne null.
		if(model==null){
			throw new IllegalArgumentException("model should not be null");
		}
		if(start.equals(null)){
			throw new IllegalArgumentException("start should not be null");
		}
		if(end.equals(null)){
			throw new IllegalArgumentException("end should not be null");
		}
		
		// get a map of the shortName to fullName 
		Map<String,String> buildingNames = model.getBuildingShortToFullName();
		
		// if either the starting or ending buildings in the route is not in the 
		// dataset, print as Unknown building name.  
		if(!buildingNames.containsKey(start) && !buildingNames.containsKey(end)) {
			System.out.println("Unknown building: " + start);
			System.out.println("Unknown building: " + end +"\n");
		} else if (!buildingNames.containsKey(start)) {
			System.out.println("Unknown building: "+ start + "\n");	
		} else if (!buildingNames.containsKey(end)) {
			System.out.println("Unknown building: "+ end + "\n");	
		} else{
			// abbreviated name of the building to its coordination
			Map<String, PointCoordinate> abbrNameToCoord = 
					   model.getShortNameToCoordinate();
			
			// The coordination of the starting and ending building/node
			String startCoord = "" + abbrNameToCoord.get(start).getX() + ","
					  + abbrNameToCoord.get(start).getY();
			String endCoord =""+ abbrNameToCoord.get(end).getX() + ","
					  + abbrNameToCoord.get(end).getY();
			
			// get the starting and ending building full names
			String startFullName = buildingNames.get(start);
			String endFullName = buildingNames.get(end);
			
			// the result route/path from building start to end.
			String resultRoute = "Path from "+ startFullName+" to "+ endFullName + ":\n";
			
			// list of  end/destination building/node associated with edge weight 
			// or distance from the start building. 
			List<GraphEdges<String,Double>> route = model.findShortestRoute
					(startCoord, endCoord); 
			String currStartCord = startCoord; 
			
			// the total distance from start building/coordinate/node 
			// to some destination building/coordinate/node
			double distance = 0.0;
			
			// process the path of each building and append to the result route.
			route = route.subList(1, route.size()); 
			for (GraphEdges<String, Double> eg : route) {
				
				// the coordination of current start and end building/node on the path 
				// in each transtion case, such as n1 to n2, n2 to n3, n3 to n4 etc.
				PointCoordinate startBuilding = model.getCoordOfBuilding(currStartCord);  
				PointCoordinate endBuilding = model.getCoordOfBuilding(eg.getEndNode());  
				
				// the direction while we move from current start to end/destination
				// building/node
    			String dir = startBuilding.getDirection(endBuilding);
				
    			// make the appropriate format for the this end building coordination
				String buildingCoord = " ("+ String.format("%.0f", endBuilding.getX())+", " 
    					+ String.format("%.0f", endBuilding.getY()) + ")\n";
				 
				// get each transitional distance from current start to this end building
				// and append the distance, direction and the coordinate o the result route.
    			resultRoute += "\tWalk "+ String.format("%.0f feet",
    					eg.getEdgeLable() - distance)  + " "+ dir + " to" + buildingCoord;  
							
				// update the current start coordination to be expanded/find
    			currStartCord = eg.getEndNode();
    			
		        // update the total distance till this end building/node
    			distance = eg.getEdgeLable(); 
			}
			// get the total distance
    		resultRoute += "Total distance: " + String.format("%.0f feet\n", distance); 
    		System.out.println(resultRoute);			
		}
	}
	
	/**
	 * The main method that allow the user to interacat using menu option to print 
	 * building names and to find the shortest walking path/route between two buildings
	 * on campus graph.
	 * @param args 
	 * @throws Exception if the file format does not match with the expected 
	 * 		  format.
	 */
	
	public static void main(String[] args) throws Exception { 
		try{
			// get the building data file and path data file
		    String fileBbuilding = "src/hw8/data/campus_buildings.dat";
		    String filePath = "src/hw8/data/campus_paths.dat";
		    PathFindingModel model = new PathFindingModel(fileBbuilding, filePath); 
		    printMenu();
		   
		    // create InputStream scanner object to interact with the users. 
		    Scanner input = new Scanner(System.in);
		    // prompts the user to enetr m and see the list of menu
		    System.out.print("Enter an option ('m' to see the menu): ");
		    while(true) {
		    	// get the current option/menu choosen from the user
		    	String currOption = input.nextLine(); 
		    	
		    	// echo empty lines or lines beginning with # 
		    	if(currOption.length() == 0 || currOption.startsWith("#")){
			    	System.out.println(currOption); 
			    	continue;
			    }
		    	
		    	// if the choosen menu by the user is "m", prints a menu of all commands, 
		    	// "b" print lists all buildings in the form short name: long name, 
		    	if(currOption.equals("m")) {
		    	    printMenu();
			    }else if(currOption.equals("b")) {
			        // if the choosen menu is "b", it prints lists all buildings in the 
			    	// form of abbreviated name: long name
			    	listBuildings(model);
			    	
			    } else if(currOption.equals("r")) {
			    	// if the choosen menu is "r", it prints the abbreviated names of the 
			    	// two buildings and the directions for the shortest route between them.
			    	System.out.print("Abbreviated name of starting building: ");
			    	String startBuilding = input.nextLine();
			    	System.out.print("Abbreviated name of ending building: ");
			    	String endBuilding = input.nextLine();
			    	shortestRoute(model, startBuilding, endBuilding);
			    	
			    } else if(currOption.equals("q")) {
			    	// if the choosen menu is "q", it cause the main method to return. 
			    	// Calling the System.exit to terminate the program. 
			    	break;
			    } else {
			    	// when an unknown command is received the program prints
			    	// the text "Unknown option".
			    	System.out.println("Unknown option\n");
			    }
		    	// repeatedly prompts the to enetr m and see the list of menu.
		    	System.out.print("Enter an option ('m' to see the menu): ");
		    }
		    input.close(); 
		} catch (Exception e) {
			System.err.println(e.toString());
	    	e.printStackTrace(System.err);
	   }			
	}
	
	/**
	 * This is a helper method used to provide a list of menu option that 
	 * prompts the user and allow the user to interact with the program.
	 */
	private static void printMenu() {
		String menuRresult = "Menu:\n";
		menuRresult += "\t" + "r to find a route\n";
		menuRresult += "\t" + "b to see a list of all buildings\n";
		menuRresult += "\t" + "q to quit\n";
		System.out.println(menuRresult); 		
	}

}
