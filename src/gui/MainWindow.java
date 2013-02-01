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
	public static String dataPath = "./data"; 
	
	/**
	 * 
	 */
	private File dataRoot;
	
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
		
		initDataPath();
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
	public static String getDataPath()
	{
		return MainWindow.dataPath;
	}
	
	/**
	 * 
	 * @param path
	 */
	public static void setDataPath(String path){
		
		MainWindow.dataPath = path;
	}
	
	
	public void newProject(String name){
		
	}
	
	public void showCaseEditor(){
		
	}
	
	public void removeCaseEditor(){
		
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
		
		this.navigator = new  ProjectNavigator(this.dataRoot);
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
	private void initContent(){
		this.editor = new TestCaseEditor();
	}
	
	/**
	 * 
	 */
	private void initDataPath()
	{
		this.dataRoot = new File(MainWindow.getDataPath());
		
		if(!dataRoot.exists()){
			boolean wasDirectoryMade = dataRoot.mkdirs();
		    if(wasDirectoryMade)
		    	ConsoleLog.Print("Direcoty Created");
		}
		
	}

}
