package cli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import logging.ConsoleLog;
import data.HttpMessageData;
import data.XMLFormat;

public class PrintTestOutput {
	
	private int unitId;
	private String outputFolder;
	private int responseCounter;
	
	public PrintTestOutput(String name){
		
	}
	
	public void setOutputPath (String path,int id){
		ConsoleLog.Print("[TEXTPANEL] nastaveni: " + path + " "+ id);
		this.unitId = id;
		this.outputFolder =path;
		responseCounter = 0;
	}
	
	
	public void onNewResponseEvent(HttpMessageData[] data, int period){
		String outputPath = this.outputFolder+File.separator+data[0].getName()+HttpMessageData.outputFolder+File.separator+unitId;
		ConsoleLog.Print("[TEXTPANEL] adresar: " + outputPath);
		File dir = new File(outputPath);
		
		if(!dir.exists()){
			dir.mkdir();
		}
		
		
		Integer responseThreadNumber = data[0].getThreadNumber();
		ConsoleLog.Print("[TEXTPANEL] vkladam:");
			
		try {
			FileOutputStream out = null;
			FileOutputStream out1 = null;
			for (int i = 0; i < data.length; i++){
				String output = "";
				String  address =responseThreadNumber + "_"+ i + "_" +period ;
				
				if(responseCounter == 0){
					 out = new FileOutputStream(outputPath+File.separator+"output.meta");
					 out1 = new FileOutputStream(outputPath+File.separator+"responseBody_"+address+".txt");
				}else{
					 out = new FileOutputStream(outputPath+File.separator+"output.meta",true);
					 out1 = new FileOutputStream(outputPath+File.separator+"responseBody_"+address+".txt",true);
				}
				Long time = data[i].getElapsedRemoteTime()/1000000; 
				String test = compareResponses(data[i].getResponseBody(),data[i].getExpectedBody(),data[i].getContentType());
				output += responseCounter+" ";
				output += data[i].getName()+" ";
				output += data[i].getResponseCode()+" ";
				output += data[i].getMethod()+" ";
				output += data[i].getResource()+" ";
				output += time + " ";
				output += data[i].getLoopNumber()+" ";
				output += data[i].getThreadNumber()+" ";
				output += period+ " ";
				output += test + "\n";
				
				out.write(output.getBytes());
				out1.write(data[i].getResponseBody().getBytes());
				
				out.close();
				out1.close();
				responseCounter++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		
		
		
	}
	
	
	private String compareResponses(String output, String expected, String contentType){
		
		if(contentType.contains("text/xml") && !output.isEmpty() && !expected.isEmpty()){
			String one = XMLFormat.format(output);
			String two = XMLFormat.format(expected);
			
			if(one.contentEquals(two)){
				return "SUCCESS";
			}
		}
		
		return "FAILED";
		
	}
	
	
}
