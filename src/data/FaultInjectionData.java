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
	 * Serialization id
	 */
	private static final long serialVersionUID = 1225218084143065994L;
	
	/**
	 * Id of the test
	 */
	private int testId;
	
	/**
	 * Name of test
	 */
	private String testName;
	
	/**
	 * Condition storage
	 */
	private Set<Condition> conditionSet= new HashSet<Condition>();
	
	/**
	 * Fault storage
	 */
	private List<Fault> faultList= new ArrayList<Fault>();

	/**
	 * 
	 */
	public static final String filename = File.separator+"FaultInjection"+File.separator+"input"+File.separator+"faultInjection.xml";
	public static final String outputFolder = File.separator+"FaultInjection"+File.separator+"output";
	
	public FaultInjectionData( String name) {
		
		this.testName = name;
	}

	
	/**
	 * Get test Id
	 * @return test Id
	 */
	public int getTestId() {
		
		return testId;
	}

	
	/**
	 * Get the name of fault injection test
	 * @param statementName nazev pravidla
	 */
	public String getTestName() {
		
		return this.testName;
	}
	
	
	/**
	 * Set the name of fault injection test
	 * @param statementName name of the test
	 */
	public void setTestName(String testName) {
		
		this.testName= testName;
	}
		
	/**
	 * Get the condition storage
	 * @return mnozina podminek
	 */
	public Set<Condition> getConditionSet() {
		
		return conditionSet;
	}
	
	/**
	 * Add condition to storage
	 * @param newCondition new condition
	 */
	public void addToConditionSet(Condition newCondition) {
		
		conditionSet.add(newCondition);
	}
	
	/**
	 * Remove the condition from storage
	 * @param removedCondition podminka k odstraneni
	 */
	public void removeFromConditionSet(Condition removedCondition) {
		
		conditionSet.remove(removedCondition);
	}
	
	/**
	 * Add fault to storage
	 * @param newFault new fault
	 */
	public void addToFaultList(Fault newFault) {
		
		faultList.add(newFault);
	}
	
	/**
	 * Remove the fault from storage
	 * @param removedFault porucha k odstraneni
	 */
	public void removeFromFaultList(Fault removedFault) {
		
		faultList.remove(removedFault);
	}

	/**
	 * Get the fault storage
	 * @return fault storage
	 */
	public List<Fault> getFaultList() {
		
		return faultList;
	}

	/**
	 *  Get the name of the test
	 * @return test name
	 */
	@Override
	public String toString() {
		
		return testName;
	}
	
	/**
	 *  Check conditions and inject a fault
	 * @param message http
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
