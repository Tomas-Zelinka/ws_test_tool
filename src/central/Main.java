package central;


import gui.MainWindow;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.http.ParseException;

import logging.ConsoleLog;

/**
 * Main class for starting the GUI
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class Main extends Options{

	
	private ProxyController proxyController;
	

	private TestUnitController testUnitController;
	
	
	private Option help;
	private Options options;
	private CommandLineParser parser;
	
	
	
	/**
	 * Head component of the GUI
	 */
	
	public Main(){
		initOptions();
		initApplication();
	}
	/**
	 * @param args - Array of program arguments
	 */
	public static void main(String[] args) {
	
		Main main = new Main();
		ConsoleLog.setConsoleLog(true);
		
		main.parseOptions(args);
		
		try{
		SwingUtilities.invokeAndWait(new Runnable() {
			  public void run() {
				  MainWindow  gui = new MainWindow();
				  gui.setVisible(true);
		    }
		  });
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used when the change project navigator root is needed
	 * The method disposes Main window and makes new one with changed root  
	 */
	public static void restartGui(){
		
//		main.dispose();
//		main = new MainWindow();
//		main.setVisible(true);
		
	}
	
	/**
	 * 
	 */
	private void initOptions(){
		
		this.help = new Option("help", "Napoveda");
		this.options = new Options();
		
		this.options.addOption(this.help);
	}
	
	private void initApplication(){
		proxyController = new ProxyController();
		testUnitController = new TestUnitController();
	}
	
	public void parseOptions(String[] args){
		
		CommandLine line = null;
		try{
			
			line = parser.parse(this.options,args);
		}catch(ParseException e){
		
			System.err.println( "Parsing failed.  Reason: " + e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(line.hasOption( "help" ) ){
			
			System.out.println("udelam baf");
		}
		
	}

	
	
	public ProxyController getProxyController() {
		return proxyController;
	}
	public void setProxyController(ProxyController proxyController) {
		this.proxyController = proxyController;
	}

	
	public TestUnitController getTestUnitController() {
		return testUnitController;
	}
	public void setTestUnitController(TestUnitController testUnitController) {
		this.testUnitController = testUnitController;
	}
}
