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

import java.util.ArrayList;

public class ConfigurationNode {
	
	private ConfigurationNode parent;
	private ArrayList<ConfigurationNode> children;
	private String stringRepresentation;
	private String moveTaken;
	private int huffmanCode;
	
	public ConfigurationNode(String configuration, int x, int y, String direction) {
		this.stringRepresentation = configuration;
		moveTaken = createMove(x,y,direction);
		this.huffmanCode = generateHuffmanCode(configuration);
		children = new ArrayList<ConfigurationNode>();
	}
	
	// Returns the integer huffman code value associated with the string representation of the configuration
	public static int generateHuffmanCode(String config) {
		String str = "";
		for (int i = 0; i < config.length(); i++) {
			char c = config.charAt(i);
			if (c == 'A')
				str += "10";
			else if (c == 'B')
				str += "010";
			else if (c == 'C')
				str += "011";
			else if (c == 'D')
				str += "11";
			else if (c == 'E')
				str += "00";
		}
		return Integer.parseInt(str,2);
	}
	
	private String createMove(int x, int y, String direction) {
		if (x == -1 || y == -1 || direction.length() == 0)
			return "START";
		return "Move piece at coordinates ( " + x + " , " + y + " )" + " one unit " + direction;
	}
	
	public void addChild(ConfigurationNode n) {
		n.setParent(this);
		children.add(n);
	}

	public ConfigurationNode getParent() {
		return parent;
	}

	public void setParent(ConfigurationNode parent) {
		this.parent = parent;
	}

	public ArrayList<ConfigurationNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ConfigurationNode> children) {
		this.children = children;
	}

	public String getMoveTaken() {
		return moveTaken;
	}

	public void setMoveTaken(String moveTaken) {
		this.moveTaken = moveTaken;
	}

	public String getStringRepresentation() {
		return stringRepresentation;
	}

	public void setStringRepresentation(String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}

	public int getHuffmanCode() {
		return huffmanCode;
	}

	public void setHuffmanCode(int huffmanCode) {
		this.huffmanCode = huffmanCode;
	}
	

}
