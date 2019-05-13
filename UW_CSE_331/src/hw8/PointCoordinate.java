package hw8;

/**
 * This class reprsents the point/pixel coordinates of the building. 
 * It is 2D point representation of the building location.
 * 
 * @specfield p.x_coordinate : double
 * @specfield p.y_coordinate : double
 */
public class PointCoordinate { 
		
	// Rep invariant:
	//		x != null and y != null
			
	// Abstract function(AF):
	// 		AF(this) = it represents the coordinates of some point p such that, 
	//          the x and y coordinates of p are, p.x and p.y respectivelly. 
	//			this.x = p.x_coordinate; 
	//			this.y = p.y_coordinate;
	
	// the x and y coordinate of the point  
	public Double x;    
	public Double y;  
	
	/**
	 * It is the construct of coordinates of a point/pixel.
	 * 
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 */
	public PointCoordinate (double x, double y) {
		 this.x = x;
		 this.y = y;
		 checkRep();
	 }
	
	/**
	 * It returns the x coordinate of the given point
	 * @return x, the x coordinate of the point
	 */
	public double getX() { 
		checkRep();
		return x; 
	}
	
	/**
	 * It returns the y coordinate of this point
	 * 
	 * @return y, the y coordinate of the point
	 */
	public double getY() { 
		checkRep();
		return y;
	}
	
	/**
	 * return the string representation of this point coordinate
	 * @requires x !=null and y != null
	 * @return strPoint, the string representation of this cordinate point
	 */	
	//@Override 
	public String toString() { 
		String strPoint = "(" + x.toString() + "," + y.toString() + ")"; 
		return strPoint;  
	}
	
	/**
	 * It return true if the given coordinate point object represent the same 
	 * coordinate of this point coordinate.
	 * 
	 * @param obj, the given object need to be compared with this Point coordinate 
	 * @return it return true if obj represents the same coordinate point with this
	 *		   coordinate.
	 */
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof PointCoordinate)){
			return false;
		}		
		PointCoordinate o = (PointCoordinate) obj;
		checkRep();
		return x.equals(o.getX())&& y.equals(o.getY());	 	
	}	
	
	/**
	 * Returns the hash code of this Coordinate point.
	 * 
	 * @return the hash code of this Coordinate point
	 */
	@Override
	public int hashCode() { 
		checkRep();
		return 29*x.hashCode() + 31*y.hashCode();
	}

	/**
	 * Determine the direction based on the angle theta between this coordinate
	 * point and the coordinate point p. The possible result will be N, NE, E, SE,
	 * S, SW, NW, or SW depending on the angle theta quadrant position and direction.
	 * 
	 * @param p, other coordinate point
	 * @return the direction of the coordinate p relative to this coordinate point
	 * 		   or based on the angle betweeen the two coordinate point. i,.e
	 * 		   N, NE, E, SE, S, SW, NW, or W
	 */
	public String getDirection(PointCoordinate p) { 
		// Returns the angle theta from the conversion of rectangular coordinates (x,y)
		// to polar coordinates, angle between this coordinate and the coordinate point p. 
	   	 double theta = Math.atan2(p.getY()- this.getY(),  p.getX() - this.getX()); 
	   	 
	   	 // return the direction based on angle theta rectangular quadrant postion
	   	 // it could be N, NE, E, SE, S, SW, NW, or W. 
	   	 if(theta >= -5*Math.PI/8 && theta <= -3*Math.PI/8) { 
	   		 return "N";
	   	 } else if(theta > -3*Math.PI/8 && theta < -Math.PI/8) {
	   		 return "NE";
	   	 } else if(theta >= - Math.PI/8 && theta <= Math.PI/8) {
	   		 return "E";
	   	 } else if(theta > Math.PI/8 && theta < 3*Math.PI/8) {
	   		 return "SE";
	   	 } else if(theta >= 3*Math.PI/8 && theta <= 5*Math.PI/8) {
	   		 return "S";
	   	 } else if(theta > 5*Math.PI/8 && theta < 7*Math.PI/8) {
	   		 return "SW";
	   	 } else if(theta > -7*Math.PI/8 && theta < -5*Math.PI/8) {
	   		 return "NW";  
	   	 } else {
	   		 return "W";     
	   	 }
   }
	
	/**
	 * Check if the  representation of the invariant holds.
	 */
	public void checkRep() {
		assert (!x.equals(null)): "x coordinate should not be null";
		assert (!y.equals(null)): "y coordinate should not be null";
	}
}