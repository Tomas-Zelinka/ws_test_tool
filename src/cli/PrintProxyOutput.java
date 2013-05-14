package cli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import proxyUnit.HttpInteraction;
import proxyUnit.HttpRequest;
import proxyUnit.HttpResponse;

public class PrintProxyOutput {

	private String outputFolder;
	private Map<Integer, HttpInteraction> interactionMap;
	private int interactionCounter;
	
	public PrintProxyOutput(String name){
		this.interactionMap = new HashMap<Integer, HttpInteraction>();
		
	}
	
	
	public void setOutputPath (String path){
		this.outputFolder = path;
		interactionCounter = 0;
	}
	
	public void onUnknownHostEvent()  {
		
	}
	
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
			
			
			//vlozeni cisla interakce
			//interactionTableModel.setValueAt(interactionId, row, 0);
			
			//HTTP RESPONSE
					//vlozeni http kodu
			if (httpResponse != null){
				output+= interactionCounter + " ";
				output+= interaction.getName() + " ";
				output += httpResponse.getHttpCode() + " " + httpResponse.getHttpCodeDesc() + " ";
			}
			
			
			//HTTP REQUEST
			if (httpRequest != null) {
				
				
				//vlozeni http metody
				output += httpRequest.getHttpMethod() + " ";
				
				//vlozeni iniciatora komunikace
				output +=httpRequest.getInitiator() + " ";
				
				//vlozeni URI
				output +=httpRequest.getInitiatorPort() + " ";
				
				output +=httpRequest.getUri() + "\n";
			}
		
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
