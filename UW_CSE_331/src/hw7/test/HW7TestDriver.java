package hw7.test;

import java.io.*;
import java.util.*;
import hw5.DirectGraph;
import hw5.GraphEdges;
import hw7.MarvelPaths2; 


/**
 * This class implements a testing driver which reads test scripts
 * from files for your graph ADT and improved MarvelPaths application
 * using Dijkstra's algorithm.
 **/
public class HW7TestDriver {


    public static void main(String args[]) {
    	try {
  		  if (args.length > 1) {
  			  printUsage();
  			  return;
  		  }
  		  HW7TestDriver td;
  		  if (args.length == 0) {
  			  td = new HW7TestDriver (new InputStreamReader(System.in), 
                                        new OutputStreamWriter(System.out));
  		  } else {
  			  String fileName = args[0];
                File tests = new File (fileName);
                
                if (tests.exists() || tests.canRead()) {
              	  td = new HW7TestDriver(new FileReader(tests),
                            new OutputStreamWriter(System.out));
                } else {
              	  System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return; 
                }
  		  }
  		  td.runTests();
  	  } catch (IOException e) {
  		  System.err.println(e.toString());
            e.printStackTrace(System.err);
  	  }
    }
    
    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw6.test.HW6TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw6.test.HW6TestDriver");
    }
    
    /** String -> Graph: maps the names of graphs to the actual graph **/
    private final Map<String, DirectGraph<String, Double>> graphs =
 		   new HashMap<String, DirectGraph<String, Double>>();  
    private final PrintWriter output; 
    private final BufferedReader input;
    
    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW5TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW7TestDriver(Reader r, Writer w) {
    	input = new BufferedReader(r);
        output = new PrintWriter(w);
    }
    
    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests() 
    	throws IOException
    {
    	String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            }
            else
            {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }
                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    	
    }
    
    private void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else if (command.equals("LoadGraph")) {
         	   loadGraph(arguments);
            } else if (command.equals("FindPath")) {
         	   findPath(arguments);
            } else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString()); 
        }
    }
    
    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to createGraph: " + arguments);
        }
        String graphName = arguments.get(0);
        createGraph(graphName);
    }
    
    private void createGraph(String graphName) {
        // Insert your code here. 
        graphs.put(graphName, new DirectGraph<String, Double>());
        output.println("created graph " + graphName); 
    }
    
    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1); 

        addNode(graphName, nodeName);
    }
    
    private void addNode(String graphName, String nodeName) {
        // Insert your code here.
 		DirectGraph<String, Double> dg = graphs.get(graphName);
 		dg.addNode(nodeName);  
         output.println("added node " + nodeName + " to " + graphName);
    }
    
    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1); 
        String childName = arguments.get(2); 
        double edgeLabel = 0.0;
        try {
        	edgeLabel = Double.parseDouble(arguments.get(3));
        } catch (NumberFormatException e) {
        	throw new CommandException ("argument is not double type number.");
        }

        addEdge(graphName, parentName, childName, edgeLabel);
    }
    private void addEdge(String graphName, String parentName, String childName,
            Double edgeLabel) {
        // Insert your code here.
        DirectGraph<String, Double > dgr = graphs.get(graphName);
        dgr.addNode(parentName);
        dgr.addNode(childName);
        dgr.addEdge(parentName, childName, edgeLabel);
        output.println(String.format("added edge %.3f", edgeLabel) + " from " + parentName + " to " + childName +" in " +graphName); 
    }
    
    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        // Insert your code here.
    	DirectGraph<String, Double> dgr =graphs.get(graphName);
    	String result = graphName + " contains: ";
    	for(String nd: dgr.getAllNodes()) {
    		result += nd + " ";
    	}
        output.println(result);   
    }
    
    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }
    
    private void listChildren(String graphName, String parentName) {
        // Insert your code here.
 	   DirectGraph<String, Double> dgr= graphs.get(graphName);
        String  outPutResult = "the children of "+ parentName + " in " +graphName + " are: ";
        
        if(!dgr.containsNode(parentName)) {
     	   outPutResult += "node " + parentName + " is not found in this graph";
        } else {
        	// get and copy the children node assocaiited with edges of the parent node(parentName)
        	List<GraphEdges<String, Double>> result = 
        			new ArrayList<GraphEdges<String, Double>>(dgr.edgesOfNode(parentName));
        	// get list of children and append each end node and edge label to the out put result. 
        	for(GraphEdges<String, Double> eg: result) {
        		// append each edge label
        		outPutResult += eg.getEndNode() + "("+ String.format("%.3f", eg.getEdgeLable()) + ") ";   	
        	} 
        }
        output.println(outPutResult);  
    } 
    
    private void loadGraph(List<String> arguments) throws Exception {
    	if (arguments.size() != 2) {
    		throw new CommandException("Bad arguments to loadGraph: " + arguments);
    	}
 	    String graphName = arguments.get(0);
        String filename = arguments.get(1);
        loadGraph(graphName, filename);    
    }
    
    
    private void loadGraph(String graphName,String fileName) throws Exception {  
    	fileName = "src/hw7/data/" + fileName;
        graphs.put(graphName, MarvelPaths2.graphBuilding(fileName)); 
        output.println("loaded graph " + graphName); 
    }
    
    private void findPath(List<String> arguments) {
 	   if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to findPath: " + arguments);
        }
 	   
 	    String graphName = arguments.get(0);
        String start = arguments.get(1).replace('_', ' ');
        String end = arguments.get(2).replace('_', ' ');
        
        findPath(graphName, start, end);
    }
    
    private void findPath(String graphName, String start, String end) {
 	   DirectGraph<String, Double> g = graphs.get(graphName);
 	   
 	   if(!g.containsNode(start) && !g.containsNode(end)) {
 		   output.println("unknown character " + start);
 		   output.println("unknown character " + end);  
 	   } else if (!g.containsNode(start)) {
 		   output.println("unknown character " + start);
 	   } else if (!g.containsNode(end)) {
 		   output.println("unknown character " + end);
 	   } else {
 		   // the current starting node
 		   String tempStart = start;
 		   String result = "path from " + tempStart + " to " + end + ":\n";
 		   List<GraphEdges<String, Double>> path = MarvelPaths2.findMinimumCostPath(g, start, end);
 		   
 		   // if the starting and ending are equal nothing to add to the path
 		   if (start.equals(end)) {
 			   result += "total cost: 0.000";
 		   } else if (path == null) {
 			   result += "no path found";
 		   } else {
 			   double cost = 0.0;
 			  path = path.subList(1, path.size());
 			   for (GraphEdges<String, Double> eg : path) {
 				   // get each transition cost such as, n1 to n2, n2 to n3, n3 to n4 etc.
 				   result += tempStart + " to " + eg.getEndNode() + " with weight " 
 						   + String.format("%.3f", eg.getEdgeLable() - cost) + "\n";
 				   // update the current node to be expanded/ find its transition path/distance
				   tempStart = eg.getEndNode();
			       // update the total cost till this end node
				   cost = eg.getEdgeLable(); 
			   }
			   result += "total cost: " + String.format("%.3f", cost); 
 		   }
 		   output.println(result); 	 	   
 	   }	   
    }
    
    
    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }
        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
