package model;


public class Vertex {
	private String name;
	private int label = -1;
	private Vertex origin;
	
	private String color = "black";
	
	public Vertex(String name) {
		setName(name);
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
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String toString() {
		return name;
	}
}
