package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.Edge;
import model.Graph;
import model.Vertex;

public class Writer {
	
	public Writer(Graph g, File f) throws IOException{
		FileWriter file = new FileWriter(f);
		for(Vertex v : g.getVertices()){
			file.write("v\t");
			file.write(v.getName()+"\t");
			file.write(v.getX()+"\t");
			file.write(v.getY()+"\n");
		}
		for(Edge e : g.getEdges()){
			file.write("e\t");
			file.write(e.getV1()+"\t");
			file.write(e.getV2()+"\t");
			file.write(e.getWeight()+"\n");
		}
		
		file.close();
	}

}
