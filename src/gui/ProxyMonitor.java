package gui;

import javax.swing.JPanel;

import central.UnitController;

public class ProxyMonitor extends JPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5479720929723166064L;

	public ProxyMonitor(UnitController controller){
		
	}
//	/**
//	 * This method inserts remote unit tab to the tabbed pane
//	 * and initialize new remote unit in unit controller
//	 */
//	public void addProxyUnit(String host, int port){
//		int selectedPanel = 0;
//		
//		try{
//			controller.addTestUnit(testUnitCounter,host,port);
//			
//			UnitPanel panel = new UnitPanel(testUnitCounter);
//			NewResponseListener listner = new PanelListener(panel);
//			
//			if(testUnitCounter == 0){
//				tabbedPane.addTab("Local Unit",panel);
//				controller.setResponseListener(listner,testUnitCounter);
//				
//			}else{
//				
//				controller.setResponseListener(listner,testUnitCounter);
//				tabbedPane.addTab("Remote Unit "  + testUnitCounter,panel);
//				selectedPanel = tabbedPane.indexOfTab("Remote Unit " +testUnitCounter);
//			}
//		}catch(RemoteException ex){
//			ConsoleLog.Message(ex.getClass().getName() + ": " + ex.getMessage());
//			return;
//		}catch(NotBoundException ex){
//			ConsoleLog.Message(ex.getClass().getName() + ": " +ex.getMessage());
//			return;
//		}catch(Exception ex){
//			ConsoleLog.Message(ex.getClass().getName() + ": " +ex.getMessage());
//			ex.printStackTrace();
//			return;
//		}
//		
//		tabbedPane.setSelectedIndex(selectedPanel);
//		ConsoleLog.Print("[TestMonitor] panel index:" + selectedPanel);
//		testUnitCounter++;
//	}
	
}

