package cz.vutbr.fit.dp.xzelin15.data;

import java.io.File;
import java.io.Serializable;


/**
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class TestCaseSettingsData implements Serializable{
	 
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1877094164580318557L;
	
	/**
	 * Number of threds to run
	 */
	private int threadsNumber;
	
	/**
	 * Number of send requests in one thread
	 */
	private int loopNumber;
	
	
	/**
	 * Port for proxy
	 */
	private int proxyPort;
	
	/**
	 * Port for tested web service
	 */
	private int proxyTestedPort;
	
	/**
	 * Connection timeout
	 */
	private int timeout;
	
	/**
	 * Time in seconds delay while periodic run
	 */
	private int period;
	
	/**
	 * Test unit use flag
	 */
	private boolean run;
	
	/**
	 * Proxy unit use flag 
	 */
	private boolean useProxy;
	
	/**
	 * Use of periodic run flag
	 */
	private boolean useSequentialRun;
	
	/**
	 * Name of test case
	 */
	private String name;
	
	/**
	 * Path of test case
	 */
	private String path;
	
	/**
	 * Host, where will be the proxy runned
	 */
	private String proxyHost;
	
	/**
	 * Timeout for request data
	 */
	private int requestTimeout;
	
	
	/**
	 * Serialization id
	 */
	public static final String filename = File.separator+"settings.xml";
	

	/**
	 * 
	 */
	public TestCaseSettingsData(){
		
		this.threadsNumber = 0;
		this.loopNumber = 0;
		this.proxyPort = 0;
		this.proxyTestedPort = 0;
		this.timeout = 3000;
		this.useProxy = false;
		this.run = false;
		this.useSequentialRun = false;
		this.path ="";
		this.name = "";
		this.proxyHost = "";
	}

	
	
	/**
	 * 
	 *  Get proxy flag
	 *  
	 * @return  proxy use flag
	 */
	public boolean getUseProxy() {
		
		return this.useProxy;
	}

	/**
	 * 
	 * Set proxy Flag
	 * 
	 * @param use proxy flage
	 */
	public void setUseProxy(boolean use) {
		
		this.useProxy = use;
	}
	
	/**
	 * 
	 * Get test name
	 * 
	 * @return test name
	 */
	public String getName() {
		
		return this.name;
	}

	/**
	 * 
	 * Set test name
	 * 
	 * @param name set test name
	 */
	public void setName(String name) {
		
		this.name = name;
	}
	
	/**
	 * 
	 * Get proxy host
	 * 
	 * @return proxy host
	 */
	public String getProxyHost() {
		
		return this.proxyHost;
	}


	/**
	 * 
	 * Set proxy host
	 * 
	 * @param proxyHost
	 */
	public void setProxyHost(String proxyHost) {
		
		this.proxyHost = proxyHost;
	}


	/**
	 * 
	 * Get proxy port where proxy is runned
	 * 
	 * @return
	 */
	public int getProxyPort() {
		
		return this.proxyPort;
	}


	/**
	 * 
	 * Set proxy port where proxy is runned
	 * 
	 * @param proxyPort
	 */
	public void setProxyPort(int proxyPort) {
		
		this.proxyPort = proxyPort;
	}


	/**
	 * 
	 * Get port of tested web service
	 * 
	 * @return
	 */
	public int getProxyTestedPort() {
		
		return this.proxyTestedPort;
	}


	/**
	 * 
	 * Set port where is the tested web service
	 * 
	 * @param proxyTestedPort
	 */
	public void setProxyTestedPort(int proxyTestedPort) {
		
		this.proxyTestedPort = proxyTestedPort;
	}

	
	/**
	 * 
	 * Get number of runned threads
	 * 
	 * @return
	 */
	public int getThreadsNumber() {
		
		return this.threadsNumber;
	}


	/**
	 * 
	 *  Set number of threads to be runned
	 * 
	 * @param threadsCount
	 */
	public void setThreadsNumber(int threadsCount) {
		
		this.threadsNumber = threadsCount;
	}


	/**
	 * 
	 * Get number of requests send in one thread
	 * 
	 * @return
	 */
	public int getLoopNumber() {
		
		return this.loopNumber;
	}

	
	/**
	 * 
	 * Set number of requests send in one thread
	 * 
	 * @param loopsCount
	 */
	public void setLoopNumber(int loopsCount) {
		
		this.loopNumber = loopsCount;
	}


	/**
	 * 
	 * Get test unit use flag
	 * 
	 * @return
	 */
	public boolean getRun() {
		
		return this.run;
	}

	/**
	 * 
	 * Set test unit use flag
	 * 
	 * @param run
	 */
	public void setRun(boolean run) {
		
		this.run = run;
	}
	 
	/**
	 * 
	 * Get path of test case
	 * 
	 * @return case path
	 */
	public String getPath() {
		
		return this.path;
	}

	/**
	 * 
	 * Set path of test case
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		
		this.path = path;
	}
	
	
	/**
	 * 
	 * Get connection timeout in milliseconds
	 * 
	 * @return timeout in milliseconds
	 */
	public int getTimeout() {
		return this.timeout;
	}



	/**
	 * 
	 * Get connection timeout milliseconds
	 * 
	 * @param timeout
	 */
	public void setTimeout(int timeout) {
		
		this.timeout = timeout;
	}



	/**
	 * 
	 * Get delay period when is used periodic testing 
	 * 
	 * @return period delay
	 */
	public int getPeriod() {
		
		return this.period;
	}



	/**
	 * 
	 * Set delay period when is used periodic testing
	 * 
	 * @param period
	 */
	public void setPeriod(int period) {
		
		this.period = period;
	}



	/**
	 * 
	 * Get flag of periodic testing
	 * 
	 * @return periodic run flag
	 */
	public boolean isUseSequentialRun() {
		
		return this.useSequentialRun;
	}



	/**
	 * 
	 * Set flag of periodic testing
	 * 
	 * @param useSequentialRun
	 */
	public void setUseSequentialRun(boolean useSequentialRun) {
		
		this.useSequentialRun = useSequentialRun;
	}

	/**
	 * 
	 * Get the request timeout in milliseconds
	 * 
	 * @return Request timeout
	 */
	public int getRequestTimeout() {
		return requestTimeout;
	}


	/**
	 * 
	 * Set the request timeout in milliseconds
	 * 
	 * @param requestTimeout
	 */
	public void setRequestTimeout(int requestTimeout) {
		this.requestTimeout = requestTimeout;
	}
	
}
