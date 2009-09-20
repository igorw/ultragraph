package model;

public class Edge {
	private Vertex v1;
	private Vertex v2;
	private int weight;
	
	private String color;
	
	public Edge(Vertex origin, Vertex target, int weight) {
		setV1(origin);
		setV2(target);
		setWeight(weight);
	}
	
	public Edge(Vertex origin, Vertex target) {
		setV1(origin);
		setV2(target);
		setWeight(1);
	}
	
	public Vertex getV1() {
		return v1;
	}
	
	public void setV1(Vertex origin) {
		this.v1 = origin;
	}
	
	public Vertex getV2() {
		return v2;
	}
	
	public void setV2(Vertex target) {
		this.v2 = target;
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
		return (v1.hasOrigin() ? v1.getLabel() : 0) + weight;
	}
	
	// get full relative weight for alternate v1
	public int getFullWeight(Vertex alternateOrigin) {
		return (alternateOrigin.hasOrigin() ? alternateOrigin.getLabel() : 0) + weight;
	}
	
	public String toString() {
		return getV1() + " " + getV2() + " " + getWeight();
	}
}
