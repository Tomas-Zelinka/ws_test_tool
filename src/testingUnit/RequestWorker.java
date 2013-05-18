package testingUnit;

import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;

import logging.ConsoleLog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import data.HttpMessageData;
import data.TestCaseSettingsData;

/**
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class RequestWorker implements Callable<HttpMessageData[]>{
	
	private HttpMessageData data;
	private TestCaseSettingsData settings;
	private int threadId;
	
	
	/**
	 * 
	 * @param settings
	 * @param data
	 * @param id
	 */
	public RequestWorker(TestCaseSettingsData settings, HttpMessageData data, int id){
		this.threadId = id;
		this.data = data;
		this.settings = settings;
		ConsoleLog.Print("[Worker] prijal: " + data.getName() + " " +settings.getName());
	}
	
	
	/**
	 * 
	 */
	public HttpMessageData[] call(){
		HttpMessageData[] clientResponseData = initResponseData(settings.getLoopNumber());
		String method = data.getMethod();
		ConsoleLog.Print("[Worker " + this.threadId +"]pracuju: " + data.getName());
		DefaultHttpClient client = new DefaultHttpClient();
		HttpUriRequest request = buildRequest();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, settings.getTimeout());
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, settings.getRequestTimeout());
		
		if(settings.getUseProxy()){
			HttpHost proxy = new HttpHost(settings.getProxyHost(),settings.getProxyPort());
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			
		}
		
		
			
			for(int i = 0; i < settings.getLoopNumber(); i++){ 
				try{
					ConsoleLog.Print("[Worker " + this.threadId +"] cyklus");
					long time1= System.nanoTime();
					HttpResponse response = client.execute(request);
					long time2 = System.nanoTime();
					long timeSpent = time2-time1;
					HttpEntity responseEntity = response.getEntity();
					String output = EntityUtils.toString(responseEntity);
					EntityUtils.consume(responseEntity);
					
					clientResponseData[i].setName(data.getName());
					clientResponseData[i].setResponseBody(output);
					clientResponseData[i].setRequestBody(data.getRequestBody());
					clientResponseData[i].setElapsedRemoteTime(timeSpent);
					clientResponseData[i].setContentType(responseEntity.getContentType().getValue());
					clientResponseData[i].setResponseCode(response.getStatusLine().getStatusCode());
					clientResponseData[i].setMethod(method);
					clientResponseData[i].setLoopNumber(i);
					clientResponseData[i].setThreadNumber(this.threadId);
					clientResponseData[i].setHost(data.getHost());
					clientResponseData[i].setResource(data.getResource());
					clientResponseData[i].setParams(data.getParams());
					clientResponseData[i].setExpectedBody(data.getExpectedBody());
				
				}catch(ConnectTimeoutException ex){
					ConsoleLog.Message("[Thread" + this.threadId +"]" + ex.getMessage());
					clientResponseData[i].setName(data.getName());
					clientResponseData[i].setResponseBody("timeouted");
					clientResponseData[i].setRequestBody("timeouted");
					clientResponseData[i].setElapsedRemoteTime(-1);
					clientResponseData[i].setContentType("");
					clientResponseData[i].setResponseCode(-1);
					clientResponseData[i].setMethod(method);
					clientResponseData[i].setLoopNumber(i);
					clientResponseData[i].setThreadNumber(this.threadId);
					clientResponseData[i].setHost(data.getHost());
					clientResponseData[i].setResource(data.getResource());
					clientResponseData[i].setParams(data.getParams());
					clientResponseData[i].setExpectedBody(data.getExpectedBody());
					
					
				}catch(SocketTimeoutException ex){
					ConsoleLog.Message("[Thread" + this.threadId +"]" + ex.getMessage());
					clientResponseData[i].setName(data.getName());
					clientResponseData[i].setResponseBody("timeouted");
					clientResponseData[i].setRequestBody("timeouted");
					clientResponseData[i].setElapsedRemoteTime(-1);
					clientResponseData[i].setContentType("");
					clientResponseData[i].setResponseCode(-1);
					clientResponseData[i].setMethod(method);
					clientResponseData[i].setLoopNumber(i);
					clientResponseData[i].setThreadNumber(this.threadId);
					clientResponseData[i].setHost(data.getHost());
					clientResponseData[i].setResource(data.getResource());
					clientResponseData[i].setParams(data.getParams());
					clientResponseData[i].setExpectedBody(data.getExpectedBody());
				}
				
				catch(Exception ex){
					ex.printStackTrace();
					
				}
			}
		
			return clientResponseData;
	}

	/**
	 * 
	 * @return
	 */
	private HttpUriRequest buildRequest(){
		
		HttpUriRequest request = null;
		String method = data.getMethod();
		String uri = "";
		if(!data.getParams().isEmpty()){
			uri = data.getHost()+data.getResource() +"?" + data.getParams();
		}else{
			uri = data.getHost()+data.getResource();
		}
		if(method.compareTo("GET") == 0){
			request = new HttpGet( uri );
			
		}
		else if(method.compareTo("POST") == 0){
			request = new HttpPost(uri);
			if(!data.getRequestBody().isEmpty()){
				
				try{
					
					StringEntity entity = new StringEntity(data.getRequestBody(), "UTF-8");
					entity.setContentType(data.getContentType());
					entity.setChunked(true);
					((HttpPost)request).setEntity(entity);
				}catch(Exception e){
					ConsoleLog.Message(e.getMessage());
				}
			}
		}
		else if(method.compareTo("PUT") == 0){
			request = new HttpPut( uri);
			try{
				
				StringEntity entity = new StringEntity(data.getRequestBody(), "UTF-8");
				entity.setContentType(data.getContentType());
				((HttpPut)request).setEntity(entity);
			}catch(Exception e){
				ConsoleLog.Message(e.getMessage());
			}
		}
		else if(method.compareTo("DELETE") == 0){
			request = new HttpDelete( uri);
		}
		else if(method.compareTo("HEAD") == 0){
			request = new HttpHead( uri);
		}
		else if(method.compareTo("OPTIONS") == 0){
			request = new HttpOptions( uri);
		}
		else if(method.compareTo("TRACE") == 0){
			request = new HttpTrace( uri);
		}
		
		return request;
	}
	
	
	/**
	 * 
	 * @param count
	 * @return
	 */
	private HttpMessageData[] initResponseData(int count){
		
		HttpMessageData[] responseData = new HttpMessageData[settings.getLoopNumber()];
		for(int i =0; i < count; i++){
			responseData[i] = new HttpMessageData(settings.getName());
		}
		
		return responseData;
	}
	
	
	
}
