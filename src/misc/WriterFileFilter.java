package misc;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import file.GraphWriter;

public class WriterFileFilter extends FileFilter {
	
	private GraphWriter w;
	
	public WriterFileFilter(GraphWriter w) {
		this.w = w;
	}
	
	public boolean accept(File f) {
		return true;
	}

	public String getDescription() {
		return w.getName();
	}
	
	public GraphWriter getWriter() {
		return w;
	}

}
