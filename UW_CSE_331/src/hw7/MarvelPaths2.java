package hw7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import hw5.DirectGraph;
import hw5.GraphEdges;
import hw6.MarvelPaths;

/**
 * This is a class used to build a graph from given file. It find the minimum cost path from 
 * starting character/node to the destination character/node. It also prints the least cost 
 * path from start to end node if both nodes are found in the graph.
 */
public class MarvelPaths2 {
	
	/**
	 * This method build a direct graph whose edge value is a double type from the given
	 * file. The edge lable value is reprsented by the inverse of the number of comic books
	 * where two characters/nodes (start and end) appear together. 
	 * @param filename, the file used to build a direct graph
	 * @requires filename != null
	 * @throws Exception, throw exception if it fail to read the data file from the
	 *  	the given file format.
	 * @return graphNew, return the graphNew, which is the graph build from the given data 
	 *          file and its edge value is Double type.  
	 */
	public static DirectGraph <String, Double> graphBuilding (String filename)
			throws Exception {
		// build a marvel graph by calling MarvelPaths/hw5 
		DirectGraph<String, String> marvelGraph2 =MarvelPaths.buildingGraph(filename); 
		// create a graph with edge label value is numeric (double).
		DirectGraph<String, Double> graphNew =new DirectGraph<String, Double>();  
		
		// A map that holds start character as key and value is the destination character assocaited
		// with the number of comic books where two characters appear together. Internal Map is 
		// destination charcter ---> number of comic books where the two characters appear together.
		Map<String, Map<String,Integer>> bookKeeping = 
				new HashMap <String,Map<String,Integer>>(); 
		
		// add each character as a start node
		for(String node : marvelGraph2.getAllNodes()) {  
			graphNew.addNode(node);  
			bookKeeping.put(node, new HashMap<String,Integer>());  
		}
		
		// now record the number of comic books where two characters (start and end) appear together  
		for(String start : marvelGraph2.getAllNodes()) {
			// get the edge lable associated with the end node as we move from starting node(start).
			Set< GraphEdges<String,String>> edgeLable = marvelGraph2.edgesOfNode(start);
			
			// keep track each edge lables and updated the number of books between 
			// the start and end node/charcter appeared together.  
			for(GraphEdges<String,String> eg: edgeLable) {
				// if they appear together for the first time, add as (start, (end, 1))  
				if(!bookKeeping.get(start).containsKey(eg.getEndNode())) {    
					bookKeeping.get(start).put(eg.getEndNode(), 1);  
				} else { // if they appear together before with other book, updated the count.
					// get old value, the number of books where two characters appear together
					Integer oldValue = bookKeeping.get(start).get(eg.getEndNode());
					// update the number of books where two characters appear together
					bookKeeping.get(start).put(eg.getEndNode(), oldValue + 1); 
				} 
			}
		}
		
		// calculate the weight of the edge between two characters/nodes, which is the
		// inverse of the number of comic books where two characters appear together
		for(String start : bookKeeping.keySet()) {
			for(String end : bookKeeping.get(start).keySet() ) {
				double edgeValue = 1.0/(bookKeeping.get(start).get(end)*1.0); 
				graphNew.addEdge(start, end, edgeValue);
			}
		}
		return graphNew;  
	}
	
