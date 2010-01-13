package misc;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import file.GraphWriter;

/**
 * FileFilter containing a GraphWriter
 */
public class WriterFileFilter extends FileFilter {
	/**
	 * GraphWriter providing a description
	 */
	private GraphWriter w;
	
	/**
	 * default constructor
	 * 
	 * @param w writer
	 */
	public WriterFileFilter(GraphWriter w) {
		this.w = w;
	}
	
	/**
	 * accept all files
	 * 
	 * @param f file to be accepted
	 */
	public boolean accept(File f) {
		return true;
	}
	
	/**
	 * file filter description
	 * 
	 * @return writer name
	 */
	public String getDescription() {
		return w.getName();
	}
	
	/**
	 * getter for writer
	 * 
	 * @return writer
	 */
	public GraphWriter getWriter() {
		return w;
	}

}
