package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5617155576631422259L;
	
	private JMenu file;
	//private JMenu view;
	//private JMenu project;
	
	private JMenuItem fileOpen;
	private JMenuItem fileNew;
 	private JMenuItem fileExit;
 	private JMenuItem fileGenerate;
	
	/**
	 * 
	 */
	public Menu(){
		initMenus();
		
	}
	
	/**
	 * 
	 */
	private void initMenus(){
		
		file = new JMenu("File");
		
		this.add(file);
		initMenuItems();
		
		
		//this.add(view);
	}
	
	/**
	 * 
	 */
	private void initMenuItems()
	{
		
		fileNew = new JMenuItem("New");
		file.add(fileNew);
		
		this.fileNew.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("file open clicked");
			}
			
		} );
		
		fileOpen = new JMenuItem("Open");
		file.add(fileOpen);
		
		this.fileOpen.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("file open clicked");
			}
			
		} );
		
		fileGenerate = new JMenuItem("Generate");
		file.add(fileGenerate);
		
		this.fileGenerate.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("file generate clicked");
			}
			
		} );
		
		fileExit = new JMenuItem("Exit");
		file.add(fileExit);
		
		this.fileExit.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("file exit clicked");
			}
			
		} );
		
		
		
		
	}
	
	
	
}
