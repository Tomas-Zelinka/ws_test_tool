package cz.vutbr.fit.dp.xzelin15.cli;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import cz.vutbr.fit.dp.xzelin15.proxyUnit.HttpInteraction;
import cz.vutbr.fit.dp.xzelin15.proxyUnit.ProxyListener;


/**
 * Class responsible for connecting the proxy unit with text output instance
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class ProxyTextListener extends UnicastRemoteObject implements ProxyListener {
	
	/**
	 * Serialization id 
	 */
	private static final long serialVersionUID = -2744057031325449967L;
	
	transient private PrintProxyOutput output;
	
	/**
	 * 
	 * @param panel
	 * @throws RemoteException
	 */
	public ProxyTextListener(PrintProxyOutput panel) throws RemoteException{
		this.output = panel;
	}
	
	/**
	 * Delegation method
	 */
	public void onUnknownHostEvent()  {
		output.onUnknownHostEvent();
	}
	
	
	/**
	 * Delegation method
	 */
	public void onNewMessageEvent(int interactionId, HttpInteraction interaction) {
		output.onNewMessageEvent(interactionId, interaction);
	}
}
