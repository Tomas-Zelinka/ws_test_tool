package central;


import gui.MainWindow;

import javax.swing.SwingUtilities;

import logging.ConsoleLog;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.http.ParseException;

/**
 * Main class for starting the GUI
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class Main extends Options{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6417256415716065905L;


	private ProxyController proxyController;
	

	private TestUnitController testUnitController;
	
	
	private Option help;
	private Option gui;
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
		ConsoleLog.setConsoleLog(false);
		
		main.parseOptions(args);
		
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
		
		this.help = new Option("h", "Napoveda");
		this.gui = new Option("g","gui");
		
		this.options = new Options();
		this.options.addOption(this.gui);
		this.options.addOption(this.help);
		
		this.parser = new BasicParser();
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
		
			ConsoleLog.Print( "Parsing failed.  Reason: " + e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(line.hasOption( "h" ) ){
			
			ConsoleLog.Print("Napoveda");
		}
		
		
		if(line.hasOption("g")){
			System.out.println("gui");
			try{
			SwingUtilities.invokeAndWait(new Runnable() {
				  public void run() {
					  MainWindow  gui = new MainWindow(getProxyController(),getTestUnitController());
					  gui.setVisible(true);
			    }
			  });
			}catch (Exception e){
				e.printStackTrace();
			}
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
