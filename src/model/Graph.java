package model;

import java.util.ArrayList;
import java.util.HashSet;

import controller.Dijkstra;

public class Graph {
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private HashSet<Edge> edges = new HashSet<Edge>();
	
	// add any number of vertices
	public void addVertex(Vertex... vertices) {
		for (Vertex vertex : vertices) {
			this.vertices.add(vertex);
			
			// add edges
			for (Edge e : vertex.getEdges()) {
				edges.add(e);
			}
		}
	}
	
	public ArrayList<Vertex> getVertices() {
		return vertices;
	}
	
	public HashSet<Edge> getEdges() {
		return edges;
	}
	
	public void shortestPath(Vertex origin, Vertex target) {
		Dijkstra d = new Dijkstra(this, origin, target);
		d.execute();
	}
}
