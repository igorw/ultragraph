package algorithm;

import javax.swing.JFrame;

import model.Graph;
import view.GraphGUI;

/**
 * algorithm interface
 * algorithms can be run, they contain a graph
 */
public interface GraphAlgorithm {
	/**
	 * run the algorithm
	 */
	public void execute();
	
	/**
	 * graph getter
	 * 
	 * @return graph graph
	 */
	public Graph getGraph();
	
	/**
	 * graph setter
	 * 
	 * @param graph graph
	 */
	public void setGraph(Graph graph);
	
	/**
	 * graphGUI setter
	 * 
	 * @param gui gui
	 */
	public void setGUI(GraphGUI gui);
	
	/**
	 * window displaying settings of the algorithm
	 * 
	 * @param parent parent window
	 */
	public void settingsFrame(JFrame parent);
	
	/**
	 * reset algorithm to a neutral state
	 */
	public void reset();
}
