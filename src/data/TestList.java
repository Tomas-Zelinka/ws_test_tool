package data;

import java.util.HashMap;

public class TestList {
	
	private HashMap<Integer,String> testList; 
	private Integer caseCounter;	
	
	
	
	public TestList(){
		testList = new HashMap<Integer,String>();
		caseCounter = new Integer(0);
	}
	
	public void addTestCase(String path){
		System.out.println(caseCounter);
		this.testList.put(caseCounter++,path);
		
	}
	
	
	public void removeTestCase(Integer id){
		System.out.println("removing: "+id);
		this.testList.remove(id);
		if(this.testList.isEmpty()){
			caseCounter = 0;
		}
	}
	
	public HashMap<Integer,String> getTestCases(){
		return this.testList;
	}
	
	public Integer getLastId(){
		return caseCounter-1;
	}
	
	
}
