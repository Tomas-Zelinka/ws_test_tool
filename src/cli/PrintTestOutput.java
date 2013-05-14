package cli;

import data.HttpMessageData;

public class PrintTestOutput {
	
	private String outputFolder;
	
	public PrintTestOutput(String name){
		this.outputFolder = name;
	}
	
	public void onNewResponseEvent(HttpMessageData[] data, int period){
		
	}
}
