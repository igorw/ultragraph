package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.Edge;
import model.Graph;
import model.Vertex;

public class Writer implements GraphWriter {
	
	// für die Funktion Writer müssen der Graph g und den gewünschte Namen für das Text File übergeben werden
	public void write(Graph g, File f) throws IOException{
		// Das File mit dem gewünschten Namen wird erzeugt
		FileWriter file = new FileWriter(f);
		// Solange Vertices im Graph g vorhanden sind
		for(Vertex v : g.getVertices()){
			file.write("v\t");	// gibt die Identifikation für Vertex
			file.write(v.getName()+"\t");	// der Name für den Vertex
			file.write(v.getX()+"\t");	// die X-Koordinate für den Vertex
			file.write(v.getY()+"\n");	// die Y-Koordinate für den Vertex
		}
		// Solange Edges in im Graph vorhandne sind
		for(Edge e : g.getEdges()){
			file.write("e\t");	// Identifikation für Edge
			file.write(e.getV1()+"\t");	// Start Vertex der Edge
			file.write(e.getV2()+"\t");	// End Vertex der Edge
			file.write(e.getWeight()+"\n");	// Gewichtung der Edge
		}
		
		// file wird geschlossen
		file.close();
	}
	
	public String getName() {
		return "UltraGraph";
	}

}
