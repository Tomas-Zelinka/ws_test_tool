/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package proxyUnit;

import java.util.List;
import org.jdom2.JDOMException;

import data.Fault;
import data.MultiplicationFault;
import data.Test;
import data.TestStatement;
import data.XPathCorruptionFault;

/**
 * Hlavni trida zajistujici pozmeneni dane HTTP zpravy za ucelem vlozeni nakonfigurovanych poruch.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class FaultInjector {
	
	private int newTestId;
	private int newTestStatementId;
	private int newConditionId;
	private int newFaultId;
	
	private Test activeTest;
		
	//private List<Test> testList= new ArrayList<Test>();
	private List<Test> testList;
	
	
	public FaultInjector() {
	
		try {
			Test test0= new Test("Test0");
			TestStatement statement0= new TestStatement(0, "Statement0");
			Fault fault0= new XPathCorruptionFault("/S:Envelope/S:Body/mult/i", "9");
			statement0.addToFaultList(fault0);
			test0.addToStatementList(statement0);
			
			Test test1= new Test("Test1");
			TestStatement statement1= new TestStatement(1, "Statement1");
			Fault fault1= new MultiplicationFault("/S:Envelope/S:Body", 1);
			statement1.addToFaultList(fault1);
			test1.addToStatementList(statement1);




			

			testList.add(test0);
			testList.add(test1);

			initNewIds();
		}
		catch (JDOMException ex) {

		}
		
		
	}

	
	public FaultInjector(List<Test> testList) {
		
		this.testList = testList;
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
	 * Metoda pro pridani noveho testu do kolekce.
	 * @param newTest novy test
	 */
	public void addToTestList(Test newTest) {
		
		testList.add(newTest);
	}
	
	/**
	 * Metoda pro odstraneni testu z kolekce.
	 * @param removedTest test k odstraneni
	 */
	public void removeFromTestList(Test removedTest) {
		
		testList.remove(removedTest);
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
	 * Metoda pro ziskani id pro nove pravidlo.
	 * @return id noveho pravidla
	 */
	public int getNewTestStatementId() {
		
		return newTestStatementId++;
	}

	/**
	 * Metoda pro zjisteni prave beziciho testu.
	 * @return prave bezici test
	 */
	public Test getActiveTest() {
		
		return activeTest;
	}
			
	
	/**
	 * Metoda pro ziskani seznamu vsech testu.
	 * @return seznam vsech testu
	 */
	public List<Test> getTestList() {
		
		return testList;
	}

	/**
	 * Metoda pro nastaveni aktivniho testu.
	 * @param activeTest aktivni test
	 */
	public void setActiveTest(Test activeTest) {
		
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
