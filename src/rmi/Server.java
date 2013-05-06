package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import testingUnit.TestingUnitImpl;
 
public class Server {
 
   public void run()  {
	   try{
    		Registry registry = LocateRegistry.createRegistry(1099);
    		TestingUnitImpl testUnit = new TestingUnitImpl();
	        registry.rebind("TestingUnit", testUnit);
	        System.out.println("server.RMI Server is ready.");
    	}catch(RemoteException e){
    		e.printStackTrace();
    	}
    	
    }
}
