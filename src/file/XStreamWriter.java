package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.Edge;
import model.Graph;
import model.Vertex;

import com.thoughtworks.xstream.XStream;

/**
 * xstream xml writer
 * uses xstream: http://xstream.codehaus.org
 * depends on xpp3: http://www.extreme.indiana.edu/xgws/xsoap/xpp/mxp1/index.html
 */
public class XStreamWriter implements GraphWriter {
	/**
	 * write to file
	 * @param g input graph
	 * @param f output file
	 * @throws IOException
	 */
	public void write(Graph g, File f) throws IOException {
		XStream xstream = new XStream();

		xstream.alias("Graph", Graph.class);
		xstream.alias("Vertex", Vertex.class);
		xstream.alias("Edge", Edge.class);
		
		FileWriter file = new FileWriter(f);
		file.write(xstream.toXML(g));
		file.close();
	}
	
	/**
	 * get the name of the format
	 */
	public String getName() {
		return "XML (XStream)";
	}
}
