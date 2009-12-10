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

public class VertexAddWindow extends JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField nameField, xField, yField;
	private JButton saveButton;
	
	public VertexAddWindow(Frame parent) {
		super(parent, "Add Vertex");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModal(true);
		setSize(300, 150);
		
		getContentPane().setLayout(new GridLayout(0, 2));
		
		getContentPane().add(new JLabel("Vertex name"));
		nameField = new JTextField();
		getContentPane().add(nameField);
	
		getContentPane().add(new JLabel("Position X"));
		xField = new JTextField();
		getContentPane().add(xField);
	
		getContentPane().add(new JLabel("Position Y"));
		yField = new JTextField();
		getContentPane().add(yField);
	
		getContentPane().add(new JLabel(""));
		saveButton = new JButton("Okay");
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
	
	public Vertex getVertex() {
		return new Vertex(nameField.getText(), Integer.valueOf(xField.getText()), Integer.valueOf(yField.getText()));
	}
}
