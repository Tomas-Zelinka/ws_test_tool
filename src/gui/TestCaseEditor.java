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
	/**
	 * ID for serialization
	 */
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
	
	public TestCaseEditor(){
		
		initComponents();
		
		mainTabbedPane.addTab("Test Case Settings",settingsEditor);
	    mainTabbedPane.addTab("HTTP Request", httpEditor);
	    mainTabbedPane.addTab("Fault Injection",faultInjectionEditor);
	    
		setLayout(new BorderLayout());
		add(mainTabbedPane,BorderLayout.CENTER);
	}
	
	
	public void setTab(int tab){
		ConsoleLog.Print("tab:"+tab);
		mainTabbedPane.setSelectedIndex(tab);
		mainTabbedPane.revalidate();
		mainTabbedPane.repaint();
		setTestCasePath();
		loadTestCase();
	}
	
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
		this.testCasePath = MainWindow.getDataRoot()+File.separator+MainWindow.getSuitePath()+ File.separator+ MainWindow.getCasePath();
		ConsoleLog.Print(this.testCasePath);
	}
	
	public String getTestCasePath(){
		return this.testCasePath;
	}
	
	
	public void saveTestCase(){
		
		ConsoleLog.Print("saving");
		FaultInjectionData faultData = faultInjectionEditor.getTest();
		HttpMessageData requestData = httpEditor.getHttpRequestData();
		TestCaseSettingsData settingsData = settingsEditor.getSettingsData();
		
		String settingsFilePath = getTestCasePath() + File.separator + TestCaseEditor.settingsFileName;
		String httpFilePath = getTestCasePath() + File.separator + TestCaseEditor.httpRequestFileName;
		String faultFilePath = getTestCasePath() + File.separator + TestCaseEditor.faultInjectionFileName;
		
		if(settingsData != null)
			dataProvider.writeObject(settingsFilePath, settingsData);
		
		if(requestData != null)
			dataProvider.writeObject(httpFilePath, requestData);
		
		if(faultData != null)
			dataProvider.writeObject(faultFilePath,faultData );
				
		ConsoleLog.Print("saved");
	}
	
	
	public void loadTestCase(){
		
		ConsoleLog.Print("loading");
		TestCaseSettingsData loadedSettings = null;
		FaultInjectionData loadedFault = null;
		HttpMessageData loadedHttpData = null;
		
		String settingsFilePath = getTestCasePath() + File.separator + TestCaseEditor.settingsFileName;
		String httpFilePath = getTestCasePath() + File.separator + TestCaseEditor.httpRequestFileName;
		String faultFilePath = getTestCasePath() + File.separator + TestCaseEditor.faultInjectionFileName;
		
		File settingsFile = new File(settingsFilePath);
		File httpDataFile = new File(httpFilePath);
		File faultDataFile = new File(faultFilePath);
		
		
		ConsoleLog.Print("httpFile:"+ httpDataFile.getPath());
		if(!settingsFile.exists()){
			ConsoleLog.Print("Settings file not found !!!");
		}else{
			loadedSettings = (TestCaseSettingsData) dataProvider.readObject(settingsFilePath);
			settingsEditor.setSettingsData(loadedSettings);
			ConsoleLog.Print(loadedSettings.toString());
		}
		
		if(!httpDataFile.exists()){
			ConsoleLog.Print("Http Data file not found !!!");
		}else{
			loadedHttpData = (HttpMessageData) dataProvider.readObject(httpFilePath);
		}
		
		
		
		if(!faultDataFile.exists()){
			ConsoleLog.Print("Fault Injection file not found !!!");
		}else{
			loadedFault = (FaultInjectionData) dataProvider.readObject(faultFilePath);
			faultInjectionEditor.setTest(loadedFault);
		}
		
		ConsoleLog.Print("loaded");
		
	}
}
