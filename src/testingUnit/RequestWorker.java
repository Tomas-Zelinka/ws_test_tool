package testingUnit;

import java.io.File;
import java.io.FileOutputStream;

import logging.ConsoleLog;

import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import data.HttpRequestData;
import data.TestCaseSettingsData;

public class RequestWorker implements Runnable{
	
	private DefaultHttpClient client;
	private HttpRequestData data;
	private HttpMessage method;
	private String responseBody;
	private TestCaseSettingsData testCaseSettings;
	
	
	
	public RequestWorker(TestCaseSettingsData data){
		testCaseSettings = data;
		client = new DefaultHttpClient();
	}
	
	
	
	public void run(){
		String[] clientResponseBody = new String[testCaseSettings.getLoopNumber()];
		int resultCode = 0;
		HttpGet clientMethod = new HttpGet("http://www.google.com/");
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpHost proxy = new HttpHost("158.234.170.80", 3128);
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		
		for(int i = 0; i < testCaseSettings.getLoopNumber(); i++){
			ConsoleLog.Print("Testcase run" + testCaseSettings.getName()+" "+ i);
			
			try{
				clientResponseBody[i] = client.execute(clientMethod,responseHandler);
				
			}catch(Exception ex){
				ex.printStackTrace();
				return;
			}finally{
				clientMethod.releaseConnection();
			}
			
		}
		
		
		for(int i = 0; i < clientResponseBody.length; i++){
			
			try{
				File output = new File(testCaseSettings.getPath()+File.separator+testCaseSettings.getName()+"_"+ i+".txt");
				System.out.println(output.getPath());
				
				if (!output.exists()) {
					output.createNewFile();
				}
				
				byte[] contentInBytes = clientResponseBody[i].getBytes();
				
				FileOutputStream writer = new FileOutputStream(output);
				writer.write(contentInBytes);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
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
