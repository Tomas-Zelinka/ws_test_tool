package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;

public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2740437090361841747L;
	private final int WIDTH = 800;
	private final int HEIGTH = 600;
	private final String APP_NAME = "Testing tool";
	private Container contentPane; 
	private String defaultDataPath = "./data"; 
	private File dataRoot;
		
	
	/*
	 * Popis
	 * 
	 * @param
	 * @return
	 */
	public MainWindow(){
		contentPane = this.getContentPane();
		windowInit();
		addMenuBar();
		
		this.setLayout(new BorderLayout());
		addTree();
		addConsole();
		//this.pack();
	
	}
	
	
	private void windowInit()
	{
		this.setTitle(this.APP_NAME);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		 
		// Determine the new location of the window
		int x = (dim.width-(this.WIDTH))/2;
		int y = (dim.height-(this.HEIGTH))/2;
		 
		
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(x, y, this.WIDTH, this.HEIGTH);
		this.setMinimumSize(new Dimension(this.WIDTH,this.HEIGTH));
	}
	
	private void addMenuBar(){
		this.setJMenuBar(new Menu());
		
	}
	
	/*private void addToolBar(){
		
	}*/
	
	private void addTree(){
		
		 initDataPath();
		 this.contentPane.add(new  ProjectNavigator(this.dataRoot),BorderLayout.LINE_START);
	}
	
	/*private void addJPanel(){
		
	}*/
	
	private void addConsole(){
		this.contentPane.add(new Console(),BorderLayout.PAGE_END);
	}
	
	private void initDataPath()
	{
		this.dataRoot = new File(this.defaultDataPath);
		
		if(!dataRoot.exists()){
			boolean wasDirecotyMade = dataRoot.mkdirs();
		    if(wasDirecotyMade)System.out.println("Direcoty Created");
		}
		
	}
	
	
	
}
