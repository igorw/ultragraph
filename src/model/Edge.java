package model;

import java.awt.Color;

/**
 * edge
 * an edge connects two nodes
 */
public class Edge implements Comparable<Edge> {
	/**
	 * first vertex
	 */
	private Vertex v1;
	
	/**
	 * second vertex
	 */
	private Vertex v2;
	
	/**
	 * weight
	 */
	private int weight;
	
	/**
	 * color for display
	 */
	private Color color = Color.black;
	
	/**
	 * constructor
	 * 
	 * @param origin origin vertex
	 * @param target target vertex
	 * @param weight weight
	 */
	public Edge(Vertex origin, Vertex target, int weight) {
		setV1(origin);
		setV2(target);
		setWeight(weight);
	}
	
	/**
	 * constructor
	 * 
	 * @param origin origin vertex
	 * @param target target vertex
	 */
	public Edge(Vertex origin, Vertex target) {
		setV1(origin);
		setV2(target);
		setWeight(1);
	}
	
	/**
	 * v1 getter
	 * @return v1
	 */
	public Vertex getV1() {
		return v1;
	}
	
	/**
	 * v1 setter
	 * @param v1 v1
	 */
	public void setV1(Vertex v1) {
		this.v1 = v1;
	}
	
	/**
	 * v2 getter
	 * @return v2
	 */
	public Vertex getV2() {
		return v2;
	}
	
	/**
	 * v2 setter
	 * @param v2 v2
	 */
	public void setV2(Vertex v2) {
		this.v2 = v2;
	}
	
	/**
	 * weight getter
	 * @return weight
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * weight setter
	 * @param weight weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**
	 * color getter
	 * @return color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * color setter
	 * @param color color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * get full relative weight
	 * this traces the added weight back to the root node
	 * for performance reasons it's cached in the label
	 * because v1 and v2 are stateless, you must specify origin
	 * 
	 * @param origin origin vertex
	 * @return full weight
	 */
	public int getFullWeight(Vertex origin) {
		return (origin.isLabeled() ? origin.getLabel() : 0) + weight;
	}

	/**
	 * reset to a neutral state
	 */
	public void reset() {
		color = Color.black;
	}
	
	/**
	 * string representation
	 */
	public String toString() {
		return getV1() + " " + getV2() + " " + getWeight();
	}
	
	/**
	 * compare edge to edge
	 * from Comparable, to allow sorting edges by weight
	 */
	public int compareTo(Edge e) {
		if (getWeight() > e.getWeight()) {
			return 1;
		} else if (getWeight() < e.getWeight()) {
			return -1;
		} else {
			return 0;
		}
	}
}
