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
	
	public VertexEditWindow(Frame parent, String title) {
		super(parent, title);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModal(true);
		setSize(300, 150);
		
		getContentPane().setLayout(new GridLayout(0, 2));
		
		getContentPane().add(new JLabel("Vertex"));
		nameField = new JTextField();
		getContentPane().add(nameField);
	
		getContentPane().add(new JLabel("Position X"));
		xField = new JTextField();
		getContentPane().add(xField);
	
		getContentPane().add(new JLabel("Position Y"));
		yField = new JTextField();
		getContentPane().add(yField);
	
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
	
	public VertexEditWindow(Frame parent, String title, Vertex v) {
		this(parent, title);
		
		// set vertex
		vertex = v;
		
		// set text fields
		nameField.setText(vertex.getName());
		xField.setText(String.valueOf(vertex.getPosX()));
		yField.setText(String.valueOf(vertex.getPosY()));
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
	
	public Vertex getVertex() {
		if (vertex == null) {
			vertex = new Vertex(nameField.getText(), Integer.valueOf(xField.getText()), Integer.valueOf(yField.getText()));
		}
		
		return vertex;
	}
}
