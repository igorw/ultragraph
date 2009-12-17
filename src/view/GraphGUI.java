package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import misc.Point;
import misc.VertexFactory;
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
	
	private VertexFactory vertexFactory = new VertexFactory();
	
	private Graph graph = new Graph();
	
	// right-click context menu
	// and mouse click location
	private JPopupMenu popup = new JPopupMenu();
	private Point mouseLocation = new Point();
	
	// vertex currently selected by mouse
	private Vertex selectedVertex = null;
	
	private Thread algoThread;
	
	// constructor
	public GraphGUI(GraphAlgorithm algo) {
		this.algo = algo;
		
		canvas = new GraphCanvas(graph);

		algorithms.add(new Dijkstra(graph, null, null));
		algorithms.add(new Prim(graph));
		algorithms.add(new Kruskal(graph));
		
		algo.setGUI(this);
		
		algoThread = new Thread(algo);
		algoThread.start();
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
		
		// window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setLayout(new BorderLayout());
		frame.setContentPane(new JPanel());
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		
		// context menu
		JMenuItem contextVertexAdd = new JMenuItem("Add Vertex");
		contextVertexAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vertex v = vertexFactory.getVertex();
				v.setX((int) mouseLocation.getX() / GraphCanvas.STEP);
				v.setY((int) mouseLocation.getY() / GraphCanvas.STEP);
				graph.add(v);
				repaint();
			}
		});
		popup.add(contextVertexAdd);
		
		// graph canvas
		canvas.setBackground(Color.white);
		canvas.setSize(450, 350);
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// select a vertex with the mouse
				// find the vertex for moving
				for (Vertex v : canvas.getMouseVertices(e.getX() / GraphCanvas.STEP, e.getY() / GraphCanvas.STEP)) {
					selectedVertex = v;
					break;
				}
				
				mouseLocation.setPoint(e.getX(), e.getY());
				
				if (e.isPopupTrigger()) {
					// show right-click menu
					popup.show(e.getComponent(), e.getX(), e.getY());
				} else if (e.isAltDown() && e.isShiftDown()) {
					// find the vertices for deleting
					graph.removeVertices(canvas.getMouseVertices(e.getX() / GraphCanvas.STEP, e.getY() / GraphCanvas.STEP));
					repaint();
				} else if (e.isAltDown()) {
					Vertex v = vertexFactory.getVertex();
					v.setX((int) e.getX() / GraphCanvas.STEP);
					v.setY((int) e.getY() / GraphCanvas.STEP);
					graph.add(v);
					repaint();
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (canvas.isTempLine()) {
					// create the edge
					Vertex droppedVertex = null;
					for (Vertex v : canvas.getMouseVertices(e.getX() / GraphCanvas.STEP, e.getY() / GraphCanvas.STEP)) {
						droppedVertex = v;
						break;
					}
					if (droppedVertex != null) {
						graph.connect(selectedVertex, droppedVertex);
					}

					// reset the canvas temp line
					canvas.setTempLine(null, null);
					repaint();
					
					// reset selectedVertex
					selectedVertex = null;
				}
			}
		});
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (e.isShiftDown()) {
					// draw hypothetical edge
					if (selectedVertex != null) {
						canvas.setTempLine(new Point(selectedVertex.getX(), selectedVertex.getY()), new Point(e.getX() / GraphCanvas.STEP, e.getY() / GraphCanvas.STEP));
						repaint();
					}
				} else {
					// drag and move a vertex
					if (selectedVertex != null) {
						// move using the center of the mouse
						// therefore subtract 15/2 = 7
						selectedVertex.setX((e.getX() - 7) / GraphCanvas.STEP);
						selectedVertex.setY((e.getY() - 7) / GraphCanvas.STEP);
						repaint();
					}
				}
			}
		});
		
		// menu
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuGraph = new JMenu("Graph");
		menuBar.add(menuGraph);
		JMenuItem menuGraphNew = new JMenuItem("New");
		menuGraphNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setGraph(new Graph());
				
				System.out.println("New graph created");
			}
		});
		menuGraphNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuGraph.add(menuGraphNew);
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
		menuGraphOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
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
		menuGraphSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
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
				Vertex v = vertexFactory.getVertex();
				w.setNameField(v.getName());
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
				algo.togglePause();
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
