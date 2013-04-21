package testingUnit;

import data.HttpMessageData;
import data.TestCaseSettingsData;

public interface TestingUnit {
	
	public void run();
		
	
	public void setTest(HttpMessageData request, TestCaseSettingsData settings);
}

 