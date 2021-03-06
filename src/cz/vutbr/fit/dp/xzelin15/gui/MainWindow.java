package cz.vutbr.fit.dp.xzelin15.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.text.PlainDocument;

import org.bounce.text.LineNumberMargin;
import org.bounce.text.xml.XMLEditorKit;

import cz.vutbr.fit.dp.xzelin15.central.UnitController;
import cz.vutbr.fit.dp.xzelin15.data.DataProvider;
import cz.vutbr.fit.dp.xzelin15.logging.ConsoleLog;
import cz.vutbr.fit.dp.xzelin15.modalWindows.AddRemoteUnitDialog;
import cz.vutbr.fit.dp.xzelin15.modalWindows.GenerateFromWSDLDialog;



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
	 * Data root path
	 */
	private static String dataRoot = "." + File.separator + "data";
	
	/**
	 * Test suit path
	 */
	private static String suitePath = "";  
	
	/**
	 * Test case path
	 */
	private static String casePath = "";  
	
	/**
	 * Path for deleting nodes in navigation panel
	 */
	private static String endpointPath = "";
	
	/**
	 * Splitpane containing main components of the GUI
	 */
	private JSplitPane centerPane;
	
	/**
	 * Center component for the central panels
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
	 * Set the toolbox to the right panel
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
	 * Get the test case path
	 */
	public static String getCasePath(){
		
		return MainWindow.casePath;
	}
	
	/**
	 * Set the test case path
	 * @param path
	 */
	public static void setCasePath(String path){
		
		if(!path.isEmpty())
			MainWindow.casePath = getSuitePath()+File.separator+path;
		else{
			MainWindow.casePath = "";
		}
		ConsoleLog.Print("[MainWindow] Case path set to: " + MainWindow.casePath);
	}
		
	/**
	 * Get the suite path
	 */
	public static String getSuitePath(){
		
		return MainWindow.suitePath;
	}
	
	/**
	 * Set the suite path
	 * @param path
	 */
	public static void setSuitePath(String path){
		
		MainWindow.suitePath = getDataRoot()+File.separator+path;
		ConsoleLog.Print("[MainWindow] Suite path set to: " + MainWindow.suitePath);
	}
		
	/**
	 * Get the data root path
	 */
	public static String getDataRoot(){
		
		return MainWindow.dataRoot;
	}
	
	/**
	 * Set the data root path
	 * @param path
	 */
	public static void setDataRoot(String root){
		
		MainWindow.dataRoot = root;
	}
	
	/**
	 * Get path of last selected node - for deleting
	 * @return String - node to be deleted
	 */
	public static String getEndpointPath() {
		return MainWindow.endpointPath;
	}

	/**
	 * Set path of last selected node - for deleting
	 * 
	 */
	public static void setEndpointPath(String endpointPath) {
		MainWindow.endpointPath = endpointPath;
	}
	
	/**
	 * 
	 * Get type of central panel to be shown
	 * 
	 * @return
	 */
	public int getPanelType(){
		
		return this.panelType;
	}
	
	/**
	 * 
	 * Set type of central panel to be shown
	 * 
	 * @param type
	 */
	public void setPanelType(int type){
		
		this.panelType = type;
	}
	
	
	/**
	 * Method for menus to refresh navigation panel
	 */
	public void refreshTree(){
		
		Navigator.refreshTree();
	}
	
	/**
	 * Clear console
	 */
	public void clearConsole(){
		this.console.clear();
	}
	
	/**
	 * Method for insertion of testcase inside the test list from navigation popup menu
	 */
	public void insertTestCase(){
		testUnitPanel.saveTestList(MainWindow.getSuitePath());
		testUnitPanel.openTestList(MainWindow.getSuitePath());
		testUnitPanel.insertTestCase(MainWindow.getCasePath());
		setContent(TESTING_UNIT);
	}
	
	/**
	 * Method for running test case in proxy from navigation popup menu
	 */
	public void runInProxy(){
		proxyUnitPanel.runUnit(MainWindow.getCasePath());
		setContent(PROXY_MONITOR);
	}
	
	/**
	 * 
	 * Switching between central panels
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
	 * Open the test suite and insert the data of a test list
	 * 
	 */
	public void openTestList(){
		
		this.testUnitPanel.openTestList(MainWindow.getSuitePath());
		setContent(MainWindow.TESTING_UNIT);
	}
	
	/**
	 * Close edited test case
	 */
	public void closeTestCase(){
		
		this.editor.closeTestCase(); 
	}
	
	/**
	 * Close edited test suite
	 */
	public void closeTestList(){
		this.testUnitPanel.closeTestList(); 
	}
	
	/**
	 * Open test case editor and load the test case data
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
		
		this.editor.loadSettings();
		this.editor.loadRequest();
		this.editor.loadFaultInjection();
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
	 * Initialization of main window
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
		setContent(TESTCASE_EDITOR);
		
	}
		
	/**
	 * 
	 * Menu bar initialization
	 * 
	 */
	private void initMenuBar(){
		
		this.setJMenuBar(new Menu());
	}
	
	/**
	 * Initialization of central panels
	 */
	private void initContentPane(UnitController testUnitController){
		
		this.editor = new TestCaseEditor(testUnitController);
		this.proxyUnitPanel = new ProxyMonitor(testUnitController);
		this.testUnitPanel = new TestMonitor(testUnitController);
		this.centerComponent = this.editor;
		this.panelType = 0;
		
		
		
		
	}
	
	/**
	 * Initialization of central pane
	 */
	private void initCenterPane(){
		
		//detection whether the root directory exists
		//if not, it is created
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
	 * Get central splitpane object
	 */
	private JSplitPane getCenterPane(){
		
		return this.centerPane;
	}
	
	
	/**
	 * Initialization of bottom splitpane - console part
	 */
	private void initBottomPane(){
		
		this.console = new Console();
		this.bottomPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,getCenterPane(),this.console);
		this.bottomPane.setDividerSize(SPLIT_RESIZERS_WIDTH);
		this.bottomPane.setResizeWeight(0.90);
	}
	
	
	/**
	 * 
	 * Menu item generating method
	 * 
	 * @param menu
	 * @param label
	 * @param listener
	 */
	private void addToolBarItem(JToolBar menu,String label,ActionListener listener,String icon){
		
		JButton button = new JButton();
		button.addActionListener(listener);
		button.setToolTipText(label);
		button.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		button.setFocusPainted(false);
		button.setIcon(new javax.swing.ImageIcon(getClass().getResource(DataProvider.getResourcePath()+icon))); // NOI18N
	       
		menu.add(button);
	}
	
	
	
	/**
	 * 
	 * ToolBoxes initialization
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
		
		addToolBarItem(testCaseToolBox,"Save TestCase",new SaveTestCaseListener(),"save.png");
		addToolBarItem(testCaseToolBox,"Generate content",new GenerateRequestListener(),"synchronize.png");
		
		addToolBarItem(testUnitToolBox,"Save Test List",new SaveTestListListener(),"save.png");
		addToolBarItem(testUnitToolBox,"Insert Test Case",new InsertTestCaseListener(),"add.png");
		addToolBarItem(testUnitToolBox,"Remove Test Case",new RemoveTestCaseListener(),"delete.png");
		addToolBarItem(testUnitToolBox,"Run Tests",new RunTestUnitListener(),"play.png");
		addToolBarItem(testUnitToolBox,"Stop Tests",new StopUnitListener(),"stop.png");
		addToolBarItem(testUnitToolBox,"Stop All Unit",new StopAllUnitsListener(),"multistop.png");
		addToolBarItem(testUnitToolBox,"Run All Units",new RunAllTestUnitsListener(),"multiplay.png");
		addToolBarItem(testUnitToolBox,"Add Remote Unit",new AddTestUnitListener(),"network_idle.png");
		addToolBarItem(testUnitToolBox,"Remove Test Unit",new RemoveTestUnitListener(),"network_offline.png");
		addToolBarItem(testUnitToolBox,"Export Configuration",new ExportTestUnitListener(),"upload.png");
		addToolBarItem(testUnitToolBox,"Import Configuration",new ImportTestUnitListener(),"download.png");
		
		addToolBarItem(proxyUnitToolBox,"Run Unit",new RunProxyListener(),"play.png");
		addToolBarItem(proxyUnitToolBox,"Stop Unit",new StopProxyListener(),"stop.png");
		addToolBarItem(proxyUnitToolBox,"Run All Units",new RunAllProxyListener(),"multiplay.png");
		addToolBarItem(proxyUnitToolBox,"Stop All Units",new StopAllProxyListener(),"multistop.png");
		addToolBarItem(proxyUnitToolBox,"Add Remote Unit",new AddProxyUnitListener(),"network_idle.png");
		addToolBarItem(proxyUnitToolBox,"Remove Remote Unit",new RemoveProxyUnitListener(),"network_offline.png");
		addToolBarItem(proxyUnitToolBox,"Export Configuration",new ExportProxyUnitListener(),"upload.png");
		addToolBarItem(proxyUnitToolBox,"Import Configuration",new ImportProxyUnitListener(),"download.png");
		
	}
	
	
	
	/********************************************* BUTTON LISTENERS **********************************************************/
	
	
	private class SaveTestCaseListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			ConsoleLog.Print("[MainWindow] Save testcase clicked");
			editor.saveRequest();
			editor.saveSettings();
			editor.saveFaultInjection(); 
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
				testUnitPanel.addUnit(host,port);
			}
		}
	}
	
	private class RemoveTestUnitListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("[MainWindow] Remove Unit clicked");
			testUnitPanel.removeUnit(testUnitPanel.getPanelIndex());
		}
	}
	
	
	private class RunTestUnitListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			//final String path = testListPath;
			testUnitPanel.runUnit(MainWindow.getSuitePath());
			ConsoleLog.Print("[MainWindow] Run Unit clicked");
		}
	}
	private class StopUnitListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			testUnitPanel.stopUnit();
			ConsoleLog.Print("[MainWindow] Stop Proxy Unit clicked");
		}
	}
	
	private class RunAllTestUnitsListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("[MainWindow] Run All Units clicked");
			testUnitPanel.runAllUnits(MainWindow.getSuitePath());
		}
	}
	
	
	private class StopAllUnitsListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			testUnitPanel.stopAllUnits();
			ConsoleLog.Print("[MainWindow] Stop Proxy Unit clicked");
		}
	}
	
	
	private class ExportTestUnitListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("[MainWindow] Export Units clicked");
			testUnitPanel.exportConfiguration();
		}
	}	
	
	private class ImportTestUnitListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("[MainWindow] Import Units clicked");
			testUnitPanel.importConfiguration();
		}
	}	
	
	private class SaveTestListListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			testUnitPanel.saveTestList(MainWindow.getSuitePath());
			ConsoleLog.Print("[MainWindow] Save Test List clicked");
		}
	}
	
	
	private class InsertTestCaseListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			testUnitPanel.insertTestCase(MainWindow.getCasePath());
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
	
	private class RunAllProxyListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			proxyUnitPanel.runAllUnits(MainWindow.getCasePath());
			ConsoleLog.Print("[MainWindow] Run Proxy Unit clicked");
		}
	}
	
	private class ExportProxyUnitListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("[MainWindow] Export Units clicked");
			proxyUnitPanel.exportConfiguration();
		}
	}	
	
	private class ImportProxyUnitListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("[MainWindow] Import Units clicked");
			proxyUnitPanel.importConfiguration();
		}
	}	
	
	
	private class StopProxyListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			proxyUnitPanel.stopUnit();
			ConsoleLog.Print("[MainWindow] Stop Proxy Unit clicked");
		}
	}
	
	private class StopAllProxyListener implements ActionListener{
		
		public void actionPerformed (ActionEvent e){
			proxyUnitPanel.stopAllUnits();
			ConsoleLog.Print("[MainWindow] Stop Proxy Unit clicked");
		}
	}
	
}
