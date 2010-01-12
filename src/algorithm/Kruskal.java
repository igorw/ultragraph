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
public class Kruskal extends DefaultGraphAlgorithm {
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
}
