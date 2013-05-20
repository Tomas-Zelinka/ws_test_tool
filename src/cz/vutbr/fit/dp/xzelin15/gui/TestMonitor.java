package cz.vutbr.fit.dp.xzelin15.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cz.vutbr.fit.dp.xzelin15.central.UnitController;
import cz.vutbr.fit.dp.xzelin15.data.TestCaseSettingsData;
import cz.vutbr.fit.dp.xzelin15.data.TestList;
import cz.vutbr.fit.dp.xzelin15.data.UnitConfiguration;
import cz.vutbr.fit.dp.xzelin15.logging.ConsoleLog;
import cz.vutbr.fit.dp.xzelin15.testingUnit.NewResponseListener;
import cz.vutbr.fit.dp.xzelin15.testingUnit.TestPanelListener;


/**
 *  Testing monitor is swing container holding all testing units
 *  
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class TestMonitor extends JPanel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6401376001409493032L;
	private JTabbedPane tabbedPane;
	private UnitController controller;
	private int testUnitCounter;
	
	private final String LOCAL_NAME = "Local Test ";
	private final String REMOTE_NAME = "Remote Test ";
	private final int LOCAL_UNIT = 0;
	
	/**
	 * 
	 * @param testUnitController
	 */
	public TestMonitor(UnitController testUnitController){
		
		controller = testUnitController;
		testUnitCounter = 0;
		initComponents();
		addUnit("none",LOCAL_UNIT);
		exportConfiguration();
	}
	
	/**
	 * Order to controller to export configuration of remote units
	 */
	public void exportConfiguration(){
		controller.exportTestConfiguration();
	}
	
	/**
	 * Order to controller to get data of remote unit and then connect them
	 */
	public void importConfiguration(){
		
		UnitConfiguration[] configArray = controller.importTestConfiguration();
		
		if(configArray != null){
			int panelCount = tabbedPane.getTabCount();
			
			//remove all units before we import new ones
			testUnitCounter = 1;
			for(int i = 1; i < panelCount; i++){
				
				removeUnit(i);
			}
			
			//import the new configuration of remote units
			for(UnitConfiguration config : configArray){
				addUnit(config.getHost(),config.getRegistryPort());
			}
		}else{
			ConsoleLog.Message("Found any configurations");
		}
	}
	
	
	
	/**
	 * This method inserts remote unit tab to the tabbed pane
	 * and initialize new remote unit in unit controller
	 */
	public void addUnit(String host, int port){
		
		int selectedPanel = 0;
		String panelName = "";
		
		try{
			//port = 0, because it is localunit 
			if(port == LOCAL_UNIT){
				
				panelName = LOCAL_NAME;
			}else{
				
				panelName = REMOTE_NAME+ testUnitCounter;
			}
			
			TestUnitPanel panel = new TestUnitPanel();
			NewResponseListener listner = new TestPanelListener(panel);
			controller.addTestUnit(testUnitCounter,host,port,panelName);
			controller.setResponseListener(listner,testUnitCounter);
			
			tabbedPane.addTab(panelName,panel);
			selectedPanel = tabbedPane.indexOfTab(panelName);
			
		}catch(RemoteException ex){
			ConsoleLog.Message(ex.getClass().getName() + ": " + ex.getMessage());
			return;
		}catch(NotBoundException ex){
			ConsoleLog.Message(ex.getClass().getName() + ": " +ex.getMessage());
			return;
		}catch(Exception ex){
			ConsoleLog.Message(ex.getClass().getName() + ": " +ex.getMessage());
			ex.printStackTrace();
			return;
		}
		
		tabbedPane.setSelectedIndex(selectedPanel);
		ConsoleLog.Print("[TestMonitor] panel index:" + selectedPanel);
		testUnitCounter++;
	}
	
	/**
	 * Remove the remote unit, the local unit cannot be removed
	 */
	public void removeUnit(int panelIndex){
		
		if (panelIndex != LOCAL_UNIT){
			ConsoleLog.Print("[TestMonitor] Removed Unit: " + getUnitKey());
			controller.removeTestUnit(getUnitKey());
			tabbedPane.remove(panelIndex);
			tabbedPane.revalidate();
		}else{
			ConsoleLog.Print("[TestMonitor] You cannot close local testing unit");
		}
	
	}
	
	/**
	 * 
	 * @param String - calls the openTestList method in unit panel
	 */
	public void openTestList(String path){
		
		int tabCount = tabbedPane.getTabCount();
		
		//opent teslist to all connected units
		for (int i =0; i < tabCount; i++){
		
			int panelIndex = i;
			File suite = new File(MainWindow.getSuitePath());
			String suiteName = suite.getName();
			
			if(panelIndex == LOCAL_UNIT){
				
				tabbedPane.setTitleAt(panelIndex,LOCAL_NAME+" - "+suiteName );
			}else{
				
				String keyString = tabbedPane.getTitleAt(panelIndex);
				int key = Integer.parseInt(keyString.split(" ")[2]);
				tabbedPane.setTitleAt(panelIndex,REMOTE_NAME+ key +" - "+suiteName );
			}
			 
			//read testlist data to get request paths
			TestList list = controller.readTestList(path);
			ArrayList<TestCaseSettingsData> settingsData = new ArrayList<TestCaseSettingsData>();
			
			//load data to array of settings data and pass it to the unit panel
			ArrayList<String> paths = list.getTestCases();
			for(int id = 0; id < paths.size(); id++){
				
				TestCaseSettingsData data = controller.readSettingsData(paths.get(id));
				settingsData.add(data);
			} 
			
			((TestUnitPanel)tabbedPane.getComponentAt(panelIndex)).openTestList(settingsData);
			
		}
	} 
	
	/** 
	 * Insert test case into unit panel, it clears gathered results 
	 * @param path - Path of the test case
	 */
	public void insertTestCase(String path){
		
		if(MainWindow.getCasePath().isEmpty()){
			ConsoleLog.Message("Test case not selected"); 
		}else{
			TestCaseSettingsData data = controller.readSettingsData(path);
			getSelectedPanel().insertTestCaseToTable(data);
			getSelectedPanel().clearResults();
		}
	}
	
	/**
	 * Remove selected test case from testlist
	 */
	public void removeTestCase(){
		getSelectedPanel().removeSelectedTestCase(); 
	}
	
	/**
	 * Save test list to file 
	 */
	public void saveTestList(String suitePath){
		
		ArrayList<TestCaseSettingsData> data = getSelectedPanel().getTestListData(); 
		TestList list = new TestList();
		
		//get the data from test list and save it to the test cases in files
		for(int i =0; i < data.size(); i++){
			TestCaseSettingsData tmpData = controller.readSettingsData(data.get(i).getPath());
			tmpData.setRun(data.get(i).getRun());
			tmpData.setLoopNumber(data.get(i).getLoopNumber());
			tmpData.setThreadsNumber(data.get(i).getThreadsNumber());
			
			controller.writeSettingsData(data.get(i).getPath(), data.get(i));
			list.addTestCase(data.get(i).getPath());
		}
			
		//write the test list to file
		controller.writeTestList(suitePath, list);
		
	}

	/**
	 * Run selected unit
	 * @param path - Test suite Path
	 */
	public void runUnit(String path){
		
		runTest(path, getUnitKey());
	}
	
	/**
	 * Stops unit when it is in periodic mode
	 */
	public void stopUnit(){
		
		stopTest(getUnitKey());
	}
	
	/**
	 * Run all connected units
	 * @param path
	 */
	public void runAllUnits(String path){
		
		int tabCount = tabbedPane.getTabCount();
		for (int i =0; i < tabCount; i++){
			String keyString = tabbedPane.getTitleAt(i);
			if(i == LOCAL_UNIT){
				runTest(path,i);
			}else{
				int key = Integer.parseInt(keyString.split(" ")[2]);
				runTest(path,key);
			}
		}
	}
	
	/**
	 * Stop all connected unit when they are in periodic mode
	 */
	public void stopAllUnits(){
		int tabCount = tabbedPane.getTabCount();
		for (int i =0; i < tabCount; i++){
			String keyString = tabbedPane.getTitleAt(i);
			if(i == LOCAL_UNIT){
				stopTest(i);
			}else{
				int key = Integer.parseInt(keyString.split(" ")[2]);
				stopTest(key);
			}
		}
	}
	
	/**
	 * Close the test list, it clears all tables and text areas
	 */
	public void closeTestList(){
		
		int panelIndex = tabbedPane.getSelectedIndex();
		
			if(panelIndex == LOCAL_UNIT ){
				tabbedPane.setTitleAt(panelIndex, LOCAL_NAME);
			}else{
				tabbedPane.setTitleAt(panelIndex,REMOTE_NAME+ getUnitKey());
			}
			getSelectedPanel().clearResults();
			getSelectedPanel().clearTestList();
		
	}
	
	/**
	 * Return index of selected panel - for mainwindow purposes
	 * @return
	 */
	public int getPanelIndex(){
		return tabbedPane.getSelectedIndex();
	}
	
	/**
	 * Stop test when it is in periodic mode - private version, used by more public methods
	 * @param key
	 */
	private void stopTest(int key){
		controller.stopTestUnit(key);
	}
	
	
	/**
	 * Gather unit key from tab title - should be replaced with unit names
	 * 
	 * @return int - return unit key, the key is used in tab titles
	 * 				 and like an id for testing units
	 */ 
	private int getUnitKey(){
		
		int panelIndex = tabbedPane.getSelectedIndex();
		String keyString = tabbedPane.getTitleAt(panelIndex);
		
		if(panelIndex == LOCAL_UNIT)// local unit selected
			return LOCAL_UNIT;
		else{
			int key = Integer.parseInt(keyString.split(" ")[2]);
			return key;
		}
	}
	
	/**
	 * Returns object of seleted panel
	 * @return JPanel - returns the selected unit panel
	 */
	private TestUnitPanel getSelectedPanel(){
		
		TestUnitPanel selectedPanel = (TestUnitPanel)tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		ConsoleLog.Print("[TestMonitor] returned Unit: " + getUnitKey());
		return selectedPanel;
	}
	
	/**
	 * Initialization of components
	 */
	private void initComponents(){
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            ConsoleLog.Print("[TestMonitor] Tab: " + tabbedPane.getSelectedIndex());
	        }
	    });
		
		this.setLayout(new BorderLayout());
		this.add(tabbedPane,BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * Run on test - private version - used by more public methods
	 * 
	 * @param path
	 * @param key
	 */
	private void runTest(String path, int key){
		
		saveTestList(path); 
		getSelectedPanel().clearResults();
		controller.runTest(path,key);
	}
	
	
	
}
