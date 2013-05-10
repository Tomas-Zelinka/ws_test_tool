package central;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingWorker;

import logging.ConsoleLog;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;

import proxyUnit.ProxyListener;
import proxyUnit.ProxyMonitoringUnit;
import rmi.ProxyUnit;
import rmi.TestUnit;
import testingUnit.NewResponseListener;
import testingUnit.TestUnitImpl;
import data.DataProvider;
import data.FaultInjectionData;
import data.HttpMessageData;
import data.TestCaseSettingsData;
import data.TestList;
import data.UnitConfiguration;

public class UnitController {

	
	public static final String PROXIES_CONF_FILE = File.separator +"proxy.ini";
	public static final String TEST_CONF_FILE = File.separator + "tests.ini";
	
	/**
	 * 
	 */
	private Map<Integer,TestUnit> testUnitsStorage;
	
	/**
	 * 
	 */
	private Map<Integer,ProxyUnit> proxyUnitsStorage;
	
	/**
	 * 
	 */
	private Map <Integer,UnitConfiguration> testUnitConfigurationsStorage;
	
	/**
	 * 
	 */
	private Map <Integer,UnitConfiguration> proxyUnitConfigurationsStorage;
	
	/**
	 * 
	 */
	private DataProvider ioProvider; 
	
	/**
	 * 
	 */
	public UnitController(){
		
		this.testUnitsStorage = new HashMap<Integer,TestUnit>();
		this.proxyUnitsStorage = new HashMap<Integer,ProxyUnit>();
		this.testUnitConfigurationsStorage = new HashMap<Integer,UnitConfiguration>();
		this.proxyUnitConfigurationsStorage = new HashMap<Integer,UnitConfiguration>();
		this.ioProvider = new DataProvider();
		this.ioProvider.createDir(Main.DEFAULT_CONFIG_DIR);
		
	}
	
	public void exportTestConfiguration(){
		
		Wini confFile = null;
		try {
			File iniFile = new File (Main.DEFAULT_CONFIG_DIR + TEST_CONF_FILE);
			if(!iniFile.exists()){
				iniFile.createNewFile();
			}
			confFile = new Wini(iniFile);
		
		} catch (InvalidFileFormatException e) {
			
			ConsoleLog.Message(e.getMessage());
		} catch (IOException e) {
			
			ConsoleLog.Message(e.getMessage());
		}
		
		
		for(int key : testUnitConfigurationsStorage.keySet()){
			
			UnitConfiguration unit = testUnitConfigurationsStorage.get(key);
			String name = unit.getName();
			String host = unit.getHost();
			int registryPort = unit.getRegistryPort();
			confFile.put(name, "id", key); 
			confFile.put(name, "host", host); 
			confFile.put(name, "registryPort", registryPort); 
		}
		
		try {
			confFile.store();
		} catch (IOException e) {
			
			ConsoleLog.Message(e.getMessage());
		}
	}
	
	public UnitConfiguration[] importTestConfiguration(){
		UnitConfiguration[] unitArray = null;
		
		File iniFile = new File (Main.DEFAULT_CONFIG_DIR + TEST_CONF_FILE);
		
		if(iniFile.exists()){
			Ini ini = new Ini();
			
			try {
				
				ini.load(new FileReader(iniFile));
			} catch (IOException e) {
			
				ConsoleLog.Message(e.getMessage());
			}
			
			Set<String> unitKeys =  ini.keySet();
			unitArray = new UnitConfiguration[unitKeys.size()];
			
			Iterator<String> it = unitKeys.iterator();
			int i = 0;
			while(it.hasNext()){
				
				String unitKey = it.next();
				Section unit = ini.get(unitKey);  
				String host = unit.get("host");
				Integer registryPort = Integer.parseInt(unit.get("registryPort"));
				Integer id = Integer.parseInt(unit.get("id"));
				UnitConfiguration unitConfig = new UnitConfiguration(id,host,registryPort,unitKey);
				unitArray[i] = unitConfig;
				i++;
			}
		}else{
			ConsoleLog.Message("File "+iniFile.getPath() + " not found");
		}
		
		return unitArray;
	}
	
