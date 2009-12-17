package model;

import java.awt.Color;

import misc.Point;

/**
 * vertex
 * represents a dot in a graph
 */
public class Vertex implements Comparable<Vertex> {
	/**
	 * name of vertex
	 */
	private String name;
	
	/**
	 * temporary numerical label
	 * -1 means infinity
	 */
	private int label = -1;
	
	/**
	 * origin vertex
	 * preceding vertex in a path
	 */
	private Vertex origin;
	
	/**
	 * vertex color
	 */
	private Color color = Color.black;
	
	/**
	 * vertex coordinates
	 */
	private Point point = new Point();
	
	/**
	 * constructor
	 * 
	 * @param name name
	 */
	public Vertex(String name) {
		setName(name);
	}
	
	/**
	 * constructor
	 * 
	 * @param name name
	 * @param x position x
	 * @param y position y
	 */
	public Vertex(String name, int x, int y) {
		this(name);
		setX(x);
		setY(y);
	}
	
	/**
	 * getter for name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * setter for name
	 * @param name name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * getter for label
	 * @return label
	 */
	public int getLabel() {
		return label;
	}
	
	/**
	 * setter for label
	 * @param label label
	 */
	public void setLabel(int label) {
		this.label = label;
	}
	
	/**
	 * check if label is set to infinity
	 * @return is labeled
	 */
	public boolean isLabeled() {
		return label != -1;
	}
	
	/**
	 * getter for origin
	 * @return origin
	 */
	public Vertex getOrigin() {
		return origin;
	}
	
	/**
	 * setter for origin
	 * @param origin origin
	 */
	public void setOrigin(Vertex origin) {
		this.origin = origin;
	}
	
	/**
	 * getter for color
	 * @return color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * setter for color
	 * @param color color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * getter for x
	 * @return position x
	 */
	public int getX() {
		return point.getX();
	}
	
	/**
	 * setter for x
	 * @param x position x
	 */
	public void setX(int x) {
		point.setX(x);
	}
	
	/**
	 * getter for y
	 * @return position y
	 */
	public int getY() {
		return point.getY();
	}
	
	/**
	 * setter for y
	 * @param y position y
	 */
	public void setY(int y) {
		point.setY(y);
	}

	/**
	 * reset to a neutral state
	 */
	public void reset() {
		color = Color.black;
		label = -1;
		origin = null;
	}
	
	/**
	 * string representation
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * compare vertex to vertex
	 * from Comparable, to allow sorting vertexes by label
	 */
	public int compareTo(Vertex v) {
		if (getLabel() > v.getLabel()) {
			return 1;
		} else if (getLabel() < v.getLabel()) {
			return -1;
		} else {
			return 0;
		}
	}
}
