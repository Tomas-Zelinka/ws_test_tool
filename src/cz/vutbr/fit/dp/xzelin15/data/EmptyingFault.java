/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package cz.vutbr.fit.dp.xzelin15.data;

import cz.vutbr.fit.dp.xzelin15.proxyUnit.HttpMessage;

/**
 * Trida predstavuje konkretni typ poruchy XML zpravy. Pokud jsou splneny prislusne podminky,
 * cele telo zpravy bude odstraneno, prijemci je dorucena pouze http hlavicka.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class EmptyingFault extends Fault {

	private String typeName;
	private String description;

	public EmptyingFault() {
	
		typeName= "EmptyingFault";
		description= "The body of the message is removed, just the header is forwarded.";
	}
	
		
	/**
	 * Ziskani popisu poruchy.
	 * @return popis poruchy
	 */
	@Override
	public String getDescription() {
		
		return description;
	}

	/**
	 * Metoda pro odstraneni tela z http zpravy.
	 * @param message http zprava
	 */
	@Override
	public void inject(HttpMessage message) {
		
		message.setChanged(true);
		message.setChangedContent("");
	}

	
	/**
	 * Metoda pro ziskani typu poruchy.
	 * @return typ poruchy
	 */
	@Override
	public String toString() {
		
		return typeName;
	}
	
	
	
	
	
	
}
