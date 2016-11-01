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
 * Test case 1: AADCBDEDEAAD
 * Test case 2: DAABEDAACDED
 * Test case 3: EACADEABDADD
 */

import java.util.Stack;

public class Main {
	
	static long startTime;

	public static void main(String[] args) {
		// Receive the starting configuration from the user
		String initialConfiguration = InputHandler.takeInput();
		// Find the shortest path to solve the puzzle
		startTime = System.currentTimeMillis();
		Stack<ConfigurationNode> shortestPath = PuzzleSolver.findShortestSolution(initialConfiguration);
		// Print the moves taken to solve the puzzle
		printSolution(shortestPath);
	}
	
	// Given a stack of configurations in the best solution, prints the moves taken to get to each one.
	// This will print all moves that need to be taken to solve the puzzle in the shortest amount of moves possible.
	private static void printSolution(Stack<ConfigurationNode> solutionPath) {
		ConfigurationNode previousConfiguration = null;
		ConfigurationNode nextConfiguration = solutionPath.pop();
		int moveCounter = 0;
		System.out.println();
		System.out.println();
		System.out.println();
		while (!solutionPath.isEmpty()) {
			moveCounter++;
			previousConfiguration = nextConfiguration;
			nextConfiguration = solutionPath.pop();
			String move = nextConfiguration.getMoveTaken();
			System.out.println();
			System.out.println("Move #" + moveCounter + ": " + move);
			System.out.println("Starting config: ");
			PuzzleSolver.moveGenerator.displayBoard(PuzzleSolver.moveGenerator.generateGrid(previousConfiguration.getStringRepresentation().toCharArray()));
			System.out.println();
			PuzzleSolver.moveGenerator.displayBoard(PuzzleSolver.moveGenerator.generateGrid(nextConfiguration.getStringRepresentation().toCharArray()));
			System.out.println();
		}
		
		float secondsTaken = (System.currentTimeMillis() - startTime)/1000f;
		System.out.println("Found solution consisting of " + moveCounter + " moves in " + secondsTaken + " seconds!");
		
	}

}
