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

import java.util.*;

public class MoveGenerator {

	private char[][] board;
	private char[][] currentConfig;
	private char[] config;

	private boolean flag = false;
	private String initialConfig;

	private Queue<possibleMove> queue;
	private ArrayList<ConfigurationNode> resultingHuffmanConfigs;

	public MoveGenerator() {
		queue = new LinkedList<possibleMove>();
		resultingHuffmanConfigs = new ArrayList<ConfigurationNode>();
	}

	public ArrayList<ConfigurationNode> generatePossibleMoves(String initialConfig) {
		queue = new LinkedList<possibleMove>();
		resultingHuffmanConfigs = new ArrayList<ConfigurationNode>();

		config = initialConfig.toCharArray();
		currentConfig = generateGrid(initialConfig.toCharArray());
		board = currentConfig;

		int numb = generateNumberMovesPossible(); // populates Queue of possibleMoves
		generateResultingConfigurations();  // populates the ArrayList resultingHuffmanConfigs with Queue of possibleMoves

		return resultingHuffmanConfigs;
	}

	private int generateNumberMovesPossible() {   // generates all possible moves from currentConfig and populates Queue with possible moves
		char[][] a = copyOf(currentConfig);

		int numberMoves = 0;

		for (int i = 1; i < 5; i++) {
			for (int j = 1; j < 6; j++) {

				if (a[i][j] == 'E') {  // iterate through array to find 'E's

					if (getAdjacentELocation(i, j).equals("below")) {   // 'E's are stacked vertically
						if (getHeight(a[i][j - 1]) == 2 && a[i][j - 1] == a[i + 1][j - 1]) { // upper left neighbor height = 2 and top and bottom are the same
							numberMoves++;
							queue.add(new possibleMove(i, j - 1, "right", (getWidth(a[i][j - 1]) == 2), (getHeight(a[i][j - 1]) == 2)));
						}

						if (getHeight(a[i][j + 1]) == 2	&& a[i][j + 1] == a[i + 1][j + 1]) {  
							numberMoves++;
							queue.add(new possibleMove(i, j + 1, "left", (getWidth(a[i][j + 1]) == 2), (getHeight(a[i][j + 1]) == 2)));
						}
					}

					if (getAdjacentELocation(i, j).equals("right")) {  // 'E's are adjacent horizontally
						if (getWidth(a[i - 1][j]) == 2 && a[i - 1][j] == a[i - 1][j + 1] && (a[i-1][j] != a[i-1][j+2] || a[i-1][j] != a[i-1][j-1])) { 
							numberMoves++;
							queue.add(new possibleMove(i - 1, j , "down", (getWidth(a[i-1][j]) == 2), (getHeight(a[i-1][j]) == 2)));
						}
						if (getWidth(a[i + 1][j]) == 2 && a[i + 1][j] == a[i + 1][j + 1] && (a[i+1][j] != a[i+1][j+2] || a[i+1][j] != a[i+1][j-1])) {
							numberMoves++;
							queue.add(new possibleMove(i + 1, j, "up", (getWidth(a[i + 1][j]) == 2), (getHeight(a[i + 1][j]) == 2)));
						}
					}

					if (a[i - 1][j] != 'E' && getWidth(a[i - 1][j]) == 1) { // block can move down into E space
						numberMoves++;
						queue.add(new possibleMove(i - 1, j, "down", (getWidth(a[i - 1][j]) == 2), (getHeight(a[i - 1][j]) == 2)));
					}

					if (a[i + 1][j] != 'E' && getWidth(a[i + 1][j]) == 1) {  // block can move up into E space
						numberMoves++;
						queue.add(new possibleMove(i + 1, j, "up", (getWidth(a[i + 1][j]) == 2), (getHeight(a[i + 1][j]) == 2)));
					}

					if (a[i][j - 1] != 'E' && getHeight(a[i][j - 1]) == 1) { // block can move right into E space
						numberMoves++;
						queue.add(new possibleMove(i, j - 1, "right", (getWidth(a[i][j - 1]) == 2), (getHeight(a[i][j - 1]) == 2)));
					}

					if (a[i][j + 1] != 'E' && getHeight(a[i][j + 1]) == 1) { // block can move left into E space
						numberMoves++;
						queue.add(new possibleMove(i, j + 1, "left",(getWidth(a[i][j + 1]) == 2), (getHeight(a[i][j + 1]) == 2)));
					}
				}
			}
		}
//		System.out.println("Number of moves possible from the following configuration is " + numberMoves);
//		displayBoard(a);
		return numberMoves;
	}

