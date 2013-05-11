package testingUnit;

import java.util.concurrent.Callable;

import org.apache.http.HttpHost;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import data.HttpMessageData;
import data.TestCaseSettingsData;

public class RequestWorker implements Callable<HttpMessageData[]>{
	
	private DefaultHttpClient client;
	private HttpMessageData data;
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
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		//HttpResponse response = null;
		String method = data.getMethod();
		String uri = data.getHost() + data.getResource() + "?" + data.getParams();
		
		//HttpRequest request = new HttpRequest();
		//request.getParams().s
		
		//CoreProtocolPNames.

		if(settings.getUseProxy()){
			HttpHost proxy = new HttpHost(settings.getProxyHost(),settings.getProxyPort());
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		
		
		if(method.compareTo("GET") == 0){
			HttpGet clientMethod = new HttpGet(uri);
			for(int i = 0; i < settings.getLoopNumber(); i++){ 
				//ConsoleLog.Print("[Worker]Testcase run" + settings.getName()+" "+ i);
				 
				try{
					long time1= System.nanoTime();
					responseBody = client.execute(clientMethod,responseHandler);
					long time2 = System.nanoTime();
					long timeSpent = time2-time1;
					clientResponseData[i].setResponseBody(responseBody);
					clientResponseData[i].setElapsedRemoteTime(timeSpent);
					clientResponseData[i].setRequestBody(data.getRequestBody());
					clientResponseData[i].setMethod("GET");
					clientResponseData[i].setLoopNumber(i);
					clientResponseData[i].setThreadNumber(this.threadId);
					clientResponseData[i].setHost(data.getHost());
					clientResponseData[i].setResource(data.getResource());
					clientResponseData[i].setParams(data.getParams());
				}catch(Exception ex){
					ex.printStackTrace();
					
				}finally{
					clientMethod.releaseConnection();
				}
				//ConsoleLog.Print("konec smycky: " + i +" - vlakno: " +this.threadId);
				//clientResponseData[i].setBody("ahoj");
			}
		
		}else if(method.compareTo("POST") == 0){
			HttpPost clientMethod = new HttpPost(uri);
			StringEntity entity;
			
			try{
				entity = new StringEntity(data.getRequestBody());
			} catch(Exception ex){
				ex.printStackTrace();
				return clientResponseData;
			}
			
			 //entity.
			 ((HttpPost) clientMethod).setEntity(entity);
			 for(int i = 0; i < settings.getLoopNumber(); i++){ 
					//ConsoleLog.Print("[Worker]Testcase run" + settings.getName()+" "+ i);
					 
					try{
						long time1= System.nanoTime();
						responseBody = client.execute(clientMethod,responseHandler);
						long time2 = System.nanoTime();
						long timeSpent = time2-time1;
						
						clientResponseData[i].setResponseBody(responseBody);
						clientResponseData[i].setRequestBody(data.getRequestBody());
						clientResponseData[i].setElapsedRemoteTime(timeSpent);
						clientResponseData[i].setMethod("POST");
						clientResponseData[i].setLoopNumber(i);
						clientResponseData[i].setThreadNumber(this.threadId);
						clientResponseData[i].setHost(data.getHost());
						clientResponseData[i].setResource(data.getResource());
						clientResponseData[i].setParams(data.getParams());
					}catch(Exception ex){
						ex.printStackTrace();
						
					}finally{
						clientMethod.releaseConnection();
					}
					//ConsoleLog.Print("konec smycky: " + i +" - vlakno: " +this.threadId);
					//clientResponseData[i].setBody("ahoj");
				}
		}
		
		
		
		
		
		//Console
		
		
		
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
