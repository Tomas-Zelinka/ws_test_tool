/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package cz.vutbr.fit.dip.fiws.business;

import java.util.List;
import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 * Abstraktni trida reprezentujici vkladanou poruchu do XML zpravy.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public abstract class Fault {
	
	
	private int faultId;

	
	public Fault(int faultId) {
		
		this.faultId = faultId;
	}
		
	
	public int getFaultId() {
		
		return faultId;
	}
	
		
	/**
	 * Abstraktni metoda pro ziskani popisu poruchy.
	 * @return 
	 */
	public abstract String getDescription();
	
	
	/**
	 * Abstraktni metoda pro aplikovani prislusne poruchy do http zpravy.
	 * @param message http zprava
	 */
	public abstract void inject(HttpMessage message);
	
	
	/**
	 * Metoda pro vypsani nazvu typu poruchy (kvuli zpetne referenci z tabulky poruch).
	 * @return nazev typu poruchy
	 */
	@Override
	public abstract String toString();
	
	
	/**
	 * Rekurzivni metoda pro vyhledani vsech deklarovanych jmennych prostoru v XML elementu.
	 * @param namespaceList seznam jmennych prostoru
	 * @param element XML element
	 */	
	protected static void seekAllNamespacesInElement(List<Namespace> namespaceList, Element element) {
		
		namespaceList.addAll(element.getNamespacesIntroduced());
		List<Element> elementChildrenList= element.getChildren();
		for (Element elementChild : elementChildrenList)
			seekAllNamespacesInElement(namespaceList, elementChild);
	}
		
	
}
