package model;

import java.util.ArrayList;

import controller.Dijkstra;

public class Graph {
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	
	// add any number of vertices
	public void addVertex(Vertex... vertices) {
		for (Vertex vertex : vertices) {
			this.vertices.add(vertex);
		}
	}
	
	public ArrayList<Vertex> getVertices() {
		return vertices;
	}
	
	// dijkstra
	public void shortestPath(Vertex origin, Vertex target) {
		Dijkstra d = new Dijkstra(this, origin, target);
		d.execute();
	}
	
	public static void main(String[] args) {
		Vertex a = new Vertex("A");
		Vertex b = new Vertex("B");
		Vertex c = new Vertex("C");
		Vertex d = new Vertex("D");
		Vertex e = new Vertex("E");
		Vertex f = new Vertex("F");
		Vertex g = new Vertex("G");
		Vertex h = new Vertex("H");

		a.connectTo(b, 2);
		a.connectTo(g, 6);
		b.connectTo(c, 7);
		b.connectTo(e, 2);
		g.connectTo(e, 1);
		g.connectTo(h, 4);
		e.connectTo(f, 2);
		f.connectTo(c, 3);
		f.connectTo(h, 2);
		c.connectTo(d, 3);
		h.connectTo(d, 2);
		
		Graph graph = new Graph();
		graph.addVertex(a, b, c, d, e, f, g, h);
		graph.shortestPath(a, d);
	}
}
