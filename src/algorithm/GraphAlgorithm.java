package algorithm;

import javax.swing.JFrame;

import model.Graph;
import view.GraphGUI;

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
	
	/**
	 * string representation of graph
	 * 
	 * @return graph name
	 */
	public String toString();
	
	/**
	 * start the algorithm in a new thread
	 */
	public void startAlgorithm();

	/**
	 * start the algorithm in a new thread
	 * 
	 * @param paused start pause status
	 */
	public void startAlgorithm(boolean paused);
	
	/**
	 * breakpoint, graph can halt/pause here
	 */
	public void breakPoint();
	
	/**
	 * pause execution
	 */
	public void pause();
	
	/**
	 * continue execution
	 */
	public void unpause();
	
	/**
	 * toggle paused status
	 */
	public void togglePause();
}
