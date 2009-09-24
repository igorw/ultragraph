package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Graph {
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	
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
	
	public ArrayList<Edge> getEdges() {
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
	
	// get all edges directly connecting v1 and v2
	public HashSet<Edge> getVerticesEdges(Vertex v1, Vertex v2) {
		HashSet<Edge> vEdges = new HashSet<Edge>();
		for (Edge e : edges) {
			if (e.getV1() == v1 && e.getV2() == v2 || e.getV2() == v1 && e.getV1() == v2) {
				vEdges.add(e);
			}
		}
		return vEdges;
	}
	
	// get initial vertex
	public Vertex getRandomVertex() {
		int index = new Random().nextInt(vertices.size() - 1) + 1;
		return vertices.get(index);
	}
	
	public void sortVertices() {
		Collections.sort(vertices);
	}
	
	public void sortEdges() {
		Collections.sort(edges);
	}
	
	public static Edge getShortestEdge(Set<Edge> edges) {
		Edge shortest = null;
		for (Edge e : edges) {
			if (shortest == null || e.getWeight() > shortest.getWeight()) {
				shortest = e;
			}
		}
		return shortest;
	}
}
