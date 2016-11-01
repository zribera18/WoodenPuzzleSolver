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

import java.util.Hashtable;
import java.util.Stack;
import java.util.ArrayList;


public class PuzzleSolver {
	
	// Table to store all configurations that have been found as we enumerate all possible configurations of the puzzle
	private static Hashtable<Integer,Integer> foundConfigurations = new Hashtable<Integer,Integer>();
	
	// Move Generator instance
	public static MoveGenerator moveGenerator = new MoveGenerator();
	
	private static boolean solved = false;

	// Given a starting configuration, returns a stack containing the shortest possible list of configurations
	// to move through in order to solve the puzzle
	public static Stack<ConfigurationNode> findShortestSolution(String initialConfiguration) {
		ArrayList<ConfigurationNode> currentLevel = new ArrayList<ConfigurationNode>();
		ArrayList<ConfigurationNode> nextLevel = new ArrayList<ConfigurationNode>();
		nextLevel.add(new ConfigurationNode(initialConfiguration,-1,-1,""));
		while (!solved) {
			// Set current level to contain all nodes in the bottom level of the tree
			currentLevel.clear();
			for (ConfigurationNode c : nextLevel)
				currentLevel.add(c);
			nextLevel.clear();
			// Loop through all nodes that were added to the tree in the previous iteration
			for (ConfigurationNode currentNode : currentLevel) {
				// Get all possible moves that can be made from each configuration in the current level
				ArrayList<ConfigurationNode> possibleMoves = moveGenerator.generatePossibleMoves(currentNode.getStringRepresentation());
				// For each possible move, add it to the tree if we haven't seen that configuration yet
				for (ConfigurationNode possibleMove : possibleMoves) {
					if (!configurationAlreadyFound(possibleMove)) {
						currentNode.addChild(possibleMove);
						nextLevel.add(possibleMove);
						// Check if we've found the solution
						if (moveGenerator.isWinningConfiguration(possibleMove.getStringRepresentation())) {
							return solutionFound(possibleMove);
						}
					}
				}
			}
		}
		return null;
	}
	
	private static boolean configurationAlreadyFound(ConfigurationNode c) {
		boolean found = false;
		
		if (foundConfigurations.containsKey(new Integer(c.getHuffmanCode()))) found = true;
		
		if (!found) {
			foundConfigurations.put(new Integer(c.getHuffmanCode()),new Integer(0));
		}
		
		return found;
	}
	
	private static Stack<ConfigurationNode> solutionFound(ConfigurationNode winningConfiguration) {
		Stack<ConfigurationNode> solutionPath = new Stack<ConfigurationNode>();
		solutionPath.add(winningConfiguration);
		ConfigurationNode currentNode = winningConfiguration;
		// Starting with the leaf node which represents the winning configuration, trace back up the tree and add all the configurations between
		// the winning configuration and the starting configuration
		while ((currentNode = currentNode.getParent()) != null) {
			solutionPath.add(currentNode);
		}
		return solutionPath;
	}

}
