package view;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Edge;
import model.Graph;
import model.Vertex;

public class DijkstraGUI {
	private Graph graph;
	private JFrame frame = new JFrame("Dijkstra");
	private Canvas canvas;
	
	public DijkstraGUI(Graph graph) {
		this.graph = graph;
		canvas = new GraphCanvas(graph);
	}
	
	public void init() {
		/*try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setLayout(new BorderLayout());
		frame.setContentPane(new JPanel());
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		
		canvas.setBackground(Color.white);
		canvas.setSize(450, 350);
		
		// prepare edges for display
		for (Edge e : graph.getEdges()) {
			e.setColor(Color.gray);
		}
		
		frame.setVisible(true);
	}
	
	public void repaint() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		canvas.repaint();
	}
	
	private class GraphCanvas extends Canvas {
		private static final long serialVersionUID = 1L;
		private Graph graph;
		
		private int STEP = 4;
		
		public GraphCanvas(Graph graph) {
			super();
			
			this.graph = graph;
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			
			for (Edge e : graph.getEdges()) {
				g.setColor(e.getColor());
				g.drawLine(e.getV1().getPosX() * STEP + 8, e.getV1().getPosY() * STEP + 8, e.getV2().getPosX() * STEP + 8, e.getV2().getPosY() * STEP + 8);
				
				g.setColor(Color.black);
				g.setFont(new Font(null, Font.PLAIN, 10));
				g.drawString(String.valueOf(e.getWeight()),
						(e.getV1().getPosX() + (e.getV1().getPosX() - e.getV2().getPosX()) / 2) * STEP,
						(e.getV1().getPosY() + (e.getV1().getPosY() - e.getV2().getPosY()) / 2) * STEP);
			}
			
			for (Vertex v : graph.getVertices()) {
				g.setColor(v.getColor());
				g.drawOval(v.getPosX() * STEP, v.getPosY() * STEP, 15, 15);
				
				g.setColor(Color.black);
				g.setFont(new Font(null, Font.PLAIN, 10));
				g.drawString(v.getName(), v.getPosX() * STEP + 5, v.getPosY() * STEP + 11);
			}
		}
	}
}
