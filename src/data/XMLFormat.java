package data;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;



public class XMLFormat {
	 public static String format(String unformattedXml) {
		 String result = "";

		 try{ 
		 
		 StringReader reader = new StringReader(unformattedXml);
		    StringWriter writer = new StringWriter();
		    TransformerFactory tFactory = TransformerFactory.newInstance();
		    Transformer transformer = tFactory.newTransformer();
		    transformer .setOutputProperty(OutputKeys.INDENT, "yes");
			transformer .setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		   
		    transformer.transform(
		            new javax.xml.transform.stream.StreamSource(reader), 
		            new javax.xml.transform.stream.StreamResult(writer));

		    result = writer.toString();
		 } catch (ClassCastException e) {
			e.printStackTrace();
		 }catch (TransformerConfigurationException e) {
			e.printStackTrace();
		 } catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		 }catch (TransformerException e) {
			e.printStackTrace();
		 }
		 
		 return result;
   
	    }

	   
}
