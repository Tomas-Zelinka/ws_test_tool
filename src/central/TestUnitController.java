package central;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingWorker;

import proxyUnit.HttpInteraction;
import proxyUnit.NewMessageListener;

import testingUnit.LocalTestUnit;
import testingUnit.NewResponseListener;
import testingUnit.RemoteTestUnit;

public class TestUnitController {

	
	private Map<Integer,RemoteTestUnit> unitStorage;
	private LocalTestUnit localUnit;
	
	private List<NewResponseListener> newResponseListenerList= new ArrayList<NewResponseListener>();
	
	/**
	 * 
	 */
	public TestUnitController(){
		this.unitStorage = new HashMap<Integer,RemoteTestUnit>();
	}
	
	public void addResponseListener(NewResponseListener listener){
		newResponseListenerList.add(listener);
	}
	
	public void publishNewMessageEvent(String message) {
		
		for (NewResponseListener currentListener : newResponseListenerList)
			currentListener.onNewResponseEvent(message);
	}
	
	/**
	 * 
	 * @param key
	 */
	public void addRemoteUnit(Integer key){
		RemoteTestUnit newRemoteUnit = new RemoteTestUnit(); 
		unitStorage.put(key, newRemoteUnit);
	}
	
	/**
	 * 
	 */
	public void addLocalUnit(){
		this.localUnit = new LocalTestUnit(this);
	}
	
	/**
	 * 
	 * @param key
	 */
	public void removeRemoteUnit(Integer key){
		this.unitStorage.remove(key);
	
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public RemoteTestUnit getRemoteTestUnit(Integer key){
		
		return this.unitStorage.get(key);
	}
	
	 public void testPrint(){
		 System.out.println("ahoj");
	 }
	/**
	 * 
	 * @return
	 */
	public LocalTestUnit getLocalTestUnit(){
		return this.localUnit;
	}
	
	/**
	 * 
	 * @param path
	 * @param unitId
	 */
	public void runTestOnUnit(String path, int unitId){
		BackgroundWorker worker = new BackgroundWorker(path,unitId);
		worker.execute();
	}
	
	/**
	 * 
	 */
	public void runTestOnAllUnits(){
		
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
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
