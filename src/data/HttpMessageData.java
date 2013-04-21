package data;

import java.io.File;
import java.util.HashMap;

public class HttpMessageData {

	public static final int METHOD_POST = 1;
	public static final int METHOD_GET = 2;
	public static final String filename = File.separator+"Http"+File.separator+"input"+File.separator+"httpRequest.xml";
	/**
	 * 
	 */
	private String body;
	private String name;
	
	/**
	 * 
	 */
	private HashMap<String,String> headers;
	
	/**
	 * 
	 */
	private String requestPath;
	
	public HttpMessageData(String name){
		this.headers = new HashMap<String,String>();
		this.name = name;
		this.body = "";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setBody(String inputBody){
		this.body = inputBody;
	}
	
	public String getBody (){
		return this.body;
	}
	
	public String getRequestPath(){
		return this.requestPath;
	}
	
	public void setRequestPath(String str){
		this.requestPath = str;
	}
	
	
	public String getHeaderValue(String name){
		return headers.get(name);
	}
	
	public void addHeader(String name, String value){
		headers.put(name,value);
	}
	
	public void removeHeader(String name){
		headers.remove(name);
	}
	
	
}
