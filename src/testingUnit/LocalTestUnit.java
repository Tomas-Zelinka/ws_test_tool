package testingUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import logging.ConsoleLog;

import org.apache.http.client.HttpClient;

import data.DataProvider;
import data.TestCaseSettingsData;
import data.TestList;

public class LocalTestUnit implements TestingUnit {

	
	private ExecutorService executor;
	
	private int threadsNumber;
	
	private DataProvider reader;
	private HttpClient client;
	
	private TestList testList;
	
	private ArrayList<TestCaseSettingsData> casesToRun;
	
	
	

	public LocalTestUnit(){
	
		reader = new DataProvider();
		
	}
	
	public void runTestList(){
		
		/**
		 * toto bude predmet testovani, jestli pro jeden testcase vice vlaken nebo sekvencne
		 * zatim sekvencne
		 */
		for (int i =0; i  < casesToRun.size(); i++){
				
				TestCaseSettingsData settings = casesToRun.get(i);
				initTestCase(settings);
				RequestWorker test = new RequestWorker(settings);
				test.run();
				//executor.execute(test);
		
		
		System.out.println("Ukoncuju Test case");
		executor.shutdown();
		
		System.out.println("Cekam na vlakna");
		
		while(!executor.isTerminated()){
			
		}
		
		System.out.println("Jdu na dalsi test case");
		}
		
	}
	
	private int getThreadsNumber(){
		return this.threadsNumber;
	}
	
	
	private void setThreadsNumber(int number){
		this.threadsNumber = number;
	}
	
	
	public static void main(String[] args){
		
		LocalTestUnit unit = new LocalTestUnit();
		unit.runTestList();
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
