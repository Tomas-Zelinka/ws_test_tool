package data;

import java.io.File;
import java.util.HashMap;

public class HttpMessageData {

	
	public static final String HEADER_HTTP_VERSION = "Version";
	public static final String HEADER_HTTP_METHOD = "Method";
	public static final String HEADER_HTTP_URI = "URI";
	public static final String HEADER_HTTP_CONTENTTYPE = "ContentType";
	
	public static final int METHOD_POST = 1;
	public static final int METHOD_GET = 2;
	public static final String filename = File.separator+"Http"+File.separator+"input"+File.separator+"httpRequest.xml";
	/**
	 * 
	 */
	private String requestBody;
	private String responseBody;
	private float elapsedRemoteTime;
	private int responseCode;
	
	private String name;
	
	/**
	 * 
	 */
	private HashMap<String,String> mandatoryHeaders;
	private HashMap<String,String> optionalHeaders;
	
	/**
	 * 
	 */ 
	private String requestPath;
	
	public HttpMessageData(String name){
		this.mandatoryHeaders = new HashMap<String,String>();
		this.optionalHeaders = new HashMap<String,String>();
		this.name = name;
		this.requestBody = "";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setBody(String inputBody){
		this.requestBody = inputBody;
	}
	
	public String getBody (){
		return this.requestBody;
	}
	
	public String getRequestPath(){
		return this.requestPath;
	}
	
	public void setRequestPath(String str){
		this.requestPath = str;
	}
	
	
	public String getMandatoryHeaderValue(String name){
		return mandatoryHeaders.get(name);
	}
	
	public void addMandatoryHeader(String name, String value){
		mandatoryHeaders.put(name,value);
	}
	
	public void removeMandatoryHeader(String name){
		mandatoryHeaders.remove(name);
	}
	
	
	
	public String getOptionalHeaderValue(String name){
		return optionalHeaders.get(name);
	}
	
	public void addOptionalyHeader(String name, String value){
		optionalHeaders.put(name,value);
	}
	
	public void removeOptionalHeader(String name){
		optionalHeaders.remove(name);
	}
	
	
	
}
