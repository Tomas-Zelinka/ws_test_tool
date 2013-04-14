package testingUnit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.HttpClient;

public class LocalTestUnit implements TestingUnit {

	
	private ExecutorService executor;
	
	private int threadsNumber;
	
	
	private HttpClient client;
	
	private String[] testList;
	
	public LocalTestUnit(){
	
		threadsNumber = 8;
		executor = Executors.newFixedThreadPool(getThreadsNumber());
	}
	
	public void runTestList(){
		
		int MOC = 10;
		for (int i =0; i  < 5; i++){
				RequestWorker test = new RequestWorker();
				executor.execute(test);
		}
		
		System.out.println("Ukoncuju");
		executor.shutdown();
		
		System.out.println("Cekam na vlakna");
		
		while(!executor.isTerminated()){
			
		}
		
		System.out.println("Koncim");
		
		
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
	
	
}