	public void exportProxyConfiguration(){
		
		Wini confFile = null;
		try {
			File iniFile = new File (Main.DEFAULT_CONFIG_DIR + PROXIES_CONF_FILE);
			if(!iniFile.exists()){
				iniFile.createNewFile();
			}
			confFile = new Wini(iniFile);
		
		} catch (InvalidFileFormatException e) {
			
			ConsoleLog.Message(e.getMessage());
		} catch (IOException e) {
			
			ConsoleLog.Message(e.getMessage());
		}
			
		for(int key : proxyUnitConfigurationsStorage.keySet()){
			
			UnitConfiguration unit = proxyUnitConfigurationsStorage.get(key);
			String name = unit.getName();
			String host = unit.getHost();
			int registryPort = unit.getRegistryPort();
			confFile.put(name, "id", key); 
			confFile.put(name, "host", host); 
			confFile.put(name, "registryPort", registryPort); 
			
		}
		
		try {
			confFile.store();
		} catch (IOException e) {
			
			ConsoleLog.Message(e.getMessage());
		}
		
	}
	
	public UnitConfiguration[] importProxyConfiguration(){
		
		UnitConfiguration[] unitArray = null;
		
		File iniFile = new File (Main.DEFAULT_CONFIG_DIR + PROXIES_CONF_FILE);
		
		if(iniFile.exists()){
			Ini ini = new Ini();
			
			try {
				
				ini.load(new FileReader(iniFile));
			} catch (IOException e) {
			
				ConsoleLog.Message(e.getMessage());
			}
			
			Set<String> unitKeys =  ini.keySet();
			unitArray = new UnitConfiguration[unitKeys.size()];
			
			Iterator<String> it = unitKeys.iterator();
			int i = 0;
			while(it.hasNext()){
				
				String unitKey = it.next();
				Section unit = ini.get(unitKey);  
				String host = unit.get("host");
				Integer registryPort = Integer.parseInt(unit.get("registryPort"));
				Integer id = Integer.parseInt(unit.get("id"));
				UnitConfiguration unitConfig = new UnitConfiguration(id,host,registryPort,unitKey);
				unitArray[i] = unitConfig;
				i++;
			}
		}else{
			ConsoleLog.Message("File "+iniFile.getPath() + " not found");
		}
		
		return unitArray;
	}
	
	/**
	 * 	
	 * @param key
	 * @param host
	 * @param port
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public void addTestUnit(Integer key,String host, Integer port,String name) throws RemoteException, NotBoundException{
		
		TestUnit newUnit = null;
		
		if(key == 0){
			newUnit = new TestUnitImpl();
		}else{
			Registry registry = LocateRegistry.getRegistry(host,port);
			newUnit = (TestUnit) registry.lookup("TestingUnit");
			UnitConfiguration config = new UnitConfiguration(key, host, port, name);
			testUnitConfigurationsStorage.put(key,config);
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
		
		testUnitConfigurationsStorage.remove(key);
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
	public void addProxyUnit(Integer key,String host, Integer port,String name) throws RemoteException, NotBoundException{
		
			ProxyUnit newUnit =null; 
			if(key == 0){
				newUnit = new ProxyMonitoringUnit();
			}else{
				Registry registry = LocateRegistry.getRegistry(host,port);
				newUnit = (ProxyUnit) registry.lookup("ProxyUnit");
				ConsoleLog.Message(newUnit.testConnection());
				UnitConfiguration config = new UnitConfiguration(key, host, port, name);
				proxyUnitConfigurationsStorage.put(key,config);
			}
			
			
			proxyUnitsStorage.put(key, newUnit);
		
	}
	
	/**
	 * 
	 * @param listener
	 * @param unitId
	 * @throws RemoteException
	 */
	public void setPanelListener(ProxyListener listener,int unitId) throws RemoteException{
		
		ProxyUnit unit = getProxyUnit(unitId);
		unit.setPanelListener(listener); 
	}
	
