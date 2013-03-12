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
	
	public void addButton( JButton button){
		this.add(button);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		
	}
	
	
}