	private void generateResultingConfigurations() {  // iterates through the queue of possible moves and populates Array List with resultingHuffmanConfigs
		char[][] c = copyOf(currentConfig);

		for (possibleMove x : queue) {

			c = copyOf(currentConfig);
			movePiece(x.xLocation, x.yLocation, c, x.direction);

			if (x.isDouble && x.isStack) {
				if(x.direction == "right") {
					movePiece(x.xLocation + 1, x.yLocation, c, x.direction);
					movePiece(x.xLocation , x.yLocation - 1, c, x.direction);
					movePiece(x.xLocation + 1, x.yLocation - 1, c, x.direction);
				}
				else if(x.direction == "left") {
					movePiece(x.xLocation, x.yLocation + 1, c, x.direction);
					movePiece(x.xLocation + 1, x.yLocation, c, x.direction);
					movePiece(x.xLocation + 1, x.yLocation + 1, c, x.direction);
				}
				else if(x.direction == "down"){ 
					movePiece(x.xLocation, x.yLocation + 1, c, x.direction);
					movePiece(x.xLocation - 1, x.yLocation, c, x.direction);
					movePiece(x.xLocation - 1, x.yLocation + 1, c, x.direction);

				}
				else {
					movePiece(x.xLocation, x.yLocation + 1, c, x.direction);
					movePiece(x.xLocation + 1, x.yLocation, c, x.direction);
					movePiece(x.xLocation + 1, x.yLocation + 1, c, x.direction);
				}

			} else if (x.isDouble) {
				if(x.direction == "right")
					movePiece(x.xLocation, x.yLocation - 1, c, x.direction);
				else
					movePiece(x.xLocation, x.yLocation + 1, c, x.direction);

			} else if (x.isStack) {
				if(x.direction == "down")
					movePiece(x.xLocation - 1, x.yLocation, c, x.direction);
				else
					movePiece(x.xLocation + 1, x.yLocation, c, x.direction);
			}

			resultingHuffmanConfigs.add(new ConfigurationNode( generateConfigString(c) , x.xLocation , x.yLocation, x.direction));
		}
	}

	public char[][] generateGrid(char[] z) {  // given a char array as input, generates the corresponding 4x5 game board
		char[][] layout = new char[10][10];

		int x = 1;
		int y = 1;

		for (int i = 0; i < z.length; i++) {
			for (int j = 0; j < getWidth(z[i]); j++) {
				if (layout[x][y] == '\0') {
					if (getHeight(z[i]) == 1) {
						layout[x][y] = z[i];
					} else {
						layout[x][y] = z[i];
						layout[x + 1][y] = z[i];
					}

					y++;
					if (y == 6) {
						x++;
						y = 1;
					}
				} else {
					while (layout[x][y] != '\0') {
						y++;
						if (y == 6) {
							x++;
							y = 1;
						}
					}

					if (getHeight(z[i]) == 1) {
						layout[x][y] = z[i];
					} else {
						layout[x][y] = z[i];
						layout[x + 1][y] = z[i];
					}
				}
			}
		}
		return layout;
	}

	private String generateConfigString(char[][] a) {  // generates corresponding Huffman String representation based on 2-D array 
		String config = "";

		for (int i = 1; i < 5; i++) {
			for (int j = 1; j < 6; j++) {
				if (a[i][j] == 'A') {
					j++;
				}
				if (a[i][j] == 'C') {
					if (a[i - 1][j] == 'C') {
						j += 2;
					} else
						j++;
				}
				if (a[i][j] == 'B' && a[i - 1][j] == 'B') {
					j++;
				}
				config += a[i][j];
			}
		}
		return config;
	}

	private char[][] movePiece(int x, int y, char[][] c, String dir) { // moves pieces based on input
		int directionX;  // direction the X coordinate should be changed by
		int directionY; // "            " Y coordinate "                  "

		if (dir.equals("up")) {
			directionX = -1;
			directionY = 0;
		} else if (dir.equals("down")) {
			directionX = 1;
			directionY = 0;
		} else if (dir.equals("left")) {
			directionX = 0;
			directionY = -1;
		} else {
			directionX = 0;
			directionY = 1;
		}

		char[][] tempConfig = c;

		char temp = 'Z';
		temp = tempConfig[x][y];
		tempConfig[x][y] = tempConfig[x + directionX][y + directionY];
		tempConfig[x + directionX][y + directionY] = temp;
		return tempConfig;
	}

