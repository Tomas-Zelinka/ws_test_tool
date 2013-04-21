package testingUnit;

import data.DataProvider;
import data.HttpMessageData;
import data.TestCaseSettingsData;
import data.TestList;

public class RemoteTestUnit implements TestingUnit {

	private TestList testList;
	private DataProvider reader;
	
	public RemoteTestUnit(){
		reader = new DataProvider();
	}
	
	
	public void run(){
		
	}
	
	
	
	public void setTest(HttpMessageData request, TestCaseSettingsData settings) {
		}
	
}
