package testingUnit;

import data.TestList;

public interface TestingUnit {
	
	public void run();
		
	public TestList getTestList(); 

	public void setTestList(String path);
}

 