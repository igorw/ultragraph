package vizualisation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import model.Edge;
import model.Graph;
import model.Vertex;
import tools.Shell;

public class GraphViz {
	private Graph graph;
	private String name;
	private int i = 0;
	
	public GraphViz(Graph graph, String name) {
		this.graph = graph;
		this.name = name;
	}
	
	// snapshot a frame
	public void frame() {
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
					// ignore reverse of already processed
					continue;
				}
				out.write("\t" + e.getV1() + " -- " + e.getV2() + " [color=" + e.getColor() + " label=" + e.getWeight() + "];\n");
				processed.add(e);
			}
			out.write("}\n");
			out.close();
			
			Shell.exec("circo -Tgif -o " + name + String.format("%03d", i++) + ".gif < " + tempFile.getAbsolutePath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	// save animated gif
	public void save() {
		try {
			// generate animated gif
			Shell.exec("gifsicle --delay=200 --loop " + name + "*.gif > anim_" + name + ".gif");
			Shell.exec("rm " + name + "*.gif");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
