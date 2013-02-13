package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import logging.ConsoleLog;


public class MainWindow extends JFrame{

	/**
	 * ID for serialization
	 */
	private static final long serialVersionUID = 2740437090361841747L;
	
	/**
	 * Width of the main window
	 */
	private final int WIDTH = 800;
	
	/**
	 * Height of the main window
	 */
	private final int HEIGTH = 600;
	
	
	/**
	 * Width of the resizing frames
	 */
	private final int SPLIT_RESIZERS_WIDTH = 3;
	
	/**
	 * Application title
	 */
	private final String APP_NAME = "Testing tool";
	
	/**
	 * Splitpane containing Project navigator and action place
	 */
	private JSplitPane centerPane;
	
	/**
	 * SplitPane containing centerPane and Console for tool responses
	 */
	private JSplitPane bottomPane;
		
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
	 * JPanel holding the various editors and controls
	 */
	private JPanel editor;
	
	/**
	 * Extended JPanel holding JTree for document navigation
	 */
	private ProjectNavigator navigator;
	
	/**
	 * Extended JPanel holding ScrollPane with TextArea for tool responses
	 */
	private Console console;
	
	/**
	 * Constructor for main window
	 * 
	 * At first are initiated the underlying components and then 
	 * those components are placed into the containers 
	 * 
	 */
	public MainWindow(){
		
		initContent();
		initConsole();
		
		windowInit();
		addMenuBar();
		addCenterPane();
		addBottomPane();
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
	 * @param panel
	 */
	public void setContent(JPanel panel){
		this.editor = panel;
		this.centerPane.add(this.editor);
	}
	
	
	public void newProject(String name){
		
	}
	
	
	/**
	 * 
	 */
	public void removeContent(){
		this.centerPane.remove(this.editor);
		this.editor = null;
	}
	
	/**
	 * 
	 */
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
		this.setLayout(new BorderLayout());
		this.setBackground(Color.gray);
		
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	private JSplitPane getCenterPane(){
		return this.centerPane;
	}
	
	
	
	/**
	 * 
	 * 
	 */
	private void addMenuBar(){
		this.setJMenuBar(new Menu());
		
	}
	
	/**
	 * 
	 */
	private void addCenterPane(){
		
		File root = new File(MainWindow.getDataRoot());
		
		if(!root.exists()){
			boolean wasDirectoryMade = root.mkdirs();
		    if(wasDirectoryMade)
		    	ConsoleLog.Print("Direcoty Created");
		}
		
		this.navigator = new  ProjectNavigator(root);
		this.centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,this.navigator,this.editor);
		this.centerPane.setDividerSize(SPLIT_RESIZERS_WIDTH);
		
	}
	
	/**
	 * 
	 */
	private void addBottomPane(){
		
		this.bottomPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,getCenterPane(),this.console);
		this.bottomPane.setDividerSize(SPLIT_RESIZERS_WIDTH);
		this.bottomPane.setResizeWeight(0.90);
		getContentPane().add(this.bottomPane);
	}
	
	/**
	 * 
	 */
	private void initConsole(){
		this.console = new Console();
	}
	
	/**
	 * 
	 */
	public void initContent(){
		this.editor = new PlainPanel();
	}

}
