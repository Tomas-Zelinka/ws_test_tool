package cz.vutbr.fit.dp.xzelin15.testingUnit;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import cz.vutbr.fit.dp.xzelin15.data.HttpMessageData;
import cz.vutbr.fit.dp.xzelin15.gui.TestUnitPanel;

/**
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class TestPanelListener extends UnicastRemoteObject implements NewResponseListener{

	/**
	 * Registered test unit panel
	 */
	transient TestUnitPanel unitPanel;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8422078277925072138L;
	
	
	/**
	 * Put the GUI panel inside object
	 * @param panel
	 * @throws RemoteException
	 */
	public TestPanelListener(TestUnitPanel panel) throws RemoteException{
		this.unitPanel = panel;
	}
	
	/**
	 * This method is called when new array of responses are ready to send
	 */
	public void onNewResponseEvent(HttpMessageData[] data,int period){
		this.unitPanel.onNewResponseEvent(data,period);
	}

	
}
