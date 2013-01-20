package central;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import settings.Settings;


public class Perfomer implements Runnable{

	
	private Properties _settings;
	private Logger _logger;
	private FileHandler _loggerHandler;
	
	
	/*public static void main(String[] args) {
		
		//Perfomer program = new Perfomer();
		//program.run();
		Agent pitkin = new Agent();
		
		pitkin.run();
		
	}*/
	
	/**
	 * @param
	 * @return
	 */
	public Perfomer (){
			
		System.out.println("ahoj");
		_settings = Settings.readSettings();
		String wsdl = _settings.getProperty("wsdl");
		
		System.out.println(wsdl);
		initLogging();
	}
	
	/**
	 * @param
	 * @return
	 */
	public void run(){
		
		//_logger.entering(getClass().getName(), "run");
		String localFileName = "test.wsdl";
		String remoteFileName = "http://localhost/bookStore/BookStore?wsdl";
		File localFile = new File(localFileName);
		
		/*try{
			URL  remoteFile = new URL(remoteFileName);
		}catch(Exception e){
			_logger.log(Level.SEVERE, "Error doing XYZ", e);
			e.printStackTrace();
		}*/
		//_logger.exiting(getClass().getName(), "run");
		//_logger.log(Level.SEVERE, "Error doing XYZ");
		
				
			IOWSDL wsdl = new IOWSDL();
			
			wsdl.get();
			
			
	
		
		
	}
	
	
	/**
	 * @param
	 * @return
	 */
	private void initLogging()
	{
		
		
		
	}
}
