package testingUnit;

import org.apache.http.HttpMessage;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import data.HttpRequestData;

public class RequestWorker implements Runnable {

	
	
	
	private int name;
	private int loopCount;
	private boolean test;
	private DefaultHttpClient client;
	private HttpRequestData data;
	private HttpMessage method;
	private String responseBody;
	private String testCasePath;
	
	
	public RequestWorker(){
		
	}
	
	public RequestWorker(HttpRequestData input, int count){
		//this.name = number;
		this.loopCount = count;
		test = true;
		this.data = input;
		client = new DefaultHttpClient();
		
		
		initRequest();
		System.out.println("Ahoj ja jsem vlakno" + this.name);
	}
	@Override
	public void run(){
		
//		String[] clientResponseBody = new String[loopCount];
//		int resultCode = 0;
//		HttpMessage clientMethod = getMethod();
//		
//		
//		for(int i = 0; i < this.loopCount; i++){
//			try{
//				resultCode = client.execute(clientMethod);
//				clientResponseBody[i] = clientMethod.getResponseBodyAsString();
//			}catch(Exception ex){
//				ex.printStackTrace();
//				return;
//			}finally{
//				clientMethod.releaseConnection();
//			}
//			
//		}
		
		
		
	}
	
	
	
	
	private void initRequest(){
		setMethod(this.data.getMethod());
		
	}
	
	private void setMethod(int type){
		
		switch (type){
		case HttpRequestData.METHOD_GET:
				this.method = new HttpPost();
			break;
		case HttpRequestData.METHOD_POST:
				this.method = new HttpGet();
			break;
		default:
			break;
		}
	}
	
//	private HttpMethod getMethod(){
//		return this.method;
//	}
	
	
	
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		responseBody = responseBody;
	}
	
}
