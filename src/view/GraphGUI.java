package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

import model.Graph;
import model.Vertex;
import algorithm.GraphAlgorithm;
import file.Reader;
import file.Writer;

public class GraphGUI {
	private GraphAlgorithm algo;
	private JFrame frame = new JFrame("UltraGraph");
	private GraphCanvas canvas;
	
	private boolean isConfigured = false;
	
	public GraphGUI(GraphAlgorithm algo) {
		this.algo = algo;
		canvas = new GraphCanvas(this.algo.getGraph());
	}
	
	public void init() {
		// set up look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (System.getProperty("os.name").contains("Mac")) {
			// add menubar to os x
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setLayout(new BorderLayout());
		frame.setContentPane(new JPanel());
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		
		canvas.setBackground(Color.white);
		canvas.setSize(450, 350);
		
		// menu
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuGraph = new JMenu("Graph");
		menuBar.add(menuGraph);
		JMenuItem menuFileNew = new JMenuItem("New");
		menuFileNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateGraph(new Graph());
				
				System.out.println("New graph created");
			}
		});
		menuGraph.add(menuFileNew);
		JMenuItem menuGraphOpen = new JMenuItem("Open");
		menuGraphOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				
				int returnVal = chooser.showOpenDialog(frame);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					return;
				}
				
				Reader r = new Reader();
				updateGraph(r.getGraph(chooser.getSelectedFile()));
				
				System.out.println("Graph opened: " + chooser.getSelectedFile().getName());
			}
		});
		menuGraph.add(menuGraphOpen);
		JMenuItem menuGraphSave = new JMenuItem("Save");
		menuGraphSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				
				int returnVal = chooser.showSaveDialog(frame);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					return;
				}
				
				try {
					final Writer w = new Writer(algo.getGraph(), chooser.getSelectedFile());
					w.getClass(); // dummy
				} catch (IOException e1) {
					System.out.println("Save failed");
				}
				
				System.out.println("Graph saved: " + chooser.getSelectedFile().getName());
			}
		});
		menuGraph.add(menuGraphSave);
		
		JMenu menuVertex = new JMenu("Vertex");
		menuBar.add(menuVertex);
		JMenuItem menuVertexAdd = new JMenuItem("Add");
		menuVertexAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final VertexAddWindow w = new VertexAddWindow(frame);
				
				w.addSaveListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						algo.getGraph().add(w.getVertex());
						canvas.repaint();
						System.out.println("vertex added");
					}
				});
				
				w.setVisible(true);
			}
		});
		menuVertex.add(menuVertexAdd);
		JMenuItem menuVertexEdit = new JMenuItem("Edit");
		menuVertexEdit.addActionListener(new ActionListener() {
			private Vertex selectedVertex = null;
			
			public void actionPerformed(ActionEvent e) {
				
				final VertexSelectWindow s = new VertexSelectWindow(frame, algo.getGraph());
				s.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectedVertex = s.getVertex();
					}
				});
				s.setVisible(true);
				
				if (selectedVertex == null) {
					System.out.println("no vertex selected");
					return;
				}
				
				final VertexEditWindow w = new VertexEditWindow(frame, selectedVertex);
				w.addSaveListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						w.save();
						canvas.repaint();
						System.out.println("vertex edited");
					}
				});
				w.setVisible(true);
			}
		});
		menuVertex.add(menuVertexEdit);
		JMenuItem menuVertexRemove = new JMenuItem("Remove");
		menuVertexRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuVertex.add(menuVertexRemove);
		
		JMenu menuEdge = new JMenu("Edge");
		menuBar.add(menuEdge);
		JMenuItem menuEdgeAdd = new JMenuItem("Add");
		menuEdgeAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuEdge.add(menuEdgeAdd);
		JMenuItem menuEdgeEdit = new JMenuItem("Edit");
		menuEdgeEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuEdge.add(menuEdgeEdit);
		JMenuItem menuEdgeRemove = new JMenuItem("Remove");
		menuEdgeRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuEdge.add(menuEdgeRemove);
		
		JMenu menuAlgo = new JMenu("Algorithm");
		menuBar.add(menuAlgo);
		JMenuItem menuAlgoSettings = new JMenuItem("Settings");
		menuAlgoSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isConfigured = true;
				algo.settingsFrame(frame);
			}
		});
		menuAlgo.add(menuAlgoSettings);
		JMenuItem menuAlgoStart = new JMenuItem("Start");
		menuAlgoStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isConfigured) {
					isConfigured = true;
					algo.settingsFrame(frame);
				}
				algo.execute();
			}
		});
		menuAlgo.add(menuAlgoStart);
		JMenuItem menuAlgoStop = new JMenuItem("Pause");
		menuAlgoStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuAlgo.add(menuAlgoStop);
		JMenuItem menuAlgoStep = new JMenuItem("Next Step");
		menuAlgoStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuAlgo.add(menuAlgoStep);
		
		JMenu menuDebug = new JMenu("Debug");
		menuBar.add(menuDebug);
		JMenuItem menuDebugVertices = new JMenuItem("List Vertices");
		menuDebugVertices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("----------");
				for (Vertex v : algo.getGraph().getVertices()) {
					System.out.println(v);
				}
				System.out.println("----------");
			}
		});
		menuDebug.add(menuDebugVertices);
		
		frame.setJMenuBar(menuBar);
		
		frame.setVisible(true);
	}
	
	public void repaint() {
		canvas.repaint();
	}
	
	private void updateGraph(Graph graph) {
		algo.setGraph(graph);
		canvas.setGraph(algo.getGraph());
		canvas.repaint();
	}
}
