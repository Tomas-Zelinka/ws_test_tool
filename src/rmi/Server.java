package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import proxyUnit.ProxyMonitoringUnit;
import testingUnit.TestUnitImpl;
 
public class Server {
 
   public void run()  {
	   try{
    		Registry registry = LocateRegistry.createRegistry(1099);
    		TestUnitImpl testUnit = new TestUnitImpl();
    		ProxyMonitoringUnit proxyUnit= new ProxyMonitoringUnit();
	        registry.rebind("TestingUnit", testUnit);
	        registry.rebind("ProxyUnit", proxyUnit);
	        System.out.println("server.RMI Server is ready.");
    	}catch(RemoteException e){
    		e.printStackTrace();
    	}
    	
    }
}
