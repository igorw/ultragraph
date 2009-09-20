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

public class Kruskal {
	private Graph graph;
	private Forrest forrest = new Forrest();
	private HashSet<Edge> cleanGrid = new HashSet<Edge>();
	
	public Kruskal(Graph graph) {
		this.graph = graph;
		
		// initialise clean grid
		// clean grid contains no reverse edges
		for (Vertex v : graph.getVertices()) {
			for (Edge e : v.getEdges()) {
				if (!cleanGrid.contains(e.getTarget().findEdge(v))) {
					// ignore reverse connections
					cleanGrid.add(e);
				}
			}
		}
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
				if (processed.contains(e.getTarget().findEdge(e.getOrigin()))) {
					continue;
				}
				out.write("\t" + e.getOrigin() + " -- " + e.getTarget() + " [color=" + e.getColor() + " label=" + e.getWeight() + "];\n");
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
			shortestEdge.getOrigin().setColor("red");
			shortestEdge.getTarget().setColor("red");
			shortestEdge.setColor("red");
			shortestEdge.getTarget().findEdge(shortestEdge.getOrigin()).setColor("red");
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
		for (Edge e : cleanGrid) {
			
			// no point if it's already included
			// also reverse-edges
			if (forrest.contains(e) || forrest.contains(e.getTarget().findEdge(e.getOrigin()))) {
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

		a.connectTo(b, 2);
		a.connectTo(c, 6);
		b.connectTo(c, 3);
		b.connectTo(d, 3);
		b.connectTo(e, 4);
		c.connectTo(d, 1);
		d.connectTo(e, 5);
		d.connectTo(f, 5);
		e.connectTo(f, 3);
		
		Graph graph = new Graph();
		graph.addVertex(a, b, c, d, e, f);
		
		Kruskal kruskal = new Kruskal(graph);
		kruskal.execute();
	}
}