	/**
	 * 
	 * @param key
	 */
	public void removeProxyUnit(Integer key){
		
		proxyUnitConfigurationsStorage.remove(key);
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
		
		TestList list = (TestList) ioProvider.readObject(path + TestList.filename);
		if( list != null){
		
			ArrayList<String> testCases = list.getTestCases();
			
			
			for(String casePath: testCases){
				
				TestCaseSettingsData settings = (TestCaseSettingsData) ioProvider.readObject(casePath+TestCaseSettingsData.filename);
				if(settings != null)
				{
					if(settings.getUseProxy()){
						
						FaultInjectionData fault = (FaultInjectionData) ioProvider.readObject(casePath+FaultInjectionData.filename);
						if(fault != null){
							
							ProxyUnitWorker proxyUnit = new ProxyUnitWorker(fault,settings,unitId);
							proxyUnit.execute();
						}else{
							ConsoleLog.Message("Fault Injeciton file not found!");
						}
					}
					
					
					if(settings.getRun()){
						HttpMessageData request = (HttpMessageData) ioProvider.readObject(casePath+HttpMessageData.filename);
						
						if(request != null){
							TestUnitWorker testUnit = new TestUnitWorker(request,settings,unitId);
							testUnit.execute();
						}else{
							ConsoleLog.Message("Http request file not found!");
						}
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
		
		if(settings != null){
			
			if(settings.getUseProxy()){
				FaultInjectionData fault = (FaultInjectionData) ioProvider.readObject(casePath+FaultInjectionData.filename);
				
				if(fault != null){
					ProxyUnitWorker proxyUnit = new ProxyUnitWorker(fault,settings,unitId);
					proxyUnit.execute();
				}else{
					ConsoleLog.Message("Fault Injeciton file not found!");
				}
			}
		}else{
			ConsoleLog.Message("Test case settings file not found!");
		}
	}
	
		
	/**
	 * 
	 * @param unitId
	 */
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
	 * @param casePath
	 * @return
	 */
	public TestCaseSettingsData readSettingsData(String casePath){
		
		TestCaseSettingsData data = (TestCaseSettingsData) this.ioProvider.readObject(casePath + TestCaseSettingsData.filename);
		return data;
	}
	
	/**
	 * 
	 * @param casePath
	 * @return
	 */
	public HttpMessageData readRequestData(String casePath){
		
		HttpMessageData data = (HttpMessageData) this.ioProvider.readObject(casePath + HttpMessageData.filename);
		return data;
	}
	
	/**
	 * 
	 * @param casePath
	 * @return
	 */
	public FaultInjectionData readFaultData(String casePath){
		
		FaultInjectionData data = (FaultInjectionData) this.ioProvider.readObject(casePath + FaultInjectionData.filename);
		return data;
	}
	
	/**
	 * 
	 * @param casePath
	 * @param data
	 */
	public void writeSettingsData(String casePath, TestCaseSettingsData data){
		
		this.ioProvider.writeObject(casePath + TestCaseSettingsData.filename,data);
	}
	
	/**
	 * 
	 * @param casePath
	 * @param data
	 */
	public void writeRequestData(String casePath, HttpMessageData data){
		
		this.ioProvider.writeObject(casePath + HttpMessageData.filename,data);
	}
	
	/**
	 * 
	 * @param casePath
	 * @param data
	 */
	public void writeFaultData(String casePath, FaultInjectionData data){
		
		this.ioProvider.writeObject(casePath + FaultInjectionData.filename,data);
	}
	
	
	/**
	 * 
	 * @param casePath
	 * @param data
	 */
	public void writeTestList(String suitePath, TestList data){
		
		this.ioProvider.writeObject(suitePath + TestList.filename,data);
	}
	
	/**
	 * 
	 * @param casePath
	 * @return
	 */
	public TestList readTestList(String listPath){
		
		TestList data = (TestList) this.ioProvider.readObject(listPath + TestList.filename);
		return data;
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
