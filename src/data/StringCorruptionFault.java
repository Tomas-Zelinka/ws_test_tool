/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package data;

import proxyUnit.HttpMessage;

/**
 * Trida predstavuje konkretni typ poruchy naruseni retezce ve zprave. Pokud jsou splneny prislusne podminky,
 * vsechny vyskyty retezce "originalString" budou nahrazeny retezcem "chanedString".
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class StringCorruptionFault extends Fault {
	
	
	private String originalSubstring;
	private String changedSubstring;
	
	private String typeName;
	private String description;

	
	public StringCorruptionFault( String originalSubstring, String changedSubstring) {
		
		
		this.originalSubstring= originalSubstring;
		this.changedSubstring= changedSubstring;
		typeName= "StringCorruptionFault";
		description= "All occurrances of substring \"" + originalSubstring + "\" replace by \"" + changedSubstring + "\".";
	}
	
	/**
	 * Ziskani retezce urceneho k nahrazeni puvodniho retezce.
	 * @return retezec pro nahrazeni puvodniho retezce
	 */
	public String getChangedSubstring() {
		
		return changedSubstring;
	}

	/**
	 * Metoda pro ziskani puvodniho retezce, ktery ma byt nahrazen.
	 * @return metoda pro ziskani puvodniho retezce
	 */
	public String getOriginalSubstring() {
		
		return originalSubstring;
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
	 * Metoda pro ziskani typu poruchy.
	 * @return typ poruchy
	 */
	@Override
	public String toString() {
		
		return typeName;
	}

		
	/**
	 * Metoda pro injekci poruchy. Nahradi vsechny vyskyty podretezce originalSubString za changedSubstring.
	 * @param message http zprava
	 */
	@Override
	public void inject(HttpMessage message) {
		
		String changedContent= message.getChangedContent();
		changedContent= changedContent.replaceAll(originalSubstring, changedSubstring);
		
		message.setChangedContent(changedContent);
		message.setChanged(true);
			
	}
	
	
	
	
}
