package hw5;

/**
 * @author fentahun Reta
 * This class represents the edge associated with its destination node in the graph.
 * For instance if there is a graph between node (n1) and (n2) with ena edge e1 from n1 to
 * n2, the GraphEdges will be represented by n2(e1). That means n2 is the end node and e1
 * is the edge that connect from n1 to n2.
 *  
 *  Abstract function (AF):
 *  AF = for all edegLabel(such that between nodes n1 and n2) e1, e2 in the graph 
 *  such that e1 != e2  or n2(e1) != n2(e2)
 *  The two edge label between n1 and n2 should not be the same.
 *   
 *  Representation of invariants: 
 *  endNode != null and edgeLable != nul and GraphEdges !=null
 *
 * T: The end or destination node in the graph
 * E: The edge lable assocated with the end nod 
 */
public class GraphEdges <T extends Comparable<T>, E extends Comparable<E>>  
                                  implements Comparable<GraphEdges<T,E>>  { 
	
	private final T endNode;         // the end node of this edge
	private final E edgeLable;       // the lable of this edge 
	
	/**
	 * 
	 * @param endNode, the end or destination node of the graph 
	 * @param edgeLable, lable or name of the edge
	 * @requires endNode != null and edgeLable != null
	 * @effect create a new edge (GraphEdges) that is associated with the end node or point and label. 
	 */
	public GraphEdges(T endNode, E edgeLable) {
		this.edgeLable = edgeLable;  
		this.endNode = endNode;                   
		checkRep();  
	}
	
	/**
	 * return the end node of this edge
	 * @reuires GraphEdges is not null 
	 * @return getEndNode, the end point (destination) of this edge 
	 */
	public T getEndNode() {  
		checkRep(); 
		return endNode; 
	}
	
	/**
	 * return the edge lable or name of this edge 
	 * @reuires GraphEdges is not null
	 * @return edgeLable, return the lable of this edge
	 */
	public E getEdgeLable() { 
		checkRep();
		return edgeLable;
	}
	// public int numEdges( ) { return edgeLable; }
	
	/**
	 * return the string representation of this edge
	 * @requires endNode !=null and edgeLable != null
	 * @return strEdge, the string representation of this edge
	 */	
	@Override 
	public String toString() { 
		String strEdge = endNode.toString() + "(" + edgeLable.toString() + ")";
		return strEdge;  
	}
	
	/**
	 * return true if the given edge object represent the same edge(end point and edge lable) with this edge.
	 * @param obj, the given edge object need to be compared with this edge 
	 * @requires obj != null and GraphEdges != null
	 * @return, it return true if obj epresent the same edge(end point and edge lable) with this edge.
	 */
	@SuppressWarnings("rawtypes") 
	@Override
	public boolean equals(Object obj) {
		checkRep();
		if (!(obj instanceof GraphEdges)) { 
			return false; 
		}		
		GraphEdges lable = (GraphEdges) obj;   
		checkRep();
		return endNode.equals(lable.endNode) && edgeLable.equals(lable.edgeLable); 
	}
	
	/**
	 * Return the hash code of this edge.  
	 * @return hash code of this edge 
	 */
	@Override
	public int hashCode() { 
		checkRep(); 
		return 31*endNode.hashCode() + 29*edgeLable.hashCode();     
	}
	
	/**
	 * It compares this edge with other edge(edg) object for order. Returns a negative
	 * integer, zero, or positive integer value if this edge less than, equal to, or
	 * greater than to edg respectivelly.
	 * @requires edge is null and this edge must not be null.
	 * @param edg, the edge object need to compare withis edge
	 * @return negative integer, zero, or a positive integer  value if this edge less than, equal to, or
	 *         greater than to edg respectivelly.
	 */
	@Override
	public int compareTo(GraphEdges<T, E> edg) {  
		checkRep();
		// compare the end node of the edges 
		if (!(endNode.equals(edg.endNode))) {
			checkRep();
			return endNode.compareTo(edg.endNode);
		}

		// if end node is the same check the edges lable name  
		if (!(edgeLable.equals(edg.edgeLable))) {
			checkRep();
			return edgeLable.compareTo(edg.edgeLable);
		}
		checkRep();
		return 0;   
	}
 
	/**
	 * Checks that if the representation invariant holds.
	 */
	private void checkRep() {
		assert(!edgeLable.equals(null)) : "edgeLable never be null";  // need to be more check 
		assert(!endNode.equals(null)) : "endNode never be null ";  
	}	 
}
