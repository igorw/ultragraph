package model;

import java.util.HashSet;

public class Vertex {
	private String name;
	private int label = Vertex.INFINITE;
	private Vertex origin;
	
	private HashSet<Edge> edges = new HashSet<Edge>();

	public static final int INFINITE = -1;
	
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
	
	public boolean hasOrigin() {
		return origin != null;
	}

	public Vertex getOrigin() {
		return origin;
	}

	public void setOrigin(Vertex origin) {
		this.origin = origin;
	}
	
	public HashSet<Edge> getEdges() {
		return edges;
	}
	
	public void connectTo(Vertex vertex, int weight, boolean connectBack) {
		edges.add(new Edge(this, vertex, weight));
		if (connectBack) {
			// add edge back from the remote vertex
			vertex.connectTo(this, weight, false);
		}
	}
	
	// if no connectBack given, assume true
	public void connectTo(Vertex vertex, int weight) {
		connectTo(vertex, weight, true);
	}
	
	// find an edge to a specific vertex
	public Edge findEdge(Vertex vertex) {
		for (Edge e : edges) {
			if (e.getTarget() == vertex) {
				return e;
			}
		}
		return null;
	}
	
	public String toString() {
		return name;
	}
}
