package hw4;

import java.util.Iterator;
import java.util.Stack;

/**
 * <b>RatPolyStack</B> is a mutable finite sequence of RatPoly objects.
 * <p>
 * Each RatPolyStack can be described by [p1, p2, ... ], where [] is an empty
 * stack, [p1] is a one element stack containing the Poly 'p1', and so on.
 * RatPolyStacks can also be described constructively, with the append
 * operation, ':'. such that [p1]:S is the result of putting p1 at the front of
 * the RatPolyStack S.
 * <p>
 * A finite sequence has an associated size, corresponding to the number of
 * elements in the sequence. Thus the size of [] is 0, the size of [p1] is 1,
 * the size of [p1, p1] is 2, and so on.
 * <p>
 */
public final class RatPolyStack implements Iterable<RatPoly> {

  private final Stack<RatPoly> polys;

  // Abstraction Function:
  // Each element of a RatPolyStack, s, is mapped to the
  // corresponding element of polys.
  //
  // RepInvariant:
  // polys != null &&
  // forall i such that (0 <= i < polys.size(), polys.get(i) != null

  /**
   * @effects Constructs a new RatPolyStack, [].
   */
  public RatPolyStack() {
    polys = new Stack<RatPoly>();
    checkRep();
  }

  /**
   * Returns the number of RayPolys in this RatPolyStack.
   *
   * @return the size of this sequence.
   */
  public int size() {
    // Fill in this method
	  return polys.size();
  }

  /**
   * Pushes a RatPoly onto the top of this.
   *
   * @param p The RatPoly to push onto this stack.
   * @requires p != null
   * @modifies this
   * @effects this_post = [p]:this
   */
  public void push(RatPoly p) {
    // check and push the RatPoly p at the top of the stack.
	checkRep();
    polys.push(p);
	checkRep();  
  }

  /**
   * Removes and returns the top RatPoly.
   *
   * @requires this.size() > 0
   * @modifies this
   * @effects If this = [p]:S then this_post = S
   * @return p where this = [p]:S
   */
  public RatPoly pop() {
	  checkRep();
	  if (polys.size() == 0) {
		  return new RatPoly();		  
	  } else {
		  checkRep();
		  return polys.pop(); 
	  } 
  }

  /**
   * Duplicates the top RatPoly on this.
   *
   * @requires this.size() > 0
   * @modifies this
   * @effects If this = [p]:S then this_post = [p, p]:S
   */
  public void dup() {
	  // if stack is zero nothing to do
	  if (polys.size() == 0) {
		  return;
	  } else {		  
		  RatPoly temp = polys.peek();
		  polys.push(temp);
	  }
	  checkRep();
  }

  /**
   * Swaps the top two elements of this.
   *
   * @requires this.size() >= 2
   * @modifies this
   * @effects If this = [p1, p2]:S then this_post = [p2, p1]:S
   */
  public void swap() {
     // if stack is less than 2 nothing to do
	 checkRep();
	 if (polys.size() < 2) {
		 return;
	 } else {
		 RatPoly temp1 = polys.pop(); 
		 RatPoly temp2 = polys.pop();
		 polys.push(temp1);
		 polys.push(temp2); 
	 } 
	 checkRep();
  }

  /**
   * Clears the stack.
   *
   * @modifies this
   * @effects this_post = []
   */
  public void clear() {
    // remove all elements and make the stack empty
    polys.clear();
    checkRep();
  }

  /**
   * Returns the RatPoly that is 'index' elements from the top of the stack.
   *
   * @param index The index of the RatPoly to be retrieved.
   * @requires index >= 0 && index < this.size()
   * @return If this = S:[p]:T where S.size() = index, then returns p.
   */
  public RatPoly getNthFromTop(int index) {
	// create a stack that holds copy of polys
	Stack<RatPoly> copyPolys = new Stack<RatPoly>();
	copyPolys.addAll(polys); // add all elements of polys to copyPolys
	// remove all elements before the index of elements to be retrieved
	for (int i = 0; i < index; i++) {
		copyPolys.pop();	
	}
	RatPoly temp = copyPolys.pop(); // get element positioned at index 
	checkRep();
	return temp;   
  }

  /**
   * Pops two elements off of the stack, adds them, and places the result on
   * top of the stack.
   *
   * @requires this.size() >= 2
   * @modifies this
   * @effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 + p2
   */
  public void add() {
	checkRep();
	if (polys.size() >= 2) {
		RatPoly p1 = polys.pop();
		RatPoly p2 = polys.pop();
		polys.push(p1.add(p2));
	} 
	checkRep(); 
  }

  /**
   * Subtracts the top poly from the next from top poly, pops both off the
   * stack, and places the result on top of the stack.
   *
   * @requires this.size() >= 2
   * @modifies this
   * @effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 - p1
   */
  public void sub() {
	checkRep();
    if (polys.size() >= 2) {
    	RatPoly p1 = polys.pop();
		RatPoly p2 = polys.pop();
		polys.push(p2.sub(p1)); 
    }
    checkRep();
  }

  /**
   * Pops two elements off of the stack, multiplies them, and places the
   * result on top of the stack.
   *
   * @requires this.size() >= 2
   * @modifies this
   * @effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 * p2
   */
  public void mul() {
	checkRep(); 
	if (polys.size() >= 2) {
	    RatPoly p1 = polys.pop();
		RatPoly p2 = polys.pop();
		polys.push(p1.mul(p2));
	}
	checkRep();
  }

  /**
   * Divides the next from top poly by the top poly, pops both off the stack,
   * and places the result on top of the stack.
   *
   * @requires this.size() >= 2
   * @modifies this
   * @effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 / p1
   */
  public void div() {
	checkRep();
	if (polys.size() >= 2) {
	    RatPoly p1 = polys.pop();
		RatPoly p2 = polys.pop();
		polys.push(p2.div(p1));
	}
	checkRep();
  }

  /**
   * Pops the top element off of the stack, differentiates it, and places the
   * result on top of the stack.
   *
   * @requires this.size() >= 1
   * @modifies this
   * @effects If this = [p1]:S then this_post = [p2]:S where p2 = derivative
   *          of p1
   */
  public void differentiate() {
	checkRep();
	if (polys.size() >= 1) {
	    RatPoly p1 = polys.pop();
		polys.push(p1.differentiate());   
	}
	checkRep();
  }

  /**
   * Pops the top element off of the stack, integrates it, and places the
   * result on top of the stack.
   *
   * @requires this.size() >= 1
   * @modifies this
   * @effects If this = [p1]:S then this_post = [p2]:S where p2 = indefinite
   *          integral of p1 with integration constant 0
   */
  public void integrate() {
	checkRep();
	if (polys.size() >= 1) {
		RatPoly p1 = polys.pop();
		polys.push(p1.antiDifferentiate(RatNum.ZERO));    
	}
	checkRep();   
  }

  /**
   * Returns an iterator of the elements contained in the stack.
   *
   * @return an iterator of the elements contained in the stack in order from
   *         the bottom of the stack to the top of the stack.
   */
  @Override
  public Iterator<RatPoly> iterator() {
    return polys.iterator();
  }

  /**
   * Checks that the representation invariant holds (if any).
   */
  private void checkRep() {
    assert (polys != null) : "polys should never be null.";
    
    for (RatPoly p : polys) {
        assert (p != null) : "polys should never contain a null element.";
    }
  }
}
