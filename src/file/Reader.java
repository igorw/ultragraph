package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import model.Graph;
import model.Vertex;

public class Reader {
	
	HashMap<String, Vertex> temp = new HashMap<String, Vertex>();
	Graph g = new Graph();

	public Graph getGraph(File f){
		try{
			FileReader fr = new FileReader(f);
			// neuer Bufferreader
			BufferedReader input = new BufferedReader(fr);
			
			String inputLine = "";
			
			// solange noch Linien vorhanden sind
			while((inputLine = input.readLine())!=null){
			
			// Splitet die Linien mit den Tablulatoren
			String[] tokens = inputLine.split("\t");
			
			
			// Wenn der erste Character "v" ist, wird ein neuer Vertex mit den nächsten drei Character erzeugt.
			if(tokens[0].equals("v")){
					Vertex v = new Vertex(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
					temp.put(v.getName(), v);
					//Vertex wird zum Graph g hinzugef¸gt
					g.add(v);
			}
			
			// Wenn der erste Character "e" ist, wird eine neue Edge mit den nächsten drei Character erzeugt.
			if(tokens[0].equals("e")){
					// hier werden im Graph g zwei Vertex verbunden
					g.connect(temp.get(tokens[1]), temp.get(tokens[2]), Integer.parseInt(tokens[3]));
				}		
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		//gibt den Graph g zurück
		return g;
		
	}

}
