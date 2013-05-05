package testingUnit;

import gui.UnitPanel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import data.HttpMessageData;

public class PanelListener extends UnicastRemoteObject implements NewResponseListener{

	transient UnitPanel unitPanel;
	private static final long serialVersionUID = 8422078277925072138L;
	
	
	public PanelListener(UnitPanel panel) throws RemoteException{
		this.unitPanel = panel;
	}
	
	public void onNewResponseEvent(HttpMessageData[] data){
		this.unitPanel.onNewResponseEvent(data);
	}

	
}
