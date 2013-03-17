/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package proxyUnit;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPath;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

/**
 * Trida predstavuje konkretni typ poruchy XML zpravy. Pokud jsou splneny prislusne podminky,
 * vsechny casti dokumentu odpovidajici xpath budou n-krat znasobeny.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class MultiplicationFault extends Fault {
	
	
	private String xpath;
	private int count;
	
	private String typeName;
	private String description;

	
	public MultiplicationFault(int faultId, String xpath, int count) throws JDOMException {
		
		super(faultId);
		this.xpath = xpath;
		this.count = count;
		//TODO: tohle mozna oddelat...pouze testuje, zda zadany cesta odpovida XPath vyrazu
		XPath.newInstance(xpath);
		this.typeName = "MultiplicationFault";
		this.description = "Parts of the message which correspond with \"" + xpath + "\" multiplicate \"" + count + "\"x.";;
	}
	
	
	/**
	 * Metoda pro ziskani popisu poruchy.
	 * @return popis poruchy
	 */
	@Override
	public String getDescription() {
		
		return description;
	}

	/**
	 * Metoda pro aplikovani poruchy pro znasobeni casti zpravy. Nejprve je vyhodnocen zadany XPath vyraz
	 * a nasledne se provede znasobeni casti zpravy, na kterou XPath ukazuje.
	 * @param message http zprava
	 */
	@Override
	public void inject(HttpMessage message) {
		
		try {
			String changedContent= message.getChangedContent();
		
			SAXBuilder builder= new SAXBuilder();
			Document document= builder.build(new ByteArrayInputStream(changedContent.getBytes()));
			
			//musime nejprve nalez vsechny jmenne prostory
			List<Namespace> namespaceList= new ArrayList<Namespace>();
			seekAllNamespacesInElement(namespaceList, document.getRootElement());
			
			//chceme pouze elementy z xml
			XPathExpression<Element> xPathExpression= XPathFactory.instance().compile(xpath, Filters.element(),
					null, namespaceList);
			
			//klonujeme prislusne elementy odpovidajici xpath
			List<Element> elementList= xPathExpression.evaluate(document);
			for (Element currentElement : elementList) {
				for (int i= 0; i < count; i++) {
					Element cloneElement= currentElement.clone();
					Element parentElement= currentElement.getParentElement();
					//pokud se nejedna o root uzel
					if (parentElement != null)
						currentElement.getParentElement().addContent(cloneElement);
				}
			}
			
			
			//zapiseme zmeny do HttpMessage
			XMLOutputter outputter= new XMLOutputter(Format.getRawFormat());
			
			String xmlResult= outputter.outputString(document);
			
			//odstranit encoding, ktery XMLOutputter implicitne vytvori
			message.setChangedContent(xmlResult.replaceFirst("encoding=\"UTF-8\"", ""));
						
			outputter= new XMLOutputter(Format.getPrettyFormat());
			message.setChangedFormattedContent(outputter.outputString(document));
			
		}
		
		catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Vypsani typu poruchy (kvuli komponente tabulky).
	 * @return typ poruchy
	 */
	@Override
	public String toString() {
		
		return typeName;
	}
	
	
	
	
	
}
