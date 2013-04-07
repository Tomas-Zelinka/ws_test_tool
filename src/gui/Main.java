package gui;


import javax.swing.SwingUtilities;

import logging.ConsoleLog;

/**
 * Main class for starting the GUI
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class Main {

	
	/**
	 * Head component of the GUI
	 */
	public static MainWindow main;
	
	/**
	 * @param args - Array of program arguments
	 */
	public static void main(String[] args) {
	
		ConsoleLog.setConsoleLog(true);
		try{
		SwingUtilities.invokeAndWait(new Runnable() {
			  public void run() {
				  main = new MainWindow();
				  main.setVisible(true);
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
		
		main.dispose();
		main = new MainWindow();
		main.setVisible(true);
		
	}

}
