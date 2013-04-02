/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package data;

import proxyUnit.HttpMessage;
import proxyUnit.HttpRequest;
import proxyUnit.HttpResponse;

/**
 * Trida reprezentujici podminku udavajici, zda se poruchy budou aplikovat na http pozadavek nebo odpoved.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class DestinationCondition extends Condition {
	
	
	private boolean applyOnRequest;
	private String typeName;
	private String description;

	
	public DestinationCondition( boolean applyOnRequest) {
		
		//super(conditionId);
		this.applyOnRequest = applyOnRequest;
		typeName= "DestinationCondition";
		if (applyOnRequest)
			description= "Apply on http request";
		else
			description= "Apply on http response";
	}

	
	/**
	 * Metoda pro ziskani popisu podminky.
	 * @return popis podminky
	 */
	@Override
	public String getDescription() {
		
		return description;
	}

	/**
	 * Metoda pro zjisteni, zda je tato podminka splnena pro danou http zpravu.
	 * @param message http zprava
	 * @return true, pokud je podminka splnena
	 */
	@Override
	public boolean isFulfilled(HttpMessage message) {
		
		if (message instanceof HttpRequest && applyOnRequest)
			return true;
		if (message instanceof HttpResponse && !applyOnRequest)
			return true;
		
		return false;
	}

	/**
	 * Metoda pro vypsani nazvu typu podminky.
	 * @return typ podminky
	 */
	@Override
	public String toString() {
		
		return typeName;
	}
	
	
	
	
}