	/**
	 * @param graph, the graph used to find the minimum cost path from start to end
	 * @param start, the start node/character to find the path
	 * @param end, the end/destination node/character to find the path
	 * @throws IllegalArgumentException if either graph is null,  start or end is null, or
	 *         start or end not found in the graph.
	 * @return minPath, the minimum cost path from start to end, other wise return 
	 *         null if there is no path exists from start to end.
	 */	
	public static  List<GraphEdges<String, Double>> findMinimumCostPath ( 
			DirectGraph<String, Double> graph, String start, String end) {
		
		if(graph.equals(null)){
			throw new IllegalArgumentException("the graph cannot be null");
		}
		if(start.equals(null)||end.equals(null)){
			throw new IllegalArgumentException("nodes cannot be null");
		}
		if(!graph.containsNode(start)|| !graph.containsNode(end)){ 
			throw new IllegalArgumentException("node should be in the graph");
		}
		
		// priority queue of paths from start to various nodes. Each path priority
        // is the total cost of that path. It contains all paths to unfinished nodes.
		// the priority queue is created with specified intial capacity(10) with comparator
		// and order the paths by their total costs.
		PriorityQueue<List<GraphEdges<String, Double>>> active = new
				PriorityQueue<List<GraphEdges<String, Double>>>(10, new   
						Comparator<List<GraphEdges<String, Double>>>() {
					public int compare(List<GraphEdges<String, Double>> edgeList1, 
							   List<GraphEdges<String, Double>> edgeList2) {
						GraphEdges<String, Double> edge1 = edgeList1.get(edgeList1.size() - 1);  
						GraphEdges<String, Double> edge2 = edgeList2.get(edgeList2.size() - 1);
						if (!edge1.getEdgeLable().equals(edge2.getEdgeLable())) {
							return edge1.getEdgeLable().compareTo(edge2.getEdgeLable()); 
						} else {
							return edgeList1.size() - edgeList2.size(); 
						}						
					}
				}); 
		
		// The set of nodes for which the minimum-cost path is known
		Set<String> finished  = new HashSet<String>(); 
		
		// Initially we only know the path from start to itself, which has a cost
		// of zero. So, add a path from start to itself to PriorityQueue active.  
		ArrayList<GraphEdges<String, Double>> pathToItSelf = new ArrayList<GraphEdges<String, Double>>();
		pathToItSelf.add(new GraphEdges<String, Double>(start, 0.0));  
		active.add(pathToItSelf);    
		
		// Inv: active contains all paths to unfinished nodes that only pass
	    // through finished nodes via the minimum cost paths to those nodes
		while(!active.isEmpty()) {
			 // minPath is the lowest-cost path to its destination
			List<GraphEdges<String, Double>> minPath = active.poll();
			// destination node in minPath
			String minDest = minPath.get(minPath.size()-1).getEndNode();
			
			// if we reaching at the end/destination node return the minimum path. 
			if(minDest.equals(end)) { 
				return minPath;
			}			
			if(finished.contains(minDest)) {
				continue;
			}
			// add minDest to finished, set of nodes for which the minimum-cost path is known
			finished.add(minDest);
			
			// Then add all new paths to unfinished nodes that go through the newly
	        // finished node via this shortest path.
			double oldPath = minPath.get (minPath.size() - 1).getEdgeLable();
			for(GraphEdges<String, Double> eg : graph.edgesOfNode(minDest)) { 
				// if child is not in finished
				if(!finished.contains(eg.getEndNode())){ 
					//update the new path cost from the old value 
					double newPathCost = oldPath + eg.getEdgeLable(); 
					List<GraphEdges<String, Double>> newPath = new ArrayList<GraphEdges<String, Double>>(minPath);
					newPath.add(new GraphEdges<String, Double>(eg.getEndNode(), newPathCost)); 
					// add newPath to active  
					active.add(newPath);   				
				}			
			}		
		}
		return null;  // no path exists from start to end		
	}
	
	/**
	 * This is the main method to interact with the users. The user has to enetr the
	 * start and end/destination node using a scanner object. Thus, it prints the shortest 
	 * path between each transition node to reach the destination node and the total cost
	 * from the start to the end/destination node if there is a path. Otherwise, it print 
	 * out an advisable message.
	 * @param args
	 * @effects print out the shortest path from start to end node or other message.
	 * @throws Exception if the program cannot find the file from the given relative pathname.
	 */
	public static void main(String[] args) throws Exception {
		// create the marvel graph by calling creatGraph method
		DirectGraph<String, Double> graph = graphBuilding("src/hw7/data/marvel.tsv");
		System.out.println("Find the shortes path between two marvel places");
		
		// create InputStream scanner object to interact with the users. 
		Scanner input = new Scanner(System.in);
		System.out.print("Enter the starting node: ");  
		String start = input.nextLine();
		System.out.print("Enter the ending node: ");
		String end = input.nextLine(); 
				
		// if start and end nodes is not found in the graph, print as unknown node.
		if (!graph.containsNode(start) && !graph.containsNode(end)) {
			System.out.println("unknown character " + start);
			System.out.println("unknown character " + end);			
		} else if (!graph.containsNode(start)) {
			System.out.println("unknown character " + start);
		} else if (!graph.containsNode(end)) {
			System.out.println("unknown character " + end); 
		} else { 
			// setup the current starting node
			String currStartNode = start;
			String result = "path from " + currStartNode + " to " + end + ":\n";
			List<GraphEdges<String, Double>> paths = MarvelPaths2.findMinimumCostPath(graph, start, end);
					
			if (start.equals(end)) {
				result += "total cost: 0.000";
			}else if (paths == null) { 
				result += "no path found"; 
			} else {
				// a variable that hold the total cost from start node to some destination node
				double cost = 0.0;
				paths = paths.subList(1, paths.size()); 
				for (GraphEdges<String, Double> eg : paths) {
					// get each transition cost such as, n1 to n2, n2 to n3, n3 to n4 etc.
					result += currStartNode + " to " + eg.getEndNode() + " with weight " 
							+ String.format("%.3f", eg.getEdgeLable() - cost) + "\n";
					// update the current node to be expanded/ find its transition path/distance
					currStartNode = eg.getEndNode();
			        // update the total cost till this end node
					cost = eg.getEdgeLable();
				} 
				// get the total cost
				result += "total cost: " + String.format("%.3f", cost);				
			}
			System.out.println(result); 			
		}
		input.close(); 	
	}  
}
