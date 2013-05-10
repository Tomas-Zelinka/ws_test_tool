package cli;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import testingUnit.NewResponseListener;
import data.HttpMessageData;

public class TestTextListener extends UnicastRemoteObject implements NewResponseListener{

	transient PrintTestOutput output;
	private static final long serialVersionUID = 8422078277925072138L;
	
	
	public TestTextListener(PrintTestOutput output) throws RemoteException{
		this.output = output;
	}
	
	public void onNewResponseEvent(HttpMessageData[] data){
		this.output.onNewResponseEvent(data);
	}

	
}
