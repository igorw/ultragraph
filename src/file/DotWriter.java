package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.Edge;
import model.Graph;
import model.Vertex;

/**
 * dot writer
 * writer for the graphviz 'dot' format
 * http://www.graphviz.org/doc/info/lang.html
 * example processing:
 * neato -Tpng -n test.dot > test.png
 */
public class DotWriter implements GraphWriter {
	/**
	 * constant to multiply coordinates by
	 * used to adjust positions of vertices
	 */
	private static final int STEP = 10;
	
	/**
	 * write to file
	 * @param g input graph
	 * @param f output file
	 * @throws IOException
	 */
	public void write(Graph g, File f) throws IOException {
		FileWriter file = new FileWriter(f);
		
		file.write("graph mygraph {\n");
		
		for (Vertex v : g.getVertices()) {
			// negative y because it's postscript style coordinates
			file.write("\t" + v.getName() + " [pos=\"" + v.getX() * STEP + "," + -v.getY() * STEP + "\"];\n");
		}
		
		for (Edge e : g.getEdges()) {
			file.write("\t" + e.getV1().getName() + " -- " + e.getV2().getName() + " [label=\"" + e.getWeight() + "\"];\n");
		}
		
		file.write("}\n");
		
		file.close();
	}
	
	/**
	 * get the name of the format
	 */
	public String getName() {
		return "dot (graphviz)";
	}
}
