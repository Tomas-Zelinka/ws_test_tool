/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package proxyUnit;

/**
 * Trida reprezentujici podminku spojenou s obsahem retezece ve zprave. Pokud zprava na jakemkoliv miste
 * (bez ohledu na syntaxi XML) obsahuje zadany retezec, pak je podminka splnena.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class ContainsCondition extends Condition {
	
		
	private String containString;
	private String typeName;
	private String description;
	
	
	public ContainsCondition(String containString) {
		
		//super(conditionId);
		this.containString= containString;
		typeName= "ContainsCondition";
		description= "The occurrance of \"" + containString + "\"";
	}
	
	/**
	 * Metoda pro ziskani retezce hledaneho ve zprave. Pokud zprava obsahuje takovy retezec, pominka je splnena.
	 * @return hledany retezec
	 */
	public String getContainString() {
		
		return containString;
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
		
		String content= message.getContent();
		return content.contains(containString);
		
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
