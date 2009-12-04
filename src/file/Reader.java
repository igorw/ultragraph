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
			BufferedReader input = new BufferedReader(fr);
			
			String inputLine = "";
			while((inputLine = input.readLine())!=null){
			
			String[] tokens = inputLine.split("\t");
			
			
			
			if(tokens[0].equals("v")){
					Vertex v = new Vertex(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
					temp.put(v.getName(), v);
					g.add(v);
			}
				
			if(tokens[0].equals("e")){
					g.connect(temp.get(tokens[1]), temp.get(tokens[2]), Integer.parseInt(tokens[3]));
				}		
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return g;
		
	}

}
