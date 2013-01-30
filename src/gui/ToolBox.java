package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class ToolBox extends JToolBar implements ActionListener {
/**
	 * 
	 */
	private static final long serialVersionUID = 3608875508872233381L;
	
	static final private String START = "Start";
	static final private String EDIT = "Edit";
	static final private String STOP = "Stop";
	
	public ToolBox() {
			//Create the toolbar.
		
		addButtons();
		
		//Create the text area used for output.  Request
		//enough space for 5 rows and 30 columns.
		//Lay out the main panel.
		//setPreferredSize(new Dimension(450, 130));
		
		
	}
	
	protected void addButtons() {
		JButton button = null;
		
		//first button
		button = new JButton(START);
		this.add(button);
		
		//second button
		button = new JButton(EDIT);
		this.add(button);
		
		//third button
		button = new JButton(STOP);
		this.add(button);
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		
		
	}
	
	
}