	private void displayMoveOptions() {  // iterates through the queue of possible moves and displays all possible resulting configurations
		char[][] c = copyOf(currentConfig);

		for (possibleMove x : queue) {

			c = copyOf(currentConfig);
			movePiece(x.xLocation, x.yLocation, c, x.direction);

			if (x.isDouble && x.isStack) {
				if(x.direction == "right") {
					movePiece(x.xLocation + 1, x.yLocation, c, x.direction);
					movePiece(x.xLocation , x.yLocation - 1, c, x.direction);
					movePiece(x.xLocation + 1, x.yLocation - 1, c, x.direction);
				}
				else if(x.direction == "left") {
					movePiece(x.xLocation, x.yLocation + 1, c, x.direction);
					movePiece(x.xLocation + 1, x.yLocation, c, x.direction);
					movePiece(x.xLocation + 1, x.yLocation + 1, c, x.direction);
				}
				else if(x.direction == "down"){ 
					movePiece(x.xLocation, x.yLocation + 1, c, x.direction);
					movePiece(x.xLocation - 1, x.yLocation, c, x.direction);
					movePiece(x.xLocation - 1, x.yLocation + 1, c, x.direction);

				}
				else {
					movePiece(x.xLocation, x.yLocation + 1, c, x.direction);
					movePiece(x.xLocation + 1, x.yLocation, c, x.direction);
					movePiece(x.xLocation + 1, x.yLocation + 1, c, x.direction);
				}

			} else if (x.isDouble) {
				if(x.direction == "right")
					movePiece(x.xLocation, x.yLocation - 1, c, x.direction);
				else
					movePiece(x.xLocation, x.yLocation + 1, c, x.direction);

			} else if (x.isStack) {
				if(x.direction == "down")
					movePiece(x.xLocation - 1, x.yLocation, c, x.direction);
				else
					movePiece(x.xLocation + 1, x.yLocation, c, x.direction);
			}

			displayBoard(c);
			System.out.println("GeneratedConfigString: " + generateConfigString(c));

		}
	}

	public void displayBoard(char[][] x) {  // given a 2-D char array, displays a 2-D pictorial representation of the give 
		for (int z = 1; z < 5; z++) {
			for (int j = 1; j < 6; j++) {
				char c = x[z][j];
				if (c == 'E') c = ' ';
				System.out.print(c + " ");
			}
			System.out.println("");
		}
	}

	private void displayQueue() {  // displays the Queue containing possible moves in legible form
		for (possibleMove x : queue) {
			System.out.println("Move piece at (" + x.xLocation + ", " + x.yLocation + ") " + x.direction);
		}
	}

	private int getCharPosition(char x) {  // finds location of char x within a string initialConfig
		for (int i = 0; i < initialConfig.length(); i++)
			if (initialConfig.charAt(i) == x)
				return i;
		return -1;
	}

	private static int getWidth(char x) { // returns the width of the block represented by char x
		if (x == 'A')
			return 2;
		else if (x == 'B')
			return 1;
		else if (x == 'C')
			return 2;
		else if (x == 'D')
			return 1;
		else if (x == 'E')
			return 1;
		else
			return -1;
	}

	private static int getHeight(char x) {  // returns the size of the block represented by char x
		if (x == 'A')
			return 1;
		else if (x == 'B')
			return 2;
		else if (x == 'C')
			return 2;
		else if (x == 'D')
			return 1;
		else if (x == 'E')
			return 1;
		else
			return -1;
	}

	private String getAdjacentELocation(int x, int y) {  // locates adjacent empty spaces to aid in finding of possible moves
		if (board[x + 1][y] == 'E')
			return "below";
		else if (board[x][y - 1] == 'E')
			return "left";
		else if (board[x][y + 1] == 'E')
			return "right";
		else
			return "none";
	}

	private char[][] copyOf(char[][] original) {  // creates copy of 2-D array to avoid conflicts through object references
		int length = original.length;
		char[][] target = new char[length][original[0].length];
		for (int i = 0; i < length; i++) {
			System.arraycopy(original[i], 0, target[i], 0, original[i].length);
		}
		return target;
	}

	public boolean isWinningConfiguration(String config) {
		char[][] temp = generateGrid(config.toCharArray());
		if (temp[2][4] == 'C' && temp[2][5] == 'C' && temp[3][4] == 'C' && temp[3][5] == 'C')
			return true;
		return false;
	}

	public static boolean isValidConfiguration(String config2) {
		int[] rowWidthCount = new int[4];
		int rowCounter = 0;
		for(int i = 0; i < config2.length(); i++ ) {
			if ( rowWidthCount[rowCounter] == 5 ) {
				rowCounter++;
			} 
			if (getHeight(config2.charAt(i)) == 2) {
				rowWidthCount[rowCounter] += getWidth(config2.charAt(i));
				rowWidthCount[rowCounter +1] += getWidth(config2.charAt(i));
			} else {
				rowWidthCount[rowCounter] += getWidth(config2.charAt(i));
			}
		}
		for( int i = 0; i < rowWidthCount.length; i++) {
			if (rowWidthCount[i] != 5) { return false; }
		}
		return true;
	}

	private class possibleMove {  // class to store data for each possibleMove
		int xLocation;
		int yLocation;
		String direction;
		boolean isDouble;
		boolean isStack;

		public possibleMove(int x, int y, String dir, boolean dub, boolean stack) {
			xLocation = x;
			yLocation = y;
			direction = dir;
			isDouble = dub;
			isStack = stack;
		}
	}

}
