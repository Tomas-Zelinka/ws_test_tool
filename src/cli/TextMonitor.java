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

public class TextMonitor {

	private UnitController controller;
	private int testUnitCounter;
	private int proxyUnitCounter;
	private final String TEST_LOCAL_NAME = "Local Test";
	private final String PROXY_LOCAL_NAME = "Local Proxy ";
	private final int LOCAL_UNIT = 0;
	private HashMap <Integer,PrintProxyOutput> proxyOutputs;
	
	
	public TextMonitor(UnitController controller){
		this.controller = controller;
		testUnitCounter = 0;
		proxyUnitCounter = 0;
		proxyOutputs = new HashMap<Integer,PrintProxyOutput>();
		addTestUnit("none",LOCAL_UNIT,TEST_LOCAL_NAME);
		addProxyUnit("none",LOCAL_UNIT,PROXY_LOCAL_NAME);
	}
	
	
	
	/**
	 * 
	 * @param casePath
	 */
	public void runTestList(String suitePath){
		
		ConsoleLog.Print("[TextMonitor]: Test:" + suitePath);
		controller.runTest(suitePath, LOCAL_UNIT);
	}
	
	/**
	 * 
	 * @param casePath
	 * @param config
	 */
	public void runTestList(String casePath,String config){
		ConsoleLog.Print("[TextMonitor]: Test+config:" + casePath);
		importTestConfiguration();
		
		for(int i = 0; i < testUnitCounter; i++)
			controller.runTest(casePath, i);
	}
	
	
	/**
	 * 
	 * @param casePath
	 */
	public void runProxy(String casePath){
		
		ConsoleLog.Print("[TextMonitor]: Proxy:" + casePath);
		PrintProxyOutput output = proxyOutputs.get(LOCAL_UNIT);
		controller.createDir(casePath+FaultInjectionData.outputFolder+File.separator+LOCAL_UNIT);
		ConsoleLog.Print("[TextMonitor]: Proxy created :" + casePath+FaultInjectionData.outputFolder+File.separator+LOCAL_UNIT);
		output.setOutputPath(casePath+FaultInjectionData.outputFolder+File.separator+LOCAL_UNIT); 
		controller.runProxy(casePath, LOCAL_UNIT);
	}
	
	/**
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
	 * 
	 * @param host
	 * @param port
	 * @param name
	 */
	private void addTestUnit(String host, int port, String name){
		
		try{
			
			PrintTestOutput output = new PrintTestOutput(name);
			NewResponseListener listner = new TestTextListener(output);
			//proxyOutputs.put(testUnitCounter,output);
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
