package data;

import java.util.HashMap;

import logging.ConsoleLog;

public class TestList {
	
	private HashMap<Integer,String> testList; 
	private Integer caseCounter;	
	
	
	/**
	 * 
	 */
	public TestList(){
		
		testList = new HashMap<Integer,String>();
		caseCounter = new Integer(0);
	}
	
	/**
	 * 
	 */
	public void addTestCase(String path){
		
		ConsoleLog.Print(caseCounter.toString());
		this.testList.put(caseCounter++,path);
		
	}
		
	/**
	 * 
	 */
	public void removeTestCase(Integer id){
		
		ConsoleLog.Print("[TestList] Removing case: "+id);
		this.testList.remove(id);
		if(this.testList.isEmpty()){
			caseCounter = 0;
		}
	}
	
	/**
	 * 
	 */
	public HashMap<Integer,String> getTestCases(){
		
		return this.testList;
	}
	
	/**
	 * 
	 */
	public Integer getLastId(){
		
		return caseCounter-1;
	}
	
	
}
