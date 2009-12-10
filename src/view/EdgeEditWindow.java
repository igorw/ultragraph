package view;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Edge;
import model.Graph;
import model.Vertex;

public class EdgeEditWindow extends JDialog {
	private static final long serialVersionUID = 1L;

	private JComboBox v1Box, v2Box;
	private JTextField weightField;
	private JButton saveButton;

	private Edge edge;
	
	// constructor for adding
	public EdgeEditWindow(Frame parent, String title, Graph graph) {
		super(parent, title);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModal(true);
		setSize(300, 150);
		
		getContentPane().setLayout(new GridLayout(0, 2));
		
		getContentPane().add(new JLabel("Vertex 1"));
		v1Box = new JComboBox(graph.getVertices());
		getContentPane().add(v1Box);
	
		getContentPane().add(new JLabel("Vertex 2"));
		v2Box = new JComboBox(graph.getVertices());
		getContentPane().add(v2Box);
	
		getContentPane().add(new JLabel("Weight"));
		weightField = new JTextField();
		getContentPane().add(weightField);
	
		getContentPane().add(new JLabel(""));
		saveButton = new JButton("Save");
		getContentPane().add(saveButton);
		getRootPane().setDefaultButton(saveButton);

		// close on okay
		addSaveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	// constructor for editing
	public EdgeEditWindow(Frame parent, String title, Graph graph, Edge e) {
		this(parent, title, graph);
		
		// set edge
		edge = e;
		
		// set fields
		v1Box.setSelectedItem(edge.getV1());
		v1Box.setEnabled(false);
		
		v2Box.setSelectedItem(edge.getV2());
		v2Box.setEnabled(false);
		
		weightField.setText(String.valueOf(edge.getWeight()));
	}
	
	public void addSaveListener(ActionListener l) {
		saveButton.addActionListener(l);
	}
	
	public void save() {
		if (edge == null) {
			System.out.println("Cannot save a non-existent edge");
			return;
		}

		edge.setV1((Vertex) v1Box.getSelectedItem());
		edge.setV2((Vertex) v2Box.getSelectedItem());
		edge.setWeight(Integer.valueOf(weightField.getText()));
	}
	
	public Edge getEdge() {
		if (edge == null) {
			edge = new Edge((Vertex) v1Box.getSelectedItem(), (Vertex) v2Box.getSelectedItem(), Integer.valueOf(weightField.getText()));
		}
		
		return edge;
	}
}
