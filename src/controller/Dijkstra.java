package controller;

import java.util.HashSet;

import model.Edge;
import model.Graph;
import model.Vertex;

public class Dijkstra {
	private Graph graph;
	private Vertex origin;
	private Vertex target;

	// boxed means visited at least once
	private HashSet<Vertex> boxed = new HashSet<Vertex>();
	// explored means all edges were traversed
	private HashSet<Vertex> explored = new HashSet<Vertex>();
	private Vertex currentVertex;
	
	public Dijkstra(Graph graph, Vertex origin, Vertex target) {
		this.graph = graph;
		this.origin = origin;
		this.target = target;
	}
	
	public void execute() {
		Edge selectedEdge;
		
		currentVertex = origin;

		// loop through all vertices
		while (true) {
			
			// loop through all edges
			while (null != (selectedEdge = getLowestEdge(currentVertex))) {
				boxVertex(selectedEdge.getTarget(), currentVertex, selectedEdge);
				
				System.out.println(currentVertex + " " + selectedEdge.getTarget() + " " + selectedEdge.getTarget().getWeight());
			}
			
			currentVertex = getLowestVertex();
			System.out.println(currentVertex);
		}
	}
	
	// touch vertex, set origin and weight
	private void boxVertex(Vertex vertex, Vertex origin, Edge edge) {
		vertex.setOrigin(origin);
		vertex.setWeight(edge.getFullWeight());
		
		boxed.add(vertex);
	}
	
	// find edge with lowest weight for a given vertex
	// to be boxed
	private Edge getLowestEdge(Vertex vertex) {
		Edge selectedEdge = null;
		for (Edge edge : vertex.getEdges()) {
			
			// don't get edges to the origin
			if (edge.getTarget() == origin) {
				continue;
			}
			
			// ignore already boxed targets
			// unless it results in a faster path (lower weight)
			if (boxed.contains(edge.getTarget()) && edge.getTarget().getWeight() >= edge.getFullWeight()) {
				continue;
			}
			
			if (selectedEdge == null) {
				// set the first edge
				selectedEdge = edge;
			} else if (edge.getWeight() < selectedEdge.getWeight()) {
				// we have a lower edge, use this instead
				selectedEdge = edge;
			}
		}
		return selectedEdge;
	}
	
	// find the vertex with the lowest weight
	private Vertex getLowestVertex() {
		Vertex selectedVertex = null;
		for (Vertex vertex : graph.getVertices()) {
			if (vertex != currentVertex && vertex.hasOrigin() && (selectedVertex == null || vertex.getWeight() < selectedVertex.getWeight())) {
				selectedVertex = vertex;
			}
		}
		return selectedVertex;
	}
}
