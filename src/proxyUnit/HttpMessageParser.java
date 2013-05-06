/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package proxyUnit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Trida pro parsovani http zpravy. Obsahuje metoda pro ziskani nezbytnych informaci o zprave.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class HttpMessageParser {
	
	
	
	/**
	 * Metoda pro parsovani hlavicky prichozi http zpravy (muze jit jak o http pozadavek, tak o odpoved) a vytvoreni
	 * prisluslneho objektu, jenz ji bude reprezentovat.
	 * @param rawMessage nezpracovana http zprava
	 * @param incomingSocket vstupni soket, kterym zprava prisla
	 * @return objekt symbolizujici http zpravu
	 */
	public static HttpMessage parseHttpHeader(String rawMessage, Socket incomingSocket) {
		
		Pattern pattern= Pattern.compile("^HTTP", Pattern.MULTILINE);
		Matcher matcher= pattern.matcher(rawMessage);
		
		//jedna se o http request
		if (!matcher.find()) {
			//parsujeme http metodu a URI
			//GET http://localhost:8080/CustomerDB/resources/entities.customer HTTP/1.1
			pattern= Pattern.compile("^([A-Z]*) (.*) ", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			String httpMethod= null;
			String uri= null;
			if (matcher.find()) {
				httpMethod= matcher.group(1);
				uri= matcher.group(2);
			}
			
			//zjistime iniciatora spojeni
			String initiatorIp= incomingSocket.getInetAddress().getHostAddress();
			int initiatorPort= incomingSocket.getPort();
			
			//Host: localhost:8080
//			pattern= Pattern.compile("^Host: (.*)$", Pattern.MULTILINE);
//			matcher= pattern.matcher(rawMessage);
//			String initiator= null;
//			if (matcher.find()) {
//				initiator= matcher.group(1);
//			}
			
			//parsujeme celou http hlavicku (nehladove vyhledavani)
			//TODO: tohle muze fungovat jen pokud prazdny radek vypada: \r\n\r\n
			pattern= Pattern.compile("^(.+?\\s\\s\\s\\s)", Pattern.DOTALL);
			matcher= pattern.matcher(rawMessage);
			String httpHeader= null;
			if (matcher.find()) {
				httpHeader= matcher.group(1);
			}
			
			//parsujeme velikost tela http zpravy
			pattern= Pattern.compile("^Content-Length: ([0-9]+)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			int contentLength= -1;
			if (matcher.find()) {
				String result= matcher.group(1);
				contentLength= Integer.parseInt(result);
			}
			
			//parsujeme prenosove kodovani http zpravy
			pattern= Pattern.compile("^Transfer-Encoding: ([a-z]+)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			String transferEncoding= null;
			if (matcher.find()) {
				transferEncoding= matcher.group(1);
			}
			
			//parsujeme kodovani obsahu http zpravy
			pattern= Pattern.compile("^Content-Encoding: ([a-z]+)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			String contentEncoding= null;
			if (matcher.find()) {
				contentEncoding= matcher.group(1);
			}
			
			return new HttpRequest(httpMethod, initiatorIp, initiatorPort, uri, httpHeader, contentLength, 
					transferEncoding, contentEncoding, false);
		}
			
		//jedna se o http response
		else {
			//parsujeme navratovy kod
			//HTTP/1.1 200 OK
			String initiatorIp= incomingSocket.getInetAddress().getHostAddress();
			int initiatorPort= incomingSocket.getLocalPort();
			
			pattern= Pattern.compile("^HTTP[^ ]* ([^ ]*) (.*)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			String httpCode= null;
			String httpCodeDesc= null;
			if (matcher.find()) {
				httpCode= matcher.group(1);
				httpCodeDesc= matcher.group(2);
			}
			
			//parsujeme celou http hlavicku (nehladove vyhledavani)
			//TODO: tohle muze fungovat jen pokud prazdny radek vypada: \r\n\r\n
			pattern= Pattern.compile("^(.+?\\s\\s\\s\\s)", Pattern.DOTALL);
			matcher= pattern.matcher(rawMessage);
			String httpHeader= null;
			if (matcher.find()) {
				httpHeader= matcher.group(1);
			}
			
			//parsujeme velikost tela http zpravy
			pattern= Pattern.compile("^Content-Length: ([0-9]+)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			int contentLength= -1;
			if (matcher.find()) {
				String result= matcher.group(1);
				contentLength= Integer.parseInt(result);
			}
			
			//parsujeme prenosove kodovani http zpravy
			pattern= Pattern.compile("^Transfer-Encoding: ([a-z]+)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			String transferEncoding= null;
			if (matcher.find()) {
				transferEncoding= matcher.group(1);
			}
			
			//parsujeme kodovani obsahu http zpravy
			pattern= Pattern.compile("^Content-Encoding: ([a-z]+)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			String contentEncoding= null;
			if (matcher.find()) {
				contentEncoding= matcher.group(1);
			}
			
			return new HttpResponse(httpCode,initiatorIp,initiatorPort, httpCodeDesc, null, httpHeader, contentLength, transferEncoding,
					contentEncoding, false);
		}
	}
	
	/**
	 * Metoda pro prevedeni obsahu XML zpravy z puvodniho hrubeho formatu do lepe citelne podoby.
	 * @param xmlMessage puvodni hruba XML zprava
	 * @return nova formatovana zprava
	 */
	public static String formatXmlMessage(String xmlMessage) {
		
		System.out.println("++++++++++++++++++++++++++");
		System.out.println(xmlMessage);
		System.out.println("++++++++++++++++++++++++++");
		try {
			SAXBuilder builder= new SAXBuilder();
			Document document= builder.build(new ByteArrayInputStream(xmlMessage.getBytes()));
						
			XMLOutputter outputter= new XMLOutputter(Format.getPrettyFormat());
			String result= outputter.outputString(document);
			return result;
			
			
		}
		catch(JDOMException ex) {
			//TODO: Calculator webova sluzba vraci v SOAP zprave divne znake, ktere pak vyhodi tuto vyjimku
			System.err.println(ex.getMessage());
			ex.printStackTrace();
			System.exit(-1);
//			return null;
		}
		catch(IOException ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
			System.exit(-1);
		}
		
		return null;
			
	}
	
	
	
	/**
	 * Metoda pro parsovani http obsahu zpravy.
	 * @param rawMessage hruba zprava ze vstupniho soketu
	 * @param httpMessage zprava s parsovanou hlavickou
	 */
	public static void parseHttpContent(HttpMessage httpMessage, String rawMessage, boolean chunkedEncoding) {
		
		//parsujeme obsah zpravy
		Pattern pattern= Pattern.compile("<\\?xml.*", Pattern.DOTALL);
		Matcher matcher= pattern.matcher(rawMessage);
		String content= null;
		if (matcher.find()) {
			content= matcher.group(0);
		}
			

		//naformatujeme obsah
		String formattedContent= null;
		if (content == null) {
			content= "";
			formattedContent= "";
		}
		else
			formattedContent= formatXmlMessage(content);
		
		//nastaveni nove ziskanych atributu
		httpMessage.setContent(content);
		httpMessage.setFormattedContent(formattedContent);
		
	}
	
	/**
	 * Metoda pro aktualizaci pole Content-Length v http hlavicce.
	 * @param httpMessage http zprava
	 */
	public static void replaceContentLength(HttpMessage httpMessage) {
		
		//HeaderCorruptionFault uz mohl vytvorit changedHttpHeader..
		String changedHeader;
		if (httpMessage.getChangedHttpHeader() == null)
			changedHeader= new String(httpMessage.getHttpHeader());
		else
			changedHeader= new String(httpMessage.getChangedHttpHeader());
		
		Pattern pattern= Pattern.compile("^Content-Length: [0-9]+$", Pattern.MULTILINE);
		Matcher matcher= pattern.matcher(changedHeader);
		changedHeader= matcher.replaceFirst("Content-Length: " + (httpMessage.getChangedContent().getBytes().length));
		//changedHeader= matcher.replaceFirst("Content-Length: 204");
		
		//nastaveni pozmenene hlavicky
		httpMessage.setChangedHttpHeader(changedHeader);
	}
		
		
}
