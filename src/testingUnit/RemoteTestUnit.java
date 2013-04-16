package testingUnit;

import data.DataProvider;
import data.TestList;

public class RemoteTestUnit implements TestingUnit {

	private TestList testList;
	private DataProvider reader;
	
	public RemoteTestUnit(){
		reader = new DataProvider();
	}
	
	
	public void run(){
		
	}
	
	
	public TestList getTestList() {
		return testList;
	}

	public void setTestList(String path) {
		this.testList = (TestList) reader.readObject(path);
	}
	
}
