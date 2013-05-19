package cz.vutbr.fit.dp.xzelin15.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import cz.vutbr.fit.dp.xzelin15.proxyUnit.ProxyMonitoringUnit;
import cz.vutbr.fit.dp.xzelin15.testingUnit.TestUnitImpl;


/**
 * 
 * Class for RMI server 
 * The server is binding the instances of unit classes 
 * to local interface
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class Server {
 
	/**
	 * 
	 * Publish the instances of test and proxy units
	 * 
	 */
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
