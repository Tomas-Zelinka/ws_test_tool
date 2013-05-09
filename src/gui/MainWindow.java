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
import modalWindows.AddRemoteUnitDialog;
import modalWindows.GenerateFromWSDLDialog;

import org.bounce.text.LineNumberMargin;
import org.bounce.text.xml.XMLEditorKit;

import central.UnitController;


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
	
	private int panelType;
	protected TestCaseEditor editor;
	private Statistics statsPanel;
	private ProxyMonitor proxyUnitPanel;
	private  TestMonitor testUnitPanel;
	private Navigator navigator;
	
	private Component toolBox;
	private JToolBar testCaseToolBox; 
	private JToolBar testUnitToolBox;
	private JToolBar proxyUnitToolBox;
	
	public static final int TESTCASE_EDITOR = 0;
	public static final int PROXY_MONITOR = 1;
	public static final int STATISTICS = 2;
	public static final int TESTING_UNIT = 3;
	public static final int PLAIN_PANEL = 3;
	
	public static final int CASE_EDITOR_SETTINGS = 5;
	public static final int CASE_EDITOR_HTTP = 6;
	public static final int CASE_EDITOR_FAULT = 7;
	
	public static final int TESTUNIT_TOOLBOX = 5;
	public static final int TESTCASE_TOOLBOX = 6;
	public static final int PROXYMON_TOOLBOX = 7;
	
	
	
	/**
	 * Constructor for main window
	 * 
	 * At first are initiated the underlying components and then 
	 * those components are placed into the containers 
	 * 
	 */
	public MainWindow(UnitController testUnitController){
		
		initMainWindow(testUnitController);
		this.pack();
	}
	
	/**
	 * 
	 * @param box
	 */
	public void setToolBox(int box){
		
		if(toolBox != null){
			getContentPane().remove(this.toolBox);
		}
		
		if(box == MainWindow.TESTCASE_TOOLBOX){
			this.toolBox = testCaseToolBox;
		}else if(box == MainWindow.TESTUNIT_TOOLBOX){
			this.toolBox = testUnitToolBox;
		}else if(box == MainWindow.PROXYMON_TOOLBOX){
			this.toolBox = proxyUnitToolBox;
		}
		
		getContentPane().add(this.toolBox,BorderLayout.NORTH);
	}
	
	/**
	 * 
	 */
	public static String getEndpointPath(){
		
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
		
		if(!path.isEmpty())
			MainWindow.casePath = getSuitePath()+File.separator+path;
		ConsoleLog.Print("[MainWindow] Case path set to: " + MainWindow.casePath);
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
		
		MainWindow.suitePath = getDataRoot()+File.separator+path;
		ConsoleLog.Print("[MainWindow] Suite path set to: " + MainWindow.suitePath);
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
	
	public int getPanelType(){
		
		return this.panelType;
	}
	public void setPanelType(int type){
		
		this.panelType = type;
	}
	
	public void closeTestCase(){
		
		this.editor.closeTestCase(getPanelType()); 
	}
	
	public void refreshTree(){
		
		Navigator.refreshTree();
	}
	
	public void insertTestCase(){
		
		testUnitPanel.insertTestCase(MainWindow.getCasePath());
	}
	
	/**
	 * 
	 * @param component
	 */
	public void setContent(int component) {
		
		this.centerPane.remove(this.centerComponent);
		
		switch(component){
			case TESTCASE_EDITOR:
				setToolBox(MainWindow.TESTCASE_TOOLBOX);
				this.centerComponent = this.editor;
				break;
			case PROXY_MONITOR: 
				setToolBox(MainWindow.PROXYMON_TOOLBOX);
				this.centerComponent = this.proxyUnitPanel;
				break;
			case STATISTICS: 
				this.centerComponent = this.statsPanel;
				break;
			case TESTING_UNIT: 
				setToolBox(MainWindow.TESTUNIT_TOOLBOX);
				this.centerComponent = this.testUnitPanel;
				break;	
			default:
				ConsoleLog.Print("[MainWindow] - something is wrong in switch statement");
				break;
		}
		
		this.centerPane.setRightComponent(this.centerComponent);
		getCenterPane().revalidate();
		getCenterPane().repaint();
		this.pack();
	}

	/**
	 * 
	 */
	public void openTestList(){
		
		this.testUnitPanel.openTestList(MainWindow.getSuitePath()+File.separator+"testlist.xml");
		setContent(MainWindow.TESTING_UNIT);
	}
	
	/**
	 * 
	 */
	public void openTestCaseEditor(){
		
		int panelType = getPanelType();
		
		if (panelType == MainWindow.CASE_EDITOR_SETTINGS){
			this.editor.setTab(TestCaseEditor.SETTINGS_TAB);
		}else if(panelType == MainWindow.CASE_EDITOR_HTTP){
			this.editor.setTab(TestCaseEditor.HTTP_TAB);
		}else if(panelType == MainWindow.CASE_EDITOR_FAULT){
			this.editor.setTab(TestCaseEditor.FAULT_TAB);
		}
		setContent(MainWindow.TESTCASE_EDITOR);
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
	
	/**
	 * 
	 */
	private void initMainWindow(UnitController testUnitController){
		
		this.setTitle(this.APP_NAME);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		 
		// Determine the new location of the window
		int x = (dim.width-(this.WIDTH))/2;
		int y = (dim.height-(this.HEIGTH))/2;
		 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(x, y, this.WIDTH, this.HEIGTH);
		this.setMinimumSize(new Dimension(this.WIDTH,this.HEIGTH));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.gray);
		initToolBox();
		initMenuBar();
		initContentPane(testUnitController);
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
	private void initContentPane(UnitController testUnitController){
		
		this.editor = new TestCaseEditor();
		this.proxyUnitPanel = new ProxyMonitor(testUnitController);
		this.statsPanel = new Statistics();
		this.testUnitPanel = new TestMonitor(testUnitController);
		this.centerComponent = new JPanel();
		this.panelType = 0;
	}
	
	/**
	 * 
	 */
	private void initCenterPane(){
		
		File root = new File(MainWindow.getDataRoot());
		
		if(!root.exists()){
			boolean wasDirectoryMade = root.mkdirs();
		    if(wasDirectoryMade)
		    	ConsoleLog.Print("[MainWindow] Direcoty Created");
		}
		
		this.navigator = new Navigator(root);
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
	
	
	/**
	 * 
	 * @param menu
	 * @param label
	 * @param listener
	 */
	private void addToolBarItem(JToolBar menu,String label,ActionListener listener){
		
		JButton button = new JButton(label);
		button.addActionListener(listener);
		menu.add(button);
	}
	
	
	/**
	 * 
	 */
	private void initToolBox(){
		
		this.toolBox = null;
		testCaseToolBox = new JToolBar();
		testUnitToolBox = new JToolBar();
		proxyUnitToolBox = new JToolBar();
				
		testCaseToolBox.setFloatable(false);
		testUnitToolBox.setFloatable(false);
		proxyUnitToolBox.setFloatable(false);
		
		addToolBarItem(testCaseToolBox,"Save TestCase",new SaveTestCaseListener());
		addToolBarItem(testCaseToolBox,"Generate content",new GenerateRequestListener());
		
		addToolBarItem(testUnitToolBox,"Insert Test Case",new InsertTestCaseListener());
		addToolBarItem(testUnitToolBox,"Remove Test Case",new RemoveTestCaseListener());
		addToolBarItem(testUnitToolBox,"Run Tests",new RunTestUnitListener());
		addToolBarItem(testUnitToolBox,"Run All Units",new RunAllTestUnitsListener());
		addToolBarItem(testUnitToolBox,"Save Test List",new SaveTestListListener());
		addToolBarItem(testUnitToolBox,"Add Remote Unit",new AddTestUnitListener());
		addToolBarItem(testUnitToolBox,"Remove Test Unit",new RemoveTestUnitListener());
		
		addToolBarItem(proxyUnitToolBox,"Run Unit",new RunProxyListener());
		addToolBarItem(proxyUnitToolBox,"Stop Unit",new StopProxyListener());
		addToolBarItem(proxyUnitToolBox,"Add Remote Unit",new AddProxyUnitListener());
		addToolBarItem(proxyUnitToolBox,"Remove Remote Unit",new RemoveProxyUnitListener());
	}
	
	
	
	/********************************************* BUTTON LISTENERS **********************************************************/
	
	
	private class SaveTestCaseListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			ConsoleLog.Print("[MainWindow] Save testcase clicked");
			editor.saveTestCase();
		}
	}
	
	
	private class AddTestUnitListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("[MainWindow] Add Unit clicked");
			int port = 0;
			String host= "";
			AddRemoteUnitDialog window = new AddRemoteUnitDialog();
			window.setVisible(true);
			
			if(window.isOkButtonClicked()){
				port = window.getPort();
				host = window.getHost();
				testUnitPanel.addTestUnit(host,port);
			}
		}
	}
	
	private class RemoveTestUnitListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("[MainWindow] Remove Unit clicked");
			testUnitPanel.removeUnit();
		}
	}
	
	
	private class RunTestUnitListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			//final String path = testListPath;
			testUnitPanel.runUnit(MainWindow.getSuitePath()+File.separator+"testlist.xml");
			ConsoleLog.Print("[MainWindow] Run Unit clicked");
		}
	}
	
	
	private class RunAllTestUnitsListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("[MainWindow] Run All Units clicked");
			testUnitPanel.runAllUnits(MainWindow.getSuitePath()+File.separator+"testlist.xml");
		}
	}
	
	
	private class SaveTestListListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			testUnitPanel.saveTestList(MainWindow.getSuitePath()+File.separator+"testlist.xml");
			ConsoleLog.Print("[MainWindow] Save Test List clicked");
		}
	}
	
	
	private class InsertTestCaseListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			insertTestCase();
			ConsoleLog.Print("[MainWindow] Insert Test Case clicked");
		}
	}
	
	
	private class RemoveTestCaseListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			testUnitPanel.removeTestCase();
			ConsoleLog.Print("[MainWindow] Remove Test Case clicked");
		}
	}
	
	
	private class GenerateRequestListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			GenerateFromWSDLDialog window = new GenerateFromWSDLDialog(); 
			editor.setHttpBody(window.getGeneratedContent());
			window.setVisible(true);
			if(window.isAddButtonClicked()){
				editor.setHttpBody(window.getGeneratedContent());
			}
		}
	}
	
	
	private class RemoveProxyUnitListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			proxyUnitPanel.removeUnit();
			ConsoleLog.Print("[MainWindow] Remove Proxy Unit clicked");
		}
	}
	
	
	private class AddProxyUnitListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("[MainWindow] Add Proxy Unit clicked");
			
			int port = 0;
			String host= "";
			AddRemoteUnitDialog window = new AddRemoteUnitDialog();
			window.setVisible(true);
			
			if(window.isOkButtonClicked()){
				port = window.getPort();
				host = window.getHost();
				proxyUnitPanel.addUnit(host,port);
			}
			
			
		}
	}
	
	
	private class RunProxyListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			proxyUnitPanel.runUnit(MainWindow.getCasePath());
			ConsoleLog.Print("[MainWindow] Run Proxy Unit clicked");
		}
	}
	
	
	private class StopProxyListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			proxyUnitPanel.stopUnit();
			ConsoleLog.Print("[MainWindow] Stop Proxy Unit clicked");
		}
	}
	
	
	
}
