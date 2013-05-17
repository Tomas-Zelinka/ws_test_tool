package cli;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import testingUnit.NewResponseListener;
import data.HttpMessageData;

/**
 *	Class responsible for connecting the proxy unit with text output instance
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class TestTextListener extends UnicastRemoteObject implements NewResponseListener{

	transient PrintTestOutput output;
	private static final long serialVersionUID = 8422078277925072138L;
	
	/**
	 * 
	 * @param output
	 * @throws RemoteException
	 */
	public TestTextListener(PrintTestOutput output) throws RemoteException{
		this.output = output;
	}
	
	/**
	 * Delegation method 
	 */
	public void onNewResponseEvent(HttpMessageData[] data,int period){
		this.output.onNewResponseEvent(data,period);
	}

	
}
