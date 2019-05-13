package hw6.test;
import java.io.*;
import java.util.*;

import hw5.DirectGraph;
import hw5.GraphEdges;
import hw6.MarvelPaths; 


/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph, the Marvel parser, and the BFS
 * algorithm.
 **/
public class HW6TestDriver {


  public static void main(String args[]) {
	  try {
		  if (args.length > 1) {
			  printUsage();
			  return;
		  }
		  HW6TestDriver td;
		  if (args.length == 0) {
			  td = new HW6TestDriver (new InputStreamReader(System.in), 
                                      new OutputStreamWriter(System.out));
		  } else {
			  String fileName = args[0];
              File tests = new File (fileName);
              
              if (tests.exists() || tests.canRead()) {
            	  td = new HW6TestDriver(new FileReader(tests),
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
   private final Map<String, DirectGraph<String, String>> graphs =
		   new HashMap<String, DirectGraph<String, String>>();  
   private final PrintWriter output; 
   private final BufferedReader input;
   
   /**
    * @requires r != null && w != null
    *
    * @effects Creates a new HW5TestDriver which reads command from
    * <tt>r</tt> and writes results to <tt>w</tt>.
    **/
   public HW6TestDriver(Reader r, Writer w) {
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
           throw new CommandException("Bad arguments to CreateGraph: " + arguments);
       }

       String graphName = arguments.get(0);
       createGraph(graphName);
   }

   private void createGraph(String graphName) {
       // Insert your code here. 
       graphs.put(graphName, new DirectGraph<String, String>());
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
		DirectGraph<String, String> dg = graphs.get(graphName);
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
       String edgeLabel = arguments.get(3);

       addEdge(graphName, parentName, childName, edgeLabel);
   }

   private void addEdge(String graphName, String parentName, String childName,
           String edgeLabel) {
       // Insert your code here.
       DirectGraph<String, String > dgr = graphs.get(graphName);
       dgr.addNode(parentName);
       dgr.addNode(childName);
       dgr.addEdge(parentName, childName, edgeLabel);
       output.println("added edge "+edgeLabel+ " from " + parentName + " to " + childName +" in " +graphName); 
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
   	DirectGraph<String, String> dgr =graphs.get(graphName);
   	String result = graphName + " contains: ";
   	for(String nd: dgr.getAllNodes()) {
   		result +=nd + " ";
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
	   DirectGraph<String, String> dgr= graphs.get(graphName);
       String  outPutResult = "the children of "+parentName + " in " +graphName + " are: ";
       
       if(!dgr.containsNode(parentName)) {
    	   outPutResult += "node " + parentName + " is not found in the graph";
       } else {
       
       //Set<GraphEdges<String, String>> result = dgr.edgesOfNode(parentName);
       // get and copy the children node assocaiited with edges of the parent node(parentName)
       List<GraphEdges<String, String>> result = 
    		   new ArrayList<GraphEdges<String, String>>(dgr.edgesOfNode(parentName));
      // sort out lists of the GraphEdges object in lexicographic ordering  
       Collections.sort(result,MarvelPaths.edgeComparator());
       for(GraphEdges<String, String> eg: result) {  
    	   //String nodeWithedge = eg.getEndNode()+"(" + eg.getEdgeLable()+ ") ";
    	   // append each edge label
       	   outPutResult += eg +" ";   	
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
   	fileName = "src/hw6/data/" + fileName;
       graphs.put(graphName, MarvelPaths.buildingGraph(fileName));
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
	   DirectGraph<String, String> g = graphs.get(graphName);
	   
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
		   String result = "path from " + start + " to " + end + ":";
		   List<GraphEdges<String,String>> path = MarvelPaths.findPathBFS(g, start, end);
		   
		   // if the starting and ending are equal nothing to add to the path
		   if (start.equals(end)) {
			   result += "";
		   } else if (path.isEmpty()) {
			   result += "\n" + "no path found";
		   } else {
			   for (GraphEdges<String, String> eg: path) {
				   result += "\n" + tempStart + " to " + eg.getEndNode() + " via "
						   + eg.getEdgeLable();
				   tempStart = eg.getEndNode(); // update the current starting node
			   }
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
