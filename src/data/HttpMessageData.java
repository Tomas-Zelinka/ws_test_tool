package data;

import java.io.File;
import java.io.Serializable;

public class HttpMessageData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3769765519967458600L;
	
	public static final String inputFilename = File.separator+"Http"+File.separator+"input"+File.separator+"httpRequest.xml";
	public static final String outputFolder = File.separator+"Http"+File.separator+"output";
	
	
	/**
	 * Time elapsed to get response
	 */
	private long elapsedRemoteTime;
	
	/**
	 * HTTP response code
	 */
	private int responseCode;
	
	/**
	 * Number of thread runned 
	 */
	private int threadNumber;
	
	/**
	 * Number of send requests by one thread
	 */
	private int loopNumber;
	
	/**
	 * HTTP request body
	 */
	private String requestBody;
	
	/**
	 * HTTP response body
	 */
	private String responseBody;
	
	/**
	 * Expected HTTP response body
	 */
	private String expectedBody;
	
	/**
	 * HTTP method of request
	 */
	private String method;
	
	/**
	 * The host of sent request
	 */
	private String host;
	
	/**
	 * The tested resource or service
	 */
	private String resource;
	
	/**
	 * Optional GET parameters
	 */
	private String params;
	
	/**
	 * HTTP header value of content type
	 */
	private String contentType;
	
	/**
	 *  The name of request
	 */
	private String name;
	
	/**
	 * The path of the request
	 */
	private String requestPath;
	
	/**
	 * @param name
	 */
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
	
	/**
	 * 
	 * Get content type value
	 * 
	 * @return content type
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * 
	 *  Set content type value
	 * 
	 * @param contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	/**
	 *
	 * Get thread number value
	 * 
	 * @return thread number
	 */
	public int getThreadNumber() {
		
		return threadNumber;
	}

	/**
	 * 
	 * Set thread number value
	 * 
	 * @param threadNumber
	 */
	public void setThreadNumber(int threadNumber) {
		
		this.threadNumber = threadNumber;
	}

	/**
	 * 
	 * Get loop number value
	 * 
	 * @return loop number
	 */
	public int getLoopNumber() {
		
		return loopNumber;
	}

	
	/**
	 * 
	 * Set loop number value
	 * 
	 * @param loopNumber
	 */
	public void setLoopNumber(int loopNumber) {
		
		this.loopNumber = loopNumber;
	}

	/**
	 * 
	 * Get method value
	 * 
	 * @return method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * 
	 * Set method value
	 * 
	 * @param method
	 */
	public void setMethod(String method) {
		
		this.method = method;
	}

	/**
	 * 
	 * Get host value
	 * 
	 * @return host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 
	 * Set host value
	 * 
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 
	 * Get Resource or service - the part of URI
	 * 
	 * @return
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * 
	 * Set Resource or service - the part of URI
	 * 
	 * @param resource
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	/**
	 * 
	 * Get params value
	 * 
	 * @return
	 */
	public String getParams() {
		
		return this.params;
	}

	/**
	 * 
	 * Set params value
	 * 
	 * @param par
	 */
	public void setParams(String par) {
		
		this.params = par;
	}
	
	/**
	 * 
	 * Get response body
	 * 
	 * @return response body
	 */
	public String getResponseBody() {
		
		return responseBody;
	}

	/**
	 *  
	 * Set response body
	 * 
	 * @param responseBody
	 */
	public void setResponseBody(String responseBody) {
		
		this.responseBody = responseBody;
	}

	/**
	 * 
	 * Get elapsed time value
	 * 
	 * @return elapsed time
	 */
	public Long getElapsedRemoteTime() {
		
		return elapsedRemoteTime;
	}

	/**
	 * 
	 * Set elapsed time value
	 * 
	 * @param elapsedRemoteTime
	 */
	public void setElapsedRemoteTime(long elapsedRemoteTime) {
		
		this.elapsedRemoteTime = elapsedRemoteTime;
	}

	/**
	 * 
	 * Get response code value
	 * 
	 * @return
	 */
	public int getResponseCode() {
		
		return responseCode;
	}

	/**
	 * 
	 * Set response code value
	 * 
	 * @param responseCode
	 */
	public void setResponseCode(int responseCode) {
		
		this.responseCode = responseCode;
	}
	
	/**
	 * 
	 * Get test case name
	 * 
	 * @return test case name
	 */
	public String getName() {
		
		return name;
	}

	/**
	 * 
	 * Set test case name
	 * 
	 * @param test case name
	 */
	public void setName(String name) {
		
		this.name = name;
	}
	
	/**
	 * 
	 * Set request body value
	 * 
	 * @param request body
	 */
	public void setRequestBody(String inputBody){
		
		this.requestBody = inputBody;
	}
	
	/**
	 * 
	 * Get request body value
	 * 
	 * @return request body
	 */
	public String getRequestBody (){
		
		return this.requestBody;
	}
	
	/**
	 * 
	 * Get test case path
	 * 
	 * @return test case path
	 */
	public String getRequestPath(){
		
		return this.requestPath;
	}
	
	/**
	 * 
	 * Set request path
	 * 
	 * @param str
	 */
	public void setRequestPath(String str){
		
		this.requestPath = str;
	}
	
	/**
	 * 
	 * Get expected body value
	 * 
	 * @return expected body
	 */
	public String getExpectedBody() {
		return expectedBody;
	}

	/**
	 * 
	 * Set expected body value
	 * 
	 * @param expectedBody
	 */
	public void setExpectedBody(String expectedBody) {
		this.expectedBody = expectedBody;
	}
	
	
}
