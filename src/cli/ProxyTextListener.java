package cli;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import proxyUnit.HttpInteraction;
import proxyUnit.ProxyListener;

public class ProxyTextListener extends UnicastRemoteObject implements ProxyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2744057031325449967L;
	
	transient private PrintProxyOutput output;
	
	public ProxyTextListener(PrintProxyOutput panel) throws RemoteException{
		this.output = panel;
	}
	
	
	public void onUnknownHostEvent()  {
		output.onUnknownHostEvent();
	}
	
	public void onNewMessageEvent(int interactionId, HttpInteraction interaction) {
		output.onNewMessageEvent(interactionId, interaction);
	}
}
