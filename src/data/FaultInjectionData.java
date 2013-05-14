/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package data;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import proxyUnit.HttpMessage;


/**
 * Trida predstavuje testovaci pravidlo sestavajici z mnoziny podminek a seznamu poruch.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class FaultInjectionData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1225218084143065994L;
	
	private int testId;
	private String testName;
	private Set<Condition> conditionSet= new HashSet<Condition>();
	private List<Fault> faultList= new ArrayList<Fault>();

	public static final String filename = File.separator+"FaultInjection"+File.separator+"input"+File.separator+"faultInjection.xml";
	public static final String outputFolder = File.separator+"FaultInjection"+File.separator+"output";
	
	public FaultInjectionData( String name) {
		
		this.testName = name;
	}

	
	/**
	 * Metoda pro ziskani nazvu pravidla.
	 * @return nazev pravidla
	 */
	public int getTestId() {
		
		return testId;
	}

	
	/**
	 * Metoda pro nastaveni nazvu pravidla.
	 * @param statementName nazev pravidla
	 */
	public String getTestName() {
		
		return this.testName;
	}
	
	
	/**
	 * Metoda pro nastaveni nazvu pravidla.
	 * @param statementName nazev pravidla
	 */
	public void setTestName(String testName) {
		
		this.testName= testName;
	}
		
	/**
	 * Metoda pro ziskani mnoziny podminek.
	 * @return mnozina podminek
	 */
	public Set<Condition> getConditionSet() {
		
		return conditionSet;
	}
	
	/**
	 * Metoda pro pridani nove podminky do kolekce.
	 * @param newCondition nova podminka
	 */
	public void addToConditionSet(Condition newCondition) {
		
		conditionSet.add(newCondition);
	}
	
	/**
	 * Metoda pro odstraneni podminky z kolekce.
	 * @param removedCondition podminka k odstraneni
	 */
	public void removeFromConditionSet(Condition removedCondition) {
		
		conditionSet.remove(removedCondition);
	}
	
	/**
	 * Metoda pro pridani nove poruchy do seznamu.
	 * @param newFault nova porucha
	 */
	public void addToFaultList(Fault newFault) {
		
		faultList.add(newFault);
	}
	
	/**
	 * Metoda pro odstraneni poruchy z kolekce.
	 * @param removedFault porucha k odstraneni
	 */
	public void removeFromFaultList(Fault removedFault) {
		
		faultList.remove(removedFault);
	}

	/**
	 * Metoda pro ziskani seznamu poruch.
	 * @return seznam poruch
	 */
	public List<Fault> getFaultList() {
		
		return faultList;
	}

	/**
	 * Prepsani metody toString() kvuli zobrazovani nazvu pravidla v komponente JTree.
	 * @return nazev pravidla
	 */
	@Override
	public String toString() {
		
		return testName;
	}
	
	/**
	 * Metoda pro aplikovani pravidla nad http zpravou.
	 * @param message http zprava
	 */
	public void applyTest(HttpMessage message) {
		
		//nejprve zjistime, zda jsou splneny vsechny podminky
		boolean conditionsFulfilled= true;
		for (Condition currentCondition : conditionSet) {
			if (!currentCondition.isFulfilled(message)) {
				conditionsFulfilled= false;
				break;
			}
		}
		
		//pokud jsou splneny vsechny podminky, vlozime do zpravy prislusne chyby
		if (conditionsFulfilled)
			for (Fault currentFault : faultList) {
				currentFault.inject(message);
			}
		
		
		
	}
	
	
	
	
	
	
	
	
}
