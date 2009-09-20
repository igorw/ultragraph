package model;

public class Edge {
	private Vertex origin;
	private Vertex target;
	private int weight;
	
	private String color;
	
	public Edge(Vertex origin, Vertex target, int weight) {
		setOrigin(origin);
		setTarget(target);
		setWeight(weight);
	}
	
	public Edge(Vertex origin, Vertex target) {
		setOrigin(origin);
		setTarget(target);
		setWeight(1);
	}
	
	public Vertex getOrigin() {
		return origin;
	}
	
	public void setOrigin(Vertex origin) {
		this.origin = origin;
	}
	
	public Vertex getTarget() {
		return target;
	}
	
	public void setTarget(Vertex target) {
		this.target = target;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	// get the full relative weight
	public int getFullWeight() {
		return (origin.hasOrigin() ? origin.getLabel() : 0) + weight;
	}
	
	// get full relative weight for alternate origin
	public int getFullWeight(Vertex alternateOrigin) {
		return (alternateOrigin.hasOrigin() ? alternateOrigin.getLabel() : 0) + weight;
	}
	
	public String toString() {
		return getOrigin() + " " + getTarget() + " " + getWeight();
	}
}
