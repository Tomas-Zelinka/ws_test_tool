package cz.vutbr.fit.dp.xzelin15.testingUnit;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import cz.vutbr.fit.dp.xzelin15.data.HttpMessageData;
import cz.vutbr.fit.dp.xzelin15.gui.TestUnitPanel;


public class TestPanelListener extends UnicastRemoteObject implements NewResponseListener{

	transient TestUnitPanel unitPanel;
	private static final long serialVersionUID = 8422078277925072138L;
	
	
	public TestPanelListener(TestUnitPanel panel) throws RemoteException{
		this.unitPanel = panel;
	}
	
	public void onNewResponseEvent(HttpMessageData[] data,int period){
		this.unitPanel.onNewResponseEvent(data,period);
	}

	
}
