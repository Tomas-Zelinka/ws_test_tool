/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package proxyUnit;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import logging.ConsoleLog;
import data.FaultInjectionData;

/**
 * Trida reprezentuje funkcni jednotku pro monitorovani komunikace mezi testovanou webovou sluzbou a jejim
 * okolim. Aplikace navenek pracuje jako bezny proxy server s tim, ze veskera zachycena komunikace bude predana
 * k analyze jednotce pro vkladani poruch.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class ProxyMonitoringUnit {
	
		
	private int proxyPort= 55555;
	private int testedWsPort= 80;
	private String proxyHost= "localhost";
	private FaultInjector faultInjector;
	private boolean proxyFlag;
	
	private ServerSocket serverSocket;
	private Socket incomingSocket;
	private Socket outgoingSocket;

	
	private Integer interactionId= 0;
	private Map<Integer, HttpInteraction> interactionMap= new ConcurrentHashMap<Integer, HttpInteraction>();
	
	private List<NewMessageListener> newMessageListenerList= new ArrayList<NewMessageListener>();
	private List<UnknownHostListener> unknownHostListenerList= new ArrayList<UnknownHostListener>();
	
	
	public ProxyMonitoringUnit() {
		
		proxyFlag= false;
		faultInjector= new FaultInjector();
		
	}

		
	/**
	 * Metoda k ziskani noveho id pro novy test.
	 * @return nove id
	 */
	public int getNewTestId() {
		
		return faultInjector.getNewTestId();
	}
	
	/**
	 * Metoda k ziskani noveho id pro novou podminku.
	 * @return nove id
	 */
	public int getNewConditionId() {
		
		return faultInjector.getNewConditionId();
	}
	
	/**
	 * Metoda k ziskani noveho id pro novou poruchu.
	 * @return nove id
	 */
	public int getNewFaultId() {
		
		return faultInjector.getNewFaultId();
	}
	
	/**
	 * Metoda pro nastaveni aktivniho testu.
	 * @param activeTest aktivni test
	 */
	public void setActiveTest(FaultInjectionData activeTest) {
		
		faultInjector.setActiveTest(activeTest);
	}
	
	/**
	 * Metoda k ziskani aktivniho testu.
	 * @return aktivni test
	 */
	public FaultInjectionData getActiveTest() {
		
		return faultInjector.getActiveTest();
	}
	
	
	
	
	/**
	 * Metoda pro nastaveni id interakce.
	 * @param interactionId id interakce
	 */
	public void setInteractionId(int interactionId) {
		
		this.interactionId = interactionId;
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
	public void run() {
		
		setProxyFlag(true);
		try {
			serverSocket= new ServerSocket(this.proxyPort);
			System.out.println("Proxy server is running at port " + proxyPort);
			//int interactionId= 0;
			while(isProxyFlag()) {
				ConsoleLog.Print("[ProxyUnit] interakce" + interactionId);
				//server vytvori pro kazde prichozi spojeni novy soket (blokujici operace)
				incomingSocket= serverSocket.accept();
//				if (!isProxyFlag())
//					break;
				//vytvoreni odchoziho soketu pro preposlani zpravy
				outgoingSocket= new Socket(this.proxyHost, this.testedWsPort);
				
				//vytvoreni dvou vlaken pro prichozi a odchozi spojeni
				ProxyThread inThread= new ProxyThread(interactionId, this, incomingSocket, outgoingSocket);
				ProxyThread outThread= new ProxyThread(interactionId,this, outgoingSocket, incomingSocket);
				inThread.start();
				outThread.start();
				//ConsoleLog.Print("[ProxyUnit] interakce" + interactionId);
				interactionId++;
			}
			
			
		}
		//radne ukonceni proxy serveru..
		catch(SocketException ex) {
			//TODO: tohle se okamzite vyhodi, pokud nastaveny proxy port je jiz pouzivan jinou aplikaci
			
			System.out.println("Proxy server stopped:" + ex.getMessage());
		}
		//zadany host nenalezen..predame udalost GUI k zobrazeni dialogu
		catch(UnknownHostException ex) {
			this.publishUnknownMessageEvent();
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
	
	
	/**
	 * Metoda k ziskani seznamu interakci.
	 * @return seznam interakci
	 */
	public Map<Integer, HttpInteraction> getInteractionMap() {
		
		return interactionMap;
	}
	
	
	
	
	/**
	 * Metoda pro ziskani URI proxy hostu
	 * @return proxy host URI
	 */
	public String getProxyHost() {
		
		return this.getProxyHost();
	}

	/**
	 * Metoda pro ziskani portu, na kterem nasloucha proxy server.
	 * @return port proxy serveru
	 */
	public int getProxyPort() {
		
		return this.getProxyPort();
	}

	/**
	 * Metoda pro ziskani portu, na kterem bezi testovana sluzba.
	 * @return port testovane sluzby
	 */
	public int getTestedWsPort() {
		
		return this.getTestedWsPort();
	}
	
	
	/**
	 * Metoda pro nastaveni portu, na kterem nasloucha testovana webova sluzba.
	 * @param proxyHost port testovane webove sluzby
	 */
	public void setProxyHost(String host) {
		
		this.proxyHost = host ;
	}

	/**
	 * Metoda pro nastaveni portu, na kterem nasloucha proxy server.
	 * @param proxyPort port proxy serveru
	 */
	public void setProxyPort(int port) {
		
		this.proxyPort = port;
	}

	/**
	 * Metoda pro nastaveni URI proxy hostu.
	 * @param testedWsPort URI proxy hostu
	 */
	public void setTestedWsPort(int port) {
		
		this.testedWsPort =port;
	}
		
	
	/**
	 * Metoda pro zaregistrovani odberatele udalosti o nove zprave (soucast navrhoveho vzoru Observer).
	 * @param listener odberatel
	 */
	public void addNewMessageListener(NewMessageListener listener) {
		
		newMessageListenerList.add(listener);
	}
	
	/**
	 * Metoda pro upozorneni vsech odberatelu o udalosti prichodu nove zpravy (soucast navrhoveho vzoru
	 * Observer).
	 */
	private void publishNewMessageEvent(int interactionId, HttpInteraction interaction) {
		
		for (NewMessageListener currentListener : newMessageListenerList)
			currentListener.onNewMessageEvent(interactionId, interaction);
	}
	
	/**
	 * Metoda pro zaregistrovani odberatele udalosti o nove zpravy (soucast navrhoveho vzoru Observer).
	 * @param listener odberatel
	 */
	public void addUnknownHostListener(UnknownHostListener listener) {
		
		unknownHostListenerList.add(listener);
	}
	
	/**
	 * Metoda pro upozorneni vsech odberatelu o udalosti prichodu nove zpravy (soucast navrhoveho vzoru
	 * Observer). Protected...je volano z ProxyMonitoringUnit.
	 */
	public void publishUnknownMessageEvent() {
		
		for (UnknownHostListener currentListener : unknownHostListenerList)
			currentListener.onUnknownHostEvent();
	}
	
	/**
	 * Metoda pro oznameni z proxy monitoru o prichodu nove zpravy. Vytvori se reprezentace interakce
	 * obsahujici httpRequest a prislusny httpResponse. Nasledne je zprava predana injektoru poruch
	 * a pote jsou publikovany zmeny pro vsechny odberatele.
	 * @param interactionId id interakce
	 * @param httpMessage nova zprava
	 */
	public synchronized  void newMessageNotifier(int interactionId, HttpMessage httpMessage) {
		
		//SITUACE KDY SPOJENI NENI RADNE UKONCENO A PROXY VLAKNA BEZI
		//je potreba inkrementovat id interakce..jinak se bude v GUI prepisovat porad stejny radek
//		while (interactionMap.get(interactionId) != null && interactionMap.get(interactionId).getHttpRequest() != null &&
//				interactionMap.get(interactionId).getHttpResponse() != null) {
//			
//			interactionId++;
//			this.setInteractionId(interactionId);
//		}
		
		
		int port = httpMessage.getInitiatorPort();
			
		//ConsoleLog.Print(""+port);
		HttpInteraction interaction;
		//pokud v mape dosud neni prislusna http interakce..vytvorime novou
		if (!interactionMap.containsKey(port) ){
			interaction= new HttpInteraction();
			if (httpMessage instanceof HttpRequest)
				interaction.setHttpRequest((HttpRequest) httpMessage);
			else
				interaction.setHttpResponse((HttpResponse) httpMessage);
			
			//vlozeni objektu interakce do mapy
			interactionMap.put(port, interaction);
		}
		//pokud v mape jiz existuje tato interakce..pridame k ni prislusny request/response
		else {
			interaction= interactionMap.get(port);
			if (httpMessage instanceof HttpRequest)
				interaction.setHttpRequest((HttpRequest) httpMessage);
			else
				interaction.setHttpResponse((HttpResponse) httpMessage);
		}
		
		//aplikujeme pripadne poruchy do zpravy
		faultInjector.applyTest(httpMessage);
		
		//publikujeme zmeny v mape interakci
		publishNewMessageEvent(port, interaction);
		
//		System.out.println("*********************");
//		System.out.println("changedContent" + httpMessage.getChangedContent());
//		System.out.println("*********************");
		
		
		
	}
	
	
	
}
