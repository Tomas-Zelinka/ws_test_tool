package data;

import java.util.Map;

public class HttpRequestData {

	public static final int METHOD_POST = 1;
	public static final int METHOD_GET = 2;
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
	
	public String getBody (String str){
		return this.body;
	}
	
	public String getRequestPath(){
		return this.requestPath;
	}
	
	public void setRequestPath(String str){
		this.requestPath = str;
	}
	
	
	public int getMethod(){
		return HttpRequestData.METHOD_GET;
	}
	
	
	
	
}
