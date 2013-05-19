package cz.vutbr.fit.dp.xzelin15.central;



import javax.swing.SwingUtilities;


import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import cz.vutbr.fit.dp.xzelin15.cli.TextMonitor;
import cz.vutbr.fit.dp.xzelin15.gui.MainWindow;
import cz.vutbr.fit.dp.xzelin15.logging.ConsoleLog;
import cz.vutbr.fit.dp.xzelin15.rmi.Server;


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
	
	private CommandLineParser parser;
	private Server server;
	private TextMonitor textMonitor;
	private UnitController unitController;
	
	
	/**
	 * Variables for options
	 */
	private Options options;
	private Option help;
	private Option gui;
	private Option rmiServer;
	private Option runTestList;
	private Option runProxy;
	private Option configuration;
	
	
	public static final String DEFAULT_ROOT_DIR = "data";
	public static final String DEFAULT_CONFIG_DIR =  "config";
	
	/**
	 * The main of the application
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
	 * Function for parsing command line arguments
	 * 
	 * @param args
	 */
	public void parseOptions(String[] args){
		
		CommandLine line = null;
		
		boolean hasConfiguration = false;
		String configFilePath = "";
		
		try{ 
			
			line = parser.parse(this.options,args);
		}catch(ParseException e){
			
			ConsoleLog.Message( "Parsing failed. \nReason: " + e.getMessage());
			System.exit(-1);
		}catch(Exception e){
			
			ConsoleLog.Message(e.getMessage());
			System.exit(-1);
		}
		
		//parameter for configuration file
		if(line.hasOption("config")){
			
			hasConfiguration = true;
			configFilePath = line.getOptionValue("config");
			ConsoleLog.Print("[Main] configFile: "+ configFilePath);
		}
		
		//parameter for help
		if(line.hasOption( "h" ) ){
			
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "run.sh [rgh]", this.options );
			System.exit(0);
			
		}
		
		//parameter for GUI
		else if(line.hasOption("g")){
			
			ConsoleLog.Print("[Main] GUI option ");
			try{
				
				//Rendering GUI will wait until all parts will be loaded
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
		
		//parameter for RMI server
		else if(line.hasOption( "r" ) ){
			
			ConsoleLog.Print("[Main] Server");
			this.server = new Server();
			server.run();
		}	

		//parameter for testlist run
		else if(line.hasOption("testlist")){
			
			String suitePath = line.getOptionValue("testlist");
			
			if(hasConfiguration){ //distributed run
				
				this.textMonitor.runTestList(suitePath,configFilePath);
				ConsoleLog.Print("[Main] Run remote testlist: "+ suitePath);
			}else{				//local run
				
				this.textMonitor.runTestList(suitePath);
				ConsoleLog.Print("[Main] Run local testlist: "+ suitePath);
			}
			System.exit(0);// 
		}
		
		//parameter to run proxy
		else if(line.hasOption("proxy")){
			
			String casePath = line.getOptionValue("proxy");
			
			if(hasConfiguration){  // distributed run
				
				this.textMonitor.runProxy(casePath,configFilePath);
				ConsoleLog.Print("[Main] Run remote proxies: "+ casePath);
			}else{				   // local run	
				
				this.textMonitor.runProxy(casePath);
				ConsoleLog.Print("[Main] Run local proxy: "+ casePath);
			}
		}else{// if no arguments are given, the hel is print
		
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "run.sh [rgh]", this.options );
			System.exit(0);
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
	
	
	/**
	 * Option initialization
	 */
	@SuppressWarnings("static-access")
	private void initOptions(){
		
		this.help = new Option("h", "Print this help");
		this.gui = new Option("g","Run tool with graphic user interfaces");
		this.rmiServer = new Option("r","Run RMI server and starts RMI registry on default port 1099");
		this.options = new Options();
		this.runTestList = OptionBuilder.withArgName("suitepath")
										.hasArg()
										.withDescription("Run test list from given path")
										.create("testlist");
		
		this.runProxy = OptionBuilder.withArgName("casepath")
									 .hasArg()
									 .withDescription("Run proxy with settings from given path")
									 .create("proxy");
		
		this.configuration = OptionBuilder.withArgName("configFile")
				 .hasArg()
				 .create("config");
		
		
		this.options.addOption(this.help);
		this.options.addOption(this.gui);
		this.options.addOption(this.rmiServer);
		this.options.addOption(this.runTestList);
		this.options.addOption(this.runProxy);
		this.options.addOption(this.configuration);
		
		
		this.parser = new BasicParser();
	}
	
	/**
	 * Head controllers initialization
	 */
	private void initApplication(){
		
		this.unitController = new UnitController();
		this.textMonitor = new TextMonitor(this.unitController);
	}
	
	
}
