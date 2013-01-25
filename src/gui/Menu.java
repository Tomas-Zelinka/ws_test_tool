package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5617155576631422259L;
	
	private JMenu file;
	private JMenu newSubMenu;
	//private JMenu view;
	//private JMenu project;
	
	private JMenuItem fileOpen;
	private JMenuItem fileSave;
	private JMenuItem fileSaveAs;
	private JMenuItem fileSaveRequest;
	private JMenuItem fileSaveResponse;
	 
	private JMenuItem fileExit;
 	private JMenuItem fileGenerate;
 	private JMenuItem fileChangeRoot;
	
 	
 	private JMenuItem newTestProject;
 	private JMenuItem newTestSuite;
 	private JMenuItem newTestCase;
 	
 	private NewWindow newProjectWindow;
 	
 	private JFrame parentWindow;
 	
 	
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
		
		parentWindow =(JFrame) getParent();
		file = new JMenu("File");
		newSubMenu = new JMenu("New");
		
		this.add(file);
		file.add(newSubMenu);
		initFileMenuItems();
		//this.add(view);
	}
	
	/**
	 * 
	 */
	private void initFileMenuItems()
	{
		
		newTestProject = new JMenuItem("Test Project");
		newSubMenu.add(newTestProject);
		
		this.newTestProject.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				
				 
				newProjectWindow = new NewWindow();
				newProjectWindow.setVisible(true);
				System.out.println("new Test Project clicked");
			}
			
		} );
		
		
		newTestSuite = new JMenuItem("Test Suite");
		newSubMenu.add(newTestSuite);
		
		this.newTestSuite.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("new Test Suite clicked");
			}
			
		} );
		
		newTestCase = new JMenuItem("Test Case");
		newSubMenu.add(newTestCase);
		
		this.newTestCase.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("new Test Case clicked");
			}
			
		} );
		
		
		fileOpen = new JMenuItem("Open");
		file.add(fileOpen);
		
		this.fileOpen.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("file open clicked");
			}
			
		} );
		
		fileChangeRoot = new JMenuItem("Change Project");
		file.add(fileChangeRoot);
		
		this.fileChangeRoot.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("file Chenge Project clicked");
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
