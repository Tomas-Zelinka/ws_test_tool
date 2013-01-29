package gui;



public class Main {

	/**
	 * @param args
	 */
	
	public static MainWindow main;
	
	public static void main(String[] args) {
	
		main = new MainWindow();
		
		main.setVisible(true);
			
		
	}
	
	public static void restartGui(){
		
		main.dispose();
		main = new MainWindow();
		main.setVisible(true);
		
		
		
	}

}
