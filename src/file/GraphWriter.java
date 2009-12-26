package file;

import java.io.File;
import java.io.IOException;

import model.Graph;

public interface GraphWriter {
	public void write(Graph g, File f) throws IOException;
	public String getName();
}
