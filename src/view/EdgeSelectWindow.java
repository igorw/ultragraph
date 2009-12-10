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

import model.Edge;
import model.Graph;

public class EdgeSelectWindow extends JDialog {
	private static final long serialVersionUID = 1L;

	private JComboBox edgeBox;
	private JButton saveButton;
	
	public EdgeSelectWindow(Frame parent, Graph graph) {
		super(parent, "Select Edge");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModal(true);
		setSize(200, 100);
		
		getContentPane().setLayout(new GridLayout(0, 2));
		
		getContentPane().add(new JLabel("Edge"));
		edgeBox = new JComboBox(graph.getEdges());
		getContentPane().add(edgeBox);
	
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
	
	public Edge getEdge() {
		return (Edge) edgeBox.getSelectedItem();
	}
}
