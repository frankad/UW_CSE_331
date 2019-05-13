package hw5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
/** 
 * @author reta
 * <b> DirectGraph</B> a mutable finite number of nodes and their related edge that
 * represnetd by edge lable.
 *  
 * <p>
 * This class representing a direct graph containing nodes and there can be one more
 * edges between nodes, and edges have its own name/label. However, edges between two
 * nodes must not have the same labels. Internally, it is represented by list of nodes
 * and edeg lables. Each directGraph contains a set of some nodes and a set of some 
 * edges that connect the node inside the graph. The status of each node and edge might
 * be different and some nodes may have or my not have edges to connect with other nodes
 * or itself.
 * <p>
 *  
 * The nodes and a set of nodes associated with thier edges are stored in 
 * the Map, Map<T, Set<GraphEdges<T,E>>> directGraph
 * 
 *     directGraph.put(n1, SetName.add(n2, e1)), in this case n1 is the staring node and
 *     n2 is the end/destination node. Thus, we will continue adding nodes and edges. 
 *            
 * T: Nodes to be added into the constructed graph
 * E: Edge lable between nodes in the garaph <p>
 */
public class DirectGraph <T extends Comparable<T>, E extends Comparable<E>>  {
	
	/*
	 * A map that contains a node and a set of node associated with edeg lable.
	 *  node ---> Set of (node associated with edge) 
	 * Each start node map to the end node  associated with edge label, n1 ---> n2(e1)
	 * startNode = n1, endNode = n2, and e1 is the edge label from start to end.
	 */
	private final Map<T, Set<GraphEdges<T,E>>> directGraph;
	
	// Abstact Function
	// A directGraph represented by set of nodes such as [n1, n2, ..., nk]
	// and edges such as [eg1, eg2, ..., egj] that connect the nodes in the graph.
	// Nodes and edges interanlly represented by Map. Nodes are adde in the map as a key
	// and the value of the map is a set of GraphEdges that contain the edges that associated 
	// with end nodes.
	// Some nodes may have or my not have edges to connect with other nodes
	// or itself. The nodes and  a set of nodes associated with thier edges are stored in 
	// the map. For instance, a set of nodes [n1, n2,..., nk] stored in the Map directGraph
	// Map<T, Set<GraphEdges<T,E>>> such that 
	//     directGraph.put(n1, SetName.add(n2, eg1)), in this case n1 is the staring node, 
	//     n2 is the end/destination node, and eg1 is the edge that connect from n1 to n2. 
	// Thus, it will continue adding nodes and their associated edges in such a way 
	// directGraph.put(nx, SetName.add(nk, egj))
	//
	// Internally a set is used to store GraphEdges, this prevent duplicated edge between the same
	// nodes.
	//
	// Representation of Invariant(RI)
	// For every directGgraph created graphj:
	// For every graphj all nodes and edges contained in this graph must not be null.
	// graphj != null and nodes != null and edges != null and GraphEdges != null
	// For any operation in the graph each nodes and edges must be in the graph.
	
	/**
	 * effects Constructs a new Graph.
	 */
	public DirectGraph() {
		directGraph = new HashMap<T, Set<GraphEdges<T,E>>> ();
		// checkRep();		
	}
	
	/**
	 * if the given node is not found in graph, add it into the graph
	 * @param node, a noode that will be added in this graph
	 * @requires  node != null and directGraph !=null
	 * @modifies the directGraph will be modified
	 * @effect  a new node will be added into the graph
	 * @return successful, return successful(true) as a boolean if the node is added 
	 *         into the graph, otherwise return false. 
	 */
	public boolean addNode(T node) {
		boolean successful = false;		
		if (!containsNode(node)) {
			directGraph.put(node, new HashSet<GraphEdges<T, E>>());
			return successful = true;
		}
		return successful;
	}
	
	/**
	 * Adding a new edge from the given starting node(start) to ending(end) node.
	 * Then the edge from the start to end node will be labeled with a given lable.
	 * The method can add an edge by making one node as starting and ending point. 
	 * But if there is already an edge between the start and end node, it wil not 
	 * add a duplicated edge. It supports self-edge (a given node can make an edge
	 * with itself.)
	 * @param start , the starting node 
	 * @param end, the ending/destination node
	 * @param edge, the edge that connect the start and end node
	 * @requires start and  end node should be already inside the graph and
     *			start!=null && end !=null && edge != null and directGraph != null
     * @modifies directGraph will be modified
     * @effects add a new edge that points from start to end node
	 * @return successful, return true if the edge is added successfully otherwise return 
	 *          false.
	 */
	public boolean addEdge(T start, T end, E edge) {
		boolean successful = false;
		if(containsNode(start) && containsNode(end) && !edge.equals(null)&& !start.equals(null)
				&& !end.equals(null)){ 
			Set<GraphEdges<T,E>> listEdges = directGraph.get(end);
			GraphEdges<T,E> newEdge= new GraphEdges<T, E>(end,edge);   
			if(!listEdges.contains(newEdge)){
				directGraph.get(start).add(newEdge);      
				successful =true; 
			}			
		}
		return successful; 
	}
	
