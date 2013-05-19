package cz.vutbr.fit.dp.xzelin15.gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vutbr.fit.dp.xzelin15.central.UnitController;
import cz.vutbr.fit.dp.xzelin15.data.FaultInjectionData;
import cz.vutbr.fit.dp.xzelin15.data.HttpMessageData;
import cz.vutbr.fit.dp.xzelin15.data.TestCaseSettingsData;
import cz.vutbr.fit.dp.xzelin15.logging.ConsoleLog;


/**
 * 
 * The head panel holds the three panel of editing
 * 
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class TestCaseEditor extends JPanel {

	
	private static final long serialVersionUID = -2860238189144324557L;

	/**
	 * Fault injection Tab
	 */
	public static final int FAULT_TAB = 2;
	
	/**
	 * HTTP request editor tab
	 */
	public static final int HTTP_TAB = 1;
	
	/**
	 * Request settings editor tab
	 */
	public static final int SETTINGS_TAB = 0;
	
	/**
	 * Request settings editor panel
	 */
	private TestCaseSettings settingsEditor;
	
	/**
	 * Holds all editor panels
	 */
	private JTabbedPane mainTabbedPane;
	
	/**
	 * Fault injection panel
	 */
	private FaultInjectionEditor faultInjectionEditor;
	
	/**
	 * HTTP request editor panel
	 */
	private HttpRequestEditor httpEditor;
	
	/**
	 * Edited test case path
	 */
	private String testCasePath;
	
	/**
	 * Unit controller
	 */
	private UnitController controller;
	
	/**
	 * 			
	 */
	public TestCaseEditor(UnitController controller){
		
		initComponents();
		mainTabbedPane.addTab("Test Case Settings",settingsEditor);
	    mainTabbedPane.addTab("HTTP Request", httpEditor);
	    mainTabbedPane.addTab("Fault Injection",faultInjectionEditor);
	    
	    this.controller = controller;
		setLayout(new BorderLayout());
		add(mainTabbedPane,BorderLayout.CENTER);
		closeTestCase();
	}
	
	/**
	 * 
	 * Set shown tab
	 * 
	 * @param tab
	 */
	public void setTab(int tab){
		
		ConsoleLog.Print("[TestCaseEditor] Opened tab index: "+tab);
		mainTabbedPane.setSelectedIndex(tab);
		mainTabbedPane.revalidate();
		mainTabbedPane.repaint();
		setTestCasePath();
		
	}
	
	
	/**
	 *  Get shown tab index
	 * @return int - index of seleted panel
	 */
	public int getTab(){
		
		return mainTabbedPane.getSelectedIndex();
	}
	
	/**
	 * Get test case path selected in navigator
	 */
	public void setTestCasePath(){
		
		this.testCasePath =  MainWindow.getCasePath();
		ConsoleLog.Print("[TestCaseEditor] Case path set to: "+this.testCasePath);
	}
	
	/**
	 * Get edited case path
	 * @return
	 */
	public String getTestCasePath(){
		
		return this.testCasePath;
	}
	
	/**
	 * 
	 * @param body
	 */
	public void setHttpBody(String body){
		
		httpEditor.setEditorContent(body);
	}
	
	/**
	 * 	
	 */
	public void saveSettings(){
		
		if(faultInjectionEditor.isDataLoaded()){
			FaultInjectionData faultData = faultInjectionEditor.getData();
			if(faultData != null)
				controller.writeFaultData(getTestCasePath(),faultData );
		}else{
			ConsoleLog.Print("[TestCaseEditor] FaultInjection not loaded ");
		}
	}
	
	/**
	 * 
	 */
	public void saveRequest(){
		
		if(httpEditor.isDataLoaded()){
			HttpMessageData requestData = httpEditor.getData();
			if(requestData != null)
				controller.writeRequestData(getTestCasePath(), requestData);
		}else{
			ConsoleLog.Print("[TestCaseEditor] Http not loaded ");
		}
	}
	
	/**
	 * 
	 */
	public void saveFaultInjection(){

		if(settingsEditor.isDataLoaded()){
			TestCaseSettingsData settingsData = settingsEditor.getData();
			if(settingsData != null)
				controller.writeSettingsData(getTestCasePath(), settingsData);
		}else{
			ConsoleLog.Print("[TestCaseEditor] Settings not loaded ");
		}
		
	}
	
	/**
	 * 
	 */
	public void loadSettings(){
		
		TestCaseSettingsData loadedSettings = null;
		String settingsFilePath = getTestCasePath() + TestCaseSettingsData.filename;
		File casePath = new File(getTestCasePath()); 
		File settingsFile = new File(settingsFilePath);
		mainTabbedPane.setTitleAt(0, "Test Case Settings - " + casePath.getName());
		
		if(!settingsFile.exists()){
			ConsoleLog.Print("[TestCaseEditor] Settings file not found !!!");
			settingsEditor.setEnablePanel(false);
			settingsEditor.setDataLoaded(false);
			settingsEditor.clearData();
		}else{
			loadedSettings = controller.readSettingsData(getTestCasePath());
			settingsEditor.setEnablePanel(true);
			settingsEditor.setData(loadedSettings);
			
		}
	}
	
	/**
	 * 
	 */
	public void loadRequest(){
		
		HttpMessageData loadedHttpData = null;
		String httpFilePath = getTestCasePath() +  HttpMessageData.inputFilename;
		File casePath = new File(getTestCasePath()); 
		File httpDataFile = new File(httpFilePath);
		mainTabbedPane.setTitleAt(1, "Http Request - " + casePath.getName());
		
		if(!httpDataFile.exists()){
			ConsoleLog.Print("[TestCaseEditor] Http Data file not found !!!");
			httpEditor.setEnablePanel(false);
			httpEditor.setDataLoaded(false);
			httpEditor.clearData();
		}else{
			loadedHttpData = controller.readRequestData(getTestCasePath());
			httpEditor.setData(loadedHttpData);
			httpEditor.setEnablePanel(true);
		}
	}
	
	/**
	 * 
	 */
	public void loadFaultInjection(){
	
		FaultInjectionData loadedFault = null;
		String faultFilePath = getTestCasePath() + FaultInjectionData.filename;
		File casePath = new File(getTestCasePath()); 
		File faultDataFile = new File(faultFilePath);
		mainTabbedPane.setTitleAt(2, "Fault Injection - " + casePath.getName());
		
		if(!faultDataFile.exists()){
			ConsoleLog.Print("[TestCaseEditor] Fault Injection file not found !!!");
			faultInjectionEditor.setEnablePanel(false);
			faultInjectionEditor.setDataLoaded(false);
			faultInjectionEditor.clearData();
		}else{
			loadedFault = controller.readFaultData(getTestCasePath());
			faultInjectionEditor.setData(loadedFault);
			faultInjectionEditor.setEnablePanel(true);
		}
	}
	
	/**
	 * 
	 */
	public void closeTestCase(){
		settingsEditor.setEnablePanel(false);
		settingsEditor.clearData();
		mainTabbedPane.setTitleAt(0, "Test Case Settings");
		
		httpEditor.setEnablePanel(false);
		httpEditor.clearData();
		mainTabbedPane.setTitleAt(1, "Http Request" );
		
		faultInjectionEditor.setEnablePanel(false);
		faultInjectionEditor.clearData();
		mainTabbedPane.setTitleAt(2, "Fault Injection");
	}
	
	
	/**
	 * 
	 */
	private void initComponents(){
		settingsEditor = new TestCaseSettings();
		httpEditor = new HttpRequestEditor();
		faultInjectionEditor = new FaultInjectionEditor();
		mainTabbedPane = new JTabbedPane();
	}
}
