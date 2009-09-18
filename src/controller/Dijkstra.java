package controller;

import java.util.HashSet;

import model.Edge;
import model.Graph;
import model.Vertex;

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
				
				// targeted vertex is already boxed
				if (boxed.contains(e.getTarget())) {
					// unless we can get a better deal, we skip labelling
					if (e.getFullWeight(boxedVertex) >= e.getTarget().getLabel()) {
						continue;
					}
				}
				
				if (e.getTarget() == target) {
					
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
		
		System.out.println("------");
		for (Vertex vertex : graph.getVertices()) {
			System.out.println(vertex + " " + vertex.getLabel() + " " + vertex.getOrigin());
		}
		
		// trace way back
		/*Vertex vertex = target;
		System.out.println(vertex);
		
		while (vertex != origin) {
			vertex = vertex.getOrigin();
			System.out.println(vertex);
		}*/
	}
	
	// find the vertex with the lowest weight
	private Vertex getLowestVertex() {
		Vertex selectedVertex = null;
		for (Vertex vertex : graph.getVertices()) {
			if (!boxed.contains(vertex) && vertex.hasOrigin() && (selectedVertex == null || vertex.getLabel() < selectedVertex.getLabel())) {
				selectedVertex = vertex;
			}
		}
		return selectedVertex;
	}
}
