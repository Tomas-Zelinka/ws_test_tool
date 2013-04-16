package central;

import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingWorker;

import logging.ConsoleLog;
import testingUnit.LocalTestUnit;
import testingUnit.RemoteTestUnit;

public class TestUnitController {

	
	private Map<Integer,RemoteTestUnit> unitStorage;
	private LocalTestUnit localUnit;
	
	
	
	
	public TestUnitController(){
		this.unitStorage = new HashMap<Integer,RemoteTestUnit>();
	}
	
	public void addRemoteUnit(Integer key){
		RemoteTestUnit newRemoteUnit = new RemoteTestUnit(); 
		unitStorage.put(key, newRemoteUnit);
	}
	
	public void addLocalUnit(){
		this.localUnit = new LocalTestUnit();
	}
	
	
	public void removeRemoteUnit(Integer key){
		this.unitStorage.remove(key);
	
	}
	
	public RemoteTestUnit getRemoteTestUnit(Integer key){
		
		return this.unitStorage.get(key);
	}
	
	public LocalTestUnit getLocalTestUnit(){
		return this.localUnit;
	}
	
	
	
	public void runTestOnUnit(String path, int unitId){
		BackgroundWorker worker = new BackgroundWorker(path,unitId);
		worker.execute();
	}
	
	public void runTestOnAllUnits(){
		
	}
	
	private class BackgroundWorker extends SwingWorker<String,Void>{
		
		private String path;
		private int id;
		
		
		BackgroundWorker(String path, int unitId){
			this.path = path;
			this.id = unitId;
		}
		
		public String doInBackground(){
			if(id == 0){
				localUnit.setTestList(path);
				localUnit.run();
			}else{
				
			}
			return "";
		}
	}
		
}
