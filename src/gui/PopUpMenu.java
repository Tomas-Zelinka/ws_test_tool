package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class PopUpMenu {
	  JPopupMenu Pmenu;
	  JMenuItem menuItem;
	 
	  public PopUpMenu(){
		  
		  JFrame frame = new JFrame("Creating a Popup Menu");
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  Pmenu = new JPopupMenu();
		  menuItem = new JMenuItem("Cut");
		  Pmenu.add(menuItem);
		  menuItem = new JMenuItem("Copy");
		  Pmenu.add(menuItem);
		  menuItem = new JMenuItem("Paste");
		  Pmenu.add(menuItem);
		  menuItem = new JMenuItem("Delete");
		  Pmenu.add(menuItem);
		  menuItem = new JMenuItem("Undo");
		  Pmenu.add(menuItem);
		  menuItem.addActionListener(new ActionListener(){
		  
			  public void actionPerformed(ActionEvent e){}
		  });
	  frame.addMouseListener(new MouseAdapter(){
	  
		  public void mouseReleased(MouseEvent Me){
			  if(Me.isPopupTrigger()){
				  Pmenu.show(Me.getComponent(), Me.getX(), Me.getY());
			  }
		  }
	  });
	  
	  frame.setSize(400,400);
	  frame.setVisible(true);
	  
	  }
}
