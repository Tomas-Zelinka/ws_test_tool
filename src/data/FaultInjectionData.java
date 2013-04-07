/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package data;

import java.util.ArrayList;
import java.util.List;

import proxyUnit.HttpMessage;

/**
 * Trida reprezentuje uzivatelsky test obsahujici seznam pravidel, podle kterych je testovani webove 
 * sluzby provadeno.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class FaultInjectionData {
	
	//private int testId;
	private String testName;
	private String filePath;
	
	private List<TestStatement> statementList= new ArrayList<TestStatement>();

	private int newStatementId;
	
	public FaultInjectionData(String testName) {
		
		//this.testId= testId;
		this.testName= testName;
		this.newStatementId = 0;
	}

	/**
	 * Ziskani id testu.
	 * @return id testu
	 */
	//public int getTestId() {
		
	//	return testId;
	//}

	/**
	 * Metoda pro ziskani nazvu testu.
	 * @return nazev testu
	 */
	public String getTestName() {
		
		return testName;
	}
	
	

	/**
	 * Metoda pro nastaveni nazvu testu.
	 * @param testName novy nazev testu
	 */
	public void setTestName(String testName) {
		
		this.testName= testName;
	}

	
	/**
	 * Metoda pro ziskani cesty k souboru s testem.
	 * @return cesta k souboru
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Metoda pro nastaveni cesty k souboru s testem.
	 * @param filePath cesta k souboru
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
	public int getNewStatementId(){
		return this.newStatementId;
	}
	
	public TestStatement getFirstStatement(){
		
		return this.statementList.get(0);
	}
		
	
	/**
	 * Ziskani seznamu pravidel pro dany test.
	 * @return seznam pravidel
	 */
	public List<TestStatement> getStatementList() {
		
		return statementList;
	}
	
	/**
	 * Pridani noveho pravidla do seznamu pravidel pro dany test.
	 * @param statement nove pravidlo
	 */
	public void addToStatementList(TestStatement statement) {
		
		statementList.add(statement);
		this.newStatementId++;
	}
	
	/**
	 * Odstraneni pravidla ze seznamu.
	 * @param removedStatement pravidlo urcene k odstraneni
	 */
	public void removeFromStatementList(TestStatement removedStatement) {
		
		statementList.remove(removedStatement);
	}

	/**
	 * Prepsani metody toString() kvuli zobrazovani nazvu testu v komponente JTree.
	 * @return nazev testu
	 */	
	@Override
	public String toString() {
		
		return testName;
	}
	
	
	/**
	 * Metoda pro zahajeni aplikace testu.
	 * @param message http zprava
	 */
	public void applyTest(HttpMessage message) {
		
		//aplikujeme vsechna pravidla v danem testu
		for (TestStatement currentStatement : statementList)
			currentStatement.applyStatement(message);
	}
	
	
	
	
	
	
}
