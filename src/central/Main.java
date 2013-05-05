package central;


import gui.MainWindow;

import javax.swing.SwingUtilities;

import logging.ConsoleLog;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import rmi.Server;

/**
 * Main class for starting the application
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class Main extends Options{

	
	/**
	 * Variable for serializing
	 */
	private static final long serialVersionUID = -6417256415716065905L;
	private UnitController unitController;
	private Option help;
	private Option gui;
	private Option rmiServer;
	private Options options;
	private CommandLineParser parser;
	private Server server;
	
	/**
	 * The main of the application
	 */
	public Main(){
		initOptions();
		
	}
	
	/**
	 * @param args - Array of program arguments
	 */
	public static void main(String[] args) {
		
		Main main = new Main();
		ConsoleLog.setConsoleLog(true);
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
	 * Option initialization
	 */
	private void initOptions(){
		
		this.help = new Option("h", "Napoveda");
		this.gui = new Option("g","gui");
		this.rmiServer = new Option("r","server");
		this.options = new Options();
		this.options.addOption(this.gui);
		this.options.addOption(this.help);
		this.options.addOption(this.rmiServer);
		
		this.parser = new BasicParser();
	}
	
	/**
	 * Head controllers initialization
	 */
	private void initApplication(){
		unitController = new UnitController();
	}
	
	
	/**
	 * Function for parsing command line arguments
	 * 
	 * @param args
	 */
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
			initApplication();
			System.out.println("gui");
			try{
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						MainWindow  gui = new MainWindow(getTestUnitController());
						gui.setVisible(true);
					}
				});
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
		if(line.hasOption( "r" ) ){
			initApplication();
			ConsoleLog.Print("server");
			this.server = new Server();
			server.run();
		}
		
	}

	
	/**
	 * 
	 * @return TestUnitController
	 */
	public UnitController getTestUnitController() {
		return unitController;
	}
	
	/**
	 * 
	 * @param testUnitController
	 */
	public void setTestUnitController(UnitController testUnitController) {
		this.unitController = testUnitController;
	}
}
