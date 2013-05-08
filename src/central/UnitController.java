package central;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingWorker;

import logging.ConsoleLog;
import proxyUnit.ProxyMonitoringUnit;
import proxyUnit.ProxyPanelListener;
import rmi.ProxyUnit;
import rmi.TestUnit;
import testingUnit.NewResponseListener;
import testingUnit.TestUnitImpl;
import data.DataProvider;
import data.FaultInjectionData;
import data.HttpMessageData;
import data.TestCaseSettingsData;
import data.TestList;

public class UnitController {

	
	private Map<Integer,TestUnit> testUnitsStorage;
	private Map<Integer,ProxyUnit> proxyUnitsStorage;
	private DataProvider ioProvider; 
	
	 
	/**
	 * 
	 */
	public UnitController(){
		this.testUnitsStorage = new HashMap<Integer,TestUnit>();
		this.proxyUnitsStorage = new HashMap<Integer,ProxyUnit>();
		this.ioProvider = new DataProvider();
			
	}
	/**
	 * 	
	 * @param key
	 * @param host
	 * @param port
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public void addTestUnit(Integer key,String host, int port) throws RemoteException, NotBoundException{
		TestUnit newUnit = null;
		if(key == 0){
			newUnit = new TestUnitImpl();
		}else{
			Registry registry = LocateRegistry.getRegistry(host,port);
			newUnit = (TestUnit) registry.lookup("TestingUnit");
			ConsoleLog.Message(newUnit.testConnection());
		}
		testUnitsStorage.put(key, newUnit);
	}

	/**
	 * 
	 * @param listener
	 * @param unitId
	 * @throws RemoteException
	 */
	public void setResponseListener(NewResponseListener listener, int unitId) throws RemoteException{
		
		TestUnit unit = getTestUnit(unitId);
		unit.addResponseListener(listener); 
	}
	
	/**
	 * 
	 * @param key
	 */
	public void removeTestUnit(Integer key){
		this.testUnitsStorage.remove(key);
	
	}

	/**
	 * 
	 * @return
	 */
	public TestUnit getTestUnit(int key){
		return this.testUnitsStorage.get(key);
	}
	
	/**
	 * 
	 * @param key
	 * @param host
	 * @param port
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public void addProxyUnit(Integer key,String host, int port) throws RemoteException, NotBoundException{
		
			ProxyUnit newUnit =null; 
			if(key == 0){
				newUnit = new ProxyMonitoringUnit();
			}else{
				Registry registry = LocateRegistry.getRegistry(host,port);
				newUnit = (ProxyUnit) registry.lookup("ProxyUnit");
				ConsoleLog.Message(newUnit.testConnection());
			}
			proxyUnitsStorage.put(key, newUnit);
		
	}
	
	/**
	 * 
	 * @param listener
	 * @param unitId
	 * @throws RemoteException
	 */
	public void setPanelListener(ProxyPanelListener listener,int unitId) throws RemoteException{
		ProxyUnit unit = getProxyUnit(unitId);
		unit.setPanelListener(listener); 
		
	}
	
	/**
	 * 
	 * @param key
	 */
	public void removeProxyUnit(Integer key){
		this.proxyUnitsStorage.remove(key);
	
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public ProxyUnit getProxyUnit(Integer key){
		
		return this.proxyUnitsStorage.get(key);
	}
		
	/**
	 * 
	 * @param path
	 * @param unitId
	 */
	public void runTest(String path, int unitId){
		
		TestList list = (TestList) ioProvider.readObject(path);
		if( list != null){
		
			HashMap<Integer,String> testCases = list.getTestCases();
			Object[] keys = testCases.keySet().toArray();
			
			for(int i =0; i < keys.length; i++){
				String casePath = testCases.get(keys[i]);
				
				TestCaseSettingsData settings = (TestCaseSettingsData) ioProvider.readObject(casePath+TestCaseSettingsData.filename);
				if(settings != null)
				{
					if(settings.getUseProxy()){
						FaultInjectionData fault = (FaultInjectionData) ioProvider.readObject(casePath+FaultInjectionData.filename);
						ProxyUnitWorker proxyUnit = new ProxyUnitWorker(fault,settings,unitId);
						proxyUnit.execute();
					}
					
					
					if(settings.getRun()){
						HttpMessageData request = (HttpMessageData) ioProvider.readObject(casePath+HttpMessageData.filename);
						TestUnitWorker testUnit = new TestUnitWorker(request,settings,unitId);
						testUnit.execute();
					}
				}else{
					ConsoleLog.Message("Test case settings file not found!");
				}
			}
		}else{
			ConsoleLog.Message("Testlist not found!");
		}
		
	}
	
	
	/**
	 * 
	 * @param path
	 * @param unitId
	 */
	public void runProxy(String path, int unitId){
		
		String casePath = path;
		
		TestCaseSettingsData settings = (TestCaseSettingsData) ioProvider.readObject(casePath+TestCaseSettingsData.filename);
		if(settings != null)
		{
			if(settings.getUseProxy()){
				FaultInjectionData fault = (FaultInjectionData) ioProvider.readObject(casePath+FaultInjectionData.filename);
				ProxyUnitWorker proxyUnit = new ProxyUnitWorker(fault,settings,unitId);
				proxyUnit.execute();
			}
		}else{
			ConsoleLog.Message("Test case settings file not found!");
		}
	}
	
	
	public void stopProxy(int unitId){
		ProxyUnit unit = getProxyUnit(unitId);
		
		try{
			unit.stopProxy();
		}catch(RemoteException ex){
			ConsoleLog.Message(ex.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	private class TestUnitWorker extends SwingWorker<String,Void>{
		
		private HttpMessageData data;
		private TestCaseSettingsData settings;
		private int id;
		
		
		
		TestUnitWorker(HttpMessageData data,TestCaseSettingsData settings, int unitId){
			this.data = data;
			this.settings = settings;
			this.id = unitId;
		}
		
		public String doInBackground(){
			
			
			TestUnit unit = getTestUnit(this.id);
			ProxyUnit proxyUnit = getProxyUnit(this.id);
			try{
				
				ConsoleLog.Message (unit.testConnection());
				unit.setTest(data,settings);
				unit.run();
				proxyUnit.stopProxy();
			}catch(RemoteException ex){
				ConsoleLog.Message(ex.getMessage());
			}
			
			
			
			return "";
		}
	}
	
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	private class ProxyUnitWorker extends SwingWorker<String,Void>{
		
		
		private int id;
		private FaultInjectionData activeTest;
		private TestCaseSettingsData settings;
		
		ProxyUnitWorker(FaultInjectionData data,TestCaseSettingsData settings, int unitId){
			this.settings = settings;
			this.activeTest = data;
			this.id = unitId;
		}
		
		public String doInBackground(){
			
			ProxyUnit unit = getProxyUnit(this.id);
			try{
				unit.setProxyHost(settings.getProxyHost());
				unit.setProxyPort(settings.getProxyPort());
				unit.setTestedWsPort(settings.getProxyTestedPort());
				unit.setActiveTest(activeTest);
				ConsoleLog.Print("[Controller] Proxy start");
				unit.run();
				
			}catch(RemoteException ex){
				ConsoleLog.Message(ex.getMessage());
			}
			return "";
		}
	}
		
}
