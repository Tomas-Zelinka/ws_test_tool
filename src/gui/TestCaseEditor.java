package gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import logging.ConsoleLog;
import central.UnitController;
import data.FaultInjectionData;
import data.HttpMessageData;
import data.TestCaseSettingsData;


public class TestCaseEditor extends JPanel {

	
	private static final long serialVersionUID = -2860238189144324557L;

	/**
	 * 
	 */
	public static final int FAULT_TAB = 2;
	public static final int HTTP_TAB = 1;
	public static final int SETTINGS_TAB = 0;
	private TestCaseSettings settingsEditor;
	private JTabbedPane mainTabbedPane;
	private FaultInjectionEditor faultInjectionEditor;
	private HttpRequestEditor httpEditor;
	private String testCasePath;
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
	 * 
	 * @return
	 */
	public int getTab(){
		
		return mainTabbedPane.getSelectedIndex();
	}
	
	public void setTestCasePath(){
		
		this.testCasePath =  MainWindow.getCasePath();
		ConsoleLog.Print("[TestCaseEditor] Case path set to: "+this.testCasePath);
	}
	
	public String getTestCasePath(){
		
		return this.testCasePath;
	}
	
	public void setHttpBody(String body){
		
		httpEditor.setEditorContent(body);
	}
	
		
	public void saveSettings(){
		
		if(faultInjectionEditor.isDataLoaded()){
			FaultInjectionData faultData = faultInjectionEditor.getData();
			if(faultData != null)
				controller.writeFaultData(getTestCasePath(),faultData );
		}else{
			ConsoleLog.Print("[TestCaseEditor] FaultInjection not loaded ");
		}
	}
	
	public void saveRequest(){
		
		if(httpEditor.isDataLoaded()){
			HttpMessageData requestData = httpEditor.getData();
			if(requestData != null)
				controller.writeRequestData(getTestCasePath(), requestData);
		}else{
			ConsoleLog.Print("[TestCaseEditor] Http not loaded ");
		}
	}
	
	public void saveFaultInjection(){

		if(settingsEditor.isDataLoaded()){
			TestCaseSettingsData settingsData = settingsEditor.getData();
			if(settingsData != null)
				controller.writeSettingsData(getTestCasePath(), settingsData);
		}else{
			ConsoleLog.Print("[TestCaseEditor] Settings not loaded ");
		}
		
	}
	
	
	
	
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
