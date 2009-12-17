package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Vector;

import misc.Point;
import model.Edge;
import model.Graph;
import model.Vertex;

/**
 * graph canvas
 * canvas to display a graph
 */
public class GraphCanvas extends Canvas {
	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * graph to be displayed
	 */
	private Graph graph;
	
	/**
	 * coordinates for temp line
	 */
	private Point p1, p2;
	
	/**
	 * step by which vertex sizes are multiplied
	 */
	public static int STEP = 4;
	
	/**
	 * constructor
	 * @param graph graph
	 */
	public GraphCanvas(Graph graph) {
		super();
		
		setGraph(graph);
	}
	
	/**
	 * getter for graph
	 * @return graph
	 */
	public Graph getGraph() {
		return graph;
	}
	
	/**
	 * setter for graph
	 * @param graph graph
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	/**
	 * set points for a temporary line
	 * set null to remove the line
	 * 
	 * @param p1 p1
	 * @param p2 p2
	 */
	public void setTempLine(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	/**
	 * check if a temporary line is set
	 * 
	 * @return temp line is set
	 */
	public boolean isTempLine() {
		return p1 != null;
	}
	
	/**
	 * get all vertices located under the mouse pointer
	 * 
	 * @param x position x
	 * @param y position y
	 * @return list of vertices
	 */
	public Vector<Vertex> getMouseVertices(int x, int y) {
		Vector<Vertex> vertices = new Vector<Vertex>();
		for (Vertex v : graph.getVertices()) {
			if (v.getX() * GraphCanvas.STEP < x * STEP && v.getX() * GraphCanvas.STEP + 15 > x * STEP && v.getY() * GraphCanvas.STEP < y * STEP && v.getY() * GraphCanvas.STEP + 15 > y * STEP) {
				vertices.add(v);
			}
		}
		return vertices;
	}
	
	/**
	 * (re)paint the graph
	 */
	public void paint(Graphics g) {
		super.paint(g);
		
		// enable anti-aliasing
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// draw edges
		for (Edge e : graph.getEdges()) {
			g.setColor(e.getColor());
			g.drawLine(e.getV1().getX() * STEP + 8, e.getV1().getY() * STEP + 8, e.getV2().getX() * STEP + 8, e.getV2().getY() * STEP + 8);
			
			// get mid coordinates
			int x = (e.getV1().getX() + e.getV2().getX()) / 2;
			int y = (e.getV1().getY() + e.getV2().getY()) / 2;
			
			g.setColor(Color.black);
			g.setFont(new Font(null, Font.PLAIN, 10));
			g.drawString(String.valueOf(e.getWeight()),
				x * STEP,
				y * STEP);
		}
		
		// draw temp line
		if (p1 != null && p2 != null) {
			g.setColor(Color.black);
			g.drawLine(p1.getX() * STEP + 8, p1.getY() * STEP + 8, p2.getX() * STEP + 8, p2.getY() * STEP + 8);	
		}
		
		// draw vertices
		for (Vertex v : graph.getVertices()) {
			// fill background white
			g.setColor(Color.white);
			g.fillOval(v.getX() * STEP, v.getY() * STEP, 15, 15);
			
			// draw circle
			g.setColor(v.getColor());
			g.drawOval(v.getX() * STEP, v.getY() * STEP, 15, 15);
			
			// vertex name
			g.setColor(Color.black);
			g.setFont(new Font(null, Font.PLAIN, 10));
			g.drawString(v.getName(), v.getX() * STEP + 5, v.getY() * STEP + 11);
			
			// vertex label
			if (v.isLabeled()) {
				g.setColor(Color.gray);
				g.setFont(new Font(null, Font.PLAIN, 10));
				g.drawString(String.valueOf(v.getLabel()), v.getX() * STEP + 20, v.getY() * STEP + 11);
			}
		}
	}
}
