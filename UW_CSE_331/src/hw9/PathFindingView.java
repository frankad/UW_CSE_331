package hw9;

import java.awt.BasicStroke;
import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import javax.imageio.ImageIO;
import javax.swing.JComponent; 

import hw5.GraphEdges;
import hw8.PathFindingModel;
import hw8.PointCoordinate;

/**
 * This class represents the view of path finding. It displays the 
 * path on the  map, and it enders the contents of a model and
 * specifies how the model data should be presented.   
 */
public class PathFindingView extends JComponent { 
	
	// to verify that the sender and receiver of a serialized 
	// object, to make the loaded classes are compatible
	// with respect to serialization.	
	private static final long serialVersionUID = 1L;
	
	// the [path finding model from hw8
	private PathFindingModel model;
	
	// the edn/destination building associated with its edge weight  
	// or physical distance in the shortest route .
	private List<GraphEdges<String, Double>> shortestRoute ;
	
	// the coordinates of the building in the route
    private List<PointCoordinate> buildingCoordOnRoute;  
	
	// set up the campus map image onto which we will draw
	private BufferedImage img;
	
	// The ratio of width and height of the window to the 
	// actual campus map width and height respectivelly.
	double widthRatio, heightRatio;  
	
	/**
	 * Construct the GUI path finding view of the campus 
	 * 
	 * @param model the path finding model
	 * @requires model != null
	 * @effects the campus map is loaded
	 */
	public PathFindingView (PathFindingModel model) { 
		this.model = model;
		shortestRoute = null;
		buildingCoordOnRoute = new ArrayList<PointCoordinate>();
		widthRatio = 0.0;
		heightRatio = 0.0;
		 
		// set the size of this container as preffered size
		// to appear naturally on the screen.
		this.setPreferredSize(new Dimension (1000, 740));
		
		// read and load the image from the given file path if the given
		// path is well formatted. other wise throw exception.
		try {
		    img = ImageIO.read(new File("src/hw8/data/campus_map.jpg"));
		} catch (IOException e) {
		    e.printStackTrace();
		}	
	}
	
	/**
	 * It finds the shortest distance from start to end/destination 
	 * building in the campus
	 * 
	 * @param start starting building's or location to find the path/route
	 * @param end  destination building's or location to find the path/route
	 * @requires start != null and end !=null 
	 * @return distance the shortest path/route from the starting building 
	 * 		(start) to ending building(end).
	 */	
	public double routeDistnace(String start, String end) {
		Map<String, PointCoordinate> abbNameToCoord = 
				model.getShortNameToCoordinate();
	   
		// the statr and end building location, "x,y"
		String satrtLocation = "";
		String endLocation = "";
		
		// append the x and y component of the the start 
		// end location
		satrtLocation += abbNameToCoord.get(start).getX()  + 
				"," + abbNameToCoord.get(start).getY();
		endLocation += abbNameToCoord.get(end).getX()  + 
				"," + abbNameToCoord.get(end).getY();
		
		// find the shortest route from satrt to the end/
		// destination building
		shortestRoute = model.findShortestRoute(satrtLocation, 
				endLocation); 
		 
		// the distance from the start to end building is the edge 
		// weight associate with last building
		double distance = shortestRoute.
				get(shortestRoute.size() -1).getEdgeLable(); 
		
		// store the building coordinates that are part of the current
		// route, from start to end building
		for (GraphEdges<String,Double> ndEdge : shortestRoute) {
			String location = ndEdge.getEndNode();
			buildingCoordOnRoute.add(model.getCoordOfBuilding(location)); 
		}
		
		repaint();
		// return the distnce covered from start to end building
		return distance;	 		
	} 
	
	/**
	 * reset the operation and can be accomplished by resetting the 
	 * the field back to its original state, view or route to its 
	 * initial state.
	 */
	public void reset() {
		shortestRoute = null;
		repaint();
	}
	
	/**
	 * It paints the components on the given graphics.
	 * 
	 * @param g the graphics used when the component painted
	 * @modifies the view of the GUI
	 * @effect It display the map and the path/route between two buildings
	 * 		  when the user selects the starting and ending building. It 
	 * 		  also clear out the route displayed on the map when the user 
	 *        choose to rest, and resize the map when the main window is resized
	 */
	@Override   
	public void paintComponent(Graphics g) {
		//call the super class of the paint
        super.paintComponent(g);
        
        // cast the Graphics object back to Graphics2D to make graphics2d 
        // methods availabl. So, Graphics2D object passed as argument when
        // calling back the painting methods
        Graphics2D g2 = (Graphics2D) g;
        
        // draw the image/map of the campus, the first 0, 0 arguments
        // specifies the position for the top-left of the image/window. 
        // Followed by the width and height dimensions on the window. 
        // since we are using BufferedImage ImageObserver is set to null
        g2.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0, 
        		img.getWidth(), img.getHeight(), null); 
        
        // the width and height ratio value based on window and campus 
        // map height and width.
        widthRatio = (1.0*getWidth())/img.getWidth();
		heightRatio = (1.0*getHeight())/img.getHeight();
		
		// draw the path line by setting the color of the path
		// to yellow color.
		g2.setColor(Color.YELLOW);
		// stroke object with the specified width of the line to 
		// be drawn on the campus image.
		g2.setStroke(new BasicStroke(4));
		
		// draw a line from the starting to the destination building or
		// connect each building across the route from start to end building
		if ( shortestRoute != null) {
			
			// the starting building coordinate on the map.
			int startX = (int) Math.round(buildingCoordOnRoute.get(0).getX() * widthRatio);
			int startY = (int) Math.round(buildingCoordOnRoute.get(0).getY() * heightRatio);
			
			// Set up the current building as the starting coordinate
			// position of the shortest path.
			int currentStartX = startX;
			int currentStartY = startY; 
			
			// loop through each building coordination in the route and
			// draw the path by connecting each consequent location
			for (PointCoordinate bc: buildingCoordOnRoute) {
				// coordinate of the current end/destination building 
				int endX = (int) Math.round(bc.getX()*widthRatio);
				int endY = (int) Math.round(bc.getY()*heightRatio); 
				
				// draw a line from current starting building to 
				// current end/destnation building
				g2.drawLine(currentStartX, currentStartY, endX, endY);
				
				// update the current starting building
				currentStartX = endX;
				currentStartY = endY;		
			}
			
			// Mark the starting building with red circle. The first two
			// parameters are x, y coordinates of the ovale shape to be 
			// drawn. The last two parameters are the width and the height
			// of the shape. 
			g2.setColor(Color.RED);
			g2.fillOval(startX - 4, startY - 4, 15, 15);						
			
            // mark the end/destination building with blue circle
			g2.setColor(Color.BLUE);
			g2.fillOval(currentStartX - 4, currentStartY - 4, 15, 15);		
		}
	}
}
