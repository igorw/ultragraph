package algorithm;

import java.awt.Color;
import java.util.HashSet;

import model.DirectedEdge;
import model.Edge;
import model.Graph;
import model.Vertex;
import view.DijkstraGUI;

// dijkstra's algorithm
// find shortest path between two vertices
public class Dijkstra implements GraphAlgorithm {
	private Graph graph;
	private Vertex origin;
	private Vertex target;
	
	// boxed means all edges were traversed
	private HashSet<Vertex> boxed = new HashSet<Vertex>();
	
	// visualization
	private DijkstraGUI gui;
	
	public Dijkstra(Graph graph, Vertex origin, Vertex target) {
		this.graph = graph;
		this.origin = origin;
		this.target = target;
		this.gui = new DijkstraGUI(graph);
	}
	
	public void execute() {
		// initialise gui
		gui.init();
		
		// set initial label
		origin.setLabel(0);
		
		// box initial vertex
		boxVertex(origin);

		// loop through all vertices
		Vertex boxedVertex;
		while (null != (boxedVertex = getLowestVertex())) {
			if (boxVertex(boxedVertex)) {
				// found shortest path
				break;
			}
		}
		
		// vertex not found, some error
		if (boxedVertex == null) {
			System.out.println("Error - no vertex found for boxing");
			return;
		}
		
		System.out.println("-----");
		
		// we're done
		
		// trace way back
		Vertex vertex = target;
		System.out.println(vertex);
		
		// viz
		vertex.setColor(Color.green);
		gui.repaint();
		Edge shortest = Graph.getShortestEdge(graph.getVerticesEdges(vertex, vertex.getOrigin()));
		if (shortest != null) {
			shortest.setColor(Color.green);
			gui.repaint();
		}
		
		while (vertex != origin) {
			vertex = vertex.getOrigin();
			System.out.println(vertex);
			
			// viz
			vertex.setColor(Color.green);
			gui.repaint();
			shortest = Graph.getShortestEdge(graph.getVerticesEdges(vertex, vertex.getOrigin()));
			if (shortest != null) {
				shortest.setColor(Color.green);
				gui.repaint();
			}
		}
	}
	
	// returns whether boxed is target
	private boolean boxVertex(Vertex v) {
		System.out.println(v);
		
		v.setColor(Color.red);
		gui.repaint();
		
		// add newly boxed vertex to boxed array
		boxed.add(v);
		
		// we found our target
		if (v == target) {
			return true;
		}
		
		// label all vertices
		for (DirectedEdge de : graph.getVertexEdges(v)) {
			
			// targeted vertex is boxed
			// we can skip it
			if (boxed.contains(de.getTarget())) {
				continue;
			}
			
			// viz
			de.getEdge().setColor(Color.red);
			gui.repaint();
			
			// targeted vertex is already touched
			// unless we can get a better deal, we skip labelling
			// this also catches going back to parent vertices
			if (de.getTarget().isLabeled() && de.getEdge().getFullWeight(v) >= de.getTarget().getLabel()) {
				System.out.println(v + " " + de.getTarget() + " " + de.getTarget().getLabel() + " unprofitable");
				continue;
			}
			
			// set label
			de.getTarget().setLabel(de.getFullWeight());
			de.getTarget().setOrigin(v);
			
			gui.repaint();
			
			System.out.println(v + " " + de.getTarget() + " " + de.getTarget().getLabel());
		}
		
		return false;
	}
	
	// find the vertex with the lowest weight
	// first sort vertices asc, then check conditions
	private Vertex getLowestVertex() {
		graph.sortVertices();
		for (Vertex v : graph.getVertices()) {
			if (!boxed.contains(v) && v.isLabeled()) {
				return v;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		Vertex a = new Vertex("A", 15, 15);
		Vertex b = new Vertex("B", 50, 50);
		Vertex c = new Vertex("C", 15, 50);
		
		Graph graph = new Graph();
		graph.add(a, b, c);
		
		graph.connect(a, b, 2);
		graph.connect(a, c, 1);
		graph.connect(b, c, 3);
		
		Dijkstra dijkstra = new Dijkstra(graph, a, c);
		dijkstra.execute();
	}
}