	/**
	 * If the given node is inside the graph, just remove from graph
	 * So, if that node is removed, all edges associated to node should be removed.
	 * @param node
	 * @requires The node should be already inside the graph. It also directgraph != null and node !=null 
	 * @modifies the directGraph will be modified
	 * @effects remove the given node from the graph
	 * @return successful, return successful (true) if the given node successfully removed from 
	 *         directGraph. otherwise return successful(fasle)
	 */
	public boolean reomevNode(T node) {
		boolean successful = false;
		if (directGraph.containsKey(node) && !node.equals(null)) { 
			directGraph.remove(node); 
			successful = true;
			for(T end: directGraph.keySet()){ 
		    	Set<GraphEdges<T,E>> edgs = directGraph.get(end); 
		    	for(GraphEdges <T, E> eg: edgs){ 
		    		if(eg.getEndNode().equals(node)){
		    			directGraph.get(end).remove(eg);  
		    		} 
		    	}
		    }	
		}
		return successful; 		
	}
	
	/**
	 * This method removed an edge by considering the starting and ending point.
	 * It return true if the edge from the start to end node removed sussfully 
	 * otherwise return false. But it wil not add aduplicated edge between the
	 * same nodes
	 * @param start the staring node 
	 * @param end, the ending/destination node of the path fromstart to end
	 * @param edge, the edge that connect from start to end
	 * @requires edge != null && end != null && end  != null. start and end must
	 * 		be inside the graph.
	 * @effects remove the edges from the graph
	 * @return successful, return true if the edge removed successfullly. Otherwise return
	 * 		 false.
	 */
	public boolean removeEdge(T start, T end, E edge) {
		boolean successful = false;
		if(containsNode(start) && containsNode(end) && !edge.equals(null)&& !start.equals(null)
				&& !end.equals(null)){ 
			if (checkEdgeExist(start, end)) { 
				GraphEdges<T, E> edgeToEnd = new GraphEdges<T, E>(end, edge);
				// check if this edge begin from node start
				if (directGraph.get(start).contains(edgeToEnd)) {   
					directGraph.get(start).remove(edgeToEnd); // remove the edge from start to end
					// update successful, since edge is removed
					successful = true; 
				}
			}
		}
		return successful;		
	}
	
	/**
	 * It check if the given two nodes are connected by edges or if there exist an
	 * edge between the two given nodes.
	 * @param start, the starting node or point in the graph
	 * @param end, the end node in the graph
	 * @requires graph != null and start != null and end != null. Start and end must
	 * 		 be contained in the graph.
	 * @return connected, return true if there exit an edge between the tow noeds, 
	 * 		 otherwise return false
	 */ 
	public boolean checkEdgeExist(T start,T end){
		boolean connected =false;
		if(!start.equals(null) || !end.equals(null) || containsNode(start) || 
				containsNode(end)){
			Set<GraphEdges<T,E>> edgeFromStart =directGraph.get(start);
			for(GraphEdges<T,E> eg: edgeFromStart){
				if(eg.getEndNode().equals(end)){
					connected = true; 
				} 
			}			
		}
		return connected; 
	}
	
	/**
	 * Check whether the graph has the given node or not 
     * @param a node to be checked in the graph 
     * @requires graph != null and node != null
     * @return true if the graph contained the given node, otherwise return false
	 */
	public boolean containsNode(T node){ 
		return directGraph.containsKey(node); 
	}
	
	/**
	 * It returns the set of nodes that are found in graph
	 * @requires directGraph!=null
	 * @return a set of node in the graph
	 */
	public Set<T> getAllNodes() {  
		// make a copy of all nodes to return
		// checkRep(); 
		return new TreeSet<T>(directGraph.keySet());  
	}
	
	/**
	 * Returns the set of all edges related or associated with the given node, nd.
	 * @param nd a given node
	 * @requires nd != null and graph must not be null
	 * @return the set of  edges associated to the given node nd. 
	 */
	public Set<GraphEdges<T, E>> edgesOfNode(T nd) {
		// make a copy of the GraphEdges list  
	    Set <GraphEdges<T, E>> edgesAndNodes = directGraph.get(nd); 
		// checkRep();  
		return new TreeSet<GraphEdges<T, E>>(edgesAndNodes);   
	}
	
	/**
	 * return set of edges from  the given staring(start) node to the ending(end) node.
	 * @param start, the staring node 
	 * @param end, ending/destination node 
	 * @requires, start and end must not be null, and they must be in the graph 
	 * @return startToEnd, the set of edge lables from the staring node(start) to end.
	 */
	public Set<E> edgeLablesFromStart(T start, T end){
		Set <E>  edgeStartToEnd = new HashSet<E>(); 
		if(start !=null && end !=null && directGraph.containsKey(start) && 
				directGraph.containsKey(end)) {
			// edges between start and end node 
			Set<GraphEdges<T, E>> result = directGraph.get(start);
			for (GraphEdges<T, E> eg: result) {
				if (eg.getEndNode().equals(end)) {
					edgeStartToEnd.add(eg.getEdgeLable());
				} 
			}
		}
		checkRep();
		return edgeStartToEnd; 
	}  
	
