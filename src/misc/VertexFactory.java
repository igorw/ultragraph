package misc;

import model.Vertex;

// create vertices with pretty names

public class VertexFactory {
	// increase when adding vertices
	private String alphabet = "abcdefghijklmnopqrstuvwxyza";
	private int currentLetter = 0;
	
	public Vertex getVertex() {
		if (currentLetter >= alphabet.length()) {
			currentLetter = 0;
		}
		return new Vertex(String.valueOf(alphabet.charAt(currentLetter++)));
	}
}
