package gui;

import java.awt.BorderLayout;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logging.ConsoleLog;
import testingUnit.NewResponseListener;
import testingUnit.TestPanelListener;
import central.UnitController;
import data.TestCaseSettingsData;
import data.TestList;

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
	
	
	/**
	 * 
	 * @param testUnitController
	 */
	public TestMonitor(UnitController testUnitController){
		
		controller = testUnitController;
		testUnitCounter = 0;
		initComponents();
		addTestUnit("",0);
	}
	
	/**
	 * 
	 */
	public void exportConfiguration(){
		
	}
	
	
	/**
	 * This method inserts remote unit tab to the tabbed pane
	 * and initialize new remote unit in unit controller
	 */
	public void addTestUnit(String host, int port){
		int selectedPanel = 0;
		
		try{
			controller.addTestUnit(testUnitCounter,host,port);
			
			TestUnitPanel panel = new TestUnitPanel();
			NewResponseListener listner = new TestPanelListener(panel);
			
			if(testUnitCounter == 0){
				tabbedPane.addTab("Local Unit",panel);
				controller.setResponseListener(listner,testUnitCounter);
				
			}else{
				
				controller.setResponseListener(listner,testUnitCounter);
				tabbedPane.addTab("Remote Unit "  + testUnitCounter,panel);
				selectedPanel = tabbedPane.indexOfTab("Remote Unit " +testUnitCounter);
			}
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
	 * 
	 */
	public void removeUnit(){
		
		int panelIndex = tabbedPane.getSelectedIndex();
		if (panelIndex != 0){
			controller.removeTestUnit(getUnitKey());
			tabbedPane.remove(panelIndex);
			tabbedPane.revalidate();
			ConsoleLog.Print("[TestMonitor] Removed Unit: " + getUnitKey());
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
		for (int i =0; i < tabCount; i++){
		
			int panelIndex = i;
			String splitedPath = (MainWindow.getSuitePath().split("\\"+File.separator))[2];
			if(panelIndex == 0){
				tabbedPane.setTitleAt(panelIndex,"Local Unit"+" - "+splitedPath );
			}else{
				String keyString = tabbedPane.getTitleAt(panelIndex);
				int key = Integer.parseInt(keyString.split(" ")[2]);
				tabbedPane.setTitleAt(panelIndex,"Remote Unit "+ key +" - "+splitedPath );
			}
			 
			TestList list = controller.readTestList(path);
			ArrayList<TestCaseSettingsData> settingsData = new ArrayList<TestCaseSettingsData>();
			
			ArrayList<String> paths = list.getTestCases();
			for(int id = 0; id < paths.size(); id++){
				
				TestCaseSettingsData data = controller.readSettingsData(paths.get(id));
				settingsData.add(data);
			} 
			
			((TestUnitPanel)tabbedPane.getComponentAt(panelIndex)).openTestList(settingsData);
			
		}
	} 
	
	/** 
	 * 
	 * @param path
	 */
	public void insertTestCase(String path){
		
		if(MainWindow.getCasePath().isEmpty()){
			ConsoleLog.Message("Test case not selected"); 
		}else{
			TestCaseSettingsData data = controller.readSettingsData(path);
			getSelectedPanel().insertTestCaseToTable(data);
		}
	}
	
	/**
	 * 
	 */
	public void removeTestCase(){
		getSelectedPanel().removeSelectedTestCase(); 
	}
	
	/**
	 * 
	 */
	public void saveTestList(String suitePath){
		
		ArrayList<TestCaseSettingsData> data = getSelectedPanel().getTestListData(); 
		TestList list = new TestList();
		
		for(int i =0; i < data.size(); i++){
			TestCaseSettingsData tmpData = controller.readSettingsData(data.get(i).getPath());
			tmpData.setRun(data.get(i).getRun());
			tmpData.setLoopNumber(data.get(i).getLoopNumber());
			tmpData.setThreadsNumber(data.get(i).getThreadsNumber());
			
			controller.writeSettingsData(data.get(i).getPath(), data.get(i));
			list.addTestCase(data.get(i).getPath());
		}
			
		controller.writeTestList(suitePath, list);
		
	}

	
	public void runUnit(String path){
		
		runTest(path, getUnitKey());
	}
	
	public void runAllUnits(String path){
		
		int tabCount = tabbedPane.getTabCount();
		for (int i =0; i < tabCount; i++){
			String keyString = tabbedPane.getTitleAt(i);
			if(i == 0){
				runTest(path,i);
			}else{
				int key = Integer.parseInt(keyString.split(" ")[2]);
				runTest(path,key);
			}
		}
	}
	
	
	
	/**
	 * 
	 * @return int - return unit key, the key is used in tab titles
	 * 				 and like an id for testing units
	 */ 
	public int getUnitKey(){
		
		int panelIndex = tabbedPane.getSelectedIndex();
		String keyString = tabbedPane.getTitleAt(panelIndex);
		
		if(panelIndex == 0)// local unit selected
			return 0;
		else{
			int key = Integer.parseInt(keyString.split(" ")[2]);
			return key;
		}
	}
	
	/**
	 * 
	 * @return JPanel - returns the selected unit panel
	 */
	private TestUnitPanel getSelectedPanel(){
		
		TestUnitPanel selectedPanel = (TestUnitPanel)tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		ConsoleLog.Print("[TestMonitor] returned Unit: " + getUnitKey());
		return selectedPanel;
	}
	
	/**
	 * 
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
	
	private void runTest(String path, int key){
		
		saveTestList(path); 
		getSelectedPanel().clearResults();
		controller.runTest(path,getUnitKey());
	}
	
	
}
