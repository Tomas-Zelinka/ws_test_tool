package testingUnit;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import logging.ConsoleLog;
import data.HttpMessageData;
import data.TestCaseSettingsData;

public class TestingUnitImpl extends UnicastRemoteObject implements TestingUnit {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6458013158279501115L;
	private List<NewResponseListener> newResponseListenerList= new ArrayList<NewResponseListener>();
	
	private ExecutorService executor;
	private HttpMessageData httpRequest;
	private TestCaseSettingsData settingsData;
	private HttpMessageData[][] messages;
	
	public TestingUnitImpl() throws RemoteException{
		//reader = new DataProvider();
		//testConnection();
	}
	
	
	public void run(){
		/**
		 * toto bude predmet testovani, jestli pro jeden testcase vice vlaken nebo sekvencne
		 * zatim sekvencne
		 */
		ConsoleLog.Print("runned");
		
		Set<Future<HttpMessageData[]>> outputs = new HashSet<Future<HttpMessageData[]>>();
		int threadsNumber = settingsData.getThreadsNumber();
		messages = new HttpMessageData [threadsNumber][settingsData.getLoopNumber()];
		ConsoleLog.Print("bude se provadet: "+threadsNumber+" vlaken");
		

		for (int j =0; j  <  threadsNumber; j++){
			
			initTestCase(settingsData);
			RequestWorker test = new RequestWorker(settingsData,httpRequest,j);
			Future<HttpMessageData[]> workerOutput = executor.submit(test);
			outputs.add(workerOutput);
		}
	
		int i = 0;
		for (Future<HttpMessageData[]> output : outputs){
			try {
				//ConsoleLog.Print("[TestUnit] Cekam na response");
				messages[i] = output.get();
				//ConsoleLog.Print("[TestUnit] Cekam na panel");
				this.publishNewMessageEvent(messages[i]);
				ConsoleLog.Print("[RemoteTestUnit] koncim vlakno" + i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(RemoteException e){
				e.printStackTrace();
			}
			i++;
		}
	}
	
	
	
	public void setTest(HttpMessageData request, TestCaseSettingsData settings) throws RemoteException{
		this.settingsData = settings;
		this.httpRequest = request;
		System.out.println(" [RemoteTestUnit] data recieved" + httpRequest.getName());
	}
	
	public void addResponseListener(NewResponseListener listener)throws RemoteException{
		ConsoleLog.Print("[RemoteTestUnit] pridavam listenera");
		newResponseListenerList.add(listener);
	}
	
	public String testConnection() throws RemoteException{
		return "Connected";
	} 
	
	private void initTestCase(TestCaseSettingsData settings){
		executor = Executors.newFixedThreadPool(settings.getThreadsNumber());
	}
	
	private void publishNewMessageEvent(HttpMessageData[] data) throws RemoteException {
		
		for (NewResponseListener currentListener : newResponseListenerList){
			ConsoleLog.Print("[RemoteTestUnit]posilam data");
			currentListener.onNewResponseEvent(data);
		}
	}
	
}
