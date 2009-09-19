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
	private HashSet<Vertex> touched = new HashSet<Vertex>();
	private HashSet<Edge> result = new HashSet<Edge>();
	
	public Prim(Graph graph) {
		this.graph = graph;
	}
	
	public void execute() {
		Vertex selectedVertex;
		
		// get initial vertex
		selectedVertex = getRandomVertex();
		
		while (true) {
			
			// mark vertex as touched
			touched.add(selectedVertex);
			
			Edge shortestEdge = getShortestEdge();
			
			if (shortestEdge == null) {
				System.out.println("Error - no shortest edge found");
				return;
			}
			
			result.add(shortestEdge);
			
			System.out.println(shortestEdge.getOrigin() + " " + shortestEdge.getTarget());
			
			selectedVertex = shortestEdge.getTarget();
			
			// check for final spanning tree
			if (graph.getVertices().size() - 1 == result.size()) {
				break;
			}
		}
		
		// we're done
		try {
			File tempFile = File.createTempFile("graphviz", null);
			
			BufferedWriter out = new BufferedWriter(new FileWriter(tempFile.getAbsoluteFile()));
			out.write("graph g {\n");
			for (Edge e : result) {
				out.write("\t" + e.getOrigin() + " -- " + e.getTarget() + ";\n");
			}
			out.write("}\n");
			out.close();
			
			Shell.exec("dot -Tpng -o /home/igor/prim.png < " + tempFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Vertex getRandomVertex() {
		int index = new Random().nextInt(graph.getVertices().size() - 1) + 1;
		return graph.getVertices().get(index);
	}

	// get shortest edge
	public Edge getShortestEdge() {
		Edge shortest = null;
		for (Edge e : graph.getEdges()) {
			
			// skip self-referencial edges
			if (e.getTarget() == e.getOrigin()) {
				continue;
			}
			
			// skip already touched targets
			if (touched.contains(e.getTarget())) {
				continue;
			}
			
			// and their vice-versas
			Edge mirror = e.getOrigin().getMirror(e.getTarget());
			if (mirror != null && touched.contains(mirror)) {
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
