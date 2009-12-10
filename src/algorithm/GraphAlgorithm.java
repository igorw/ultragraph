package algorithm;

import javax.swing.JFrame;

import model.Graph;
import view.GraphGUI;

public interface GraphAlgorithm {
	public void execute();
	public Graph getGraph();
	public void setGraph(Graph graph);
	public void setGUI(GraphGUI gui);
	public void settingsFrame(JFrame parent);
	public void reset();
}
