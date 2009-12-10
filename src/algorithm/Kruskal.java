package algorithm;

import java.awt.Color;

import javax.swing.JFrame;

import model.Edge;
import model.Forest;
import model.Graph;
import model.Vertex;
import view.GraphGUI;

// kruskal's algorithm
// get a minimal spanning tree
public class Kruskal implements GraphAlgorithm {
	private Graph graph;
	private Forest forest = new Forest();
	
	public Kruskal(Graph graph) {
		this.graph = graph;
		
		this.graph.sortEdges();
	}
	
	public void execute() {
		
		// prepare edges for display
		for (Edge e : graph.getEdges()) {
			e.setColor(Color.gray);
		}
		
		while (true) {
			Edge shortestEdge = getShortestEdge();
			
			if (shortestEdge == null) {
				System.out.println("no shortest edge found");
				System.exit(0);
			}
			
			System.out.println("shortest found: " + shortestEdge);
			
			// select vertices and edge
			shortestEdge.getV1().setColor(Color.red);
			shortestEdge.getV2().setColor(Color.red);
			shortestEdge.setColor(Color.red);
			
			forest.add(shortestEdge);
			
			if (forest.size() == 1 && forest.countEdges() == graph.getVertices().size() - 1) {
				break;
			}
		}
		
		System.out.println("done");
	}
	
	// get shortest edge that does not complete a circuit
	private Edge getShortestEdge() {
		for (Edge e : graph.getEdges()) {
			
			// no point if it's already included
			if (forest.contains(e)) {
				continue;
			}
			
			// check for circuits (origin and target connect to forest)
			if (forest.connectsBoth(e)) {
				continue;
			}
			
			return e;
		}
		return null;
	}
	
	// graph getter
	public Graph getGraph() {
		return graph;
	}
	
	// graph setter
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	public void setGUI(GraphGUI gui) {
	}
	
	public void settingsFrame(JFrame parent) {
	}
	
	// reset to a neutral state
	public void reset() {
	}
	
	public static void main(String[] args) {
		Vertex a = new Vertex("A");
		Vertex b = new Vertex("B");
		Vertex c = new Vertex("C");
		Vertex d = new Vertex("D");
		Vertex e = new Vertex("E");
		Vertex f = new Vertex("F");
		
		Graph graph = new Graph();
		graph.add(a, b, c, d, e, f);

		graph.connect(a, b, 2);
		graph.connect(a, c, 6);
		graph.connect(b, c, 3);
		graph.connect(b, d, 3);
		graph.connect(b, e, 4);
		graph.connect(c, d, 1);
		graph.connect(d, e, 5);
		graph.connect(d, f, 5);
		graph.connect(e, f, 3);
		
		Kruskal kruskal = new Kruskal(graph);
		kruskal.execute();
	}
}
