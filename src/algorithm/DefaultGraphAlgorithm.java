package algorithm;

import model.Graph;
import view.GraphGUI;

/**
 * abstract algorithm
 */
public abstract class DefaultGraphAlgorithm implements GraphAlgorithm, Runnable {
	/**
	 * the graph used for calculation
	 */
	protected Graph graph;
	
	/**
	 * the GUI handles display
	 */
	protected GraphGUI gui;
	
	/**
	 * thread for the algo to run in
	 */
	private Thread thread;
	
	/**
	 * status, paused or not
	 */
	private boolean paused = true;
	
	/**
	 * constructor
	 */
	public DefaultGraphAlgorithm(Graph graph) {
		this.graph = graph;
	}
	
	/**
	 * graph getter
	 * 
	 * @return graph graph
	 */
	public Graph getGraph() {
		return graph;
	}
	
	/**
	 * graph setter
	 * 
	 * @param graph graph
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	/**
	 * graphGUI setter
	 * 
	 * @param gui gui
	 */
	public void setGUI(GraphGUI gui) {
		this.gui = gui;
	}
	
	/**
	 * reset algorithm to a neutral state
	 */
	public void reset() {
		paused = true;
		
		graph.reset();
	}
	
	/**
	 * start the algorithm in a new thread
	 */
	public void startAlgorithm() {
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * start the algorithm in a new thread
	 * 
	 * @param paused start pause status
	 */
	public void startAlgorithm(boolean paused) {
		this.paused = paused;
		startAlgorithm();
	}
	
	/**
	 * breakpoint, graph can halt/pause here
	 */
	public final void breakPoint() {
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
	
	/**
	 * pause execution
	 */
	public void pause() {
		paused = true;
	}
	
	/**
	 * continue execution
	 */
	public void unpause() {
		paused = false;
	}
	
	/**
	 * toggle paused status
	 */
	public void togglePause() {
		paused = !paused;
	}
	
	/**
	 * thread run method
	 * executes the algorithm
	 */
	public void run() {
		execute();
	}
	
	/**
	 * string representation of algorithm
	 * 
	 * @return graph name
	 */
	public String toString() {
		return getClass().getSimpleName();
	}
}
