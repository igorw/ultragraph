package algorithm;

import java.awt.Color;

import javax.swing.JFrame;

import model.Edge;
import model.Forest;
import model.Graph;

/**
 * kruskal's algorithm
 * get a minimal spanning tree
 */
public class Kruskal extends GraphAlgorithm {
	/**
	 * forest containing trees
	 */
	private Forest forest = new Forest();
	
	/**
	 * constructor
	 * 
	 * @param graph graph
	 */
	public Kruskal(Graph graph) {
		super(graph);
		
		this.graph.sortEdges();
	}
	
	/**
	 * run the algorithm
	 */
	public void execute() {
		
		// prepare edges for display
		for (Edge e : graph.getEdges()) {
			e.setColor(Color.gray);
		}
		
		while (true) {
			Edge shortestEdge = getShortestEdge();
			
			if (shortestEdge == null) {
				System.out.println("no shortest edge found");
				return;
			}
			
			System.out.println("shortest found: " + shortestEdge);
			
			// select vertices and edge
			shortestEdge.getV1().setColor(Color.red);
			shortestEdge.getV2().setColor(Color.red);
			shortestEdge.setColor(Color.red);
			breakPoint();
			
			forest.add(shortestEdge);
			
			if (forest.size() == 1 && forest.countEdges() == graph.getVertices().size() - 1) {
				break;
			}
		}
		
		System.out.println("done");
	}
	
	/**
	 * get shortest edge that does not complete a circuit
	 * @return shortest edge
	 */
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
	
	/**
	 * window displaying settings of the algorithm
	 * 
	 * @param parent parent window
	 */
	public void settingsFrame(JFrame parent) {
	}
	
	/**
	 * reset to a neutral state
	 */
	public void reset() {
		super.reset();
		
		graph.sortEdges();
		forest.clear();
		
		gui.repaint();
	}
	
	/**
	 * string representation of graph
	 * 
	 * @return graph name
	 */
	public String toString() {
		return getClass().getName();
	}
	
	/*public static void main(String[] args) {
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
	}*/
}
