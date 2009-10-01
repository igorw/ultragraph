package model;

public class DirectedEdge {
	private Vertex origin;
	private Vertex target;
	private Edge edge;
	
	public DirectedEdge(Vertex origin, Vertex target, Edge edge) {
		this.origin = origin;
		this.target = target;
		this.edge = edge;
	}
	
	public Vertex getOrigin() {
		return origin;
	}
	
	public Vertex getTarget() {
		return target;
	}
	
	public Edge getEdge() {
		return edge;
	}
	
	public int getFullWeight() {
		return edge.getFullWeight(origin);
	}
}
