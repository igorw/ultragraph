package model;

import java.awt.Color;
import java.awt.Point;

public class Vertex implements Comparable<Vertex> {
	private String name;
	private int label = -1;
	private Vertex origin;
	
	private Color color = Color.black;
	
	private Point position = new Point();
	
	public Vertex(String name) {
		setName(name);
	}
	
	public Vertex(String name, int posX, int posY) {
		this(name);
		setPosX(posX);
		setPosY(posY);
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
	
	public int getPosX() {
		return (int) position.getX();
	}
	
	public void setPosX(int posX) {
		position.move(posX, (int) position.getY());
	}
	
	public int getPosY() {
		return (int) position.getY();
	}
	
	public void setPosY(int posY) {
		position.move((int) position.getX(), posY);
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
