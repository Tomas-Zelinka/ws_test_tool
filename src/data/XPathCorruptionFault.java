/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Attribute;
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

import proxyUnit.HttpMessage;

/**
 * Trida predstavuje konkretni typ poruchy XML zpravy. Pokud jsou splneny prislusne podminky,
 * vsechny vyskyty retezce "originalString" budou nahrazeny retezcem "chanedString".
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class XPathCorruptionFault extends Fault {
	
	//private XPath xPath;
	private String xpath;
	private String changedSubstring;
	
	private String typeName;
	private String description;

	
	public XPathCorruptionFault( String xpath, String changedSubstring) throws JDOMException {
		
		
		this.xpath= xpath;
		//TODO: tohle mozna oddelat...pouze testuje, zda zadany cesta odpovida XPath vyrazu
		XPath.newInstance(xpath);
		//this.xPath= XPath.newInstance(xPathString);
		this.changedSubstring= changedSubstring;
		typeName= "XPathCorruptionFault";
		description= "Values of elements/attributes at \"" + xpath + "\" replace by \"" + changedSubstring + "\".";
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
	 * Metoda pro injekci poruchy. Nahradi vsechny vyskyty na pozici urcene pomoci "xPath" novym retezcem 
	 * "changedSubstring".
	 * @param message http zprava
	 */
	@Override
	public void inject(HttpMessage message) {
		
		try {
			String changedContent= message.getChangedContent();
		
			SAXBuilder builder= new SAXBuilder();
			Document document= builder.build(new ByteArrayInputStream(changedContent.getBytes()));
			
			//musime nejprve nalezt vsechny jmenne prostory
			List<Namespace> namespaceList= new ArrayList<Namespace>();
			seekAllNamespacesInElement(namespaceList, document.getRootElement());

			
			//hledame elementy
			XPathExpression<Element> elementExpression= XPathFactory.instance().compile(xpath, Filters.element(), null,
					namespaceList);
			List<Element> elementList= elementExpression.evaluate(document);
						
			for (Element currentElement : elementList) {
				currentElement.setText(changedSubstring);
			}
			
			
			//hledame atributy
			XPathExpression<Attribute> attributeExpression= XPathFactory.instance().compile(xpath, Filters.attribute(), null,
					namespaceList);
			List<Attribute> attributeList= attributeExpression.evaluate(document);
						
			for (Attribute currentAttribute : attributeList) {
				currentAttribute.setValue(changedSubstring);
			}
			
			
			if (elementList.isEmpty() && attributeList.isEmpty())
				System.out.println("Cesta " + xpath + " nenalezena.");

			
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
	 * Metoda pro ziskani typu poruchy.
	 * @return typ poruchy
	 */
	@Override
	public String toString() {
		
		return typeName;
	}
	
	
	
	
	
}
