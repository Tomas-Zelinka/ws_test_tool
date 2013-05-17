package cli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import proxyUnit.HttpInteraction;
import proxyUnit.HttpRequest;
import proxyUnit.HttpResponse;

/**
 * Class is responsible for receiving data from proxy unit 
 * and prints it into a file. 
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class PrintProxyOutput {
	
	/**
	 * 	Variable for output folder
	 */
	private String outputFolder;
	
	/**
	 * Variable for interaction storage
	 */
	private Map<Integer, HttpInteraction> interactionMap;
	
	/**
	 * Variable for number of received interactions
	 */
	private int interactionCounter;
	
	
	public PrintProxyOutput(String name){
		this.interactionMap = new HashMap<Integer, HttpInteraction>();
		
	}
	
	/**
	 * Resets directory and counter when new test is runned
	 * 
	 * @param path
	 */
	public void setOutputPath (String path){
		File dir = new File(path);
		
		if(!dir.exists()){
			dir.mkdir();
		}
			
		interactionCounter = 0;
	}
	/**
	 * 
	 */
	public void onUnknownHostEvent()  {
		
	}
	
	/**
	 * Receives data from proxy unit and writes it into a file
	 * 
	 * @param interactionId
	 * @param interaction
	 */
	public void onNewMessageEvent(int interactionId, HttpInteraction interaction) {
		
		
		if (!interactionMap.containsKey(interactionId) ){
			interactionMap.put(interactionId, interaction);
			//insertRowInteractionTable(interactionId, interaction);
			
		}
		//pokud v mape jiz existuje tato interakce..pridame k ni prislusny request/response
		else {
			interactionMap.remove(interactionId);
			interactionMap.put(interactionId, interaction);
			printOutput(interaction);
		}
		
		
	}
	
	/**
	 * Prints given interaction into a file
	 * 
	 * @param interaction
	 */
	private void printOutput(HttpInteraction interaction) {
		
		HttpRequest httpRequest= interaction.getHttpRequest();
		HttpResponse httpResponse= interaction.getHttpResponse();
		String output = "";
		
		try {
			FileOutputStream out = new FileOutputStream(this.outputFolder+File.separator+"output.meta",true);
			FileOutputStream out1 = new FileOutputStream(this.outputFolder+File.separator+"requestBody_"+interactionCounter+".txt");
			FileOutputStream out2 = new FileOutputStream(this.outputFolder+File.separator+"changedRequestBody_"+interactionCounter+".txt");
			FileOutputStream out3 = new FileOutputStream(this.outputFolder+File.separator+"responseBody_"+interactionCounter+".txt");
			FileOutputStream out4 = new FileOutputStream(this.outputFolder+File.separator+"changedResponseBody_"+interactionCounter+".txt");
			
			
			
			if (httpResponse != null){
				output += interactionCounter + " ";
				output += interaction.getName() + " ";
				output += httpResponse.getHttpCode() + " " + httpResponse.getHttpCodeDesc() + " ";
			}
			
			
			//HTTP REQUEST
			if (httpRequest != null) {
				
				
				//insert http method
				output += httpRequest.getHttpMethod() + " ";
				
				//insert initiator of request
				output +=httpRequest.getInitiator() + " ";
				
				//initiator port 
				output +=httpRequest.getInitiatorPort() + " ";
				
				//insert uri of request
				output +=httpRequest.getUri() + "\n";
			}
		
			//write the interaction into file
			out.write(output.getBytes());
			out1.write(interaction.getHttpRequest().getContent().getBytes());
			out2.write(interaction.getHttpRequest().getChangedContent().getBytes());
			out3.write(interaction.getHttpResponse().getContent().getBytes());
			out4.write(interaction.getHttpResponse().getChangedContent().getBytes());
			out.close();
			out1.close();
			out2.close();
			out3.close();
			out4.close();
			interactionCounter++;
			
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
