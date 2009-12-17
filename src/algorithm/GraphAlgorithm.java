package algorithm;

import javax.swing.JFrame;

import model.Graph;
import view.GraphGUI;

/**
 * algorithm interface
 * algorithms can be run, they contain a graph
 */
public abstract class GraphAlgorithm implements Runnable {
	/**
	 * the graph used for calculation
	 */
	protected Graph graph;
	
	/**
	 * the GUI handles display
	 */
	protected GraphGUI gui;
	
	/**
	 * constructor
	 */
	public GraphAlgorithm(Graph graph) {
		this.graph = graph;
	}
	
	/**
	 * run the algorithm
	 */
	public abstract void execute();
	
	/**
	 * graph getter
	 * 
	 * @return graph graph
	 */
	public abstract Graph getGraph();
	
	/**
	 * graph setter
	 * 
	 * @param graph graph
	 */
	public abstract void setGraph(Graph graph);
	
	/**
	 * graphGUI setter
	 * 
	 * @param gui gui
	 */
	public abstract void setGUI(GraphGUI gui);
	
	/**
	 * window displaying settings of the algorithm
	 * 
	 * @param parent parent window
	 */
	public abstract void settingsFrame(JFrame parent);
	
	/**
	 * reset algorithm to a neutral state
	 */
	public void reset() {
		paused = true;
		
		graph.reset();
	}
	
	/**
	 * string representation of graph
	 * 
	 * @return graph name
	 */
	public abstract String toString();
	
	Thread thread;
	
	public void startAlgorithm() {
		thread = new Thread(this);
		thread.start();
	}

	private boolean paused = true;
	
	protected void breakPoint() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		if (thread != null) {
			while (paused) {
				Thread.yield();
			}
		}
		
		gui.repaint();
	}
	
	public void pause() {
		paused = true;
	}
	
	public void unpause() {
		paused = false;
	}
	
	public void togglePause() {
		paused = !paused;
	}
	
	public void run() {
		execute();
	}
}
