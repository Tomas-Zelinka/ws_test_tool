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
 * vsechny vyskyty retezce "originalString" v http hlavicce budou nahrazeny retezcem "chanedString".
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class HeaderCorruptionFault extends Fault {
	
	
	private String originalSubstring;
	private String changedSubstring;
	private String typeName;
	private String description;

	public HeaderCorruptionFault( String originalSubstring, String changedSubstring) {
		
		this.originalSubstring = originalSubstring;
		this.changedSubstring = changedSubstring;
		
		typeName= "HeaderCorruptionFault";
		description= "All the occurrances of the substring \"" + originalSubstring + "\" replace by \"" + changedSubstring + "\".";
		
	}

	
	
	
	/**
	 * Metoda vraci popis poruchy.
	 * @return popis poruchy
	 */
	@Override
	public String getDescription() {
		
		return description;
	}

	
	/**
	 * Metoda vklada poruchu do http hlavicky. Nahradi vsechny retezce "originalSubstring" za 
	 * "changedSubstring".
	 * @param message http zprava
	 */
	@Override
	public void inject(HttpMessage message) {
		
		String changedHeader= new String(message.getHttpHeader());
		changedHeader= changedHeader.replaceAll(originalSubstring, changedSubstring);
		message.setChangedHttpHeader(changedHeader);
		message.setChanged(true);
		
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
