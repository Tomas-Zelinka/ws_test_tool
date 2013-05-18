/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package proxyUnit;

import data.FaultInjectionData;

/**
 * Hlavni trida zajistujici pozmeneni dane HTTP zpravy za ucelem vlozeni nakonfigurovanych poruch.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class FaultInjector {
	
	private int newTestId;
	private int newConditionId;
	private int newFaultId;
	
	private FaultInjectionData activeTest;
		
	public FaultInjector() {
		
		
	}

	
	/**
	 * Metoda pro ziskani id pro novou podminku.
	 * @return id nove podminky
	 */
	public int getNewConditionId() {
		
		return newConditionId++;
	}

	/**
	 * Metoda pro ziskani id pro novou poruchu.
	 * @return id nove poruchy
	 */
	public int getNewFaultId() {
		
		return newFaultId++;
	}

	/**
	 * Metoda pro ziskani id pro novy test.
	 * @return id noveho testu
	 */
	public int getNewTestId() {
		
		return newTestId++;
	}

	/**
	 * Metoda pro zjisteni prave beziciho testu.
	 * @return prave bezici test
	 */
	public FaultInjectionData getActiveTest() {
		
		return activeTest;
	}
			
	
	/**
	 * Metoda pro nastaveni aktivniho testu.
	 * @param activeTest aktivni test
	 */
	public void setActiveTest(FaultInjectionData activeTest) {
		
		this.activeTest= activeTest;
	}
	
	
	
	/**
	 * Metoda pro aplikovani aktivniho testu na prichozi zpravu.
	 * @param message prichozi zprava
	 */
	public void applyTest(HttpMessage message) {
		
		//nejprve zkopirujeme puvodni obsah do pozmeneneho
		String content= message.getContent();
		
		//(new String())...chceme kopii retezcu..ne odkaz
		message.setChangedContent(new String(content));
				
		
		//pokud byl vybran nektery test..aplikujeme jej
		if (activeTest != null)
			activeTest.applyTest(message);
		
		//aktualizujeme Content-Length v hlavicce
		HttpMessageParser.replaceContentLength(message);
	}
	
	
	
	
	
	
	
}
