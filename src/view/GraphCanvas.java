package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import model.Edge;
import model.Graph;
import model.Vertex;

// canvas to display a graph
public class GraphCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	private Graph graph;
	
	// vertex currently selected by mouse
	private Vertex selectedVertex = null;
	
	public static int STEP = 4;
	
	public GraphCanvas(Graph graph) {
		super();
		
		setGraph(graph);
		
		// select a vertex with the mouse
		addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				selectedVertex = null;
			}
			public void mousePressed(MouseEvent e) {
				for (Vertex v : getGraph().getVertices()) {
					if (v.getPosX() * STEP < e.getX() && v.getPosX() * STEP + 15 > e.getX() && v.getPosY() * STEP < e.getY() && v.getPosY() * STEP + 15 > e.getY()) {
						selectedVertex = v;
						break;
					}
				}
			}
		});
		
		// drag and move a vertex
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (selectedVertex != null) {
					selectedVertex.setPosX(e.getX() / STEP);
					selectedVertex.setPosY(e.getY() / STEP);
					repaint();
				}
			}
		});
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	// (re)paint the graph
	public void paint(Graphics g) {
		super.paint(g);
		
		// draw edges
		for (Edge e : graph.getEdges()) {
			g.setColor(e.getColor());
			g.drawLine(e.getV1().getPosX() * STEP + 8, e.getV1().getPosY() * STEP + 8, e.getV2().getPosX() * STEP + 8, e.getV2().getPosY() * STEP + 8);
			
			// get mid coordinates
			int x = (e.getV1().getPosX() + e.getV2().getPosX()) / 2;
			int y = (e.getV1().getPosY() + e.getV2().getPosY()) / 2;
			
			g.setColor(Color.black);
			g.setFont(new Font(null, Font.PLAIN, 10));
			g.drawString(String.valueOf(e.getWeight()),
				x * STEP,
				y * STEP);
		}
		
		// draw vertices
		for (Vertex v : graph.getVertices()) {
			// fill background white
			g.setColor(Color.white);
			g.fillOval(v.getPosX() * STEP, v.getPosY() * STEP, 15, 15);
			
			// draw circle
			g.setColor(v.getColor());
			g.drawOval(v.getPosX() * STEP, v.getPosY() * STEP, 15, 15);
			
			// vertex name
			g.setColor(Color.black);
			g.setFont(new Font(null, Font.PLAIN, 10));
			g.drawString(v.getName(), v.getPosX() * STEP + 5, v.getPosY() * STEP + 11);
			
			// vertex label
			if (v.isLabeled()) {
				g.setColor(Color.gray);
				g.setFont(new Font(null, Font.PLAIN, 10));
				g.drawString(String.valueOf(v.getLabel()), v.getPosX() * STEP + 20, v.getPosY() * STEP + 11);
			}
		}
	}
}
