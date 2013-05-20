package cz.vutbr.fit.dp.xzelin15.central;


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
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;

import cz.vutbr.fit.dp.xzelin15.data.DataProvider;
import cz.vutbr.fit.dp.xzelin15.data.FaultInjectionData;
import cz.vutbr.fit.dp.xzelin15.data.HttpMessageData;
import cz.vutbr.fit.dp.xzelin15.data.TestCaseSettingsData;
import cz.vutbr.fit.dp.xzelin15.data.TestList;
import cz.vutbr.fit.dp.xzelin15.data.UnitConfiguration;
import cz.vutbr.fit.dp.xzelin15.logging.ConsoleLog;
import cz.vutbr.fit.dp.xzelin15.proxyUnit.ProxyListener;
import cz.vutbr.fit.dp.xzelin15.proxyUnit.ProxyMonitoringUnit;
import cz.vutbr.fit.dp.xzelin15.rmi.ProxyUnit;
import cz.vutbr.fit.dp.xzelin15.rmi.TestUnit;
import cz.vutbr.fit.dp.xzelin15.testingUnit.NewResponseListener;
import cz.vutbr.fit.dp.xzelin15.testingUnit.TestUnitImpl;


public class UnitController {

	
	public static final String PROXIES_CONF_FILE = File.separator +"proxy.ini";
	public static final String TEST_CONF_FILE = File.separator + "tests.ini";
	
	/**
	 * Variable for test units storage
	 */
	private Map<Integer,TestUnit> testUnitsStorage;
	
	/**
	 * Variable for proxy unit storage
	 */
	private Map<Integer,ProxyUnit> proxyUnitsStorage;
	
	/**
	 *  Variable for proxy unit configuration storage
	 */
	private Map <Integer,UnitConfiguration> testUnitConfigurationsStorage;
	
	/**
	 * Variable for test unit configuration storage
	 */
	private Map <Integer,UnitConfiguration> proxyUnitConfigurationsStorage;
	
	/**
	 * Variable for data operations provider
	 */
	private DataProvider ioProvider; 
	
	/**
	 * In constructor are initialized all private variables
	 */
	public UnitController(){
		
		this.testUnitsStorage = new HashMap<Integer,TestUnit>();
		this.proxyUnitsStorage = new HashMap<Integer,ProxyUnit>();
		this.testUnitConfigurationsStorage = new HashMap<Integer,UnitConfiguration>();
		this.proxyUnitConfigurationsStorage = new HashMap<Integer,UnitConfiguration>();
		this.ioProvider = new DataProvider();
		this.ioProvider.createDir(Main.DEFAULT_CONFIG_DIR);
		
	}
	
	/**
	 * This method is exporting test unit configuration.
	 * At first it opens the default file. Then all information about
	 * testing units are collected and write into a INI file.
	 */
	public void exportTestConfiguration(){
		
		Wini confFile = null;
		
		// open file with configuration
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
		
		// collect informations from connected test units
		for(int key : testUnitConfigurationsStorage.keySet()){
			
			UnitConfiguration unit = testUnitConfigurationsStorage.get(key);
			String name = unit.getName();
			String host = unit.getHost();
			int registryPort = unit.getRegistryPort();
			confFile.put(name, "id", key); 
			confFile.put(name, "host", host); 
			confFile.put(name, "registryPort", registryPort); 
		}
		
		// store the collected informations
		try {
			confFile.store();
		} catch (IOException e) {
			
			ConsoleLog.Message(e.getMessage());
		}
	}
	
