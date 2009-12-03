package view;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

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
		// set up look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		
		// menu
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		JMenuItem menuFileNew = new JMenuItem("New");
		//menuFileNew.addActionListener(actionListener);
		menuFile.add(menuFileNew);
		JMenuItem menuFileOpen = new JMenuItem("Open");
		menuFileOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				
				int returnVal = chooser.showOpenDialog(frame);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					return;
				}
				
				System.out.println("File opened: " + chooser.getSelectedFile().getName());
			}
		});
		menuFile.add(menuFileOpen);
		JMenuItem menuFileSave = new JMenuItem("Save");
		menuFileSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuFile.add(menuFileSave);
		
		JMenu menuVertex = new JMenu("Vertex");
		menuBar.add(menuVertex);
		JMenuItem menuVertexAdd = new JMenuItem("Add");
		//menuFileNew.addActionListener(actionListener);
		menuVertex.add(menuVertexAdd);
		JMenuItem menuVertexEdit = new JMenuItem("Edit");
		menuFileOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuVertex.add(menuVertexEdit);
		JMenuItem menuVertexRemove = new JMenuItem("Remove");
		menuFileSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuVertex.add(menuVertexRemove);
		
		JMenu menuEdge = new JMenu("Edge");
		menuBar.add(menuEdge);
		JMenuItem menuEdgeAdd = new JMenuItem("Add");
		//menuFileNew.addActionListener(actionListener);
		menuEdge.add(menuEdgeAdd);
		JMenuItem menuEdgeEdit = new JMenuItem("Edit");
		menuFileOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuEdge.add(menuEdgeEdit);
		JMenuItem menuEdgeRemove = new JMenuItem("Remove");
		menuFileSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuEdge.add(menuEdgeRemove);
		
		frame.setJMenuBar(menuBar);
		
		frame.setVisible(true);
	}
	
	public void repaint() {
		try {
			// wait 2 seconds
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
}
