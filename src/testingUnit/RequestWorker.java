package testingUnit;

import java.util.concurrent.Callable;

import logging.ConsoleLog;

import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import data.HttpMessageData;
import data.TestCaseSettingsData;

public class RequestWorker implements Callable<HttpMessageData[]>{
	
	private DefaultHttpClient client;
	private HttpMessageData data;
	private HttpMessage method;
	private String responseBody;
	private TestCaseSettingsData settings;
	private int threadId;
	
	
	
	public RequestWorker(TestCaseSettingsData settings, HttpMessageData data, int id){
		this.threadId = id;
		this.data = data;
		this.settings = settings;
		this.client = new DefaultHttpClient();
		this.responseBody = "";
	}
	
	
	
	public HttpMessageData[] call(){
		HttpMessageData[] clientResponseData = initResponseData(settings.getLoopNumber());
		HttpGet clientMethod = new HttpGet("http://www.seznam.cz/");
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		//Console
		
		if(settings.getUseProxy()){
			HttpHost proxy = new HttpHost(settings.getProxyHost(),settings.getProxyPort());
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		for(int i = 0; i < settings.getLoopNumber(); i++){ 
			//ConsoleLog.Print("[Worker]Testcase run" + settings.getName()+" "+ i);
			 
			try{
				responseBody = client.execute(clientMethod,responseHandler);
				clientResponseData[i].setBody(responseBody);
			}catch(Exception ex){
				ex.printStackTrace();
				
			}finally{
				clientMethod.releaseConnection();
			}
			//ConsoleLog.Print("konec smycky: " + i +" - vlakno: " +this.threadId);
			//clientResponseData[i].setBody("ahoj");
		}
		
		//Thread.yield();
		
		//ConsoleLog.Print("Odesilam vysledky - vlakno: " +this.threadId);
		return clientResponseData;
	}

	
	private HttpMessageData[] initResponseData(int count){
		
		HttpMessageData[] responseData = new HttpMessageData[settings.getLoopNumber()];
		for(int i =0; i < count; i++){
			responseData[i] = new HttpMessageData(settings.getName());
		}
		
		return responseData;
	}
}
