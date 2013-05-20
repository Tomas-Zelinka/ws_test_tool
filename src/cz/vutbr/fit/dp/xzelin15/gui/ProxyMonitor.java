package cz.vutbr.fit.dp.xzelin15.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cz.vutbr.fit.dp.xzelin15.central.UnitController;
import cz.vutbr.fit.dp.xzelin15.data.UnitConfiguration;
import cz.vutbr.fit.dp.xzelin15.logging.ConsoleLog;
import cz.vutbr.fit.dp.xzelin15.proxyUnit.ProxyPanelListener;

/**
 * 
 * Holds the panels of proxy units
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class ProxyMonitor extends JPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5479720929723166064L;
	
	/**
	 * Prefix for local unit
	 */
	private final String LOCAL_NAME = "Local Proxy ";
	
	/**
	 * Prefix for remote unit
	 */
	private final String REMOTE_NAME = "Remote Proxy ";
	
	/**
	 * Local unit number
	 */
	private final int LOCAL_UNIT = 0;
	
	/**
	 * Number of connected units
	 */
	private int proxyUnitCounter;
	
	/**
	 * Unit controller
	 */
	private UnitController controller;
	
	/**
	 * Pane for unit tabs
	 */
	private JTabbedPane tabbedPane;
	
	public ProxyMonitor(UnitController controller){
		this.proxyUnitCounter = 0;
		this.controller = controller;
		initComponents();
		addUnit("none",LOCAL_UNIT);
		exportConfiguration();
	}
	
	/**
	 * Order to unit controller to export registered proxy units
	 */
	public void exportConfiguration(){
		controller.exportProxyConfiguration();
	}
	
	
	/**
	 * Get data from controller about units to be connected and
	 * send order back to controller to connect the units
	 * 
	 * It has to be from this object because we need show the panels
	 */
	public void importConfiguration(){
		
		UnitConfiguration[] configArray = controller.importProxyConfiguration();
		
		if(configArray != null){
			int panelCount = tabbedPane.getTabCount();
			
			//remove all registered panels
			proxyUnitCounter = 1;
			for(int i = 1; i < panelCount; i++){
				removeTest(i);
				
			}
			
			//create new panels from configuration
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
			if(port == LOCAL_UNIT){
				
				panelName = LOCAL_NAME;
			}else{
				
				panelName = REMOTE_NAME  + proxyUnitCounter;
			}
			
			ProxyUnitPanel panel = new ProxyUnitPanel();
			ProxyPanelListener listner = new ProxyPanelListener(panel);
			
			controller.addProxyUnit(proxyUnitCounter,host,port,panelName);
			controller.setPanelListener(listner,proxyUnitCounter);
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
		ConsoleLog.Print("[ProxyMonitor] panel index:" + selectedPanel);
		proxyUnitCounter++;
	}
	
	/**
	 * Remove selected unit panel with its unit
	 * @param panelIndex
	 */
	public void removeUnit(){
		int panelIndex = tabbedPane.getSelectedIndex();
		removeTest( panelIndex);
	}
	
	/**
	 * Run proxy unit
	 * @param path - path of the test case
	 */
	public void runUnit(String path){
		
		int panelIndex = tabbedPane.getSelectedIndex();
		runTest (path, panelIndex);
	}
	
	/**
	 * Stop the selected unit
	 */
	public void stopUnit(){
		
		int panelIndex = tabbedPane.getSelectedIndex();
		stopTest (panelIndex);
	}
	
	/**
	 * Run all connected units
	 * @param path
	 */
	public void runAllUnits(String path){
		
		for(int i = 0; i < tabbedPane.getTabCount(); i++){
			runTest (path,i);
		}
	}
	
	/**
	 * Stop all connected units
	 */
	public void stopAllUnits(){
		
		for(int i = 0; i < tabbedPane.getTabCount(); i++){
			stopTest (i);
		}
	}
	
	/**
	 * 
	 * @return
	 */
//	private int getPanelIndex(){
//		return tabbedPane.getSelectedIndex();
//	}
	
	/**
	 * 
	 * @return int - return unit key, the key is used in tab titles
	 * 				 and like an id for testing units
	 */ 
	private int getUnitKey(int panelIndex){
		
		String keyString = tabbedPane.getTitleAt(panelIndex);
		
		if(panelIndex == LOCAL_UNIT)// local unit selected
			return LOCAL_UNIT;
		else{
			int key = Integer.parseInt(keyString.split(" ")[2]);
			return key;
		}
	}
	
	
	/**
	 * 
	 * @param panelIndex
	 */
	private void stopTest(int panelIndex){
		
		controller.stopProxy(getUnitKey(panelIndex));
		if(panelIndex == LOCAL_UNIT ){
			tabbedPane.setTitleAt(panelIndex, LOCAL_NAME);
		}else{
			tabbedPane.setTitleAt(panelIndex,REMOTE_NAME+ getUnitKey(panelIndex));
		}
	}
	
	/**
	 * 
	 * Run one test - it is used by more public methods
	 * 
	 * @param path
	 * @param panelIndex
	 */
	private void runTest(String path, int panelIndex){
		
		File casePath = new File(path);
		String caseName = casePath.getName();
		
		if(panelIndex == LOCAL_UNIT){
			tabbedPane.setTitleAt(panelIndex,LOCAL_NAME +" - "+caseName );
		}else{
			tabbedPane.setTitleAt(panelIndex,REMOTE_NAME+ getUnitKey(panelIndex) +" - "+caseName );
		}
		//getSelectedPanel().clearResults();
		controller.runProxy(path,getUnitKey(panelIndex));
	}
	
	
	/**
	 * Remove selected panel with its proxy unit - used by more public methods
	 * @param panelIndex
	 */
	private void removeTest(int panelIndex){
		
		if (panelIndex != LOCAL_UNIT){
			ConsoleLog.Print("[ProxyMonitor] Removed Unit: " + getUnitKey(panelIndex));
			controller.removeTestUnit(getUnitKey(panelIndex));
			tabbedPane.remove(panelIndex);
			tabbedPane.revalidate();
			
		}else{
			ConsoleLog.Print("[ProxyMonitor] You cannot close local testing unit");
		}
	}
	
	/**
	 * Intializaton of all panels
	 */
	private void initComponents(){
		tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            ConsoleLog.Print("[ProxyMonitor] Tab: " + tabbedPane.getSelectedIndex());
	        }
	    });
		this.setLayout(new BorderLayout());
		this.add(tabbedPane,BorderLayout.CENTER);
	}
}

