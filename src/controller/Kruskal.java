package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import model.Edge;
import model.Forrest;
import model.Graph;
import model.Vertex;
import tools.Shell;

// kruskal's algorithm
// get a minimal spanning tree
public class Kruskal implements GraphAlgorithm {
	private Graph graph;
	private Forrest forrest = new Forrest();
	
	public Kruskal(Graph graph) {
		this.graph = graph;
	}

	private int i = 0;
	private void generateImage() {
		try {
			File tempFile = File.createTempFile("graphviz", null);
			
			BufferedWriter out = new BufferedWriter(new FileWriter(tempFile.getAbsoluteFile()));
			out.write("graph g {\n");
			for (Vertex v : graph.getVertices()) {
				out.write("\r" + v + " [color=" + v.getColor() + "];\n");
			}
			HashSet<Edge> processed = new HashSet<Edge>();
			for (Edge e : graph.getEdges()) {
				if (processed.contains(e)) {
					continue;
				}
				out.write("\t" + e.getV1() + " -- " + e.getV2() + " [color=" + e.getColor() + " label=" + e.getWeight() + "];\n");
				processed.add(e);
			}
			out.write("}\n");
			out.close();
			
			// run dot to generate gifs
			Shell.exec("circo -Tgif -o kruskal" + i++ + ".gif < " + tempFile.getAbsolutePath());
			
			//
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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
		
		// initial image
		generateImage();
		
		while (true) {
			Edge shortestEdge = getShortestEdge();
			
			if (shortestEdge == null) {
				System.out.println("no shortest edge found");
				System.exit(0);
			}
			
			System.out.println("shortest found: " + shortestEdge);
			
			// select vertices and edge
			shortestEdge.getV1().setColor("red");
			shortestEdge.getV2().setColor("red");
			shortestEdge.setColor("red");
			generateImage();
			
			forrest.add(shortestEdge);
			
			if (forrest.size() == 1 && forrest.countEdges() == graph.getVertices().size() - 1) {
				break;
			}
		}
		
		System.out.println("done");
		
		try {
			// make an animated gif from out images
			Shell.exec("gifsicle --delay=200 --loop kruskal*.gif > anim_kruskal.gif");
			Shell.exec("rm kruskal*.gif");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	// get shortest edge that does not complete a circuit
	private Edge getShortestEdge() {
		Edge shortest = null;
		for (Edge e : graph.getEdges()) {
			
			// no point if it's already included
			if (forrest.contains(e)) {
				continue;
			}
			
			// check for curcuits (origin and target connect to forrest)
			if (forrest.connectsBoth(e)) {
				continue;
			}
			
			if (shortest == null || e.getWeight() < shortest.getWeight()) {
				shortest = e;
			}
		}
		return shortest;
	}
	
	public static void main(String[] args) {
		Vertex a = new Vertex("A");
		Vertex b = new Vertex("B");
		Vertex c = new Vertex("C");
		Vertex d = new Vertex("D");
		Vertex e = new Vertex("E");
		Vertex f = new Vertex("F");
		
		Graph graph = new Graph();
		graph.add(a, b, c, d, e, f);

		graph.connect(a, b, 2);
		graph.connect(a, c, 6);
		graph.connect(b, c, 3);
		graph.connect(b, d, 3);
		graph.connect(b, e, 4);
		graph.connect(c, d, 1);
		graph.connect(d, e, 5);
		graph.connect(d, f, 5);
		graph.connect(e, f, 3);
		
		Kruskal kruskal = new Kruskal(graph);
		kruskal.execute();
	}
}
