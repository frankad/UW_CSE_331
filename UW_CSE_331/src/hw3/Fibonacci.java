/**
 * This is part of HW0: Environment Setup and Java Introduction for CSE 331.
 */
package hw3;

/**
 * Fibonacci calculates the <var>n</var>th term in the Fibonacci sequence.
 *
 * The first two terms of the Fibonacci sequence are both 1,
 * and each subsequent term is the sum of the previous two terms.
 *
 * @author mbolin
 */
public class Fibonacci {

    /**
     * Calculates the desired term in the Fibonacci sequence.
     *
     * @param n the index of the desired term; the first index of the sequence is 0
     * @return the <var>n</var>th term in the Fibonacci sequence
     * @throws IllegalArgumentException if <code>n</code> is not a nonnegative number
     */
    public int getFibTerm(int n) {
    	// if n is negative, throw exceptions
        if (n < 0) {
            throw new IllegalArgumentException(n + " is negative");
        } else if (n <= 1) { //For the base case the index should be 0 and 1, because our first index is 0
            return 1;
        } else { // In the inductive step, The next Fibonacci number is the sum of the previous 2 Fibonacci number, not subtracting.
            return getFibTerm(n - 1) + getFibTerm(n - 2);
        }
    }

}
