package algorithm;

import model.Edge;
import model.Graph;

/**
 * reverse prim's algorithm
 * get a maximal spanning tree
 */
public class ReversePrim extends Prim {
	/**
	 * default constructor
	 * 
	 * @param graph graph
	 */
	public ReversePrim(Graph graph) {
		super(graph);
	}
	
	/**
	 * compare edge e1 to edge e2
	 * overwrite original prim method, used to reverse algorithm
	 */
	public boolean compareEdges(Edge e1, Edge e2) {
		return e1.getWeight() > e2.getWeight();
	}
}
