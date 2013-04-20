package testingUnit;

import gui.UnitPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JPanel;

import logging.ConsoleLog;

import org.apache.http.client.HttpClient;

import central.UnitController;

import data.DataProvider;
import data.TestCaseSettingsData;
import data.TestList;

public class LocalTestUnit  implements TestingUnit  {

	private List<NewResponseListener> newResponseListenerList= new ArrayList<NewResponseListener>();
	
	
	private ExecutorService executor;
	private DataProvider reader;
	private TestList testList;
	private ArrayList<TestCaseSettingsData> casesToRun;
	private String[][] finalOutputs;
	
	
	
	public LocalTestUnit(){
		reader = new DataProvider();
	}
	
	public void run(){
		
		/**
		 * toto bude predmet testovani, jestli pro jeden testcase vice vlaken nebo sekvencne
		 * zatim sekvencne
		 */
		ConsoleLog.Print("runned");
		
		
		finalOutputs = new String [casesToRun.size()][];
		for (int i =0; i  < casesToRun.size(); i++){
			
			TestCaseSettingsData settings = casesToRun.get(i);
			Set<Future<String[]>> outputs = new HashSet<Future<String[]>>();
			int threadsNumber = settings.getThreadsNumber();
			String[] responses = new String[threadsNumber]; 
			ConsoleLog.Print("bude se provadet: "+threadsNumber+" vlaken");
			for (int j =0; j  <  threadsNumber; j++){
				
				initTestCase(settings);
				RequestWorker test = new RequestWorker(settings,j);
				Future<String[]> workerOutput = executor.submit(test);
				outputs.add(workerOutput);
		
				System.out.println("Ukoncuju Test case");
				executor.shutdown();
				
				System.out.println("Cekam na vlakna");
				
				
				
			}
			//while(!executor.isTerminated()){ 
				
			//}
			
			
			
			for (Future<String[]> output : outputs){
				try {
					System.out.println("Cekam na response");
					responses = output.get();
					System.out.println("Cekam na panel");
					this.publishNewMessageEvent(responses[0]);
					System.out.println("nevim");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			finalOutputs[i]= responses;
			System.out.println("Jdu na dalsi test case");
		}
		
	}
	
	public void addResponseListener(NewResponseListener listener){
		newResponseListenerList.add(listener);
	}
	
	public void publishNewMessageEvent(String message) {
		
		for (NewResponseListener currentListener : newResponseListenerList)
			currentListener.onNewResponseEvent(message);
	}
	
	
	public String[][] getResponses(){
		return this.finalOutputs;
	}
	

	
	public TestList getTestList() {
		return testList;
	}

	public void setTestList(String path) {
		casesToRun = new ArrayList<TestCaseSettingsData>();
		this.testList = (TestList)reader.readObject(path);
		HashMap<Integer,String> testCases = testList.getTestCases();
		
		for(Integer caseId : testCases.keySet()){
			String casePath = testCases.get(caseId);
			ConsoleLog.Print(casePath);
			TestCaseSettingsData testCase = (TestCaseSettingsData)reader.readObject(casePath);
			
			if(testCase.getRun())
				casesToRun.add(testCase);
		}
		
	}
	
//	public void setTestCase(TestCaseSettingsData data){
//		testCaseSettings = data;
//		
//		loopCount = data.getLoopNumber();
//		name = data.getName();
//		System.out.println("Ahoj ja jsem vlakno" + this.name);
//		threadsNumber = data.getThreadsNumber(); 
//	}
	
	public void initTestCase(TestCaseSettingsData settings){
		executor = Executors.newFixedThreadPool(settings.getThreadsNumber());
	}
	
}
