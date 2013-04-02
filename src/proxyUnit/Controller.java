/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package proxyUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import data.DataProvider;
import data.Test;

import proxyUnit.HttpInteraction;

/**
 * Trida controller zajistuje reakci aplikacni logiky na uzivatelske udalosti generovane v GUI.
 * V podstate predstavuje vzor fasadu pro pristup k business vrstve.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class Controller {
	
	
	//<editor-fold defaultstate="collapsed" desc="MainProxyThread class">
	/**
	 * Pomocna vnorena trida reprezentujici hlavni vlakno proxy serveru naslouchajici na zadanem portu.
	 */
	public class MainProxyThread extends Thread {
		
		@Override
		public void run() {
			
			proxyMonitor.startProxy();
		}
		
	}
	//</editor-fold>
	
		
	
	private ProxyMonitoringUnit proxyMonitor;
	private Thread proxyThread;
	private FaultInjector faultInjector;
	
	//private List<HttpInteraction> interactionList= new ArrayList<HttpInteraction>();
	//private Map<Integer, HttpInteraction> interactionMap= new HashMap<Integer, HttpInteraction>();
	private Map<Integer, HttpInteraction> interactionMap= new ConcurrentHashMap<Integer, HttpInteraction>();
		
	private List<NewMessageListener> newMessageListenerList= new ArrayList<NewMessageListener>();
	private List<UnknownHostListener> unknownHostListenerList= new ArrayList<UnknownHostListener>();
	
	
	public Controller() {
		
		//ziskame nastaveni z XML
		Settings settings= DataProvider.deserializeSettingsFromXML();
		
		//ziskame seznam testu z XML
		List<Test> testList= DataProvider.deserializeTestsFromXML(settings.getFilePathList());
		
		//inicializace proxy monitorovaci jednotky
		proxyMonitor= new ProxyMonitoringUnit(this, settings.getProxyPort(), settings.getTestedWsPort(),
				settings.getProxyHost());
		
		//inicializace injektoru chyb
		faultInjector= new FaultInjector(testList);
	}

	/**
	 * Metoda k ziskani seznamu interakci.
	 * @return seznam interakci
	 */
	public Map<Integer, HttpInteraction> getInteractionMap() {
		
		return interactionMap;
	}
	
	/**
	 * Metoda pro pridani noveho testu do kolekce.
	 * @param newTest novy test
	 */
	public void addToTestList(Test newTest) {
		
		faultInjector.addToTestList(newTest);
	}
	
	/**
	 * Metoda pro odstraneni testu z kolekce.
	 * @param removedTest test k odstraneni
	 */
	public void removeTestFromList(Test removedTest) {
		
		faultInjector.removeFromTestList(removedTest);
	}
	
	/**
	 * Metoda k ziskani seznamu testu z injektoru poruch.
	 * @return seznam testu
	 */
	public List<Test> getTestList() {
		
		return faultInjector.getTestList();
	}
	
	/**
	 * Metoda k ziskani noveho id pro novy test.
	 * @return nove id
	 */
	public int getNewTestId() {
		
		return faultInjector.getNewTestId();
	}
	
	/**
	 * Metoda k ziskani noveho id pro nove pravidlo.
	 * @return nove id
	 */
	public int getNewTestStatementId() {
		
		return faultInjector.getNewTestStatementId();
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
	public void setActiveTest(Test activeTest) {
		
		faultInjector.setActiveTest(activeTest);
	}
	
	/**
	 * Metoda k ziskani aktivniho testu.
	 * @return aktivni test
	 */
	public Test getActiveTest() {
		
		return faultInjector.getActiveTest();
	}
	
	
	/**
	 * Metoda pro ziskani URI proxy hostu
	 * @return proxy host URI
	 */
	public String getProxyHost() {
		
		return proxyMonitor.getProxyHost();
	}

	/**
	 * Metoda pro ziskani portu, na kterem nasloucha proxy server.
	 * @return port proxy serveru
	 */
	public int getProxyPort() {
		
		return proxyMonitor.getProxyPort();
	}

	/**
	 * Metoda pro ziskani portu, na kterem bezi testovana sluzba.
	 * @return port testovane sluzby
	 */
	public int getTestedWsPort() {
		
		return proxyMonitor.getTestedWsPort();
	}
	
	
	/**
	 * Metoda pro nastaveni portu, na kterem nasloucha testovana webova sluzba.
	 * @param proxyHost port testovane webove sluzby
	 */
	public void setProxyHost(String proxyHost) {
		
		proxyMonitor.setProxyHost(proxyHost);
	}

	/**
	 * Metoda pro nastaveni portu, na kterem nasloucha proxy server.
	 * @param proxyPort port proxy serveru
	 */
	public void setProxyPort(int proxyPort) {
		
		proxyMonitor.setProxyPort(proxyPort);
	}

	/**
	 * Metoda pro nastaveni URI proxy hostu.
	 * @param testedWsPort URI proxy hostu
	 */
	public void setTestedWsPort(int testedWsPort) {
		
		proxyMonitor.setTestedWsPort(testedWsPort);
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
	protected void publishUnknownMessageEvent() {
		
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
	public void newMessageNotifier(int interactionId, HttpMessage httpMessage) {
		
		//SITUACE KDY SPOJENI NENI RADNE UKONCENO A PROXY VLAKNA BEZI
		//je potreba inkrementovat id interakce..jinak se bude v GUI prepisovat porad stejny radek
		while (interactionMap.get(interactionId) != null && interactionMap.get(interactionId).getHttpRequest() != null &&
				interactionMap.get(interactionId).getHttpResponse() != null) {
			
			interactionId++;
			proxyMonitor.setInteractionId(interactionId);
		}
			
		
		HttpInteraction interaction;
		//pokud v mape dosud neni prislusna http interakce..vytvorime novou
		if (!interactionMap.containsKey(interactionId)) {
			interaction= new HttpInteraction();
			if (httpMessage instanceof HttpRequest)
				interaction.setHttpRequest((HttpRequest) httpMessage);
			else
				interaction.setHttpResponse((HttpResponse) httpMessage);
			
			//vlozeni objektu interakce do mapy
			interactionMap.put(interactionId, interaction);
		}
		//pokud v mape jiz existuje tato interakce..pridame k ni prislusny request/response
		else {
			interaction= interactionMap.get(interactionId);
			if (httpMessage instanceof HttpRequest)
				interaction.setHttpRequest((HttpRequest) httpMessage);
			else
				interaction.setHttpResponse((HttpResponse) httpMessage);
		}
		
		//aplikujeme pripadne poruchy do zpravy
		faultInjector.applyTest(httpMessage);
		
		//publikujeme zmeny v mape interakci
		publishNewMessageEvent(interactionId, interaction);
		
//		System.out.println("*********************");
//		System.out.println("changedContent" + httpMessage.getChangedContent());
//		System.out.println("*********************");
		
		
		
	}
	
	/**
	 * Vytvoreni noveho vlakna a spusteni proxy serveru.
	 */
	public void startProxy() {
		
		proxyThread= new MainProxyThread();
		proxyThread.start();
	}
	
	/**
	 * Ukonceni proxy serveru.
	 */
	public void stopProxy() {
		
		proxyMonitor.stopProxy();
	}
	
	
	/**
	 * Seralizace vsech testu a uzivatelskych nastaveni do XML souboru ve slozce dbdata.
	 */
	public void saveDataToXML() {
		
		//serializujeme vsechny testy ze seznamu
		DataProvider.serializeTestsToXML(faultInjector.getTestList());
		
		//vytvorime seznam vsechy cest k souborum s testy
		List<String> filePathList= new ArrayList<String>();
		List<Test> testList= faultInjector.getTestList();
		for (Test currentTest : testList) {
			filePathList.add(currentTest.getFilePath());
		}
		
		//serializujeme soubor settings.xml s nastavenim
		Settings settings= new Settings(proxyMonitor.getProxyPort(), proxyMonitor.getTestedWsPort(),
				proxyMonitor.getProxyHost(), filePathList);
		DataProvider.serializeSettingsToXML(settings);
		
		
	}
	
	
	
	
}
