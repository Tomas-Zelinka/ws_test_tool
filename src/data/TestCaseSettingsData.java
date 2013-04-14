package data;

public class TestCaseSettingsData {
	 
	
	private int threadsNumber;
	private int loopNumber;
	private String name;
	
	


	private String proxyHost;
	private int proxyPort;
	private int proxyTestedPort;
	
	
	
	
	
	public TestCaseSettingsData(){
		 
	 }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getProxyHost() {
		return proxyHost;
	}


	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}


	public int getProxyPort() {
		return proxyPort;
	}


	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}


	public int getProxyTestedPort() {
		return proxyTestedPort;
	}


	public void setProxyTestedPort(int proxyTestedPort) {
		this.proxyTestedPort = proxyTestedPort;
	}

	
	public int getThreadsNumber() {
		return threadsNumber;
	}


	public void setThreadsNumber(int threadsCount) {
		this.threadsNumber = threadsCount;
	}


	public int getLoopNumber() {
		return loopNumber;
	}


	public void setLoopNumber(int loopsCount) {
		this.loopNumber = loopsCount;
	}


	
	 
	
	 
}
