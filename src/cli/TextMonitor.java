package cli;

import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;

import logging.ConsoleLog;
import proxyUnit.ProxyListener;
import testingUnit.NewResponseListener;
import central.UnitController;
import data.FaultInjectionData;
import data.UnitConfiguration;

/**
 * Class is responsible for receiving data from test unit 
 * and prints it into a file. 
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class TextMonitor {

	/**
	 *  Local test unit name
	 */
	private final String TEST_LOCAL_NAME = "Local Test";
	
	/**
	 *  Remote test unit name
	 */
	private final String PROXY_LOCAL_NAME = "Local Proxy ";
	
	/**
	 * Local unit key
	 */
	private final int LOCAL_UNIT = 0;
	
	/**
	 * Unit controller
	 */
	private UnitController controller;
	
	/**
	 * Number of stored test units
	 */
	private int testUnitCounter;
	
	/**
	 * Number of stored proxy units
	 */
	private int proxyUnitCounter;
	
	/**
	 *  Proxy output printer storage
	 */
	private HashMap <Integer,PrintProxyOutput> proxyOutputs;
	
	/**
	 * Test output printer storage
	 */
	private HashMap <Integer,PrintTestOutput> testOutputs;
	
	
	public TextMonitor(UnitController controller){
		this.controller = controller;
		testUnitCounter = 0;
		proxyUnitCounter = 0;
		proxyOutputs = new HashMap<Integer,PrintProxyOutput>();
		testOutputs = new HashMap<Integer,PrintTestOutput>();
		addTestUnit("none",LOCAL_UNIT,TEST_LOCAL_NAME);
		addProxyUnit("none",LOCAL_UNIT,PROXY_LOCAL_NAME);
	}

	/**
	 * Get the local unit and order run the test list
	 * on local unit
	 * 
	 * @param casePath
	 */
	public void runTestList(String suitePath){
		
		ConsoleLog.Print("[TextMonitor]: Test:" + suitePath);
		PrintTestOutput output = testOutputs.get(LOCAL_UNIT);
		output.setOutputPath(suitePath,LOCAL_UNIT);
		controller.runTest(suitePath, LOCAL_UNIT);
		
	}
	
	/**
	 *  Get the remote unit and order run the test list
	 * on that unit
	 * @param casePath
	 * @param config
	 */
	public void runTestList(String suitePath,String config){
		ConsoleLog.Print("[TextMonitor]: Test+config:" + suitePath);
		importTestConfiguration();
		
		for(int i = 0; i < testUnitCounter; i++){
			PrintTestOutput output = testOutputs.get(i);
			output.setOutputPath(suitePath,i);
			controller.runTest(suitePath, i);
		}
		
	}
	
	
	/**
	 *  Get the local proxy unit and order run the test list
	 *  on local proxy unit
	 *  
	 * @param casePath
	 */
	public void runProxy(String casePath){
		
		ConsoleLog.Print("[TextMonitor]: Proxy:" + casePath);
		PrintProxyOutput output = proxyOutputs.get(LOCAL_UNIT);
		output.setOutputPath(casePath+FaultInjectionData.outputFolder+File.separator+LOCAL_UNIT); 
		ConsoleLog.Print("[TextMonitor]: Proxy created :" + casePath+FaultInjectionData.outputFolder+File.separator+LOCAL_UNIT);
		
		controller.runProxy(casePath, LOCAL_UNIT);
	}
	
	/**
	 *  Get the remote proxy unit and order run the test list
	 *  on that proxy unit
	 *  
	 * @param casePath
	 * @param config
	 */
	public void runProxy(String casePath,String config){
		
		ConsoleLog.Print("[TextMonitor]: Proxy+config:" + casePath);
		importProxyConfiguration();
		ConsoleLog.Print("[TextMonitor]: Proxy+config: imported");
		for(int i = 0; i < proxyUnitCounter; i++){
			PrintProxyOutput output = proxyOutputs.get(LOCAL_UNIT);
			ConsoleLog.Print("[TextMonitor]: Proxy created :" + casePath+FaultInjectionData.outputFolder+File.separator+i);
			
			controller.createDir(casePath+FaultInjectionData.outputFolder+File.separator+i);
			output.setOutputPath(casePath+FaultInjectionData.outputFolder+File.separator+i); 
			
			controller.runProxy(casePath, i);
		}
	}
	
	
	
	/**
	 * Get configuration of test units and order the creation
	 * and store them to future use
	 * 
	 */
	private void importTestConfiguration(){
		
		UnitConfiguration[] configArray = controller.importTestConfiguration();
		
		if(configArray != null){
					
			for(UnitConfiguration config : configArray){
				addTestUnit(config.getHost(),config.getRegistryPort(),config.getName());
			}
		}else{
			ConsoleLog.Message("Found any configurations");
		}
	}
	
	/**
	 * 
	 * Get configuration of proxy units and order the creation
	 * and store them to future use
	 * 
	 */
	public void importProxyConfiguration(){
		
		UnitConfiguration[] configArray = controller.importProxyConfiguration();
		
		if(configArray != null){
		
			for(UnitConfiguration config : configArray){
				addProxyUnit(config.getHost(),config.getRegistryPort(),config.getName());
			}
		}else{
			ConsoleLog.Message("Found any configurations");
		}
	}
	
	/**
	 * Create the output instance and its listener. Then connect
	 * through the listener the test unit and the output instance. 
	 * 
	 * @param host
	 * @param port
	 * @param name
	 */
	private void addTestUnit(String host, int port, String name){
		
		try{
			
			PrintTestOutput output = new PrintTestOutput(name);
			NewResponseListener listner = new TestTextListener(output);
			testOutputs.put(testUnitCounter,output);
			controller.addTestUnit(testUnitCounter,host,port,name);
			controller.setResponseListener(listner,testUnitCounter);
		
		}catch(RemoteException ex){
			ConsoleLog.Message(ex.getClass().getName() + ": " + ex.getMessage());
			return;
		}catch(NotBoundException ex){
			ConsoleLog.Message(ex.getClass().getName() + ": " +ex.getMessage());
			return;
		}catch(Exception ex){
			ConsoleLog.Message(ex.getClass().getName() + ": " +ex.getMessage());
			ex.printStackTrace();
			return;
		}
		
		testUnitCounter++;
	}
	
	/**
	 * Create the output instance and its listener. Then connect
	 * through the listener the proxy unit and the output instance. 
	 *  
	 * @param host
	 * @param port
	 * @param name
	 */
	private void addProxyUnit(String host, int port, String name){
		try{
			
			PrintProxyOutput output = new PrintProxyOutput(name);
			ProxyListener listner = new ProxyTextListener(output);
			proxyOutputs.put(proxyUnitCounter,output);
			controller.addProxyUnit(proxyUnitCounter,host,port,name);
			controller.setPanelListener(listner,proxyUnitCounter);
			
			
		}catch(RemoteException ex){
			ConsoleLog.Message(ex.getClass().getName() + ": " + ex.getMessage());
			return;
		}catch(NotBoundException ex){
			ConsoleLog.Message(ex.getClass().getName() + ": " +ex.getMessage());
			return;
		}catch(Exception ex){
			ConsoleLog.Message(ex.getClass().getName() + ": " +ex.getMessage());
			ex.printStackTrace();
			return;
		}
		proxyUnitCounter++;
	}
	
}
