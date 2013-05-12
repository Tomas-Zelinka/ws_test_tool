package data;

import java.io.File;
import java.io.Serializable;

public class HttpMessageData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3769765519967458600L;
	
	public static final String filename = File.separator+"Http"+File.separator+"input"+File.separator+"httpRequest.xml";
	/**
	 * 
	 */
	
	private long elapsedRemoteTime;
	private int responseCode;
	private int threadNumber;
	private int loopNumber;
	private String requestBody;
	private String responseBody;
	private String expectedBody;
	private String method;
	private String host;
	private String resource;
	private String params;
	private String contentType;
	private String name;
	
	
	/**
	 * 
	 */ 
	private String requestPath;
	
	public HttpMessageData(String name){
		this.elapsedRemoteTime = 0;
		this.responseCode = 0;
		this.threadNumber = 0;
		this.loopNumber = 0;
		this.requestBody = "";
		this.responseBody = "";
		this.method = "";
		this.host= "";
		this.resource = "";
		this.params = "";
		this.contentType = "";
		this.name = name;
		
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public int getThreadNumber() {
		
		return threadNumber;
	}

	public void setThreadNumber(int threadNumber) {
		
		this.threadNumber = threadNumber;
	}

	public int getLoopNumber() {
		
		return loopNumber;
	}

	public void setLoopNumber(int loopNumber) {
		
		this.loopNumber = loopNumber;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		
		this.method = method;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public String getParams() {
		
		return this.params;
	}

	public void setParams(String par) {
		
		this.params = par;
	}
	
	public String getResponseBody() {
		
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		
		this.responseBody = responseBody;
	}

	public float getElapsedRemoteTime() {
		
		return elapsedRemoteTime;
	}

	public void setElapsedRemoteTime(long elapsedRemoteTime) {
		
		this.elapsedRemoteTime = elapsedRemoteTime;
	}

	public int getResponseCode() {
		
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		
		this.responseCode = responseCode;
	}
	
	public String getName() {
		
		return name;
	}

	public void setName(String name) {
		
		this.name = name;
	}
	
	public void setRequestBody(String inputBody){
		
		this.requestBody = inputBody;
	}
	
	public String getRequestBody (){
		
		return this.requestBody;
	}
	
	public String getRequestPath(){
		
		return this.requestPath;
	}
	
	public void setRequestPath(String str){
		
		this.requestPath = str;
	}
	
	public String getExpectedBody() {
		return expectedBody;
	}

	public void setExpectedBody(String expectedBody) {
		this.expectedBody = expectedBody;
	}
	
	
}
