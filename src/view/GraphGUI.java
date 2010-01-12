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

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import misc.Point;
import misc.VertexFactory;
import misc.WriterFileFilter;
import model.Edge;
import model.Graph;
import model.Vertex;
import algorithm.Dijkstra;
import algorithm.GraphAlgorithm;
import algorithm.Kruskal;
import algorithm.Prim;
import file.DotWriter;
import file.GraphWriter;
import file.Reader;
import file.Writer;
import file.XStreamWriter;

/**
 * main GUI class
 */
public class GraphGUI {
	/**
	 * currently active graphing algorithm
	 */
	private GraphAlgorithm algo;
	
	/**
	 * main gui frame
	 */
	private JFrame frame = new JFrame("UltraGraph");
	
	/**
	 * canvas for visualization
	 */
	private GraphCanvas canvas;
	
	/**
	 * was the algorithm configured
	 */
	private boolean isConfigured = false;
	
	/**
	 * list of graphing algorithms
	 */
	private Vector<GraphAlgorithm> algorithms;
	
	/**
	 * factory for pretty-name vertices
	 */
	private VertexFactory vertexFactory = new VertexFactory();
	
	/**
	 * the active graph
	 */
	private Graph graph;
	
	/**
	 * right-click context menu
	 */
	private JPopupMenu popup = new JPopupMenu();
	
	/**
	 * mouse click location
	 */
	private Point mouseLocation = new Point();
	
	/**
	 * vertex currently selected by mouse
	 * used for dragging
	 */
	private Vertex selectedVertex = null;
	
	/**
	 * default constructor
	 */
	public GraphGUI(Vector<GraphAlgorithm> algorithms, Graph graph) {
		this.graph = graph;
		
		canvas = new GraphCanvas(graph);
		
		this.algorithms = algorithms;
		
		algo = algorithms.firstElement();
		algo.setGUI(this);
	}
	
	/**
	 * constructor
	 */
	public GraphGUI(Vector<GraphAlgorithm> algorithms) {
		this(algorithms, new Graph());
	}
	
	/**
	 * set up all components, set visible
	 */
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
				
				if (e.getClickCount() == 2) {
					// double click
					// ...
				} else if (e.isPopupTrigger()) {
					// show right-click menu
					popup.show(e.getComponent(), e.getX(), e.getY());
				} else if (e.isAltDown() && e.isShiftDown()) {
					// find the vertices for deleting
					graph.removeAll(canvas.getMouseVertices(e.getX() / GraphCanvas.STEP, e.getY() / GraphCanvas.STEP));
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
					if (selectedVertex != null && droppedVertex != null) {
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

				// save using default format
				FileFilter f = new WriterFileFilter(new Writer());
				chooser.addChoosableFileFilter(f);
				
				// save using alternate format
				chooser.addChoosableFileFilter(new WriterFileFilter(new DotWriter()));
				chooser.addChoosableFileFilter(new WriterFileFilter(new XStreamWriter()));
				
				// set default writer
				chooser.setFileFilter(f);
				
				int returnVal = chooser.showSaveDialog(frame);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					return;
				}
				
				try {
					GraphWriter w = ((WriterFileFilter) chooser.getFileFilter()).getWriter();
					w.write(graph, chooser.getSelectedFile());
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
				
				graph.remove(selectedVertex);
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
				
				graph.remove(selectedEdge);
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
				algo.startAlgorithm(false);
			}
		});
		menuAlgoStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuAlgo.add(menuAlgoStart);
		JMenuItem menuAlgoStop = new JMenuItem("Pause");
		menuAlgoStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				algo.togglePause();
			}
		});
		menuAlgo.add(menuAlgoStop);
		/*JMenuItem menuAlgoStep = new JMenuItem("Next Step");
		menuAlgoStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuAlgo.add(menuAlgoStep);*/
		JMenuItem menuAlgoSettings = new JMenuItem("Settings");
		menuAlgoSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isConfigured = true;
				algo.settingsFrame(frame);
			}
		});
		menuAlgoSettings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
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
		menuAlgoSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuAlgo.add(menuAlgoSelect);
		
		JMenu menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		JMenuItem menuHelpAbout = new JMenuItem("About");
		menuHelpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "UltraGraph Copyright 2009 Boldi & Wiedler", "About UltraGraph", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("logo.png"));
			}
		});
		menuHelp.add(menuHelpAbout);
		
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
	
	/**
	 * repaint the canvas
	 */
	public void repaint() {
		canvas.repaint();
	}
	
	/**
	 * change the algorithm
	 * @param selectedAlgo algorithm
	 */
	public void setAlgorithm(GraphAlgorithm selectedAlgo) {
		this.algo = selectedAlgo;
		selectedAlgo.setGraph(graph);
		selectedAlgo.setGUI(this);
		
		isConfigured = false;
	}
	
	/**
	 * change the graph
	 * @param graph graph
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
		algo.setGraph(graph);
		canvas.setGraph(graph);
		repaint();
	}
	
	/**
	 * main method
	 * @param args args
	 */
	public static void main(String[] args) {
		Graph graph = new Graph();
		
		Vector<GraphAlgorithm> algorithms = new Vector<GraphAlgorithm>();
		
		algorithms.add(new Dijkstra(graph, null, null));
		algorithms.add(new Kruskal(graph));
		algorithms.add(new Prim(graph));
		
		GraphGUI gui = new GraphGUI(algorithms, graph);
		gui.init();
	}
}
