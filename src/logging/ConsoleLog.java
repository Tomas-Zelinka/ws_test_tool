package logging;

/**
 * 
 * Class responsible for printing tool messages and debugging informations
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class ConsoleLog {

	/**
	 * Use debug log flag
	 */
	private static boolean state = false;
	
	/**
	 * 
	 *  Debug output
	 *  
	 * @param str
	 */
	public static void Print(String str){
		
		if (state)
			System.out.println(str);
	}
	
	/**
	 * 
	 * Set the debug flag
	 * 
	 * @param input
	 */
	public static void setConsoleLog(boolean input){
		state = input;
	}
	
	/**
	 * 
	 * Tool message output
	 * 
	 * @param str
	 * 
	 */
	public static void Message(String str){
		System.out.println("System message: "+str);
	}
} 
