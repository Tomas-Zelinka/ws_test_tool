package cz.vutbr.fit.dp.xzelin15.testingUnit;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cz.vutbr.fit.dp.xzelin15.data.HttpMessageData;
import cz.vutbr.fit.dp.xzelin15.data.TestCaseSettingsData;
import cz.vutbr.fit.dp.xzelin15.logging.ConsoleLog;
import cz.vutbr.fit.dp.xzelin15.rmi.TestUnit;

/**
 * Class represents implementation of test unit
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class TestUnitImpl extends UnicastRemoteObject implements TestUnit {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6458013158279501115L;
	
	/**
	 *  Remote instance of panel listener
	 */
	private NewResponseListener responseListener;
	
	/**
	 * Thread pool
	 */
	private ExecutorService executor;
	
	/**
	 * HTTP request data
	 */
	private HttpMessageData httpRequest;
	
	/**
	 * Request settings data
	 */
	private TestCaseSettingsData settingsData;
	
	/**
	 * Array of received messages
	 */
	private HttpMessageData[][] messages;
	
	/**
	 * Periodic mode flag
	 */
	private boolean periodic;
	
	public TestUnitImpl() throws RemoteException{
		this.periodic = false;
	}
	
	/**
	 * Executes new thread in background of GUI. Create pool of threads and send them
	 * request data and will run all those threads. Then will wait for 
	 */
	public void run() throws RemoteException{
		
		ConsoleLog.Print("[RemoteTestUnit] Runned: " + settingsData.getName());
		this.periodic = settingsData.isUseSequentialRun();
		int period = 0;
		
		// periodic cycle, if is not turn on, it is processed once 
		do{
			//initialization of threads
			Set<Future<HttpMessageData[]>> outputs = new HashSet<Future<HttpMessageData[]>>();
			int threadsNumber = settingsData.getThreadsNumber();
			messages = new HttpMessageData [threadsNumber][settingsData.getLoopNumber()];
			ConsoleLog.Print("[RemoteTestUnit] bude se provadet: "+threadsNumber+" vlaken");
			
			//run treads
			for (int j =0; j  <  threadsNumber; j++){
				
				initTestCase(settingsData);
				RequestWorker test = new RequestWorker(settingsData,httpRequest,j);
				Future<HttpMessageData[]> workerOutput = executor.submit(test);
				outputs.add(workerOutput);
			}
			executor.shutdown();
			
			
			//wait for the threads and send responses to the unit panel
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
	
	
	/**
	 * Set test case data for this unit
	 */
	public void setTest(HttpMessageData request, TestCaseSettingsData settings) throws RemoteException{
		this.settingsData = settings;
		this.httpRequest = request;
		ConsoleLog.Print("[RemoteTestUnit] data recieved " + httpRequest.getName());
	}
	
	/**
	 * Register listener with this unit
	 */
	public void addResponseListener(NewResponseListener listener)throws RemoteException{
		ConsoleLog.Print("[RemoteTestUnit] pridavam listenera");
		responseListener = listener;
	}
	
	/**
	 * 
	 */
	public String testConnection() throws RemoteException{
		return "Connected";
	} 
	
	/**
	 * Stop test unit if it is in periodic mode
	 */
	public void stopUnit() throws RemoteException{
		this.periodic = false;
	}
	
	/**
	 * Initialization of thread pool
	 * @param settings
	 */
	private void initTestCase(TestCaseSettingsData settings){
		executor = Executors.newFixedThreadPool(settings.getThreadsNumber());
	}
	
	/**
	 * Send data to GUI panel
	 * @param data - HTTP responses data
	 * @param period - last period number
	 * @throws RemoteException
	 */
	private void publishNewMessageEvent(HttpMessageData[] data, int period) throws RemoteException {
		ConsoleLog.Print("[RemoteTestUnit]posilam data");
		responseListener.onNewResponseEvent(data, period);
		
	}
	
	
	
}
