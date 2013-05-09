package data;

import java.io.File;
import java.util.ArrayList;

import logging.ConsoleLog;

public class TestList {
	
	private ArrayList<String> testList; 
	public static final String filename = File.separator+"testlist.xml";
	
	
	/**
	 * 
	 */
	public TestList(){
		
		testList = new ArrayList<String>();
		
	}
	
	/**
	 * 
	 */
	public void addTestCase(String path){
		
		this.testList.add(path);
		
	}
		
	/**
	 * 
	 */
	public void removeTestCase(Integer id){
		
		ConsoleLog.Print("[TestList] Removing case: "+id);
		this.testList.remove(id);
		
	}
	
	/**
	 * 
	 */
	public ArrayList<String> getTestCases(){
		
		return this.testList;
	}
	
	public void setTestCases(ArrayList<String> data){
		
		this.testList = data;
	}
	
	
}
