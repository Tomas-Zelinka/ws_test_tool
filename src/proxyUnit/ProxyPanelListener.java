package proxyUnit;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import gui.ProxyUnitPanel;

public class ProxyPanelListener extends UnicastRemoteObject implements ProxyListener {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2744057031325449967L;
	
	transient private ProxyUnitPanel panel;
	
	public ProxyPanelListener(ProxyUnitPanel panel) throws RemoteException{
		this.panel = panel;
	}
	
	
	public void onUnknownHostEvent()  {
		panel.onUnknownHostEvent();
	}
	
	public void onNewMessageEvent(int interactionId, HttpInteraction interaction) {
		panel.onNewMessageEvent(interactionId, interaction);
	}
}
