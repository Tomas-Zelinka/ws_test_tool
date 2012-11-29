/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package cz.vutbr.fit.dip.fiws.business;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;



/**
 * Trida predstavuje konkretni typ poruchy. Pomoci WSDL popisu jsou uzivateli nabidnuty operace
 * webove sluzby, jejichz obsah bude pozmenen dle konfigurace.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class WsdlOperationFault extends Fault {

	private String wsdlUri;
	private String operationName;
	private String operationContent;
	
	private String typeName;
	private String description;

	
	
	public WsdlOperationFault(int faultId, String wsdlUri, String operationName, String operationContent) {
		
		super(faultId);
		this.wsdlUri= wsdlUri;
		this.operationName= operationName;
		this.operationContent= operationContent;
		
		typeName= "WsdlParamFault";
		description= "The body of operation " + operationName + " will be changed.";
		
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
	 * Metoda pro injekci chyby do http zpravy. Obsah elementu odpovidajici dane operaci bude nahrazen jinym.
	 * @param message http zpravy
	 */
	@Override
	public void inject(HttpMessage message) {
		
		//pokud je telo prazdne (napriklad jde o GET pozadavek na WSDL)..chyba nemuze byt aplikovana
		if (message.getContent().equals(""))
			return;
		
		//hledame element s danou operaci kdekoliv ve strome
		String xpath= "//" + operationName;
		
		try {
			String changedContent= message.getChangedContent();
		
			SAXBuilder builder= new SAXBuilder();
			Document document= builder.build(new ByteArrayInputStream(changedContent.getBytes()));
			
			//musime nejprve nalezt vsechny jmenne prostory
			List<Namespace> namespaceList= new ArrayList<Namespace>();
			seekAllNamespacesInElement(namespaceList, document.getRootElement());
			
					
			//hledame elementy bez prefixu jmenneho prostoru
			XPathExpression<Element> elementExpression= XPathFactory.instance().compile(xpath, Filters.element(), null,
					namespaceList);
			List<Element> elementList= elementExpression.evaluate(document);
			for (Element currentElement : elementList) {
				currentElement.setText(operationContent);
			}
			
			//pokud nebylo nic nalezeno..
			if (elementList.isEmpty()) {
				System.out.println("Cesta " + xpath + " nenalezena.");
				
				//zkousime postupne pridavat prefixy jmennych prostoru do xpath
				for (Namespace currentNamespace : namespaceList) {
					xpath= "//" + currentNamespace.getPrefix() + ":" + operationName;
					elementExpression= XPathFactory.instance().compile(xpath, Filters.element(), null, namespaceList);
					elementList= elementExpression.evaluate(document);
					
					//pokud xpath odpovida..nasli jsme hledany element
					if (!elementList.isEmpty()) {
						elementList.get(0).setText(operationContent);
						break;
					}
					
					//jinak zkousime jiny prefix..
					else
						System.out.println("Cesta " + xpath + " nenalezena.");
				}
			}
			
			
			
			
			
			//zapiseme zmeny do HttpMessage
			XMLOutputter outputter= new XMLOutputter(Format.getRawFormat());
			String xmlResult= outputter.outputString(document);
			
			//prevest escapovane znaky do normalni podoby
			xmlResult= StringEscapeUtils.unescapeXml(xmlResult);
					
			//odstranit encoding, ktery XMLOutputter implicitne vytvori
			message.setChangedContent(xmlResult.replaceFirst("encoding=\"UTF-8\"", ""));
						
			outputter= new XMLOutputter(Format.getPrettyFormat());
			message.setChangedFormattedContent(StringEscapeUtils.unescapeXml(outputter.outputString(document)));
			
		}
		
		catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
			System.exit(-1);
		}
			
		
	}

	/**
	 * Ziskani nazvu typu poruchy.
	 * @return nazev typu poruchy
	 */
	@Override
	public String toString() {
		
		return typeName;
	}
	
	
	
	
}
