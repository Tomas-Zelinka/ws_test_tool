package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBox extends JToolBar implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3608875508872233381L;
	
	public ToolBox(){
		this.setFloatable(false);
	}
	
	public void addButton( JButton button){
		this.add(button);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		
	}
	
	
}