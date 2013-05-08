package rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;

import testingUnit.NewResponseListener;

import data.HttpMessageData;
import data.TestCaseSettingsData;

public interface TestUnit extends Remote {
	
	/**
	 * 
	 * @throws RemoteException
	 */
	public abstract void run() throws RemoteException;
	
	/**
	 * 
	 * @param request
	 * @param settings
	 * @throws RemoteException
	 */
	public abstract void setTest(HttpMessageData request, TestCaseSettingsData settings) throws RemoteException;
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public abstract String testConnection() throws RemoteException;
	
	/**
	 * 
	 * @param listener
	 * @throws RemoteException
	 */
	public abstract void addResponseListener(NewResponseListener listener) throws RemoteException;
	
}

 