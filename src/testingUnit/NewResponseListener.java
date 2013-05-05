package testingUnit;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data.HttpMessageData;



public interface NewResponseListener extends Remote {
	public void onNewResponseEvent(HttpMessageData[] data) throws RemoteException;
}
