package misc;

import model.Vertex;

/**
 * vertex factory
 * create vertices with pretty names 
 */
public class VertexFactory {
	/**
	 * mask for getting vertex names
	 */
	private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	
	/**
	 * counter, increment when adding vertices
	 */
	private int currentLetter = 0;
	
	/**
	 * get a new vertex
	 * @return named vertex
	 */
	public Vertex getVertex() {
		if (currentLetter >= alphabet.length()) {
			currentLetter = 0;
		}
		return new Vertex(String.valueOf(alphabet.charAt(currentLetter++)));
	}
}
