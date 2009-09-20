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
	
	private int i = 0;
	private void generateImage() throws IOException {
		File tempFile = File.createTempFile("graphviz", null);
		
		BufferedWriter out = new BufferedWriter(new FileWriter(tempFile.getAbsoluteFile()));
		out.write("graph g {\n");
		HashSet<Edge> processed = new HashSet<Edge>();
		for (Vertex v : graph.getVertices()) {
			out.write("\r" + v + " [color=" + v.getColor() + "];\n");
		}
		for (Edge e : graph.getEdges()) {
			if (processed.contains(e.getTarget().findEdge(e.getOrigin()))) {
				// ignore reverse of already processed
				continue;
			}
			out.write("\t" + e.getOrigin() + " -- " + e.getTarget() + " [color=" + e.getColor() + " label=" + e.getWeight() + "];\n");
			processed.add(e);
		}
		out.write("}\n");
		out.close();
		
		Shell.exec("circo -Tgif -o prim" + i++ + ".gif < " + tempFile.getAbsolutePath());
	}
	
	public void execute() {
		
		// prepare edges for display
		for (Edge e : graph.getEdges()) {
			e.setColor("grey");
		}
		
		// same for vertices
		for (Vertex v : graph.getVertices()) {
			v.setColor("black");
		}
		
		try {
			// initial image
			generateImage();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Edge shortestEdge;
		Vertex randomVertex = getRandomVertex();
		
		// initial edge
		shortestEdge = getShortestEdge(randomVertex);
		
		// add initial edge to tree
		tree.add(shortestEdge);
		
		try {
			// select vertex
			randomVertex.setColor("red");
			generateImage();
			
			// select edge
			shortestEdge.getOrigin().setColor("red");
			shortestEdge.getTarget().setColor("red");
			shortestEdge.setColor("red");
			shortestEdge.getTarget().findEdge(shortestEdge.getOrigin()).setColor("red");
			generateImage();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
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
			
			try {
				// select vertices and edge
				shortestEdge.getOrigin().setColor("red");
				shortestEdge.getTarget().setColor("red");
				shortestEdge.setColor("red");
				shortestEdge.getTarget().findEdge(shortestEdge.getOrigin()).setColor("red");
				generateImage();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			System.out.println(shortestEdge.getOrigin() + " " + shortestEdge.getTarget());
			
			// check for final spanning tree
			if (graph.getVertices().size() - 1 == tree.size()) {
				break;
			}
		}
		
		// we're done

		try {
			// generate animated gif
			Shell.exec("gifsicle --delay=200 --loop prim*.gif > anim_prim.gif");
			Shell.exec("rm prim*.gif");
		} catch (IOException ex) {
			ex.printStackTrace();
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
		for (Edge e : vertex.getEdges()) {
			
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
	// that touches tree at 1 end exclusively
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
		return (matches == 1) ? true : false;
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
