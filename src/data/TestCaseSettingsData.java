package data;

import java.io.File;
import java.io.Serializable;

public class TestCaseSettingsData implements Serializable{
	 
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1877094164580318557L;
	
	private int threadsNumber;
	private int loopNumber;
	private int proxyPort;
	private int proxyTestedPort;
	private int timeout;
	private int period;
	private boolean run;
	private boolean useProxy;
	private boolean useSequentialRun;
	private String name;
	private String path;
	private String proxyHost;
	
	public static final String filename = File.separator+"settings.xml";
	

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

	
	
	public boolean getUseProxy() {
		
		return this.useProxy;
	}

	public void setUseProxy(boolean use) {
		
		this.useProxy = use;
	}
	
	public String getName() {
		
		return this.name;
	}

	public void setName(String name) {
		
		this.name = name;
	}
	
	public String getProxyHost() {
		
		return this.proxyHost;
	}


	public void setProxyHost(String proxyHost) {
		
		this.proxyHost = proxyHost;
	}


	public int getProxyPort() {
		
		return this.proxyPort;
	}


	public void setProxyPort(int proxyPort) {
		
		this.proxyPort = proxyPort;
	}


	public int getProxyTestedPort() {
		
		return this.proxyTestedPort;
	}


	public void setProxyTestedPort(int proxyTestedPort) {
		
		this.proxyTestedPort = proxyTestedPort;
	}

	
	public int getThreadsNumber() {
		
		return this.threadsNumber;
	}


	public void setThreadsNumber(int threadsCount) {
		
		this.threadsNumber = threadsCount;
	}


	public int getLoopNumber() {
		
		return this.loopNumber;
	}


	public void setLoopNumber(int loopsCount) {
		
		this.loopNumber = loopsCount;
	}


	public boolean getRun() {
		
		return this.run;
	}

	public void setRun(boolean run) {
		
		this.run = run;
	}
	 
	public String getPath() {
		
		return this.path;
	}

	public void setPath(String path) {
		
		this.path = path;
	}
	
	
	public int getTimeout() {
		return this.timeout;
	}



	public void setTimeout(int timeout) {
		
		this.timeout = timeout;
	}



	public int getPeriod() {
		
		return this.period;
	}



	public void setPeriod(int period) {
		
		this.period = period;
	}



	public boolean isUseSequentialRun() {
		
		return this.useSequentialRun;
	}



	public void setUseSequentialRun(boolean useSequentialRun) {
		
		this.useSequentialRun = useSequentialRun;
	}

	
}
