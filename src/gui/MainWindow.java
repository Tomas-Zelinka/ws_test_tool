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
import modalWindows.AddWsdlToTestSuite;
import modalWindows.GenerateFromWSDLDialog;

import org.bounce.text.LineNumberMargin;
import org.bounce.text.xml.XMLEditorKit;

import central.UnitController;
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
	private Statistics statsPanel;
	//private RemoteControl remoteControlPanel;
	private ProxyMonitor proxyPanel;
	private  TestingMonitor testUnitPanel;
	private ProjectNavigator navigator;
	
	private Component toolBox;
	private JToolBar testCaseToolBox; 
	private JToolBar testUnitToolBox;
	private JToolBar proxyToolBox;
	
	
	private JButton saveTestCase;
	private JButton addWSDL;
	private JButton generateRequest;
	private JButton addUnit;
	private JButton removeUnit;
	private JButton runUnit;
	private JButton runAllUnits;
	private JButton saveTestList;
	private JButton insertTestCase;
	private JButton removeTestCase;
	private JButton exportConfiguration;
	
	public static final int TESTCASE_EDITOR = 0;
	public static final int PROXY_MONITOR = 1;
	public static final int REMOTE_CONTROL = 2;
	public static final int STATISTICS = 3;
	public static final int TESTING_UNIT = 4;
	
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
			this.toolBox = proxyToolBox;
		}
		
		getContentPane().add(this.toolBox,BorderLayout.NORTH);
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
	
	public int getPanelType(){
		return this.navigator.getPanelType();
	}
	
	public void refreshTree(){
		this.navigator.refreshTree();
	}
	
	public void insertTestCase(){
		testUnitPanel.insertTestCase(MainWindow.getDataRoot()+File.separator+MainWindow.getSuitePath()+File.separator+MainWindow.getCasePath()+File.separator+"settings.xml");
		
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
		
		this.centerPane.remove(this.centerComponent);//this.panelPane = this.centerComponent);
		
		switch(component){
			case TESTCASE_EDITOR:
				setToolBox(MainWindow.TESTCASE_TOOLBOX);
				this.centerComponent = this.editor;
				break;
			case PROXY_MONITOR: 
				setToolBox(MainWindow.PROXYMON_TOOLBOX);
				this.centerComponent = this.proxyPanel;
				break;
//			case REMOTE_CONTROL: 
//				this.centerComponent = this.remoteControlPanel;
//				break;
			case STATISTICS: 
				this.centerComponent = this.statsPanel;
				break;
			case TESTING_UNIT: 
				setToolBox(MainWindow.TESTUNIT_TOOLBOX);
				this.centerComponent = this.testUnitPanel;
				break;	
			default:
				ConsoleLog.Print("MainWindow.setContent() - something is wrong in switch statement");
				break;
				
		}
		
		
		//this.panelPane =  this.centerComponent;
		this.centerPane.setRightComponent(this.centerComponent);
		
		getCenterPane().revalidate();
		getCenterPane().repaint();
		this.pack();
	}

	public void openTestList(){
		this.testUnitPanel.openTestList(MainWindow.getDataRoot()+File.separator+MainWindow.getSuitePath()+File.separator+"testlist.xml");
		
		setContent(MainWindow.TESTING_UNIT);
	}
	
	public void openTestCaseEditor(){
		
		int panelType = this.navigator.getPanelType();
		
		if (panelType == ProjectNavigator.CASE_EDITOR_SETTINGS){
			this.editor.setTab(TestCaseEditor.SETTINGS_TAB);
		}else if(panelType == ProjectNavigator.CASE_EDITOR_HTTP){
			this.editor.setTab(TestCaseEditor.HTTP_TAB);
		}else if(panelType == ProjectNavigator.CASE_EDITOR_FAULT){
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
	private void initMainWindow(UnitController testUnitController)
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
		this.proxyPanel = new ProxyMonitor(testUnitController);
		//this.remoteControlPanel = new RemoteControl();
		this.statsPanel = new Statistics();
		this.testUnitPanel = new TestingMonitor(testUnitController);
		this.centerComponent = new JPanel();
		//this.panelPane = (JPanel)this.centerComponent;
	}
	
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
	
	/**
	 * 
	 */
	private void initToolBox(){
		this.toolBox = null;
		testCaseToolBox = new JToolBar();
		testUnitToolBox = new JToolBar();
		proxyToolBox = new JToolBar();
				
		testCaseToolBox.setFloatable(false);
		testUnitToolBox.setFloatable(false);
		proxyToolBox.setFloatable(false);
		
		saveTestCase = new JButton("Save TestCase");
		saveTestCase.addActionListener(new SaveTestCaseListener());
		
		addUnit = new JButton("Add Remote Unit");
		addUnit.addActionListener(new AddUnitListener());
		
		removeUnit = new JButton("Remove Remote Unit");
		removeUnit.addActionListener(new RemoveUnitListener());
		
		runUnit = new JButton("Run Tests");
		runUnit.addActionListener(new RunUnitListener());
		
		runAllUnits = new JButton("Run All Units");
		runAllUnits.addActionListener(new RunAllUnitsListener());
		
		saveTestList =  new JButton("Save Test List");
		saveTestList.addActionListener(new SaveTestListListener());
		
		insertTestCase =  new JButton("Insert Test Case");
		insertTestCase.addActionListener(new InsertTestCaseListener());
		
		removeTestCase =  new JButton("Remove Test Case");
		removeTestCase.addActionListener(new RemoveTestCaseListener());
		
		exportConfiguration =  new JButton("Export Unit Configuration");
		exportConfiguration.addActionListener(new ExportConfigurationListener());
		
		addWSDL = new JButton("Add WSDL");
		addWSDL.addActionListener( new AddWSDLListener());
		
		generateRequest = new JButton("Generate content");
		generateRequest.addActionListener(new GenerateRequestListner());
		
		
		testCaseToolBox.add(saveTestCase);
		testCaseToolBox.add(addWSDL);
		testCaseToolBox.add(generateRequest);
		
		testUnitToolBox.add(runUnit);
		testUnitToolBox.add(runAllUnits);
		testUnitToolBox.add(insertTestCase);
		testUnitToolBox.add(removeTestCase);
		testUnitToolBox.add(saveTestList);
		testUnitToolBox.add(addUnit);
		testUnitToolBox.add(removeUnit);
		testUnitToolBox.add(exportConfiguration);
		
		
	}
	private class SaveTestCaseListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ConsoleLog.Print("Save testcase clicked");
			editor.saveTestCase();
		}
	}
	
	private class AddUnitListener implements ActionListener{
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("Add Unit clicked");
			testUnitPanel.addRemoteUnit();
		}
	}
	private class RemoveUnitListener implements ActionListener{
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("Remove Unit clicked");
			testUnitPanel.removeUnit();
		}
	}
	
	private class RunUnitListener implements ActionListener{
		public void actionPerformed (ActionEvent e){
			
			//final String path = testListPath;
			testUnitPanel.runUnit(MainWindow.getDataRoot()+File.separator+MainWindow.getSuitePath()+File.separator+"testlist.xml");
			
			
			
			//testUnitPanel.runUnit(MainWindow.getDataRoot()+File.separator+MainWindow.getSuitePath()+File.separator+"testlist.xml");
			ConsoleLog.Print("Run Unit clicked");
			
		}
	}
	
	private class RunAllUnitsListener implements ActionListener{
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("Run All Units clicked");
			
		}
	}
	
	private class SaveTestListListener implements ActionListener{
		public void actionPerformed (ActionEvent e){
			testUnitPanel.saveTestList(MainWindow.getDataRoot()+File.separator+MainWindow.getSuitePath()+File.separator+"testlist.xml");
			
			ConsoleLog.Print("Save Test Case clicked");
			
		}
	}
	
	private class InsertTestCaseListener implements ActionListener{
		public void actionPerformed (ActionEvent e){
			insertTestCase();
			ConsoleLog.Print("Insert Test Case clicked");
			
		}
	}
	
	
	private class RemoveTestCaseListener implements ActionListener{
		public void actionPerformed (ActionEvent e){
			testUnitPanel.removeTestCase();
			ConsoleLog.Print("Remove Test Case clicked");
			
		}
	}
	
	private class ExportConfigurationListener implements ActionListener{
		public void actionPerformed (ActionEvent e){
			ConsoleLog.Print("Export Configuration clicked");
			
		}
	}
	
	private class AddWSDLListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			 AddWsdlToTestSuite window = new AddWsdlToTestSuite();
			 window.setVisible(true);
			 
			
		}
	}
	
	private class GenerateRequestListner implements ActionListener{
		public void actionPerformed(ActionEvent e){
			GenerateFromWSDLDialog window = new GenerateFromWSDLDialog();  
			window.setVisible(true);
		}
	}
	
	
}
