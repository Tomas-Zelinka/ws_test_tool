package settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
 * @author Tomas Zelinka 
 * @email xzelin15@stud.fit.vutbr.cz
 * 
 */
public class Settings {

	/**
	 * 
	 */
	private static Properties _settings; 
	private static final String _fileName = "./settings.properties";
	
	/*
	 * @param String fileName
	 * @return
	 */
	public static Properties readSettings(){
		
		_settings = new Properties();
		try{
		
			InputStream input = new FileInputStream(_fileName);
			_settings.load(input);
	
		}catch(FileNotFoundException ex){
			
			ex.printStackTrace();
		}
		catch(IOException ex){
			
			ex.printStackTrace();
		}
	
		return _settings;
	}
	
}
