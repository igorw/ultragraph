package model;

public class Edge {
	private Vertex origin;
	private Vertex target;
	private int weight;
	
	public Edge(Vertex origin, Vertex target, int weight) {
		setOrigin(origin);
		setTarget(target);
		setWeight(weight);
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
	
	// get the full relative weight
	public int getFullWeight() {
		return origin.getWeight() + weight;
	}
}
