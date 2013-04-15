package testingUnit;

import data.TestList;

public interface TestingUnit {
	
	public void runTestList();
		
	public TestList getTestList(); 

	public void setTestList(String path);
}

