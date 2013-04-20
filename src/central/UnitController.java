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
import data.FaultInjectionData;

public class UnitController {

	
	private Map<Integer,RemoteTestUnit> testUnitsStorage;
	private Map<Integer,RemoteProxyUnit> proxyUnitsStorage;
	private LocalTestUnit localTestUnit;
	private ProxyMonitoringUnit localProxyUnit; 
	
	
	/**
	 * 
	 */
	public UnitController(){
		this.testUnitsStorage = new HashMap<Integer,RemoteTestUnit>();
		this.proxyUnitsStorage = new HashMap<Integer,RemoteProxyUnit>();
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
		
		
		
		
		TestUnitWorker testUnit = new TestUnitWorker(path,unitId);
		ProxyUnitWorker proxyUnit = new ProxyUnitWorker(path,unitId);
		testUnit.execute();
		proxyUnit.execute();
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
		
		private String path;
		private int id;
		
		
		TestUnitWorker(String path, int unitId){
			this.path = path;
			this.id = unitId;
		}
		
		public String doInBackground(){
			if(id == 0){
				
					localProxyUnit.run();
				
				localTestUnit.setTestList(path);
				localTestUnit.run();
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
		
		private String path;
		private int id;
		
		
		ProxyUnitWorker(String path, int unitId){
			this.path = path;
			this.id = unitId;
		}
		
		public String doInBackground(){
			if(id == 0){
				localProxyUnit.run();
			}else{
				
			}
			return "";
		}
	}
		
}
