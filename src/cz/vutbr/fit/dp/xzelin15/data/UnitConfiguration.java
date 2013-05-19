package cz.vutbr.fit.dp.xzelin15.data;

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
	
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 
	 * @return
	 */
	public int getRegistryPort() {
		return registryPort;
	}

	/**
	 * 
	 * @param registryPort
	 */
	public void setRegistryPort(int registryPort) {
		this.registryPort = registryPort;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
