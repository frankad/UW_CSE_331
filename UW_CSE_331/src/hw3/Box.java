/**
 * This is part of HW0: Environment Setup and Java Introduction for CSE 331.
 */
package hw3;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


/**
 * This is a container can be used to contain Balls. The key
 * difference between a BallContainer and a Box is that a Box has a
 * finite volume. Once a box is full, a client cannot put in more Balls.
 */
public class Box implements Iterable<Ball> {

    /**
     * ballContainer is used to internally store balls for this Box
     */
    private BallContainer ballContainer;
    private double maxVolume;

    /**
     * Constructor that creates a new box.
     * @param maxVolume Total volume of balls that this box can contain.
     */
    public Box(double maxVolume) {
        // Your code goes here.  Remove the exception after you're done.
    	ballContainer = new BallContainer();
    	this.maxVolume = maxVolume;
    }

    /**
     * Implements the Iterable interface for this box.
     * @return an Iterator over the Ball objects contained
     * in this box.
     */
    public Iterator<Ball> iterator() {
        return ballContainer.iterator();
    }


    /**
     * This method is used to add Ball objects to this box of
     * finite volume.  The method returns true if a ball is
     * successfully added to the box, i.e., ball is not already in the
     * box and if the box is not already full; and it returns false,
     * if ball is already in the box or if the box is too full to
     * contain the new ball.
     * @param b Ball to be added.
     * @requires b != null.
     * @return true if ball was successfully added to the box,
     * i.e. ball is not already in the box and if the box is not
     * already full. Returns false, if ball is already in the box or
     * if the box is too full to contain the new ball. 
     */
    public boolean add(Ball b) {
        // Your code goes here.  Remove the exception after you're done.
    	// volume if this ball added. Then compare with the maximum volume.
    	double total_volume = getVolume()+ b.getVolume();  
    	if((!ballContainer.contains(b)) && (total_volume<=maxVolume)){
    		return ballContainer.add(b);  
    	}else{
    		return false;
    	}
    }

    /**
     * This method returns an iterator that returns all the balls in
     * this box in ascending size, i.e., return the smallest Ball
     * first, followed by Balls of increasing size.
     * @return an iterator that returns all the balls in this box in
     * ascending size.
     */
    public Iterator<Ball> getBallsFromSmallest() {
        // Your code goes here.  Remove the exception after you're done. 
    	ArrayList<Ball> orderedBalls= new ArrayList<Ball>(); 
    	Iterator<Ball> itr= ballContainer.iterator();
    	
		while(itr.hasNext()){
			
			orderedBalls.add(itr.next());  
		
		}
		Collections.sort(orderedBalls, new BallComparator()); 
		return orderedBalls.iterator();  
    }

    /**
     * Removes a ball from the box. This method returns
     * <tt>true</tt> if ball was successfully removed from the
     * container, i.e. ball is actually in the box. You cannot
     * remove a Ball if it is not already in the box and so ths
     * method will return <tt>false</tt>, otherwise.
     * @param b Ball to be removed.
     * @requires b != null.
     * @return true if ball was successfully removed from the box,
     * i.e. ball is actually in the box. Returns false, if ball is not
     * in the box.
     */
    public boolean remove(Ball b) {
        return ballContainer.remove(b);
    }

    /**
     * Each Ball has a volume. This method returns the total volume of
     * all the Balls in the box.
     * @return the volume of the contents of the box.
     */
    public double getVolume() {
       return ballContainer.getVolume();
    }

    /**
     * Returns the number of Balls in this box.
     * @return the number of Balls in this box.
     */
    public int size() {
        return ballContainer.size();
    }

    /**
     * Empties the box, i.e. removes all its contents.
     */
    public void clear() {
        ballContainer.clear();
    }

    /**
     * This method returns <tt>true</tt> if this box contains
     * the specified Ball. It will return <tt>false</tt> otherwise.
     * @param b Ball to be checked if its in box
     * @requires b != null.
     * @return true if this box contains the specified Ball. Returns
     * false, otherwise.
     */
    public boolean contains(Ball b) {
        return ballContainer.contains(b);
    }
    public class BallComparator implements Comparator<Ball> {
		/**
		 * Compare the volume of two ball's, then return 
		 * an integr number ( negative, zero, or positive) 
		 * to show if the first object is less than or equal to or greater
		 *  than the second object.
		 * 
		 * @param b1 the first ball
		 * @param b2 the second ball
		 * @return negative integer, zero, or a positive integer if the 
		 * first object is less than, equal to, or greater than the second
		 * object respectivelly.
		 */
		public int compare(Ball b1, Ball b2) {
			double val = Double.compare(b1.getVolume(), b2.getVolume());
			return (int)(val*100); 
		}
    }

    

}
