package hw6;
import hw5.DirectGraph;
import hw5.GraphEdges;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/**
 * 
 * @author reta,
 * This class build a graph from the given file or dataset. Then, it allows the
 * user to find the shortest path between two nodes/charactesrs if there is a path 
 * from one charcter to other in the same book. If it find the shortest path it will
 * display the path. 
 */
public class MarvelPaths {
	
	/**
	 * This method method build a direct graph from the given data file
	 * @param filename, the file used to build a direct graph
	 * @throws Exception, throw exception if it fail to read the data file from the
	 *  	the given file format.
	 * @throw throw IllegalArgumentException if the filename is null
	 * @return marvelGraph, the graph build from the given file data
	 *
	 */		
	public static DirectGraph<String, String> buildingGraph (String filename) throws Exception {
		if (filename.equals(null)) { 
			throw new IllegalArgumentException("file should not be null");
		}
		
		// creat a new direct graph
		DirectGraph<String, String> marvelGraph = new DirectGraph<String, String>();
		// a set in which all characters
		Set<String> characters = new HashSet<String>();
		// a map in which each book to all characters found in it.
		Map<String, List<String>> books = new HashMap<String, List<String>>();
		MarvelParser.parseData(filename, characters, books);
		
		// add each characters or nodes to the graph.
		for (String nd: characters) {
			marvelGraph.addNode(nd);
		}
		
		//add edges between characters/nodes in the same book by using the book (edge labels)
		for (String book : books.keySet()) {
			// the list of characters/node in this book title
			List<String> charsInBook = books.get(book);  
			
			// add edge or make connection between nodes by picking start and end node.
			for (String start : charsInBook) { 
				for (String end : charsInBook) {
					
					// do not add edge between same node (self-edge).
					if (!start.equals(end)) {
						marvelGraph.addEdge(start, end, book); 
					}
				}
			}
		}
		return marvelGraph;	 
	}
	
	/**
	 * This method finds the shortest path from the given start node to
	 * the given end node using breadth-first-search (BFS).
	 * @ requires graph != null and start != null and end != null.
	 * @param graph the direct graph need to be explored
	 * @param start the staring node of the graph
	 * @param end the end/destination node of the path
	 * @return path, it returns the shortest path from start to the end node.
	 */
	public static List<GraphEdges<String, String>> findPathBFS(  
			DirectGraph<String, String> graph,String start, String end) { 
		if (graph.equals(null)) {
			throw new IllegalArgumentException ("graph should not be null");
		}
		if (start.equals(null) || end.equals(null)) {
			throw new IllegalArgumentException ("nodes cannot be null");
		}
		// if the graph does not have the given nodes throw exception
		if (!graph.containsNode(start) || !graph.containsNode(end)) {
			throw new IllegalArgumentException ("graph does not have the given nodes");
		}
		 // list of nodes/characters that are to be visted.
		Queue<String>  nodesToBeVisted= new LinkedList <String>(); 
		
		// for each path, the key is the visted node and the value is the path from the
		// start node and the associated neighbors (end) node.
		Map<String, List<GraphEdges<String, String>>> path =  
				new HashMap<String, List<GraphEdges<String, String>>>();
		path.put(start, new ArrayList<GraphEdges<String, String>>());
		// add the start node into the fringe and start visting its neighbors
		nodesToBeVisted.add(start); 
		
		while(!nodesToBeVisted.isEmpty()) {
			// the node removed from the list as key and expanded to its neighbors
			String nd = nodesToBeVisted.poll();
			
			// if the node is already end add to the list and return.
			if(nd.equals(end)) { 
				return new ArrayList <GraphEdges<String, String>>(path.get(nd));    
			}
			//sort out all graph edge lables associated with node in alphabetical 
			// order using comparator
			List<GraphEdges<String, String>> listOfEdges = 
					new ArrayList<GraphEdges<String, String>>();
			// copy all edges of the graph 
			listOfEdges.addAll(graph.edgesOfNode(nd));
			
			// sort out the edge label using collection sort and comparator
			Comparator<GraphEdges<String, String>> comparedEdge = edgeComparator();	
			Collections.sort(listOfEdges, comparedEdge);
			
			for (GraphEdges<String, String> eg: listOfEdges) {
				String  endNode = eg.getEndNode();
				
				// if this node is not visted yet, Expand the path to this node by appending
				// to the stored shortest path of its parent/start node.
				if (!path.keySet().contains(endNode)) {
					List<GraphEdges<String, String>> newPath = 
							new ArrayList<GraphEdges<String, String>>(path.get(nd));
					newPath.add(eg);
					
					path.put(endNode, newPath);
					// mark and add the visted node at the back of the queue
					nodesToBeVisted.add(endNode); 	 				
				}
			}
		}				
		return path.get(start); 		
	}
	
	/**
	 * This method compares two GraphEdges objects using Comparator interface based on the  
	 * the GraphEdges defined class in  order to order the objects
	 * @param edge1 the GraphEdges object contains edge lable and the assocaited end node
	 * @param edge2 GraphEdges objects
	 * @ requires edge1 != null and edge2 != null
	 * @return edgeComp, it return negative integer, zero, or positive integer if egdge1 is 
	 * 			less than, equla or greater than edge2 respectivelly.
	 */
	public static Comparator<GraphEdges<String, String>> edgeComparator() {
		Comparator<GraphEdges<String, String>> edgeComp = new Comparator <GraphEdges<String, String>>(){
			public int compare(GraphEdges<String, String> edge1, GraphEdges<String, String> edge2) {
				if(!edge1.getEndNode().equals(edge2.getEndNode())) {
					return edge1.getEndNode().compareTo(edge2.getEndNode()); 
				}
				if(!edge1.getEdgeLable().equals(edge2.getEdgeLable())) {
					return edge1.getEdgeLable().compareTo(edge2.getEdgeLable());  
				}
				return 0;
			}
		};
		return edgeComp; 
	}
	
	/**
	 * This is the main method to interact with the users. The user has to enetr the
	 * start and end/destination node using a scanner object. Thus, it prints the shortest 
	 * path between if there is a path. Otherwise, it print out an advisable message.
	 * @param args
	 * @effects print out the shortest path from start to end node or other message.
	 * @throws Exception if the program cannot find the file from the given relative pathname.
	 */
	public static void main(String[] args) throws Exception {
		DirectGraph<String, String> graph = buildingGraph("src/hw6/data/marvel.tsv");
		System.out.println("Find the shortes path between two marvel characters");
		
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
			String currStartNode = start; // setup the current starting node
			String result = "path from " + currStartNode + " to " + end + ":\n";
			List<GraphEdges<String, String>> paths = MarvelPaths.findPathBFS(graph, start, end);
			if (start.equals(end)) { // if start and destination node is the same, nothing to do 
				result +=""; 
			} else if (paths.isEmpty()) {
				result += "no path found";
			} else { // if both nodes are in the path, get the route how to travel from start to end node.
				for (GraphEdges<String, String> eg : paths) {
					result += currStartNode + " to " + eg.getEndNode() + " via " 
							+ eg.getEdgeLable() + "\n";
					currStartNode = eg.getEndNode();			
				}
			}
			System.out.println(result);   
		}
		input.close();	
	}
}
