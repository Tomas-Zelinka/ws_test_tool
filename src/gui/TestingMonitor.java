package gui;

import java.awt.BorderLayout;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logging.ConsoleLog;
import testingUnit.NewResponseListener;
import testingUnit.PanelListener;
import central.UnitController;

/**
 *  Testing monitor is swing container holding all testing units
 *  
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class TestingMonitor extends JPanel  {

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
	public TestingMonitor(UnitController testUnitController){
		controller = testUnitController;
		testUnitCounter = 0;
		initComponents();
		addTestUnit();
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
	public void addTestUnit(){
		int selectedPanel = 0;
		
		try{
			controller.addTestUnit(testUnitCounter);
			
			UnitPanel panel = new UnitPanel(testUnitCounter);
			NewResponseListener listner = new PanelListener(panel);
			
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
			String splitedPath = (MainWindow.getSuitePath().split(File.separator))[2];
			if(panelIndex == 0){
				tabbedPane.setTitleAt(panelIndex,"Local Unit"+" - "+splitedPath );
			}else{
				String keyString = tabbedPane.getTitleAt(panelIndex);
				int key = Integer.parseInt(keyString.split(" ")[2]);
				tabbedPane.setTitleAt(panelIndex,"Remote Unit "+ key +" - "+splitedPath );
			}
			((UnitPanel)tabbedPane.getComponentAt(panelIndex)).openTestList(path);
			
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
			getSelectedPanel().insertTestCaseToTable(path);
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
	public void saveTestList(String path){
		getSelectedPanel().saveTestList(path); 
	}

	
	public void runUnit(String path){
		getSelectedPanel().saveTestList(path); 
		getSelectedPanel().clearResults();
		controller.runTest(path,getUnitKey());
	}
	
	/**
	 * 
	 * @return int - return unit key, the key is used in tab titles
	 * 				 and like an id for testing units
	 */
	private int getUnitKey(){
		
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
	private UnitPanel getSelectedPanel(){
		UnitPanel selectedPanel = (UnitPanel)tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
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
	
	
}
