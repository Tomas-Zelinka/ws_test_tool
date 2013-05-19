package cz.vutbr.fit.dp.xzelin15.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import cz.vutbr.fit.dp.xzelin15.data.FaultInjectionData;
import cz.vutbr.fit.dp.xzelin15.proxyUnit.ProxyListener;



public interface ProxyUnit extends Remote  {
	
	/**
	 * 
	 * Send data to proxy unit 
	 * 
	 * @param activeTest
	 * @throws RemoteException
	 */
	public abstract void setActiveTest(FaultInjectionData activeTest) throws RemoteException;
	
	/**
	 * 
	 *  Test connection of test unit
	 * 
	 * @return String
	 * @throws RemoteException
	 */
	public abstract  String testConnection() throws RemoteException;
	
	/**
	 * 
	 * Run method 
	 * 
	 * @throws RemoteException
	 */
	public abstract void run() throws RemoteException;
	
	/**
	 *
	 *  Stop the running proxy unit
	 * 
	 * @throws RemoteException
	 */
	public abstract void stopProxy() throws RemoteException;
	
	/**
	 * 
	 * Set proxy Host
	 * 
	 * @param host
	 * @throws RemoteException
	 */
	public abstract void setProxyHost(String host) throws RemoteException;
	
	/**
	 * 
	 * Set proxy port
	 * 
	 * @param port
	 * @throws RemoteException
	 */
	public abstract void setProxyPort(int port) throws RemoteException;
	
	/**
	 * 
	 * Set port of tested web service
	 * 
	 * @param port
	 * @throws RemoteException
	 */
	public abstract void setTestedWsPort(int port) throws RemoteException;
	
	/**
	 * 
	 * Set GUI panel listener
	 * 
	 * @param listener
	 * @throws RemoteException
	 */
	public abstract void setPanelListener(ProxyListener listener) throws RemoteException;
	
	
	
	
}
