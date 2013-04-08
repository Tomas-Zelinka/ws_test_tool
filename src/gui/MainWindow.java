package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.text.PlainDocument;

import logging.ConsoleLog;

import org.bounce.text.LineNumberMargin;
import org.bounce.text.xml.XMLEditorKit;

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
	private JSplitPane centerPane;
	
	
	
	/**
	 * Splitpane containing main components of the GUI
	 */
	private Component panelPane;
	
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
	
	
	protected TestCaseEditor editor;
	
	private Statistics stats;
	
	private RemoteControl remoteControl;
	
	private ProxyMonitor proxy;
	
	private  TestingMonitor testUnit;
	
	private ProjectNavigator navigator;
	
	private JToolBar mainToolBox; 
	
	private JButton saveTestCase;
	
	
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
		initToolBox();
		getContentPane().add(mainToolBox,BorderLayout.NORTH);
		initMenuBar();
		initContentPane();
		initCenterPane();
		initBottomPane();
		getContentPane().add(this.bottomPane,BorderLayout.CENTER);
		
		
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
	private void initContentPane(){
		
	
		this.editor = new TestCaseEditor();
		this.proxy = new ProxyMonitor();
		this.remoteControl = new RemoteControl();
		this.stats = new Statistics();
		this.testUnit = new TestingMonitor();
		this.centerComponent = new JPanel();
		
		
		//this.panelPane = (JPanel)this.centerComponent;
		
		
	}
	
	/**
	 * 
	 */
	
	
	/**
	 * 
	 */
	private void initCenterPane(){
		File root = new File(MainWindow.getDataRoot());
		
		if(!root.exists()){
			boolean wasDirectoryMade = root.mkdirs();
		    if(wasDirectoryMade)
		    	ConsoleLog.Print("Direcoty Created");
		}
		
		this.navigator = new ProjectNavigator(root);
		this.centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,this.navigator,this.centerComponent);
		this.centerPane.setDividerSize(SPLIT_RESIZERS_WIDTH);
	}
	
	/**
	 * 
	 */
	private JSplitPane getCenterPane(){
		return this.centerPane;
	}
	
	
	/**
	 * 
	 */
	private void initBottomPane(){
		
		this.console = new Console();
		this.bottomPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,getCenterPane(),this.console);
		this.bottomPane.setDividerSize(SPLIT_RESIZERS_WIDTH);
		this.bottomPane.setResizeWeight(0.90);
		
	}
	
	private void initToolBox(){
		mainToolBox = new JToolBar();
		mainToolBox.setFloatable(false);
		
		saveTestCase = new JButton("Save TestCase");
		saveTestCase.addActionListener(new SaveTestCaseListener());
		
		
		
		mainToolBox.add(saveTestCase);
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
	public static String getCasePath(){
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
	public static String getSuitePath(){
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
	public static String getDataRoot(){
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
	public Component getPanel() throws EmptyComponentException{
		if(this.centerComponent == null){
			throw new EmptyComponentException();
		}else{
			return this.centerComponent;
		}
		
	}
	
	public void setContent(int component) {
		int panelType = this.navigator.getPanelType();
		this.centerPane.remove(this.centerComponent);//this.panelPane = this.centerComponent);
		
		switch(component){
			case TESTCASE_EDITOR:
				this.centerComponent = this.editor;
				
				if (panelType == ProjectNavigator.CASE_EDITOR_SETTINGS){
					this.editor.setTab(TestCaseEditor.SETTINGS_TAB);
				}else if(panelType == ProjectNavigator.CASE_EDITOR_HTTP){
					this.editor.setTab(TestCaseEditor.HTTP_TAB);
				}else if(panelType == ProjectNavigator.CASE_EDITOR_FAULT){
					this.editor.setTab(TestCaseEditor.FAULT_TAB);
				}
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
		
		
		//this.panelPane =  this.centerComponent;
		this.centerPane.setRightComponent(this.centerComponent);
		
		getCenterPane().revalidate();
		getCenterPane().repaint();
	}

	
	/**
	 * Metoda pro inicializaci komponenty tridy JEditorPane pro zobrazovani XML zprav.
	 */
	public static void initEditorPane(JEditorPane currentEditorPane, JScrollPane currentScrollPane) {
		
		//pouziti bounce editor kitu pro zvyrazneni syntaxe XML
		XMLEditorKit xmlKit= new XMLEditorKit();
		currentEditorPane.setEditorKit(xmlKit);
		
		//nastaveni fontu
		currentEditorPane.setFont(new Font("Courier", Font.PLAIN, 12));
		//nastaveni sirky tabulatoru
		currentEditorPane.getDocument().putProperty(PlainDocument.tabSizeAttribute, new Integer(1));

		//zobrazeni postranniho panelu s cisly radku
		JPanel rowHeader= new JPanel(new BorderLayout());
		//rowHeader.add(new XMLFoldingMargin(reqOriginalEditorPane), BorderLayout.EAST);
		rowHeader.add(new LineNumberMargin(currentEditorPane), BorderLayout.WEST);
		currentScrollPane.setRowHeaderView(rowHeader);
		
	}
	
	
	private class SaveTestCaseListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Save testcase clicked");
			editor.saveTestCase();
		}
	}
	
}
