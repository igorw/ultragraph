package view;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Vertex;

public class VertexEditWindow extends JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField nameField, xField, yField;
	private JButton saveButton;

	private Vertex vertex;
	
	public VertexEditWindow(Frame parent, Vertex v) {
		super(parent, "Edit Vertex");
		
		// set temp vars
		vertex = v;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModal(true);
		setSize(300, 150);
		
		getContentPane().setLayout(new GridLayout(0, 2));
		
		getContentPane().add(new JLabel("Vertex"));
		nameField = new JTextField(vertex.getName());
		getContentPane().add(nameField);
	
		getContentPane().add(new JLabel("Position X"));
		xField = new JTextField(String.valueOf(vertex.getPosX()));
		getContentPane().add(xField);
	
		getContentPane().add(new JLabel("Position Y"));
		yField = new JTextField(String.valueOf(vertex.getPosY()));
		getContentPane().add(yField);
	
		getContentPane().add(new JLabel(""));
		saveButton = new JButton("Save");
		getContentPane().add(saveButton);

		// close on okay
		addSaveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public void addSaveListener(ActionListener l) {
		saveButton.addActionListener(l);
	}
	
	public void save() {
		if (vertex == null) {
			System.out.println("Cannot save a non-existent vertex");
			return;
		}
		
		vertex.setName(nameField.getText());
		vertex.setPosX(Integer.valueOf(xField.getText()));
		vertex.setPosY(Integer.valueOf(yField.getText()));
	}
}
