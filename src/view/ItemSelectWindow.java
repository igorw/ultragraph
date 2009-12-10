package view;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ItemSelectWindow<E> extends JDialog {
	private static final long serialVersionUID = 1L;

	private JComboBox itemBox;
	private JButton selectButton;
	
	public ItemSelectWindow(Frame parent, String title, String label, Vector<E> items) {
		super(parent, "Select Edge");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModal(true);
		setSize(200, 100);
		
		getContentPane().setLayout(new GridLayout(0, 2));
		
		getContentPane().add(new JLabel("Edge"));
		itemBox = new JComboBox(items);
		getContentPane().add(itemBox);
	
		getContentPane().add(new JLabel(""));
		selectButton = new JButton("Okay");
		getContentPane().add(selectButton);
		getRootPane().setDefaultButton(selectButton);

		// close on okay
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public void addActionListener(ActionListener l) {
		selectButton.addActionListener(l);
	}
	
	@SuppressWarnings("unchecked")
	public E getItem() {
		return (E) itemBox.getSelectedItem();
	}
}
