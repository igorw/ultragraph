package model;

import java.util.ArrayList;

public class Vertex {
	private String name;
	private int weight = Vertex.INFINITE;
	private Vertex origin;
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	public static final int INFINITE = 0;
	
	public Vertex(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	public void connectTo(Vertex vertex, int weight, boolean remote) {
		edges.add(new Edge(this, vertex, weight));
		if (remote) {
			// add edge to the remote vertex
			vertex.connectTo(this, weight, false);
		}
	}
	
	// if no remote given, assume true
	public void connectTo(Vertex vertex, int weight) {
		connectTo(vertex, weight, true);
	}
	
	/*public int getWeightRecursive() {
		if (!hasOrigin()) {
			return Vertex.INFINITE;
		}
		
		int rWeight = origin.getWeight();
		for (Edge edge : edges) {
			if (edge == origin)
		}
	}*/
	
	public String toString() {
		return name;
	}
}
