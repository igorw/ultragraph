package model;

import java.awt.Color;


public class Vertex implements Comparable<Vertex> {
	private String name;
	private int label = -1;
	private Vertex origin;
	
	private Color color = Color.black;
	
	private int posX = 0;
	private int posY = 0;
	
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
		return posX;
	}
	
	public void setPosX(int posX) {
		this.posX = posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void setPosY(int posY) {
		this.posY = posY;
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
