package data;

import java.util.ArrayList;

public class TestList {
	
	private ArrayList<String> testList; 
		
	public TestList(){
		testList = new ArrayList<String>();
	}
	
	public void addTestCase(String path){
		this.testList.add(path);
		
	}
	
	
	public void removeTestCase(String path){
		this.removeTestCase(path);
	}
	
	public ArrayList<String> getTestCases(){
		return this.testList;
	}
}