	/**
	 * This method reads the file with stored configuration
	 * and returns informations to restore connection of test units 
	 * 
	 * @return Array with units configurations
	 */
	public UnitConfiguration[] importTestConfiguration(){
		UnitConfiguration[] unitArray = null;
		
		File iniFile = new File (Main.DEFAULT_CONFIG_DIR + TEST_CONF_FILE);
		
		//open the file
		if(iniFile.exists()){
			Ini ini = new Ini();
			
			try {
				
				ini.load(new FileReader(iniFile));
			} catch (IOException e) {
			
				ConsoleLog.Message(e.getMessage());
			}
			
			//get names of units
			Set<String> unitKeys =  ini.keySet();
			unitArray = new UnitConfiguration[unitKeys.size()];
			
			Iterator<String> it = unitKeys.iterator();
			int i = 0;
			
			//read informations when the name is the location key
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
	 * This method is exporting proxy unit configuration.
	 * At first it opens the default file. Then all information about
	 * proxy units are collected and write into a INI file.
	 */
	public void exportProxyConfiguration(){
		
		Wini confFile = null;
		//open file
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
			
		// collect informations from connected proxy units
		for(int key : proxyUnitConfigurationsStorage.keySet()){
			
			UnitConfiguration unit = proxyUnitConfigurationsStorage.get(key);
			String name = unit.getName();
			String host = unit.getHost();
			int registryPort = unit.getRegistryPort();
			confFile.put(name, "id", key); 
			confFile.put(name, "host", host); 
			confFile.put(name, "registryPort", registryPort); 
			
		}
		
		// store the collected informations
		try {
			confFile.store();
		} catch (IOException e) {
			
			ConsoleLog.Message(e.getMessage());
		}
		
	}
	
	/**
	 * This method reads the file with stored configuration
	 * and returns informations to restore connection of test units 
	 * 
	 * @return Array with units configurations
	 */
	public UnitConfiguration[] importProxyConfiguration(){
		
		UnitConfiguration[] unitArray = null;
		
		File iniFile = new File (Main.DEFAULT_CONFIG_DIR + PROXIES_CONF_FILE);
		
		//open the file
		if(iniFile.exists()){
			Ini ini = new Ini();
			
			try {
				
				ini.load(new FileReader(iniFile));
			} catch (IOException e) {
			
				ConsoleLog.Message(e.getMessage());
			}
			//get names of units
			Set<String> unitKeys =  ini.keySet();
			unitArray = new UnitConfiguration[unitKeys.size()];
			
			
			Iterator<String> it = unitKeys.iterator();
			int i = 0;
			
			//read informations when the name is the location key
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
	 * 	This method is responsible for creating testing unit and store them
	 *  in storage
	 *  
	 * @param key
	 * @param host
	 * @param port
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public void addTestUnit(Integer key,String host, Integer port,String name) throws RemoteException, NotBoundException{
		
		TestUnit newUnit = null;
		
		//request to add local unit
		if(key == 0){
			newUnit = new TestUnitImpl();
			
		//request to add remote unit
		}else{
			Registry registry = LocateRegistry.getRegistry(host,port);
			newUnit = (TestUnit) registry.lookup("TestingUnit");
			UnitConfiguration config = new UnitConfiguration(key, host, port, name);
			testUnitConfigurationsStorage.put(key,config);
			ConsoleLog.Message(newUnit.testConnection());
			
		}
		
		testUnitsStorage.put(key, newUnit);
		
		
		//ioProvider.createDir(FaultInjectionData.outputFolder+File.separator+name);
	}

	/**
	 *  Method set listener for test unit.
	 *  Then the GUI panel is able to receive messages from test units
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
	 * Remove the test unit from storage
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
	 * 	This method is responsible for creating proxy unit and store them
	 *  in storage
	 *  
	 * @param key
	 * @param host
	 * @param port
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public void addProxyUnit(Integer key,String host, Integer port,String name) throws RemoteException, NotBoundException{
		
			ProxyUnit newUnit = null; 
			
			//request to add local unit
			if(key == 0){
				newUnit = new ProxyMonitoringUnit();
				
			//request to add remote unit
			}else{
				ConsoleLog.Print(host+port);
				Registry registry = LocateRegistry.getRegistry(host,port);
				newUnit = (ProxyUnit) registry.lookup("ProxyUnit");
				ConsoleLog.Message(newUnit.testConnection());
				UnitConfiguration config = new UnitConfiguration(key, host, port, name);
				proxyUnitConfigurationsStorage.put(key,config);
			}
			
			
			proxyUnitsStorage.put(key, newUnit);
	}
	
	/**
	 *  Method set listener for test unit.
	 *  Then the GUI panel is able to receive messages from test units
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
	 * Remove the proxy unit from storage
	 * 
	 * @param key
	 */
	public void removeProxyUnit(Integer key){
		
		proxyUnitConfigurationsStorage.remove(key);
		this.proxyUnitsStorage.remove(key);
	}
	
	/**
	 * Returns the proxy unit from storage
	 * @param key
	 * @return
	 */
	public ProxyUnit getProxyUnit(Integer key){
		
		return this.proxyUnitsStorage.get(key);
	}
		
	/**
	 *  The method create instance of TestUnitWorker
	 *  and process the test list on the background of GUI
	 *  
	 * @param path
	 * @param unitId
	 */
	public void runTest(String path, int unitId){
		TestList list = (TestList) ioProvider.readObject(path + TestList.filename);
		
		if( list != null){
			
			TestUnitWorker worker = new TestUnitWorker(list,unitId);
			worker.execute();
			
//			try {
//				worker.get();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}else{
			ConsoleLog.Message("Testlist not found!");
		}
		
		
	}
	
	
	/**
	 *  The method create instance of TestUnitWorker
	 *  and process the test list in CLI
	 *  
	 * @param path
	 * @param unitId
	 */
	public void runTextTest(String path, int unitId){
		TestList list = (TestList) ioProvider.readObject(path + TestList.filename);
		
		if( list != null){
			
			TestUnitWorker worker = new TestUnitWorker(list,unitId);
			worker.execute();
			
			try {
				worker.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			ConsoleLog.Message("Testlist not found!");
		}
		
		
	}
	
	
	
	/**
	 *  The method create instance of TestUnitWorker
	 *  and process the test list on the background of GUI
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
			}else{
				ConsoleLog.Message("The Proxy is not selected in settings!");
			}
		}else{
			ConsoleLog.Message("Test case settings file not found!");
		}
	}
	
		
	/**
	 * Stop the running proxy unit
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
	 * Stop the running test unit while it is in loop strategy
	 * @param unitId
	 */
	public void stopTestUnit(int unitId){
		TestUnit unit = getTestUnit(unitId);
		
		try{
			unit.stopUnit();
		}catch(RemoteException ex){
			ConsoleLog.Message(ex.getMessage());
		}
	}
	
	
	
	/**
	 * Reads the test case settings data from given file
	 * 
	 * @param casePath
	 * @return
	 */
	public TestCaseSettingsData readSettingsData(String casePath){
		
		TestCaseSettingsData data = (TestCaseSettingsData) this.ioProvider.readObject(casePath + TestCaseSettingsData.filename);
		return data;
	}
	
	/**
	 * Reads the test case request data from given file
	 * 
	 * @param casePath
	 * @return
	 */
	public HttpMessageData readRequestData(String casePath){
		
		HttpMessageData data = (HttpMessageData) this.ioProvider.readObject(casePath + HttpMessageData.inputFilename);
		return data;
	}
	
	/**
	 * Reads the test case fault injection data from given file
	 * 
	 * @param casePath
	 * @return
	 */
	public FaultInjectionData readFaultData(String casePath){
		
		FaultInjectionData data = (FaultInjectionData) this.ioProvider.readObject(casePath + FaultInjectionData.filename);
		return data;
	}
	
	/**
	 * Writes the test case settings data to given file
	 * 
	 * @param casePath
	 * @param data
	 */
	public void writeSettingsData(String casePath, TestCaseSettingsData data){
		
		this.ioProvider.writeObject(casePath + TestCaseSettingsData.filename,data);
	}
	
	/**
	 * Writes the test case request data to given file
	 * 
	 * @param casePath
	 * @param data
	 */
	public void writeRequestData(String casePath, HttpMessageData data){
		
		this.ioProvider.writeObject(casePath + HttpMessageData.inputFilename,data);
	}
	
	/**
	 * Writes the test case fault injection data to given file
	 * 
	 * @param casePath
	 * @param data
	 */
	public void writeFaultData(String casePath, FaultInjectionData data){
		
		this.ioProvider.writeObject(casePath + FaultInjectionData.filename,data);
	}
	
	
	/**
	 * Writes the test list data to given file
	 * 
	 * @param casePath
	 * @param data
	 */
	public void writeTestList(String suitePath, TestList data){
		
		this.ioProvider.writeObject(suitePath + TestList.filename,data);
	}
	
	/**
	 * Reads the test list data from given file
	 * 
	 * @param casePath
	 * @return
	 */
	public TestList readTestList(String listPath){
		
		TestList data = (TestList) this.ioProvider.readObject(listPath + TestList.filename);
		return data;
	}
	
	/**
	 * Creates dir in given path
	 * @param path
	 */
	public void createDir(String path){
		ioProvider.createDir(path);
	}
	
	/**
	 * Class represents worker thread to process test units jobs
	 * on background of GUI
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	private class TestUnitWorker extends SwingWorker<String,Void>{
		
		private int id;
		private TestList list;
		TestUnitWorker(TestList list,int unitId){
			
			this.list = list;
			this.id = unitId;
		}
		
		@Override
		public String doInBackground(){
			
			ArrayList<String> testCases = list.getTestCases();
			
			TestUnit unit = getTestUnit(this.id);
			
			if(unit != null){
				try{
					for(String casePath: testCases){
						
						TestCaseSettingsData settings = (TestCaseSettingsData) ioProvider.readObject(casePath+TestCaseSettingsData.filename);
						if(settings != null){
							
							//in the settings is set flag to run test unit
							if(settings.getRun()){
								HttpMessageData request = (HttpMessageData) ioProvider.readObject(casePath+HttpMessageData.inputFilename);
								
								if(request != null){
									
									ConsoleLog.Message (unit.testConnection());
									unit.setTest(request,settings);
									unit.run();
							
								}else{
									ConsoleLog.Message("Http request file not found!");
								}
							}
						}else{
							ConsoleLog.Message("Test case settings file not found!");
						}
					}
					
				}catch(RemoteException ex){
					ConsoleLog.Message(ex.getMessage());
				}
			}else{
				ConsoleLog.Message("Test unit not found!");
			}
			return "";
			
		}
		
		
	}
	
	
	/**
	 * Class represents worker thread to process proxy units jobs
	 * on background of GUI
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
