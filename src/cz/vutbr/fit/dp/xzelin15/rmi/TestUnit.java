package cz.vutbr.fit.dp.xzelin15.rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;

import cz.vutbr.fit.dp.xzelin15.data.HttpMessageData;
import cz.vutbr.fit.dp.xzelin15.data.TestCaseSettingsData;
import cz.vutbr.fit.dp.xzelin15.testingUnit.NewResponseListener;



/**
 * Interface for remote testing unit
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public interface TestUnit extends Remote {
	
	/**
	 * 
	 * Run method
	 * 
	 * @throws RemoteException
	 */
	public abstract void run() throws RemoteException;
	
	/**
	 * 
	 * Send data to testi unit
	 * 
	 * @param request
	 * @param settings
	 * @throws RemoteException
	 */
	public abstract void setTest(HttpMessageData request, TestCaseSettingsData settings) throws RemoteException;
	
	/**
	 * 
	 * Test connection of test unit
	 * 
	 * @return string
	 * @throws RemoteException
	 */
	public abstract String testConnection() throws RemoteException;
	
	/**
	 * 
	 * Registration of listener
	 * The listener is sending data to GUI panel 
	 * 
	 * @param listener
	 * @throws RemoteException
	 */
	public abstract void addResponseListener(NewResponseListener listener) throws RemoteException;
	
	/**
	 * 
	 * Order to stop test unit while periodic testing
	 * 
	 * @throws RemoteException
	 */
	public abstract void stopUnit() throws RemoteException;
}

 