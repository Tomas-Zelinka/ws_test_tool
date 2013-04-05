package testingUnit;

public class RequestWorker implements Runnable {

	
	private int name;
	private int count;
	private boolean test;
	
	public RequestWorker(int number, int count){
		this.name = number;
		this.count = count;
		test = true;
		System.out.println("Ahoj ja jsem vlakno" + this.name);
	}
	@Override
	public void run(){
		while(test){
			System.out.println("Ja jsem vlakno:" + this.name);
			count--;
			if(count < 1){
				test = false;
			}
		}
	}
}
