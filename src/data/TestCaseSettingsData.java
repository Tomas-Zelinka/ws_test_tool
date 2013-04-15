package data;

public class TestCaseSettingsData {
	 
	
	private int threadsNumber;
	private int loopNumber;
	private String name;
	private String path;
	

	private boolean run;
	


	


	private String proxyHost;
	private int proxyPort;
	private int proxyTestedPort;
	
	
	
	
	
	public TestCaseSettingsData(){
		 initAttributes();
	 }

	private void initAttributes(){
		threadsNumber = 0;
		loopNumber = 0;
		proxyHost = "";
		proxyPort = 0;
		proxyTestedPort = 0;
		run = false;
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


	public boolean getRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}
	 
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	 
}
