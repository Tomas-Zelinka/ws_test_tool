package testingUnit;

import gui.TestUnitPanel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import data.HttpMessageData;

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
