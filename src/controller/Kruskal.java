package controller;

import java.util.HashSet;

import model.Edge;
import model.Graph;
import model.Vertex;

public class Kruskal {
	private Graph graph;
	private HashSet<Edge> tree = new HashSet<Edge>();
	private HashSet<Edge> cleanGrid = new HashSet<Edge>();
	
	public Kruskal(Graph graph) {
		this.graph = graph;
		
		// initialise clean grid
		for (Vertex v : graph.getVertices()) {
			for (Edge e : v.getEdges()) {
				if (!cleanGrid.contains(e.getTarget().findEdge(v))) {
					// ignore reverse connections
					cleanGrid.add(e);
				}
			}
		}
	}
	
	public void execute() {
		while (true) {
			Edge shortestEdge = getShortestEdge();
			
			if (shortestEdge == null) {
				System.out.println("no shortest edge found");
				System.exit(0);
			}
			
			System.out.println("shortest found: " + shortestEdge + "\n");
			
			tree.add(shortestEdge);
			
			if (tree.size() - 1 == graph.getVertices().size()) {
				break;
			}
		}
		
		System.out.println("done");
	}
	
	// get shortest edge that does not complete a circuit
	private Edge getShortestEdge() {
		Edge shortest = null;
		for (Edge e : cleanGrid) {
			
			// no point if it's already included
			// also reverse-edges
			if (tree.contains(e) || tree.contains(e.getTarget().findEdge(e.getOrigin()))) {
				continue;
			}
			
			// check if it creates circuit
			if (createsCircuit(e)) {
				continue;
			}
			
			if (shortest == null || e.getWeight() < shortest.getWeight()) {
				shortest = e;
			}
		}
		return shortest;
	}
	
	private boolean createsCircuit(Edge edge) {
		boolean originMatch = false;
		boolean targetMatch = false;
		System.out.println("does this create circuit: " + edge);
		for (Edge e : tree) {
			if (edge.getOrigin() == e.getOrigin() || edge.getOrigin() == e.getTarget()) {
				originMatch = true;
				System.out.println("origin match " + e);
			}
		}
		for (Edge e : tree) {
			if (edge.getTarget() == e.getOrigin() || edge.getTarget() == e.getTarget()) {
				targetMatch = true;
				System.out.println("target match: " + e);
			}
		}
		return originMatch && targetMatch;
	}
	
	public static void main(String[] args) {
		Vertex a = new Vertex("A");
		Vertex b = new Vertex("B");
		Vertex c = new Vertex("C");
		Vertex d = new Vertex("D");
		Vertex e = new Vertex("E");
		Vertex f = new Vertex("F");

		a.connectTo(b, 2);
		a.connectTo(c, 6);
		b.connectTo(c, 3);
		b.connectTo(d, 3);
		b.connectTo(e, 4);
		c.connectTo(d, 1);
		d.connectTo(e, 5);
		d.connectTo(f, 5);
		e.connectTo(f, 3);
		
		Graph graph = new Graph();
		graph.addVertex(a, b, c, d, e, f);
		
		Kruskal kruskal = new Kruskal(graph);
		kruskal.execute();
	}
}
