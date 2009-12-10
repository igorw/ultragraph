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

import model.Graph;
import model.Vertex;

public class VertexSelectWindow extends JDialog {
	private static final long serialVersionUID = 1L;

	private JComboBox vertexBox;
	private JButton saveButton;
	
	public VertexSelectWindow(Frame parent, Graph graph) {
		super(parent, "Select Vertex");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModal(true);
		setSize(200, 100);
		
		getContentPane().setLayout(new GridLayout(0, 2));
		
		getContentPane().add(new JLabel("Vertex"));
		vertexBox = new JComboBox(graph.getVertices());
		getContentPane().add(vertexBox);
	
		getContentPane().add(new JLabel(""));
		saveButton = new JButton("Okay");
		getContentPane().add(saveButton);
		getRootPane().setDefaultButton(saveButton);

		// close on okay
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public void addActionListener(ActionListener l) {
		saveButton.addActionListener(l);
	}
	
	public Vertex getVertex() {
		return (Vertex) vertexBox.getSelectedItem();
	}
}
