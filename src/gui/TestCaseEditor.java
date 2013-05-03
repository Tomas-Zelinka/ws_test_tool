package gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import logging.ConsoleLog;

import data.DataProvider;
import data.HttpMessageData;
import data.FaultInjectionData;
import data.TestCaseSettingsData;


public class TestCaseEditor extends JPanel {

	
	public static final String settingsFileName = "settings.xml";
	public static final String httpRequestFileName = "Http" + File.separator +"input" + File.separator + "httpRequest.xml";
	public static final String faultInjectionFileName = "FaultInjection" + File.separator + "input" + File.separator + "faultInjection.xml";
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
	private DataProvider dataProvider;
	private String testCasePath;
	
	/**
	 * 			
	 */
	public TestCaseEditor(){
		
		initComponents();
		
		mainTabbedPane.addTab("Test Case Settings",settingsEditor);
	    mainTabbedPane.addTab("HTTP Request", httpEditor);
	    mainTabbedPane.addTab("Fault Injection",faultInjectionEditor);
	    
		setLayout(new BorderLayout());
		add(mainTabbedPane,BorderLayout.CENTER);
		disablePanels();
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
		loadTestCase();
	}
	/**
	 * 
	 * @return
	 */
	public int getTab(){
		return mainTabbedPane.getSelectedIndex();
	}
	
	
	/**
	 * 
	 */
	private void initComponents(){
		dataProvider = new DataProvider();
		settingsEditor = new TestCaseSettings();
		httpEditor = new HttpRequestEditor();
		faultInjectionEditor = new FaultInjectionEditor();
		mainTabbedPane = new JTabbedPane();
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
	
	/**
	 * 
	 */
	public void saveTestCase(){
		
		ConsoleLog.Print("[TestCaseEditor] Started saving");
		
		if(faultInjectionEditor.isDataLoaded()){
			FaultInjectionData faultData = faultInjectionEditor.getData();
			String faultFilePath = getTestCasePath() + File.separator + TestCaseEditor.faultInjectionFileName;
			if(faultData != null)
				dataProvider.writeObject(faultFilePath,faultData );
		}else{
			ConsoleLog.Print("[TestCaseEditor] FaultInjection not loaded ");
		}
		
		if(httpEditor.isDataLoaded()){
			HttpMessageData requestData = httpEditor.getData();
			String httpFilePath = getTestCasePath() + File.separator + TestCaseEditor.httpRequestFileName;
			if(requestData != null)
				dataProvider.writeObject(httpFilePath, requestData);
		}else{
			ConsoleLog.Print("[TestCaseEditor] Http not loaded ");
		}
		
		if(settingsEditor.isDataLoaded()){
			TestCaseSettingsData settingsData = settingsEditor.getData();
			String settingsFilePath = getTestCasePath() + File.separator + TestCaseEditor.settingsFileName;
			if(settingsData != null)
				dataProvider.writeObject(settingsFilePath, settingsData);
		}else{
			ConsoleLog.Print("[TestCaseEditor] Settings not loaded ");
		}
		
		
		
		
		
		
				
		ConsoleLog.Print("[TestCaseEditor] Saved");
	}
	
	/**
	 * 
	 */
	public void loadTestCase(){
		
		ConsoleLog.Print("[TestCaseEditor] Started loading");
		TestCaseSettingsData loadedSettings = null;
		FaultInjectionData loadedFault = null;
		HttpMessageData loadedHttpData = null;
		
		
		String settingsFilePath = getTestCasePath() + File.separator + TestCaseEditor.settingsFileName;
		String httpFilePath = getTestCasePath() + File.separator + TestCaseEditor.httpRequestFileName;
		String faultFilePath = getTestCasePath() + File.separator + TestCaseEditor.faultInjectionFileName;
		ConsoleLog.Print("[TestCaseEditor] Settings file :" +settingsFilePath);
		File settingsFile = new File(settingsFilePath);
		File httpDataFile = new File(httpFilePath);
		File faultDataFile = new File(faultFilePath);
		
		
		if(!settingsFile.exists()){
			ConsoleLog.Print("[TestCaseEditor] Settings file not found !!!");
		}else{
			loadedSettings = (TestCaseSettingsData) dataProvider.readObject(settingsFilePath);
			settingsEditor.setData(loadedSettings);
			settingsEditor.setEnablePanel(true);
		}
		
		if(!httpDataFile.exists()){
			ConsoleLog.Print("[TestCaseEditor] Http Data file not found !!!");
		}else{
			loadedHttpData = (HttpMessageData) dataProvider.readObject(httpFilePath);
			httpEditor.setData(loadedHttpData);
			httpEditor.setEnablePanel(true);
		}
		
		
		
		if(!faultDataFile.exists()){
			ConsoleLog.Print("[TestCaseEditor] Fault Injection file not found !!!");
		}else{
			loadedFault = (FaultInjectionData) dataProvider.readObject(faultFilePath);
			faultInjectionEditor.setData(loadedFault);
			faultInjectionEditor.setEnablePanel(true);
		}
		
		ConsoleLog.Print("[TestCaseEditor] Case loaded");
		
	}
	
	/**
	 * 
	 */
	public void disablePanels(){
		faultInjectionEditor.setEnablePanel(false);
		httpEditor.setEnablePanel(false);
		settingsEditor.setEnablePanel(false);
	}
}
