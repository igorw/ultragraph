package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Graph {
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private HashSet<Edge> edges = new HashSet<Edge>();
	
	// add any number of vertices
	public void add(Vertex... vertices) {
		for (Vertex vertex : vertices) {
			this.vertices.add(vertex);
		}
	}
	
	public Edge connect(Vertex v1, Vertex v2, int weight) {
		Edge e = new Edge(v1, v2, weight);
		edges.add(e);
		return e;
	}
	
	public ArrayList<Vertex> getVertices() {
		return vertices;
	}
	
	public HashSet<Edge> getEdges() {
		return edges;
	}
	
	// returns set of directed edges from vertex v
	public HashSet<DirectedEdge> getVertexEdges(Vertex v) {
		HashSet<DirectedEdge> vEdges = new HashSet<DirectedEdge>();
		for (Edge e : edges) {
			if (e.getV1() == v) {
				vEdges.add(new DirectedEdge(e.getV1(), e.getV2(), e));
			} else if (e.getV2() == v) {
				vEdges.add(new DirectedEdge(e.getV2(), e.getV1(), e));
			}
		}
		return vEdges;
	}
	
	// get initial vertex
	public Vertex getRandomVertex() {
		int index = new Random().nextInt(vertices.size() - 1) + 1;
		return vertices.get(index);
	}
}
