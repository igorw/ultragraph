package controller;

import java.util.HashSet;

import model.Edge;
import model.Graph;
import model.Vertex;

// dijkstra's algorithm
// find shortest path between two vertices
public class Dijkstra {
	private Graph graph;
	private Vertex origin;
	private Vertex target;
	
	// boxed means all edges were traversed
	private HashSet<Vertex> boxed = new HashSet<Vertex>();
	
	// the currently selected vertex
	private Vertex boxedVertex;
	
	public Dijkstra(Graph graph, Vertex origin, Vertex target) {
		this.graph = graph;
		this.origin = origin;
		this.target = target;
	}
	
	public void execute() {
		
		origin.setLabel(0);
		
		boxedVertex = origin;

		// loop through all vertices
		while (true) {
			
			System.out.println(boxedVertex);
			
			// add newly boxed vertex to boxed array
			boxed.add(boxedVertex);
			
			// label all vertices
			for (Edge e : boxedVertex.getEdges()) {
				
				// we don't want to go back to the origin
				if (e.getTarget() == origin) {
					continue;
				}
				
				// targeted vertex is already touched
				// unless we can get a better deal, we skip labelling
				// this also catches going back to parent vertices
				if (e.getTarget().hasOrigin() && e.getFullWeight(boxedVertex) >= e.getTarget().getLabel()) {
					System.out.println(boxedVertex + " " + e.getTarget() + " " + e.getTarget().getLabel() + " unprofitable");
					continue;
				}
				
				e.getTarget().setLabel(e.getFullWeight());
				e.getTarget().setOrigin(boxedVertex);
				
				System.out.println(boxedVertex + " " + e.getTarget() + " " + e.getTarget().getLabel());
			}
			
			// we found our target
			if (boxedVertex == target) {
				break;
			}
			
			// find next vertex for boxing
			boxedVertex = getLowestVertex();
			
			// vertex not found, some error
			if (boxedVertex == null) {
				System.out.println("Error - no vertex found for boxing");
				return;
			}
		}
		
		System.out.println("-----");
		
		// trace way back
		Vertex vertex = target;
		System.out.println(vertex);
		
		while (vertex != origin) {
			vertex = vertex.getOrigin();
			System.out.println(vertex);
		}
	}
	
	// find the vertex with the lowest weight
	private Vertex getLowestVertex() {
		Vertex lowest = null;
		for (Vertex v : graph.getVertices()) {
			if (!boxed.contains(v) && v.hasOrigin() && (lowest == null || v.getLabel() < lowest.getLabel())) {
				lowest = v;
			}
		}
		return lowest;
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
		
		Dijkstra dijkstra = new Dijkstra(graph, a, d);
		dijkstra.execute();
	}
}
