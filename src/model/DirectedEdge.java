package model;

/**
 * directed edge
 * edge where roles of origin and target are known
 */
public class DirectedEdge {
	/**
	 * origin vertex
	 */
	private Vertex origin;
	
	/**
	 * target vertex
	 */
	private Vertex target;
	
	/**
	 * edge connecting the two
	 */
	private Edge edge;
	
	/**
	 * default constructor
	 * 
	 * @param origin origin
	 * @param target target
	 * @param edge edge
	 */
	public DirectedEdge(Vertex origin, Vertex target, Edge edge) {
		this.origin = origin;
		this.target = target;
		this.edge = edge;
	}
	
	/**
	 * origin getter
	 * 
	 * @return origin
	 */
	public Vertex getOrigin() {
		return origin;
	}
	
	/**
	 * target getter
	 * 
	 * @return target
	 */
	public Vertex getTarget() {
		return target;
	}
	
	/**
	 * edge getter
	 * 
	 * @return edge
	 */
	public Edge getEdge() {
		return edge;
	}
	
	/**
	 * wrapper around getFullWeight from edge
	 * @return
	 */
	public int getFullWeight() {
		return edge.getFullWeight(origin);
	}
}
