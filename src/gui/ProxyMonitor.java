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
import proxyUnit.ProxyPanelListener;
import central.UnitController;
import data.UnitConfiguration;

public class ProxyMonitor extends JPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5479720929723166064L;
	private final String LOCAL_NAME = "Local Proxy ";
	private final String REMOTE_NAME = "Remote Proxy ";
	private final int LOCAL_UNIT = 0;
	private int proxyUnitCounter;
	private UnitController controller;
	private JTabbedPane tabbedPane;
	
	public ProxyMonitor(UnitController controller){
		this.proxyUnitCounter = 0;
		this.controller = controller;
		initComponents();
		addUnit("none",LOCAL_UNIT);
	}
	
	/**
	 * 
	 */
	public void exportConfiguration(){
		controller.exportProxyConfiguration();
	}
	
	public void importConfiguration(){
		
		UnitConfiguration[] configArray = controller.importProxyConfiguration();
		
		if(configArray != null){
			int panelCount = tabbedPane.getTabCount();
			
			for(int i = 1; i < panelCount; i++){
				removeUnit(i);
				proxyUnitCounter--;
			}
			
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
			
			
			ProxyUnitPanel panel = new ProxyUnitPanel();
			ProxyPanelListener listner = new ProxyPanelListener(panel);
			
			if(port == 0){
				panelName = LOCAL_NAME;
				tabbedPane.addTab(panelName,panel);
				controller.addProxyUnit(proxyUnitCounter,host,port,panelName);
				controller.setPanelListener(listner,proxyUnitCounter);
				
			}else{
				panelName = REMOTE_NAME  + proxyUnitCounter;
				controller.addProxyUnit(proxyUnitCounter,host,port,panelName);
				controller.setPanelListener(listner,proxyUnitCounter);
				tabbedPane.addTab(panelName,panel);
				selectedPanel = tabbedPane.indexOfTab(panelName);
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
		ConsoleLog.Print("[ProxyMonitor] panel index:" + selectedPanel);
		proxyUnitCounter++;
	}
	
	public int getPanelIndex(){
		return tabbedPane.getSelectedIndex();
	}
	
	public void removeUnit(int panelIndex){
		
		if (panelIndex != LOCAL_UNIT){
			controller.removeTestUnit(getUnitKey());
			tabbedPane.remove(panelIndex);
			tabbedPane.revalidate();
			ConsoleLog.Print("[ProxyMonitor] Removed Unit: " + getUnitKey());
		}else{
			ConsoleLog.Print("[ProxyMonitor] You cannot close local testing unit");
		}
	}
	
	
	public void runUnit(String path){
		

		int panelIndex = tabbedPane.getSelectedIndex();
		
		String[] splittedPath = path.split("\\"+File.separator);
		
		if(splittedPath.length > 2){
			
			String caseName = splittedPath[3];
			
			if(panelIndex == LOCAL_UNIT){
				tabbedPane.setTitleAt(panelIndex,LOCAL_NAME +" - "+caseName );
			}else{
				tabbedPane.setTitleAt(panelIndex,REMOTE_NAME+ getUnitKey() +" - "+caseName );
			}
			//getSelectedPanel().clearResults();
			controller.runProxy(path,getUnitKey());
			
		}else{
			ConsoleLog.Message("Any Test case selected.");
		}
	}
	
	public void stopUnit(){
		controller.stopProxy(getUnitKey());
		
		int panelIndex = tabbedPane.getSelectedIndex();
		if(panelIndex == LOCAL_UNIT ){
			tabbedPane.setTitleAt(panelIndex, LOCAL_NAME);
		}else{
			tabbedPane.setTitleAt(panelIndex,REMOTE_NAME+ getUnitKey());
		}
		
	}
	
		
	/**
	 * 
	 * @return JPanel - returns the selected unit panel
	 */
//	private ProxyUnitPanel getSelectedPanel(){
//		ProxyUnitPanel selectedPanel = (ProxyUnitPanel)tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
//		ConsoleLog.Print("[ProxyMonitor] returned Unit: " + getUnitKey());
//		return selectedPanel;
//	}
	
	
	/**
	 * 
	 * @return int - return unit key, the key is used in tab titles
	 * 				 and like an id for testing units
	 */ 
	public int getUnitKey(){
		
		int panelIndex = tabbedPane.getSelectedIndex();
		String keyString = tabbedPane.getTitleAt(panelIndex);
		
		if(panelIndex == LOCAL_UNIT)// local unit selected
			return LOCAL_UNIT;
		else{
			int key = Integer.parseInt(keyString.split(" ")[2]);
			return key;
		}
	}
	
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

