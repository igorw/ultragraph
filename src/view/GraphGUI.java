package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;

import model.Edge;
import model.Graph;
import model.Vertex;
import algorithm.Dijkstra;
import algorithm.GraphAlgorithm;
import algorithm.Kruskal;
import algorithm.Prim;
import file.Reader;
import file.Writer;

public class GraphGUI {
	private GraphAlgorithm algo;
	private JFrame frame = new JFrame("UltraGraph");
	private GraphCanvas canvas;
	
	private boolean isConfigured = false;
	
	private Vector<GraphAlgorithm> algorithms = new Vector<GraphAlgorithm>();
	
	private Graph graph = new Graph();
	
	// increase when adding vertices
	private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private int currentLetter = 0;
	
	public GraphGUI(GraphAlgorithm algo) {
		this.algo = algo;
		
		canvas = new GraphCanvas(graph);

		algorithms.add(new Dijkstra(graph, null, null));
		algorithms.add(new Prim(graph));
		algorithms.add(new Kruskal(graph));
		
		algo.setGUI(this);
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
				setGraph(new Graph());
				
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
				setGraph(r.getGraph(chooser.getSelectedFile()));
				
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
					Writer w = new Writer(graph, chooser.getSelectedFile());
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
				final VertexEditWindow w = new VertexEditWindow(frame, "Add Vertex");
				w.addSaveListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						graph.add(w.getVertex());
						repaint();
						System.out.println("vertex added");
					}
				});
				if (currentLetter >= alphabet.length()) {
					currentLetter = 0;
				}
				w.setNameField(alphabet.substring(currentLetter++, currentLetter));
				w.setVisible(true);
			}
		});
		menuVertex.add(menuVertexAdd);
		JMenuItem menuVertexEdit = new JMenuItem("Edit");
		menuVertexEdit.addActionListener(new ActionListener() {
			private Vertex selectedVertex = null;
			
			public void actionPerformed(ActionEvent e) {
				final ItemSelectWindow<Vertex> s = new ItemSelectWindow<Vertex>(frame, "Select Vertex", "Vertex", graph.getVertices());
				s.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectedVertex = s.getItem();
					}
				});
				s.setVisible(true);
				
				if (selectedVertex == null) {
					System.out.println("no vertex selected");
					return;
				}
				
				final VertexEditWindow w = new VertexEditWindow(frame, "Edit Vertex", selectedVertex);
				w.addSaveListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						w.save();
						repaint();
						System.out.println("vertex edited");
					}
				});
				w.setVisible(true);
			}
		});
		menuVertex.add(menuVertexEdit);
		JMenuItem menuVertexRemove = new JMenuItem("Remove");
		menuVertexRemove.addActionListener(new ActionListener() {
			private Vertex selectedVertex = null;
			
			public void actionPerformed(ActionEvent e) {
				final ItemSelectWindow<Vertex> s = new ItemSelectWindow<Vertex>(frame, "Select Vertex", "Vertex", graph.getVertices());
				s.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectedVertex = s.getItem();
					}
				});
				s.setVisible(true);
				
				if (selectedVertex == null) {
					System.out.println("no vertex selected");
					return;
				}
				
				graph.removeVertex(selectedVertex);
				repaint();
				System.out.println("vertex removed");
			}
		});
		menuVertex.add(menuVertexRemove);
		
		JMenu menuEdge = new JMenu("Edge");
		menuBar.add(menuEdge);
		JMenuItem menuEdgeAdd = new JMenuItem("Add");
		menuEdgeAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final EdgeEditWindow w = new EdgeEditWindow(frame, "Add Edge", graph);
				
				w.addSaveListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						graph.connect(w.getEdge().getV1(), w.getEdge().getV2(), w.getEdge().getWeight());
						repaint();
						System.out.println("edge added");
					}
				});
				
				w.setVisible(true);
			}
		});
		menuEdge.add(menuEdgeAdd);
		JMenuItem menuEdgeEdit = new JMenuItem("Edit");
		menuEdgeEdit.addActionListener(new ActionListener() {
			private Edge selectedEdge = null;
			
			public void actionPerformed(ActionEvent e) {
				final ItemSelectWindow<Edge> s = new ItemSelectWindow<Edge>(frame, "Select Edge", "Edge", graph.getEdges());
				s.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectedEdge = s.getItem();
					}
				});
				s.setVisible(true);
				
				if (selectedEdge == null) {
					System.out.println("no edge selected");
					return;
				}
				
				final EdgeEditWindow w = new EdgeEditWindow(frame, "Edit Edge", graph, selectedEdge);
				w.addSaveListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						w.save();
						repaint();
						System.out.println("edge edited");
					}
				});
				w.setVisible(true);
			}
		});
		menuEdge.add(menuEdgeEdit);
		JMenuItem menuEdgeRemove = new JMenuItem("Remove");
		menuEdgeRemove.addActionListener(new ActionListener() {
			private Edge selectedEdge = null;
			
			public void actionPerformed(ActionEvent e) {
				final ItemSelectWindow<Edge> s = new ItemSelectWindow<Edge>(frame, "Select Edge", "Edge", graph.getEdges());
				s.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectedEdge = s.getItem();
					}
				});
				s.setVisible(true);
				
				if (selectedEdge == null) {
					System.out.println("no edge selected");
					return;
				}
				
				graph.removeEdge(selectedEdge);
				repaint();
				System.out.println("edge removed");
			}
		});
		menuEdge.add(menuEdgeRemove);
		
		JMenu menuAlgo = new JMenu("Algorithm");
		menuBar.add(menuAlgo);
		JMenuItem menuAlgoStart = new JMenuItem("Start");
		menuAlgoStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isConfigured) {
					isConfigured = true;
					algo.settingsFrame(frame);
				}
				algo.reset();
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
		JMenuItem menuAlgoSettings = new JMenuItem("Settings");
		menuAlgoSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isConfigured = true;
				algo.settingsFrame(frame);
			}
		});
		menuAlgo.add(menuAlgoSettings);

		menuAlgo.add(new JSeparator());
		
		JMenuItem menuAlgoSelect = new JMenuItem("Select algorithm");
		menuAlgoSelect.addActionListener(new ActionListener() {
			private GraphAlgorithm selectedAlgo = null;
			
			public void actionPerformed(ActionEvent e) {
				final ItemSelectWindow<GraphAlgorithm> s = new ItemSelectWindow<GraphAlgorithm>(frame, "Select algorithm", "Algorithm", algorithms, algo);
				s.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectedAlgo = s.getItem();
					}
				});
				s.setVisible(true);
				
				if (selectedAlgo == null) {
					System.out.println("no algo selected");
					return;
				}
				
				setAlgorithm(selectedAlgo);
				System.out.println("algorithm " + selectedAlgo + " selected");
				
			}
		});
		menuAlgo.add(menuAlgoSelect);
		
		/*JMenu menuDebug = new JMenu("Debug");
		menuBar.add(menuDebug);
		JMenuItem menuDebugVertices = new JMenuItem("List Vertices");
		menuDebugVertices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("----------");
				for (Vertex v : graph.getVertices()) {
					System.out.println(v);
				}
				System.out.println("----------");
			}
		});
		menuDebug.add(menuDebugVertices);*/
		
		frame.setJMenuBar(menuBar);
		
		frame.setVisible(true);
	}
	
	public void repaint() {
		canvas.repaint();
	}
	
	public void setAlgorithm(GraphAlgorithm algo) {
		this.algo = algo;
		algo.setGraph(graph);
		algo.setGUI(this);
		
		isConfigured = false;
	}
	
	public void setGraph(Graph graph) {
		this.graph = graph;
		algo.setGraph(graph);
		canvas.setGraph(graph);
		repaint();
	}
}