	/**
	 * returns a set of nodes that can be destination node starting from the 
	 * given node (start)
	 * i.e. the child of the given node.
	 * @requires start !=null 
	 * @param start, some staring node to route
	 * @return childNode, return the children nodes of the given node
	 */
	public Set<T>  getChildren(T start) { 
		Set<T> childNode = new TreeSet<T>();
		for(T end: getAllNodes()) {
			// if there is edge between start and end node
			if(checkEdgeExist(start, end)){
				childNode.add(end);   
			}
		}
		checkRep();
		return childNode; 	
	}
	
	/**
	 * Returns the number of nodes in this graph.
	 * @requires the graph must not be null
	 * @return directGraph.size(), the size of this graph.
	 */
	public int size() {
		return directGraph.size();
	}
    
	/**
	 * return true if the graph is not empty other wise return false
	 * @requirs the graph must no be null
	 * @return directGraph.isEmpty(), retrun true if the graph is not empty 
	 *           otherwise return false.
	 */
	public boolean isEmpty(){
		return directGraph.isEmpty();		
	}
	
	/**
	 * return the string representation of this graph 
	 * @requirs the graph must not be null and node !=null and edge != null 
	 * @return graphReprsentaion, the string represenation of the graph 
	 */
	@Override 
	public String toString() {
		String  graphReprsentaion ="This graph can be represented as shown:";
		graphReprsentaion +="(The parent node is followed by its children nodes)\n";		
		for(T node:getAllNodes()) {
			String str ="\tNode "+ node +" -> "+ getChildren(node).toString() + "\n";
			graphReprsentaion += str;
		}
		graphReprsentaion = graphReprsentaion.trim().replaceAll("\\r\\n", "\n");
		return graphReprsentaion.replaceAll("\\r", "\n"); 
	}
	
	/**
	 * return true if the given object represent the same as this direct graph, 
	 * the same start node and GraphEdegs. GraphEdegs is the end node in the graph
	 * associated with edge labels. 
	 * @param obj, the given object need to be compared with this dierct graph object 
	 * @requires obj != null and DirectGraph != null and GraphEdges != null
	 * @return, it return true if obj represents the same direct graph object(start node 
	 * 			and GraphEdges, end node assoicated with edge label)
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals (Object obj) { 
		if (obj instanceof DirectGraph) { 
			// cast the given object(obj) to direct graph object
			DirectGraph<T, E> dig = (DirectGraph<T, E>) obj; 
			// set of start/parent nodes in this direct graph
			Set<T> setOfStarNode =  directGraph.keySet();
			
			// they are different object if there size not equal.
			if (this.directGraph.size() != dig.size()) {
				return false;  
				
			} else if(!setOfStarNode.containsAll(dig.directGraph.keySet())) { 
				// if they have different key set return false. 
				return false;
			} else if (!dig.directGraph.keySet().containsAll(setOfStarNode)) { 
				// if they have different key set return false. 
				return false;  
			}
			else {
				// if they have the same key set, check the equality of each children 
				// and edge label of each start/parent node
				for(T start: setOfStarNode) { 
					// end node associated with edge label of this direct
					// graph object.
	        		Set <GraphEdges<T,E>> endNodeEdge = directGraph.get(start);
	        		
	        		// end node associated with label of other direct graph object,
	        		// cast to Set<GraphEdges<T, E>
	        		Set <GraphEdges<T,E>> endNodeEdgeObj = (Set<GraphEdges<T, E>>)
	        				dig.directGraph.get(start); 
	        		
	        		// if the children associated with node(GraphEdge) for each start/parent
	        		// node in each object are not the same return false.
	        		if((!endNodeEdge.containsAll(endNodeEdgeObj))) { 
	        			return false;  
	        		} else if ((!endNodeEdgeObj.containsAll(endNodeEdge))) {
	        			return false; 
	        		}
	        	}
				
			}	  
		} else {
			// object obj not instanceof DirectGraph
			return false;
		}
		return true;   // they are the same object
	}
	
	/**
	 * Return the hash code of this direct graph object.  
	 * @return hash code of this direct graph  
	 */
	@Override
	public int hashCode() { 
		return directGraph.hashCode(); 
	}

	/**
	 * Check if the represenation of the invariant holds
	 */ 	
	private void checkRep() {  
		assert (!directGraph.equals(null)): "directGraph should never be null"; 
		//assert (!directGraph.containsKey(null)): ("start node never be null");
		for(T node: getAllNodes()){
			assert (!node.equals(null)): "node should not be null";
			assert (containsNode(node)): "DircetGraph should contain the node"; 
		}
	}
}
