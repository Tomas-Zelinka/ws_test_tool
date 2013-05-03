package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logging.ConsoleLog;
import central.UnitController;

/**
 *  Testing monitor is swing kontejner holding all testing unit
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
		setupComponents();
		
	}
	
	/**
	 * 
	 */
	public void exportConfiguration(){
		
	}
	
	/**
	 * 
	 */
	public void addLocalTestUnit(){
		
		UnitPanel panel = new UnitPanel(testUnitCounter);
		//panel.setTestUnit(getLocalTestUnit());
		tabbedPane.addTab("Local Unit",panel);
		controller.setResponseListener(panel);
		testUnitCounter++;
	}
	
	/**
	 * This method inserts remote unit tab to the tabbed pane
	 * and initialize new remote unit in unit controller
	 */
	public void addRemoteUnit(){
		
		UnitPanel panel = new UnitPanel(testUnitCounter);
		
		tabbedPane.addTab("Remote Unit "  + testUnitCounter,panel);
		int selectedPanel = tabbedPane.indexOfTab("Remote Unit " +testUnitCounter);
		ConsoleLog.Print("panel index:" + selectedPanel);
		tabbedPane.setSelectedIndex(selectedPanel);
		
		controller.addRemoteTestUnit(testUnitCounter);
		//panel.setTestUnit(getRemoteTestUnit(unitCounter));
		
		testUnitCounter++;
	}
	
	/**
	 * 
	 */
	public void removeUnit(){
		
		int panelIndex = tabbedPane.getSelectedIndex();
		if (panelIndex != 0){
			ConsoleLog.Print("Removed Unit: " + getUnitKey());
			controller.removeRemoteTestUnit(getUnitKey());
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
		
		int panelIndex = tabbedPane.getSelectedIndex();
		
		if(panelIndex == 0){
			tabbedPane.setTitleAt(panelIndex,"Local Unit "+" - "+MainWindow.getSuitePath() );
		}else{
			tabbedPane.setTitleAt(panelIndex,"Remote Unit "+ getUnitKey() +" - "+MainWindow.getSuitePath() );
			
		}
		getSelectedPanel().openTestList(path);
	} 
	
	/** 
	 * 
	 * @param path
	 */
	public void insertTestCase(String path){
		
		if(MainWindow.getCasePath().isEmpty()){
			ConsoleLog.Message("[TestMonitor] Test case not selected"); 
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
		getSelectedPanel().clearUnitPanel();
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
		else
			return Integer.parseInt(keyString.substring("Remote Unit ".length()));
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
	
	/**
	 * 
	 */
	private void setupComponents(){
		controller.addLocalTestUnit();
		addLocalTestUnit();
	}
}
