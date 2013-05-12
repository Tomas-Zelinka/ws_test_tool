package testingUnit;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import logging.ConsoleLog;
import rmi.TestUnit;
import data.HttpMessageData;
import data.TestCaseSettingsData;

public class TestUnitImpl extends UnicastRemoteObject implements TestUnit {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6458013158279501115L;
	private NewResponseListener responseListener;
	
	private ExecutorService executor;
	private HttpMessageData httpRequest;
	private TestCaseSettingsData settingsData;
	private HttpMessageData[][] messages;
	private boolean periodic;
	
	public TestUnitImpl() throws RemoteException{
		this.periodic = false;
	}
	
	
	public void run() throws RemoteException{
		/**
		 * toto bude predmet testovani, jestli pro jeden testcase vice vlaken nebo sekvencne
		 * zatim sekvencne
		 */ 
		ConsoleLog.Print("[RemoteTestUnit] Runned: " + settingsData.getName());
		
		
	
		
		this.periodic = settingsData.isUseSequentialRun();
		//ConsoleLog.Print("[RemoteTestUnit] periodic" + this.periodic);
		int period = 0;
		do{
			Set<Future<HttpMessageData[]>> outputs = new HashSet<Future<HttpMessageData[]>>();
			int threadsNumber = settingsData.getThreadsNumber();
			messages = new HttpMessageData [threadsNumber][settingsData.getLoopNumber()];
			ConsoleLog.Print("[RemoteTestUnit] bude se provadet: "+threadsNumber+" vlaken");
			for (int j =0; j  <  threadsNumber; j++){
				
				initTestCase(settingsData);
				RequestWorker test = new RequestWorker(settingsData,httpRequest,j);
				Future<HttpMessageData[]> workerOutput = executor.submit(test);
				outputs.add(workerOutput);
			}
			executor.shutdown();
			
			int i = 0;
			for (Future<HttpMessageData[]> output : outputs){
				try {
					ConsoleLog.Print("[TestUnit] Cekam na response");
					messages[i] = output.get();
					//ConsoleLog.Print("[TestUnit] Cekam na panel");
					this.publishNewMessageEvent(messages[i],period);
					ConsoleLog.Print("[RemoteTestUnit] koncim vlakno" + i);
				} catch (InterruptedException e) {
					ConsoleLog.Message("Unit stopped");
					
				} catch (ExecutionException e) {
					
					e.printStackTrace();
				}catch(RemoteException e){
					
					e.printStackTrace();
				}
				i++;
			}
			
			try{
				Thread.sleep(settingsData.getPeriod());
			}catch(Exception e){
				ConsoleLog.Print(e.getMessage());
			}
			period++;
		}while(this.periodic);
		ConsoleLog.Print("KONEC");
	}
	
	
	
	public void setTest(HttpMessageData request, TestCaseSettingsData settings) throws RemoteException{
		this.settingsData = settings;
		this.httpRequest = request;
		System.out.println("[RemoteTestUnit] data recieved " + httpRequest.getName());
	}
	
	public void addResponseListener(NewResponseListener listener)throws RemoteException{
		ConsoleLog.Print("[RemoteTestUnit] pridavam listenera");
		responseListener = listener;
	}
	
	public String testConnection() throws RemoteException{
		return "Connected";
	} 
	
	public void stopUnit() throws RemoteException{
		this.periodic = false;
	}
	
	private void initTestCase(TestCaseSettingsData settings){
		executor = Executors.newFixedThreadPool(settings.getThreadsNumber());
	}
	
	private void publishNewMessageEvent(HttpMessageData[] data, int period) throws RemoteException {
		ConsoleLog.Print("[RemoteTestUnit]posilam data");
		responseListener.onNewResponseEvent(data, period);
		
	}
	
	
	
}
