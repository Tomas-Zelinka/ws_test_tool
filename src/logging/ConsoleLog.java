package logging;

public class ConsoleLog {

	private static boolean state = false;
	
	public static void Print(String str){
		
		if (state)
			System.out.println(str);
	}
	
	
	public static void setConsoleLog(boolean input){
		state = input;
	}
}