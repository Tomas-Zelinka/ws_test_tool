package central;

import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingWorker;

import logging.ConsoleLog;
import proxyUnit.HttpInteraction;
import proxyUnit.NewMessageListener;
import proxyUnit.ProxyMonitoringUnit;
import proxyUnit.RemoteProxyUnit;
import proxyUnit.UnknownHostListener;
import testingUnit.LocalTestUnit;
import testingUnit.NewResponseListener;
import testingUnit.RemoteTestUnit;
import data.DataProvider;
import data.FaultInjectionData;
import data.HttpMessageData;
import data.TestCaseSettingsData;
import data.TestList;

public class UnitController {

	
	private Map<Integer,RemoteTestUnit> testUnitsStorage;
	private Map<Integer,RemoteProxyUnit> proxyUnitsStorage;
	private LocalTestUnit localTestUnit;
	private ProxyMonitoringUnit localProxyUnit; 
	private DataProvider ioProvider;
	
	
	/**
	 * 
	 */
	public UnitController(){
		this.testUnitsStorage = new HashMap<Integer,RemoteTestUnit>();
		this.proxyUnitsStorage = new HashMap<Integer,RemoteProxyUnit>();
		this.ioProvider = new DataProvider();
	}
	

	/**
	 * 
	 * @param key
	 */
	public void addRemoteTestUnit(Integer key){
		RemoteTestUnit newRemoteUnit = new RemoteTestUnit(); 
		testUnitsStorage.put(key, newRemoteUnit);
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
	public void addLocalTestUnit(){
		this.localTestUnit = new LocalTestUnit(); 
		
	}
	
	/**
	 * 
	 */
	public void addLocalProxyUnit(){
		this.localProxyUnit = new ProxyMonitoringUnit(); 
		
	}
	
	public void setResponseListener(NewResponseListener listener){
		this.localTestUnit.addResponseListener(listener);
	}
	
	public void setNewMessageListener(NewMessageListener listener){
		this.localProxyUnit.addNewMessageListener(listener);
	}
	
	public void setUnknownHostListener(UnknownHostListener listener){
		this.localProxyUnit.addUnknownHostListener(listener);
	}
	
	
	public FaultInjectionData getProxyActiveTest(){
		return this.localProxyUnit.getActiveTest();
	}
	
	public Map<Integer, HttpInteraction> getInteractionMap(){
		return this.localProxyUnit.getInteractionMap();
	}
	/**
	 * 
	 * @param key
	 */
	public void removeRemoteTestUnit(Integer key){
		this.testUnitsStorage.remove(key);
	
	}
	
	/**
	 * 
	 * @param key
	 */
	public void removeRemoteProxyUnit(Integer key){
		this.proxyUnitsStorage.remove(key);
	
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public RemoteTestUnit getRemoteTestUnit(Integer key){
		
		return this.testUnitsStorage.get(key);
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
	 * @return
	 */
	public LocalTestUnit getLocalTestUnit(){
		return this.localTestUnit;
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
			if(id == 0){
				localTestUnit.setTest(data,settings);
				localTestUnit.run();
				localProxyUnit.stopProxy();
			}else{
				
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
			if(id == 0){
				localProxyUnit.setProxyHost(settings.getProxyHost());
				localProxyUnit.setProxyPort(settings.getProxyPort());
				localProxyUnit.setTestedWsPort(settings.getProxyTestedPort());
				localProxyUnit.setActiveTest(activeTest);		
				localProxyUnit.run();
				
			}else{
				ConsoleLog.Print("Remote jednotka");
			}
			return "";
		}
	}
		
}
