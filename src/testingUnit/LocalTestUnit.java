package testingUnit;

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

public class LocalTestUnit  implements TestingUnit  {

	private List<NewResponseListener> newResponseListenerList= new ArrayList<NewResponseListener>();
	
	
	private ExecutorService executor;
	private HttpMessageData httpRequest;
	private TestCaseSettingsData settingsData;
	private HttpMessageData[][] messages;
	
	
	
	public LocalTestUnit(){
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
				ConsoleLog.Print("[TestUnit] koncim vlakno" + i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		
//		for(int a = 0; a <messages.length ; a++){
//			this.publishNewMessageEvent(messages[a]);
//		}
		
		System.out.println("Koncim case");
		executor.shutdown();
	}
		
	
	
	public void addResponseListener(NewResponseListener listener){
		newResponseListenerList.add(listener);
	}
	
	public void publishNewMessageEvent(HttpMessageData[] data) {
		
		for (NewResponseListener currentListener : newResponseListenerList)
			currentListener.onNewResponseEvent(data);
	}
	
		
	public void setTest(HttpMessageData request, TestCaseSettingsData settings) {
		this.settingsData = settings;
		this.httpRequest = request;
	}

	
	public void initTestCase(TestCaseSettingsData settings){
		executor = Executors.newFixedThreadPool(settings.getThreadsNumber());
	}
	
}
