package algorithm;

import java.awt.Color;

import javax.swing.JFrame;

import model.DirectedEdge;
import model.Edge;
import model.Graph;
import model.Tree;
import model.Vertex;

/**
 * prim's algorithm
 * get a minimal spanning tree
 */
public class Prim extends DefaultGraphAlgorithm {
	/**
	 * spanning tree containing edges
	 */
	private Tree tree = new Tree();
	
	/**
	 * constructor
	 * 
	 * @param graph
	 */
	public Prim(Graph graph) {
		super(graph);
	}
	
	/**
	 * run the algorithm
	 */
	public void execute() {
		
		// prepare edges for display
		for (Edge e : graph.getEdges()) {
			e.setColor(Color.gray);
		}
		
		Edge shortestEdge;
		Vertex randomVertex = graph.getRandomVertex();
		
		// initial edge
		shortestEdge = getShortestEdge(randomVertex);
		
		// select vertex
		randomVertex.setColor(Color.red);
		
		// select vertices and edge
		shortestEdge.getV1().setColor(Color.red);
		shortestEdge.getV2().setColor(Color.red);
		shortestEdge.setColor(Color.red);
		
		// viz
		breakPoint();
		
		// add initial edge to tree
		tree.add(shortestEdge);
		
		if (graph.getVertices().size() - 1 == tree.size()) {
			// already done
			return;
		}
		
		while (true) {
			// find next shortest edge
			shortestEdge = getShortestEdge();
			
			// none found
			if (shortestEdge == null) {
				System.out.println("Error - no shortest edge found");
				return;
			}
			
			System.out.println(shortestEdge.getV1() + " " + shortestEdge.getV2());
			
			// select vertices and edge
			shortestEdge.getV1().setColor(Color.red);
			shortestEdge.getV2().setColor(Color.red);
			shortestEdge.setColor(Color.red);
			
			// viz
			breakPoint();
			
			// add shortest edge to tree
			tree.add(shortestEdge);
			
			// check for final spanning tree
			if (graph.getVertices().size() - 1 == tree.size()) {
				break;
			}
		}
		
		// we're done
	}

	/**
	 * get shortest edge for a vertex
	 * exclude self-referential edges
	 * 
	 * @param vertex vertex
	 * @return shortest edge
	 */
	private Edge getShortestEdge(Vertex vertex) {
		Edge shortest = null;
		for (DirectedEdge e : graph.getVertexEdges(vertex)) {
			
			// skip self-referential edges
			if (e.getTarget() == e.getOrigin()) {
				continue;
			}
			
			if (shortest == null || compareEdges(e.getEdge(), shortest)) {
				shortest = e.getEdge();
			}
		}
		return shortest;
	}
	
	/**
	 * get shortest edge
	 * that touches tree at 1 end exclusively
	 */
	private Edge getShortestEdge() {
		Edge shortest = null;
		for (Edge e : graph.getEdges()) {
			
			// skip self-referential edges
			if (e.getV2() == e.getV1()) {
				continue;
			}
			
			// check for tree connections
			if (!touchesTreeExclusive(e)) {
				continue;
			}
			
			if (shortest == null || compareEdges(e, shortest)) {
				shortest = e;
			}
		}
		return shortest;
	}
	
	/**
	 * check if an edge touches existing tree only at one end
	 * 
	 * @param edge edge
	 * @return touches tree exclusively
	 */
	public boolean touchesTreeExclusive(Edge edge) {
		int matches = 0;
		for (Vertex vertex : tree.getVertices()) {
			if (edge.getV1() == vertex) matches++;
			if (edge.getV2() == vertex) matches++;
			
			if (matches >= 2) {
				return false;
			}
		}
		return (matches == 1) ? true : false;
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
		
		tree = new Tree();
		
		gui.repaint();
	}
	
	/**
	 * compare edge e1 to edge e2
	 * useful when reversing the algorithm
	 */
	public boolean compareEdges(Edge e1, Edge e2) {
		return e1.getWeight() < e2.getWeight();
	}
}
