package algorithm;

import java.util.HashSet;

import model.DirectedEdge;
import model.Edge;
import model.Graph;
import model.Vertex;
import vizualisation.GraphViz;

// dijkstra's algorithm
// find shortest path between two vertices
public class Dijkstra implements GraphAlgorithm {
	private Graph graph;
	private Vertex origin;
	private Vertex target;
	
	// boxed means all edges were traversed
	private HashSet<Vertex> boxed = new HashSet<Vertex>();
	
	// the currently selected vertex
	private Vertex boxedVertex;
	
	// vizualisation
	private GraphViz viz;
	
	public Dijkstra(Graph graph, Vertex origin, Vertex target) {
		this.graph = graph;
		this.origin = origin;
		this.target = target;
		this.viz = new GraphViz(graph, "dijkstra");
	}
	
	public void execute() {
		
		// prepare edges for display
		for (Edge e : graph.getEdges()) {
			e.setColor("grey");
		}
		
		// initial frame
		viz.frame();
		
		// set initial label
		origin.setLabel(0);
		
		// box initial vertex
		boxedVertex = origin;
		
		boxedVertex.setColor("red");
		viz.frame();

		// loop through all vertices
		while (true) {
			
			System.out.println(boxedVertex);
			
			boxedVertex.setColor("red");
			viz.frame();
			
			// add newly boxed vertex to boxed array
			boxed.add(boxedVertex);
			
			// label all vertices
			for (DirectedEdge e : graph.getVertexEdges(boxedVertex)) {
				
				// we don't want to go back to the origin
				if (e.getTarget() == origin) {
					continue;
				}
				
				// viz
				e.getEdge().setColor("red");
				viz.frame();
				
				// targeted vertex is already touched
				// unless we can get a better deal, we skip labelling
				// this also catches going back to parent vertices
				if (e.getTarget().hasOrigin() && e.getEdge().getFullWeight(boxedVertex) >= e.getTarget().getLabel()) {
					System.out.println(boxedVertex + " " + e.getTarget() + " " + e.getTarget().getLabel() + " unprofitable");
					continue;
				}
				
				// set label
				e.getTarget().setLabel(e.getEdge().getFullWeight());
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
		
		// we're done
		
		// trace way back
		Vertex vertex = target;
		System.out.println(vertex);
		
		// viz
		vertex.setColor("green");
		Edge shortest = Graph.getShortestEdge(graph.getVerticesEdges(vertex, vertex.getOrigin()));
		if (shortest != null) {
			shortest.setColor("green");
		}
		viz.frame();
		
		while (vertex != origin) {
			vertex = vertex.getOrigin();
			System.out.println(vertex);
			
			// viz
			vertex.setColor("green");
			shortest = Graph.getShortestEdge(graph.getVerticesEdges(vertex, vertex.getOrigin()));
			if (shortest != null) {
				shortest.setColor("green");
			}
			viz.frame();
		}
		
		// save vizualisation
		viz.save();
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
		
		Graph graph = new Graph();
		graph.add(a, b, c, d, e, f, g, h);
		graph.connect(a, b, 2);
		graph.connect(a, g, 6);
		graph.connect(b, c, 7);
		graph.connect(b, e, 2);
		graph.connect(g, e, 1);
		graph.connect(g, h, 4);
		graph.connect(e, f, 2);
		graph.connect(f, c, 3);
		graph.connect(f, h, 2);
		graph.connect(c, d, 3);
		graph.connect(h, d, 2);
		
		Dijkstra dijkstra = new Dijkstra(graph, a, d);
		dijkstra.execute();
	}
}
