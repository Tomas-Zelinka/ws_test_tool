package cli;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import logging.ConsoleLog;
import central.UnitController;
import data.TestCaseSettingsData;

public class TextMonitor {

	private UnitController controller;
	private int testUnitCounter;
	private int proxyUnitCounter;
	
	
	public TextMonitor(UnitController controller){
		this.controller = controller;
		testUnitCounter = 0;
		proxyUnitCounter = 0;
		addTestUnit("",0);
		addProxyUnit("",0);
	}
	
	
	public void listRegisteredUnits(){
		
	}
	
	private void addTestUnit(String host, int port){
		
//		try{
//			//controller.addTestUnit(testUnitCounter,host,port);
//		
//		}catch(RemoteException ex){
//			ConsoleLog.Message(ex.getClass().getName() + ": " + ex.getMessage());
//			return;
//		}catch(NotBoundException ex){
//			ConsoleLog.Message(ex.getClass().getName() + ": " +ex.getMessage());
//			return;
//		}catch(Exception ex){
//			ConsoleLog.Message(ex.getClass().getName() + ": " +ex.getMessage());
//			ex.printStackTrace();
//			return;
//		}
		
		testUnitCounter++;
	}
	
	private void addProxyUnit(String host, int port){
//		try{
//			controller.addProxyUnit(proxyUnitCounter, host, port);
//		
//		}catch(RemoteException ex){
//			ConsoleLog.Message(ex.getClass().getName() + ": " + ex.getMessage());
//			return;
//		}catch(NotBoundException ex){
//			ConsoleLog.Message(ex.getClass().getName() + ": " +ex.getMessage());
//			return;
//		}catch(Exception ex){
//			ConsoleLog.Message(ex.getClass().getName() + ": " +ex.getMessage());
//			ex.printStackTrace();
//			return;
//		}
//		proxyUnitCounter++;
	}
	
	
	
	private void readUnitConfiguration(){
		
	}
	
	
	
	
	private void printOutput(){
		
	}
	
	private void runTest(String casePath){
		
		TestCaseSettingsData settings = controller.readSettingsData(casePath);
		
		
		
	}
	
	
	
	
	private void runProxy(String casePath){
		
	}
	
	
	private void stopProxy(int unitId){
		controller.stopProxy(unitId);
	}
	
}
