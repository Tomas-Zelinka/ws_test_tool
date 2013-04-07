package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {
   
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1475892992239551357L;

	public HelloImpl() throws RemoteException {
    }
 
    public String greeting() throws RemoteException {
        return "greeting";
    }
 
}
