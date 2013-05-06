package central;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingWorker;

import logging.ConsoleLog;
import proxyUnit.HttpInteraction;
import proxyUnit.NewMessageListener;
import proxyUnit.ProxyMonitoringUnit;
import proxyUnit.RemoteProxyUnit;
import proxyUnit.UnknownHostListener;
import testingUnit.NewResponseListener;
import testingUnit.TestingUnit;
import testingUnit.TestingUnitImpl;
import data.DataProvider;
import data.FaultInjectionData;
import data.HttpMessageData;
import data.TestCaseSettingsData;
import data.TestList;

public class UnitController {

	
	private Map<Integer,TestingUnit> testUnitsStorage;
	private Map<Integer,RemoteProxyUnit> proxyUnitsStorage;
	private ProxyMonitoringUnit localProxyUnit; 
	private DataProvider ioProvider; 
	
	 
	/**
	 * 
	 */
	public UnitController(){
		this.testUnitsStorage = new HashMap<Integer,TestingUnit>();
		this.proxyUnitsStorage = new HashMap<Integer,RemoteProxyUnit>();
		this.ioProvider = new DataProvider();
			
	}
	

	
	
	public void addTestUnit(Integer key,String host, int port) throws RemoteException, NotBoundException{
		TestingUnit newUnit = null;
		if(key == 0){
			newUnit = new TestingUnitImpl();
		}else{
			Registry registry = LocateRegistry.getRegistry(host,port);
			newUnit = (TestingUnit) registry.lookup("TestingUnit");
			ConsoleLog.Message(newUnit.testConnection());
		}
		testUnitsStorage.put(key, newUnit);
	}

	
	
	
	public void setResponseListener(NewResponseListener listener, int unitId) throws RemoteException{
		
		TestingUnit unit = getTestUnit(unitId);
		unit.addResponseListener(listener); 
	}
	
	public void setNewMessageListener(NewMessageListener listener){
		this.localProxyUnit.addNewMessageListener(listener);
	}
	
	public void setUnknownHostListener(UnknownHostListener listener){
		this.localProxyUnit.addUnknownHostListener(listener);
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
	public TestingUnit getTestUnit(int key){
		return this.testUnitsStorage.get(key);
	}
	
	/**
	 * 
	 * @param key
	 */
	public void addRemoteProxyUnit(Integer key){
		
	
		RemoteProxyUnit newRemoteUnit = new RemoteProxyUnit(); 
		proxyUnitsStorage.put(key, newRemoteUnit);
	}
	
	/**
	 * 
	 */
	public void addLocalProxyUnit(){
		this.localProxyUnit = new ProxyMonitoringUnit(); 
		
	}
	
	/**
	 * 
	 * @return
	 */
	public ProxyMonitoringUnit getLocalProxyUnit(){
		return this.localProxyUnit;
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public RemoteProxyUnit getRemoteProxyUnit(Integer key){
		
		return this.proxyUnitsStorage.get(key);
	}
	
	
	/**
	 * 
	 * @param key
	 */
	public void removeRemoteProxyUnit(Integer key){
		this.proxyUnitsStorage.remove(key);
	
	}

	public FaultInjectionData getProxyActiveTest(){
		return this.localProxyUnit.getActiveTest();
	}
	
	public Map<Integer, HttpInteraction> getInteractionMap(){
		return this.localProxyUnit.getInteractionMap();
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
	 */
	public void runTestOnAllUnits(){
		
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
			
			
			TestingUnit unit = getTestUnit(this.id);
			try{
				
				ConsoleLog.Message (unit.testConnection());
				unit.setTest(data,settings);
				unit.run();
			}catch(RemoteException ex){
				ConsoleLog.Message(ex.getMessage());
			}
			
			//unit.stopProxy();
			
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
			if(id == 0){
				localProxyUnit.setProxyHost(settings.getProxyHost());
				localProxyUnit.setProxyPort(settings.getProxyPort());
				localProxyUnit.setTestedWsPort(settings.getProxyTestedPort());
				localProxyUnit.setActiveTest(activeTest);
				ConsoleLog.Print("[Controller] Proxy start");
				localProxyUnit.run();
				
			}else{
				ConsoleLog.Print("[Controller] Remote jednotka");
			}
			return "";
		}
	}
		
}
