package model;

import java.util.HashSet;

/**
 * tree
 * a tree contains a set of edges
 */
public class Tree {
	/**
	 * list of edges
	 */
	private HashSet<Edge> edges = new HashSet<Edge>();
	
	/**
	 * add edge to the tree
	 * @param e edge
	 */
	public void add(Edge e) {
		edges.add(e);
	}
	
	/**
	 * remove edge from tree
	 * @param e edge
	 */
	public void remove(Edge e) {
		edges.remove(e);
	}
	
	/**
	 * getter for edges
	 * @return edges
	 */
	public HashSet<Edge> getEdges() {
		return edges;
	}
	
	/**
	 * contains edge or reverse-edge
	 * 
	 * @param edge edge
	 * @return edge contained in tree
	 */
	public boolean contains(Edge edge) {
		return edges.contains(edge);
	}
	
	/**
	 * does the edge touch the tree
	 * 
	 * @param edge edge
	 * @return edge touches tree
	 */
	public boolean connects(Edge edge) {
		for (Edge e : edges) {
			if (edge.getV1() == e.getV1() || edge.getV1() == e.getV2()) return true;
			if (edge.getV2() == e.getV1() || edge.getV2() == e.getV2()) return true;
		}
		return false;
	}
	
	/**
	 * do both edges touch the tree
	 * use a hashset to ensure vertices aren't checked twice
	 */
	public boolean connectsBoth(Edge edge) {
		HashSet<Vertex> match = new HashSet<Vertex>();
		for (Edge e : edges) {
			if (edge.getV1() == e.getV1() || edge.getV1() == e.getV2()) match.add(edge.getV1());
			if (edge.getV2() == e.getV1() || edge.getV2() == e.getV2()) match.add(edge.getV2());
			
			if (match.size() >= 2) {
				// both edges touch the tree
				return true;
			}
		}
		return false;
	}
	
	/**
	 * get set of vertices in tree
	 * @return vertices
	 */
	public HashSet<Vertex> getVertices() {
		HashSet<Vertex> vertices = new HashSet<Vertex>();
		for (Edge e : getEdges()) {
			vertices.add(e.getV1());
			vertices.add(e.getV2());
		}
		return vertices;
	}
	
	/**
	 * number of edges
	 * @return size of tree
	 */
	public int size() {
		return edges.size();
	}
}
