package hw9;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import hw8.PathFindingModel;

/**
 * It is the campus path finding GUI. It has view and controller 
 * for the path/route finding using the campus data.  
 */
public class PathFindingGUI extends JFrame { 
	// to verify that the sender and receiver of a serialized 
	// object
	private static final long serialVersionUID = 1L;

	// PathFindingModel from hw8
    private PathFindingModel model;
    
    // creat a JFrame, top level container that hold every component
	// implemented in controller and view. Overall physical windows
	// that are seen on the screen. 
	JFrame frame;
     
    // title or the text that appears in the frame title bar
    private static final String TITLE = "Campus Path Finder";
    
    /**
     * It constructs the GUI campus path/route finding 
     * @param md the the campus path finding model
     * @requires md !=null
     * @effects create a window to dispaly the view of the map
     */
    public PathFindingGUI (PathFindingModel md) {
    	
    	model = md;
    	// set the title bar that appears in the frame 
    	frame = new JFrame(TITLE);
    	
    	// Preferred width and height of the frame; the size should 
    	// be appear naturally on the screen and to seen later.
    	frame.setPreferredSize(new Dimension(1024, 768));
    	
    	// when the frame window closed, exit the program
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	// allow the frame itself to be resized
    	frame.setResizable(true); 
    	
    	// create the path finding view and controller object 
    	PathFindingView vw = new PathFindingView (model);
    	PathFindingController control = new  PathFindingController(model, vw );
    	
    	// Set the preferred width and height of the controller;
    	control.setPreferredSize(new Dimension(1024, 56)); 
    	
    	// add control and view using  border layout that determines 
    	// the positions, the view goes to center and controller to  
    	// north side of the frame.  
    	frame.add(control, BorderLayout.NORTH); 
    	frame.add(vw, BorderLayout.CENTER);
    	
    	// makes the frame's size to fit the components
    	frame.pack();
    	
    	// make the frame to display and visible on the screen
    	frame.setVisible(true);
    			
    }
	
	

}
