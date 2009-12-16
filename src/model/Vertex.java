package model;

import java.awt.Color;

import misc.Point;

public class Vertex implements Comparable<Vertex> {
	private String name;
	private int label = -1;
	private Vertex origin;
	
	private Color color = Color.black;
	
	private Point point = new Point();
	
	public Vertex(String name) {
		setName(name);
	}
	
	public Vertex(String name, int posX, int posY) {
		this(name);
		setX(posX);
		setY(posY);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int weight) {
		this.label = weight;
	}
	
	public boolean isLabeled() {
		return label != -1;
	}

	public Vertex getOrigin() {
		return origin;
	}

	public void setOrigin(Vertex origin) {
		this.origin = origin;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getX() {
		return point.getX();
	}
	
	public void setX(int x) {
		point.setX(x);
	}
	
	public int getY() {
		return point.getY();
	}
	
	public void setY(int y) {
		point.setY(y);
	}

	// reset to a neutral state
	public void reset() {
		color = Color.black;
		label = -1;
		origin = null;
	}
	
	public String toString() {
		return name;
	}
	
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
