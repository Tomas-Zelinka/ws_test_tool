package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import exceptions.EmptyComponentException;


public class MainWindow extends JFrame{

	/**
	 * ID for serialization
	 */
	private static final long serialVersionUID = 2740437090361841747L;
	
	/**
	 * Width of the main window
	 */
	private final int WIDTH = 1000;
	
	/**
	 * Height of the main window
	 */
	private final int HEIGTH = 800;
	
	
	/**
	 * Width of the resizing frames
	 */
	private final int SPLIT_RESIZERS_WIDTH = 3;
	
	/**
	 * Application title
	 */
	private final String APP_NAME = "Testing tool";
	
	/**
	 * 
	 */
	private static String dataRoot = "." + File.separator + "data";
	
	/**
	 * 
	 */
	private static String suitePath = "";  
	
	/**
	 * 
	 */
	private static String casePath = "";  
		
	/**
	 * 
	 */
	private static String endpointPath = "";  
	
	/**
	 * Splitpane containing Project navigator and action place
	 */
	private Container centerPane;
	
	private Component centerComponent;
	
	/**
	 * SplitPane containing centerPane and Console for tool responses
	 */
	private JSplitPane bottomPane;
	
	
	/**
	 * Extended JPanel holding ScrollPane with TextArea for tool responses
	 */
	private Console console;
	
	/**
	 * 
	 */
	private void initMainWindow()
	{
		this.setTitle(this.APP_NAME);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		 
		// Determine the new location of the window
		int x = (dim.width-(this.WIDTH))/2;
		int y = (dim.height-(this.HEIGTH))/2;
		 
		
		//this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(x, y, this.WIDTH, this.HEIGTH);
		this.setMinimumSize(new Dimension(this.WIDTH,this.HEIGTH));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.gray);
		
		initMenuBar();
		initCenterPane();
		initBottomPane();
		setContentPane(this.bottomPane);
	}
		
	/**
	 * 
	 * 
	 */
	private void initMenuBar(){
		this.setJMenuBar(new Menu());
		
	}
	
	/**
	 * 
	 */
	private void initCenterPane(){
		try{
			setContent(new TestCaseEditor());
		}catch(EmptyComponentException e){
			System.out.println("MainWindow.nitCenterPane() - The center pane is not Initialized");
		}
	}
	
	/**
	 * 
	 */
	private void initBottomPane(){
		
		this.console = new Console();
		
		try{
			this.bottomPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,getCenterPane(),this.console);
		}catch(EmptyComponentException e){
			System.out.println("The center pane is not Initialized");
		}
		
		this.bottomPane.setDividerSize(SPLIT_RESIZERS_WIDTH);
		this.bottomPane.setResizeWeight(0.90);
		
	}
	
	/**
	 * Constructor for main window
	 * 
	 * At first are initiated the underlying components and then 
	 * those components are placed into the containers 
	 * 
	 */
	public MainWindow(){
		initMainWindow();
		this.pack();
	
	}
	
	/**
	 * 
	 */
	public static String getEndpointPath()
	{
		return MainWindow.endpointPath;
	}
	
	/**
	 * 
	 * @param path
	 */
	public static void setEndpointPath(String path){
		
		MainWindow.endpointPath = path;
	}
	
	
	/**
	 * 
	 */
	public static String getCasePath()
	{
		return MainWindow.casePath;
	}
	
	/**
	 * 
	 * @param path
	 */
	public static void setCasePath(String path){
		
		MainWindow.casePath = path;
	}
		
	/**
	 * 
	 */
	public static String getSuitePath()
	{
		return MainWindow.suitePath;
	}
	
	/**
	 * 
	 * @param path
	 */
	public static void setSuitePath(String path){
		
		MainWindow.suitePath = path;
	}
		
	/**
	 * 
	 */
	public static String getDataRoot()
	{
		return MainWindow.dataRoot;
	}
	
	/**
	 * 
	 * @param path
	 */
	public static void setDataRoot(String root){
		
		MainWindow.dataRoot = root;
	}
	
	/**
	 * 
	 * @return
	 */
	public Container getCenterPane() throws EmptyComponentException{
		if(this.centerPane == null){
			throw new EmptyComponentException();
		}else{
			return this.centerPane;
		}
		
	}
	
	public void setContent(Component c) throws EmptyComponentException{
		
		if(c == null){
			throw new EmptyComponentException();
		}else{
			if(this.centerPane == null){
				this.centerPane = getContentPane();
			}
			if(this.centerComponent != null)
				this.centerPane.remove(this.centerComponent);
			
			this.centerComponent = c;
			this.centerPane.add(c);
			( (JPanel) this.centerPane).revalidate();
			
		}
	}

	
	
	
}
