package cz.vutbr.fit.dp.xzelin15.testingUnit;

import java.rmi.Remote;
import java.rmi.RemoteException;

import cz.vutbr.fit.dp.xzelin15.data.HttpMessageData;




public interface NewResponseListener extends Remote {
	public void onNewResponseEvent(HttpMessageData[] data, int period) throws RemoteException;
}
