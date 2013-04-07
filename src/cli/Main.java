package cli;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.http.ParseException;





public class Main extends Options {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 3075255595808059043L;
		private Option help;
		private Options options;
		private CommandLineParser parser;
			
		public Main(){
		
			parser = new BasicParser();
			initOptions();
		}
		
		/**
		 * 
		 */
		private void initOptions(){
			
			this.help = new Option("help", "Napoveda");
			this.options = new Options();
			
			this.options.addOption(this.help);
		}
		
		/**
		 * 
		 * @param args
		 */
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
	
		/**
		 * 
		 * @param args
		 */
		public static void main(String[] args){
		
			Main parser = new Main();
		
			parser.parseOptions(args);
		
		}
	
	
}
