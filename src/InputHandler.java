/*
 * CS 560 Wooden Puzzle Solver
 * Spring 2015
 * Professor Root
 * SDSU
 * 
 * Authors:
 * 
 * Matt Palamar
 * Nathan Adams
 * Rodney Alacon
 * Zach Ribera
 * 
 */

import java.util.Scanner;


public class InputHandler {
	
	// Get keyboard input from the user
	public static String takeInput() {
		Scanner keyboard = new Scanner(System.in);
		prompt();
		String input = keyboard.nextLine();
		// Continuously ask for input until the input given represents a valid configuration
		while (!MoveGenerator.isValidConfiguration(input)) {
			prompt();
			input = keyboard.nextLine();
		}
		keyboard.close();
		return input;
	}
	
	// Tell the user the expected format for the input
	private static void prompt() {
		System.out.println("Please enter the string representation of the puzzle's inital configuration.");
		System.out.println("Enter the letter-representation of each block as it appears in the configuration from left-to-right, top-to-bottom.");
		System.out.println("1x2 Block: A");
		System.out.println("2x1 Block: B");
		System.out.println("2x2 Block: C");
		System.out.println("1x1 Block: D");
		System.out.println("1x1 Empty: E");
	}

}
