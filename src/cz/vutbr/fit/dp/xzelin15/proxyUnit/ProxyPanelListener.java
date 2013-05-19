package cz.vutbr.fit.dp.xzelin15.proxyUnit;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import cz.vutbr.fit.dp.xzelin15.gui.ProxyUnitPanel;



/**
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class ProxyPanelListener extends UnicastRemoteObject implements ProxyListener {
	
	
	/**
	 * 
	 * Serialization id
	 * 
	 */
	private static final long serialVersionUID = -2744057031325449967L;
	
	/**
	 * 
	 * Proxy GUI panel
	 * 
	 */
	transient private ProxyUnitPanel panel;
	
	public ProxyPanelListener(ProxyUnitPanel panel) throws RemoteException{
		this.panel = panel;
	}
	
	/**
	 * 
	 * Send event of unknown host to GUI panel  
	 * 
	 */
	public void onUnknownHostEvent()  {
		panel.onUnknownHostEvent();
	}
	
	/**
	 * 
	 *  Send received data to GUI panel
	 * 
	 */
	public void onNewMessageEvent(int interactionId, HttpInteraction interaction) {
		panel.onNewMessageEvent(interactionId, interaction);
	}
}
