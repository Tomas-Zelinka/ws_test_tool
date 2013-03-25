/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package proxyUnit;

/**
 * Abstraktni trida reprezentujici obecnou podminku, ktera musi byt splnena, aby se aplikovaly prislusne poruchy.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public abstract class Condition {
	
	
	//private int conditionId;
	
	
	//public Condition(int conditionId) {
		
	//	this.conditionId = conditionId;
		
	//}

		
	//public int getConditionId() {
		
	//	return conditionId;
	///}
	
	
		
	/**
	 * Abstraktni metoda pro ziskani popisu podminky
	 * @return 
	 */
	public abstract String getDescription();
	
	
	/**
	 * Metoda pro zjisteni, zda je dana podminka splnena.
	 * @param message http zprava (objekt tridy HttpRequest nebo HttpResponse)
	 * @return true, pokud je podminka splnena
	 */
	public abstract boolean isFulfilled(HttpMessage message);
	
	
	/**
	 * Metoda pro vypsani typu podminky v tabulce (kvuli zpetne referenci na objekt)
	 * @return nazev typu podminky
	 */
	@Override
	public abstract String toString();
	
	
	
}
