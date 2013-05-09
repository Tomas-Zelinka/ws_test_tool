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
	private String name;
	private String path;
	
	private boolean run;
	private String proxyHost;
	private int proxyPort;
	private int proxyTestedPort;
	private boolean useProxy;
	public static final String filename = File.separator+"settings.xml";
	
	
	
	
	

	public TestCaseSettingsData(){
		 
		initAttributes();
	 }

	private void initAttributes(){
		
		useProxy = false;
		threadsNumber = 0;
		loopNumber = 0;
		proxyHost = "";
		proxyPort = 0;
		proxyTestedPort = 0;
		run = false;
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
}
