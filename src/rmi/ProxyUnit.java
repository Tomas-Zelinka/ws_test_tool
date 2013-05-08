package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import proxyUnit.ProxyListener;
import data.FaultInjectionData;


public interface ProxyUnit extends Remote  {
	
	/**
	 * 
	 * @param activeTest
	 * @throws RemoteException
	 */
	public abstract void setActiveTest(FaultInjectionData activeTest) throws RemoteException;
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public abstract  String testConnection() throws RemoteException;
	
	/**
	 * 
	 * @throws RemoteException
	 */
	public abstract void run() throws RemoteException;
	
	/**
	 * 
	 * @throws RemoteException
	 */
	public abstract void stopProxy() throws RemoteException;
	
	/**
	 * 
	 * @param host
	 * @throws RemoteException
	 */
	public abstract void setProxyHost(String host) throws RemoteException;
	
	/**
	 * 
	 * @param port
	 * @throws RemoteException
	 */
	public abstract void setProxyPort(int port) throws RemoteException;
	
	/**
	 * 
	 * @param port
	 * @throws RemoteException
	 */
	public abstract void setTestedWsPort(int port) throws RemoteException;
	
	/**
	 * 
	 * @param listener
	 * @throws RemoteException
	 */
	public abstract void setPanelListener(ProxyListener listener) throws RemoteException;
	
	
	
	
}
