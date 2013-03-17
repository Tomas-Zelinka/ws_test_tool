/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package proxyUnit;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Trida reprezentuje funkcni jednotku pro monitorovani komunikace mezi testovanou webovou sluzbou a jejim
 * okolim. Aplikace navenek pracuje jako bezny proxy server s tim, ze veskera zachycena komunikace bude predana
 * k analyze jednotce pro vkladani poruch.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class ProxyMonitoringUnit {
	
		
	private int proxyPort= 55555;
	private int testedWsPort= 8080;
	private String proxyHost= "localhost";
	
	private boolean proxyFlag;
	
	private Controller controller;
	
	private ServerSocket serverSocket;
	private Socket incomingSocket;
	private Socket outgoingSocket;
	
	private int interactionId= 0;

	
	public ProxyMonitoringUnit(Controller controller, int proxyPort, int testedWsPort, String proxyHost) {
		
		proxyFlag= false;
		this.controller= controller;
		this.proxyPort= proxyPort;
		this.testedWsPort= testedWsPort;
		this.proxyHost= proxyHost;
	}

	/**
	 * Metoda pro nastaveni id interakce.
	 * @param interactionId id interakce
	 */
	public void setInteractionId(int interactionId) {
		
		this.interactionId = interactionId;
	}
		

	/**
	 * Metoda pro ziskani URI proxy hostu
	 * @return proxy host URI
	 */
	public String getProxyHost() {
		
		return proxyHost;
	}

	/**
	 * Metoda pro ziskani portu, na kterem nasloucha proxy server.
	 * @return port proxy serveru
	 */
	public int getProxyPort() {
		
		return proxyPort;
	}

	/**
	 * Metoda pro ziskani portu, na kterem bezi testovana sluzba.
	 * @return port testovane sluzby
	 */
	public int getTestedWsPort() {
		
		return testedWsPort;
	}

	/**
	 * Metoda pro nastaveni portu, na kterem nasloucha testovana webova sluzba.
	 * @param proxyHost port testovane webove sluzby
	 */
	public void setProxyHost(String proxyHost) {
		
		this.proxyHost = proxyHost;
	}

	/**
	 * Metoda pro nastaveni portu, na kterem nasloucha proxy server.
	 * @param proxyPort port proxy serveru
	 */
	public void setProxyPort(int proxyPort) {
		
		this.proxyPort = proxyPort;
	}

	/**
	 * Metoda pro nastaveni URI proxy hostu.
	 * @param testedWsPort URI proxy hostu
	 */
	public void setTestedWsPort(int testedWsPort) {
		
		this.testedWsPort = testedWsPort;
	}
	
	
	
	private synchronized boolean isProxyFlag() {
		
		return proxyFlag;
	}

	private synchronized void setProxyFlag(boolean proxyFlag) {
		
		this.proxyFlag= proxyFlag;
	}
			
	
	/**
	 * Metoda pro spusteni proxy serveru.
	 */
	public void startProxy() {
		
		setProxyFlag(true);
		try {
			serverSocket= new ServerSocket(proxyPort);
			System.out.println("Proxy server is running at port " + proxyPort);
			
			//int interactionId= 0;
			while(isProxyFlag()) {
				//server vytvori pro kazde prichozi spojeni novy soket (blokujici operace)
				incomingSocket= serverSocket.accept();
//				if (!isProxyFlag())
//					break;
				//vytvoreni odchoziho soketu pro preposlani zpravy
				outgoingSocket= new Socket(proxyHost, testedWsPort);
				
				//vytvoreni dvou vlaken pro prichozi a odchozi spojeni
				ProxyThread inThread= new ProxyThread(interactionId, controller, incomingSocket, outgoingSocket);
				ProxyThread outThread= new ProxyThread(interactionId, controller, outgoingSocket, incomingSocket);
				inThread.start();
				outThread.start();
				interactionId++;
			}
			
			
		}
		//radne ukonceni proxy serveru..
		catch(SocketException ex) {
			//TODO: tohle se okamzite vyhodi, pokud nastaveny proxy port je jiz pouzivan jinou aplikaci
			System.out.println("Proxy server stopped");
		}
		//zadany host nenalezen..predame udalost GUI k zobrazeni dialogu
		catch(UnknownHostException ex) {
			controller.publishUnknownMessageEvent();
			stopProxy();
			
		}
		catch(IOException ex) {
			System.err.println(ex.getMessage());
			System.exit(-2);
		}
	}
	
	/**
	 * Metoda pro zavreni soketu a tim i zastaveni proxy serveru.
	 */
	public void stopProxy() {
		
		setProxyFlag(false);
		try {
			serverSocket.close();
			if (incomingSocket != null)
				incomingSocket.close();
			if (outgoingSocket != null)
				outgoingSocket.close();
		}
		catch(IOException ex) {
			System.err.println(ex.getMessage());
			System.exit(-1);
		}
	}
	
	public static void main(String[] args) {
		
//		ProxyMonitoringUnit proxyMonitor= new ProxyMonitoringUnit();
//		proxyMonitor.startProxy();
	}
}
