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
		
		initNewIds();
	}
	
	
	
	
	/**
	 * Metoda pro inicializaci id atributu pro nove objekty modelu podminek a poruch. Ve vsech kolekcich
	 * je nalezeno nejvyssi id.
	 */
	private void initNewIds() {
		
//		int highestTestId= -1;
//		int highestStatementId= -1;
//		int highestConditionId= -1;
//		int highestFaultId= -1;
//		
//		for (Test currentTest : testList) {
//			int currentTestId= currentTest.getTestId();
//			if (currentTestId > highestTestId)
//				highestTestId= currentTestId;
//			
//			for (TestStatement currentStatement : currentTest.getStatementList()) {
//				int currentStatementId= currentStatement.getStatementId();
//				if (currentStatementId > highestStatementId)
//					highestStatementId= currentStatementId;
//				
//				for (Condition currentCondition : currentStatement.getConditionSet()) {
//					int currentConditionId= currentCondition.getConditionId();
//					if (currentConditionId > highestConditionId)
//						highestConditionId= currentConditionId;
//				}
//				
//				for (Fault currentFault : currentStatement.getFaultList()) {
//					int currentFaultId= currentFault.getFaultId();
//					if (currentFaultId > highestFaultId) 
//						highestFaultId= currentFaultId;
//				}
//			}
//		}
//		//inicializace novych id
//		newTestId= highestTestId + 1;
//		newTestStatementId= highestStatementId + 1;
//		newConditionId= highestConditionId + 1;
//		newFaultId= highestFaultId;
					
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
		String formattedContent= message.getFormattedContent();
		
		//(new String())...chceme kopii retezcu..ne odkaz
		message.setChangedContent(new String(content));
		message.setChangedFormattedContent(new String(formattedContent));
				
		
		//pokud byl vybran nektery test..aplikujeme jej
		if (activeTest != null)
			activeTest.applyTest(message);
		
		//aktualizujeme Content-Length v hlavicce
		HttpMessageParser.replaceContentLength(message);
	}
	
	
	
	
	
	
	
}
