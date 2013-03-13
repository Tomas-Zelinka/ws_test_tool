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
	 * Splitpane containing main components of the GUI
	 */
	private Container centerPane;
	
	/**
	 * 
	 */
	private Component centerComponent;
	
	/**
	 * SplitPane containing centerPane and Console for tool responses
	 */
	private JSplitPane bottomPane;
	
	
	/**
	 * Extended JPanel holding ScrollPane with TextArea for tool responses
	 */
	private Console console;
	
	
	private TestCaseEditor editor;
	
	private Statistics stats;
	
	private RemoteControl remoteControl;
	
	private ProxyMonitor proxy;
	
	private  TestingUnit testUnit;
	
	public static final int TESTCASE_EDITOR = 0;
	public static final int PROXY_MONITOR = 1;
	public static final int REMOTE_CONTROL = 2;
	public static final int STATISTICS = 3;
	public static final int TESTING_UNIT = 4;
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
		this.centerPane = getContentPane();
		this.editor = new TestCaseEditor();
		this.proxy = new ProxyMonitor();
		this.remoteControl = new RemoteControl();
		this.stats = new Statistics();
		this.testUnit = new TestingUnit();
		this.centerComponent = this.editor;
		
		
		this.centerPane.add(this.centerComponent);
		
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
	
	public void setContent(int component) {
		
		this.centerPane.remove(this.centerComponent);
		
		switch(component){
			case TESTCASE_EDITOR:
				this.centerComponent = this.editor;
				break;
			case PROXY_MONITOR: 
				this.centerComponent = this.proxy;
				break;
			case REMOTE_CONTROL: 
				this.centerComponent = this.remoteControl;
				break;
			case STATISTICS: 
				this.centerComponent = this.stats;
				break;
			case TESTING_UNIT: 
				this.centerComponent = this.testUnit;
				break;	
			default:
				System.out.println("MainWindow.setContent() - something is wrong in switch statement");
				break;
				
		}
		this.centerPane.add(this.centerComponent);
		((JPanel)this.centerPane).revalidate();
		this.centerPane.repaint();
	}

	
	
	
}
