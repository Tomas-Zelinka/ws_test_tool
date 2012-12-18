package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Console extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4989941948035851333L;
	
	public Console(){
		setLayout(new BorderLayout());

	    // Make a tree list with all the nodes, and make it a JTree
	    JTextArea console = new JTextArea("ahoj",5,5);
	    //console.setMinimumSize(minimumSize)
	    // Add a listener
	   
	
	    // Lastly, put the JTree into a JScrollPane.
	    JScrollPane scrollpane = new JScrollPane();
	    scrollpane.getViewport().add(console);
	    add(BorderLayout.CENTER, scrollpane);
	}
}