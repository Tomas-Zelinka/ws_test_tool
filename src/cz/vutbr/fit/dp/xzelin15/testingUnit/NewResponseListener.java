package cz.vutbr.fit.dp.xzelin15.testingUnit;

import java.rmi.Remote;
import java.rmi.RemoteException;

import cz.vutbr.fit.dp.xzelin15.data.HttpMessageData;



/**
 * Interface for remote response listener
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public interface NewResponseListener extends Remote {
	
	/**
	 * Sends data from remote location to the GUI
	 * @param data - array of responses from one thread
	 * @param period - when periodic mode is used
	 * @throws RemoteException - connection is interrupted
	 */
	public void onNewResponseEvent(HttpMessageData[] data, int period) throws RemoteException;
}
