/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package cz.vutbr.fit.dip.fiws.business;

/**
 * Trida reprezentujici podminku souvisejici s URI zpravy.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class UriCondition extends Condition {

	
	private String uriPart;
	private String typeName;
	private String description;

	
	public UriCondition(int conditionId, String uriPart) {
		
		super(conditionId);
		this.uriPart= uriPart;
		typeName= "UriCondition";
		description= "Occurrance of \"" + uriPart + "\" in URI";
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
		
		if (message instanceof HttpRequest) {
			HttpRequest httpRequest= (HttpRequest) message;
			String uri= httpRequest.getUri();
			if (uri.contains(uriPart))
				return true;
		}
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
