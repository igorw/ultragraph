package algorithm;

import model.Graph;

/**
 * reverse of kruskal's algorithm
 * get a maximal spanning tree
 */
public class ReverseKruskal extends Kruskal {
	/**
	 * constructor
	 * 
	 * @param graph graph
	 */
	public ReverseKruskal(Graph graph) {
		super(graph);
	}
	
	/**
	 * sort the graph's edges reverse
	 */
	public void sortEdges() {
		graph.sortEdgesReverse();
	}
}
