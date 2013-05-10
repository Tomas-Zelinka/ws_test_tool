package central;


import gui.MainWindow;

import javax.swing.SwingUtilities;

import logging.ConsoleLog;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
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
	private Options options;
	private Option help;
	private Option gui;
	private Option rmiServer;
	private Option runTest;
	private Option runTestList;
	private Option runProxy;
	private CommandLineParser parser;
	private Server server;
	
	public static final String DEFAULT_ROOT_DIR = "data";
	public static final String DEFAULT_CONFIG_DIR =  "config";
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
	@SuppressWarnings("static-access")
	private void initOptions(){
		
		this.help = new Option("h", "Print this help");
		this.gui = new Option("g","Run tool with graphic user interfaces");
		this.rmiServer = new Option("r","Run RMI server and starts RMI registry on default port 1099");
		this.options = new Options();
		this.runTest = OptionBuilder.withArgName("runtest")
									.hasArg()
									.withDescription("Run test case from given path")
									.create("test");
		
		this.runTestList = OptionBuilder.withArgName("runtestList")
										.hasArg()
										.withDescription("Run test list from given path")
										.create("testlist");
		
		this.runProxy = OptionBuilder.withArgName("runproxy")
									 .hasArg()
									 .withDescription("Run proxy with settings from given path")
									 .create("runproxy");
		
		
		this.options.addOption(this.gui);
		this.options.addOption(this.help);
		this.options.addOption(this.rmiServer);
		this.options.addOption(this.runTest);
		this.options.addOption(this.runTestList);
		this.options.addOption(this.runProxy);
		
		
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
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "ant", this.options );
		}
		
		if(line.hasOption("g")){
			initApplication();
			System.out.println("[Main] GUI option ");
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
			ConsoleLog.Print("[Main] Server");
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
