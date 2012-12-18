package logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import central.Perfomer;

public class Reporter {
	
	private Logger _logger;
	private FileHandler _loggerFileHandler;
	
	
	public Reporter(){
		try {
			_logger = Logger.getLogger(Perfomer.class.getName());
			_loggerFileHandler = new FileHandler("ja.log",true);
			_logger.addHandler(_loggerFileHandler);
			_logger.setLevel(Level.ALL);
			SimpleFormatter formatter = new SimpleFormatter();
			_loggerFileHandler.setFormatter(formatter);
			
			
			_logger.log(Level.WARNING,"Logging initializied");
		} catch (SecurityException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	
}
