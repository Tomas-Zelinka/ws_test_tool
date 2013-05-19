package cz.vutbr.fit.dp.xzelin15.cli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.vutbr.fit.dp.xzelin15.data.HttpMessageData;
import cz.vutbr.fit.dp.xzelin15.data.XMLFormat;
import cz.vutbr.fit.dp.xzelin15.logging.ConsoleLog;


/**
 * Class is responsible for receiving data from test unit 
 * and prints it into a file. 
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class PrintTestOutput {
	
	/**
	 * Id of connected unit
	 */
	private int unitId;
	
	/**
	 * Responses output folder
	 */
	private String outputFolder;
	
	/**
	 * Number of received responses
	 */
	private int responseCounter;
	
	public PrintTestOutput(String name){
		
	}
	
	/**
	 * Resets directory and counter when new test is runned
	 *
	 * @param path
	 * @param id
	 */
	public void setOutputPath (String path,int id){
		
		ConsoleLog.Print("[TEXTPANEL] nastaveni: " + path + " "+ id);
		this.unitId = id;
		this.outputFolder =path;
		responseCounter = 0;
	}
	
	/**
	 * Receives data from test unit and writes it into a file
	 *
	 * @param data
	 * @param period
	 */
	public void onNewResponseEvent(HttpMessageData[] data, int period){
		
		// create directory for output when first response is received
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
				
				//create unique file name
				String  address =responseThreadNumber + "_"+ i + "_" +period ;
				
				//first response is received, so reset file content
				if(responseCounter == 0){
					 out = new FileOutputStream(outputPath+File.separator+"output.meta");
					 out1 = new FileOutputStream(outputPath+File.separator+"responseBody_"+address+".txt");
				//received more responses, so append the response to the existing file	 
				}else{
					 out = new FileOutputStream(outputPath+File.separator+"output.meta",true);
					 out1 = new FileOutputStream(outputPath+File.separator+"responseBody_"+address+".txt",true);
				}
				
				//create output string
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
	
	/**
	 * Comparing the received response body with expected and return a result string 
	 *  
	 * @param output
	 * @param expected
	 * @param contentType
	 * @return
	 */
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
