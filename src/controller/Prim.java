package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

import model.Edge;
import model.Graph;
import model.Vertex;
import tools.Shell;

// prim's algorithm
// get a minimal spanning tree
public class Prim {
	private Graph graph;
	private HashSet<Edge> tree = new HashSet<Edge>();
	
	public Prim(Graph graph) {
		this.graph = graph;
	}
	
	public void execute() {
		Edge shortestEdge;
		
		// initial edge
		shortestEdge = getShortestEdge(getRandomVertex());
		
		// add initial edge to tree
		tree.add(shortestEdge);
		
		while (true) {
			// find next shortest edge
			shortestEdge = getShortestEdge();
			
			// none found
			if (shortestEdge == null) {
				System.out.println("Error - no shortest edge found");
				return;
			}
			
			// add shortest edge to tree
			tree.add(shortestEdge);
			
			System.out.println(shortestEdge.getOrigin() + " " + shortestEdge.getTarget());
			
			// check for final spanning tree
			if (graph.getVertices().size() - 1 == tree.size()) {
				break;
			}
		}
		
		// we're done
		try {
			File tempFile = File.createTempFile("graphviz", null);
			
			BufferedWriter out = new BufferedWriter(new FileWriter(tempFile.getAbsoluteFile()));
			out.write("graph g {\n");
			for (Edge e : tree) {
				out.write("\t" + e.getOrigin() + " -- " + e.getTarget() + ";\n");
			}
			out.write("}\n");
			out.close();
			
			Shell.exec("dot -Tpng -o /home/igor/prim.png < " + tempFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// get initial vertex
	private Vertex getRandomVertex() {
		int index = new Random().nextInt(graph.getVertices().size() - 1) + 1;
		return graph.getVertices().get(index);
	}

	// get shortest edge for a vertex
	private Edge getShortestEdge(Vertex vertex) {
		Edge shortest = null;
		for (Edge e : graph.getEdges()) {
			
			// skip self-referencial edges
			if (e.getTarget() == e.getOrigin()) {
				continue;
			}
			
			if (shortest == null || e.getWeight() < shortest.getWeight()) {
				shortest = e;
			}
		}
		return shortest;
	}

	// get shortest edge
	private Edge getShortestEdge() {
		Edge shortest = null;
		for (Edge e : graph.getEdges()) {
			
			// skip self-referencial edges
			if (e.getTarget() == e.getOrigin()) {
				continue;
			}
			
			// check for tree connections
			if (!touchesTreeExclusive(e)) {
				continue;
			}
			
			if (shortest == null || e.getWeight() < shortest.getWeight()) {
				shortest = e;
			}
		}
		return shortest;
	}
	
	// edge touches existing tree only at one end
	private boolean touchesTreeExclusive(Edge edge) {
		int matches = 0;
		for (Vertex vertex : getTreeVertices()) {
			if (edge.getOrigin() == vertex) matches++;
			if (edge.getTarget() == vertex) matches++;
			
			if (matches >= 2) {
				return false;
			}
		}
		return true;
	}
	
	private HashSet<Vertex> getTreeVertices() {
		HashSet<Vertex> vertices = new HashSet<Vertex>();
		for (Edge e : tree) {
			vertices.add(e.getOrigin());
			vertices.add(e.getTarget());
		}
		return vertices;
	}
	
	public static void main(String[] args) {
		Vertex a = new Vertex("A");
		Vertex b = new Vertex("B");
		Vertex c = new Vertex("C");
		Vertex d = new Vertex("D");
		Vertex e = new Vertex("E");
		Vertex f = new Vertex("F");
		
		a.connectTo(b, 5);
		a.connectTo(d, 4);
		a.connectTo(e, 8);
		b.connectTo(c, 3);
		b.connectTo(d, 3);
		d.connectTo(c, 7);
		d.connectTo(e, 3);
		e.connectTo(f, 1);
		c.connectTo(f, 2);
		
		Graph graph = new Graph();
		graph.addVertex(a, b, c, d, e, f);
		
		Prim prim = new Prim(graph);
		prim.execute();
	}
}
