package testingUnit;


import java.rmi.Remote;
import java.rmi.RemoteException;

import data.HttpMessageData;
import data.TestCaseSettingsData;

public interface TestingUnit extends Remote {
	
	public abstract void run() throws RemoteException;
	
	public abstract void setTest(HttpMessageData request, TestCaseSettingsData settings) throws RemoteException;
	
	public abstract String testConnection() throws RemoteException;
	
	public abstract void addResponseListener(NewResponseListener listener) throws RemoteException;
	
}

 