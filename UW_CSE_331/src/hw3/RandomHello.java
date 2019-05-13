package hw3;

import java.util.*;
//import java.util.Random;

/**
 * RandomHello selects a random greeting to display to the user.
 * @author Toshiba
 *
 */
public class RandomHello {
	/**
     * Uses a RandomHello object to print
     * a random greeting to the console.
     */
    public static void main(String[] argv) {
        RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
    }

    /**
     * @return a random greeting from a list of five different greetings.
     */
    public String getGreeting() {
        // YOUR CODE GOES HERE
    	Random randomGenerator = new Random();
    	//array of string that contain a lists of greeting 
    	String[] greetings = {"Hello World","Hola Mundo","Bonjour Monde","Hallo Welt","Ciao Mondo"};
    	//index of the randomly generated greeting
    	int idx = randomGenerator.nextInt(5);
    	return greetings[idx];
    }


}
