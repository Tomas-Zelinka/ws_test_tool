/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 16.2.2012
 */
package proxyUnit;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * Interface for remote proxy unit
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public interface ProxyListener extends Remote {
	
	/**
	 * 
	 * Send received data to GUI panel
	 * 
	 * @param interactionId
	 * @param interaction
	 * @throws RemoteException
	 */
	public void onNewMessageEvent(int interactionId, HttpInteraction interaction) throws RemoteException;
	
	/**
	 * 
	 * Send event of unknown host to GUI panel 
	 * 
	 * @throws RemoteException
	 */
	public void onUnknownHostEvent() throws RemoteException;	
	
}
