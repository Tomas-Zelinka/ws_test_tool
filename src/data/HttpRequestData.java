package data;

import java.util.Map;

public class HttpRequestData {

	
	/**
	 * 
	 */
	private String body;
	
	/**
	 * 
	 */
	private Map header;
	
	/**
	 * 
	 */
	private String requestPath;
	
	public HttpRequestData(){
		
	}
	
	
	public void setBody(String inputBody){
		this.body = inputBody;
	}
	
	
	
	
	
}
