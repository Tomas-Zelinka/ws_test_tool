package data;

public class UnitConfiguration {

	private int id;
	private String host;
	private int registryPort;
	private String name;
	
	
	public UnitConfiguration(int id, String host, int port, String name){
		this.id = id;
		this.host = host;
		this.registryPort = port;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public int getRegistryPort() {
		return registryPort;
	}


	public void setRegistryPort(int registryPort) {
		this.registryPort = registryPort;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	
}
