package testingUnit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import data.HttpRequestData;

public class RequestWorker implements Runnable {

	
	
	
	private int name;
	private int loopCount;
	private boolean test;
	private HttpClient client;
	private HttpRequestData data;
	private HttpMethod method;
	private String responseBody;
	private String testCasePath;
	
	
	public RequestWorker(HttpRequestData input, int count){
		//this.name = number;
		this.loopCount = count;
		test = true;
		this.data = input;
		client = new HttpClient();
		
		
		initRequest();
		System.out.println("Ahoj ja jsem vlakno" + this.name);
	}
	@Override
	public void run(){
		
		String[] clientResponseBody = new String[loopCount];
		int resultCode = 0;
		HttpMethod clientMethod = getMethod();
		
		
		for(int i = 0; i < this.loopCount; i++){
			try{
				resultCode = client.executeMethod(clientMethod);
				clientResponseBody[i] = clientMethod.getResponseBodyAsString();
			}catch(Exception ex){
				ex.printStackTrace();
				return;
			}finally{
				clientMethod.releaseConnection();
			}
			
		}
		
		
		
	}
	
	
	
	
	private void initRequest(){
		setMethod(this.data.getMethod());
		
	}
	
	private void setMethod(int type){
		
		switch (type){
		case HttpRequestData.METHOD_GET:
				this.method = new PostMethod();
			break;
		case HttpRequestData.METHOD_POST:
				this.method = new GetMethod();
			break;
		default:
			break;
		}
	}
	
	private HttpMethod getMethod(){
		return this.method;
	}
	
	
	
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		responseBody = responseBody;
	}
	
}
