/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package cz.vutbr.fit.dp.xzelin15.proxyUnit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.ZipException;


import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import cz.vutbr.fit.dp.xzelin15.logging.ConsoleLog;

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
			String contentType = null;
			String httpHeader= null;
			int contentLength= -1;
			String transferEncoding= null;
			String contentEncoding= null;
			
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
			if (matcher.find()) {
				ConsoleLog.Print("mam hlavicku");
				httpHeader= matcher.group(1).substring(0, matcher.group(1).length());
			}
			
			//parsujeme velikost tela http zpravy
			pattern= Pattern.compile("^Content-Length: ([0-9]+)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			if (matcher.find()) {
				String result= matcher.group(1);
				contentLength= Integer.parseInt(result);
			}
			
			//parsujeme prenosove kodovani http zpravy
			pattern= Pattern.compile("^Transfer-Encoding: ([a-z]+)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			if (matcher.find()) {
				transferEncoding= matcher.group(1);
			}
			
			//parsujeme kodovani obsahu http zpravy
			pattern= Pattern.compile("^Content-Encoding: ([a-z]+)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			if (matcher.find()) {
				contentEncoding= matcher.group(1);
			}
			
			//parsujeme kodovani obsahu http zpravy
			pattern= Pattern.compile("^Content-Type: (.*)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			if (matcher.find()) {
				contentType= matcher.group(1);
			}
			
			return new HttpRequest(httpMethod, initiatorIp, initiatorPort, uri, httpHeader, contentLength, 
					transferEncoding, contentEncoding, contentType, false);
		}
			
		//jedna se o http response
		else {
			//parsujeme navratovy kod
			//HTTP/1.1 200 OK
			String initiatorIp= incomingSocket.getInetAddress().getHostAddress();
			int initiatorPort= incomingSocket.getLocalPort();
			int contentLength= -1;
			String httpCode = null;
			String httpCodeDesc = null;
			String httpHeader = null;
			String transferEncoding = null;
			String contentEncoding = null;
			String contentType = null;
			
			pattern= Pattern.compile("^HTTP[^ ]* ([^ ]*) (.*)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			if (matcher.find()) {
				httpCode= matcher.group(1);
				httpCodeDesc= matcher.group(2);
			}
			
			//parsujeme celou http hlavicku (nehladove vyhledavani)
			//TODO: tohle muze fungovat jen pokud prazdny radek vypada: \r\n\r\n
			pattern= Pattern.compile("^(.+?\\s\\s\\s\\s)", Pattern.DOTALL);
			matcher= pattern.matcher(rawMessage);
			if (matcher.find()) {
				httpHeader= matcher.group(1);
			}
			
			//parsujeme velikost tela http zpravy
			pattern= Pattern.compile("^Content-Length: ([0-9]+)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			if (matcher.find()) {
				String result= matcher.group(1);
				contentLength= Integer.parseInt(result);
			}
			
			//parsujeme prenosove kodovani http zpravy
			pattern= Pattern.compile("^Transfer-Encoding: ([a-z]+)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			if (matcher.find()) {
				transferEncoding= matcher.group(1);
			}
			
			//parsujeme kodovani obsahu http zpravy
			pattern= Pattern.compile("^Content-Encoding: ([a-z]+)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			if (matcher.find()) {
				contentEncoding= matcher.group(1);
			}
			
			//parsujeme kodovani obsahu http zpravy
			pattern= Pattern.compile("^Content-Type: (.*)$", Pattern.MULTILINE);
			matcher= pattern.matcher(rawMessage);
			if (matcher.find()) {
				contentType= matcher.group(1);
			}
			
			return new HttpResponse(httpCode,initiatorIp,initiatorPort, httpCodeDesc, null, httpHeader, contentLength, transferEncoding,
					contentEncoding,contentType, false);
		}
	}
	
	/**
	 * Metoda pro prevedeni obsahu XML zpravy z puvodniho hrubeho formatu do lepe citelne podoby.
	 * @param xmlMessage puvodni hruba XML zprava
	 * @return nova formatovana zprava
	 */
	public static String formatXmlMessage(String xmlMessage) {
		
		//ConsoleLog.Print("++++++++++++++++++++++++++");
		//ConsoleLog.Print(xmlMessage);
		//ConsoleLog.Print("++++++++++++++++++++++++++");
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
		}catch(Exception ex){
			ConsoleLog.Message(ex.getMessage());
		}
		
		return null;
			
	}
	
	
	
	/**
	 * Metoda pro parsovani http obsahu zpravy.
	 * @param rawMessage hruba zprava ze vstupniho soketu
	 * @param httpMessage zprava s parsovanou hlavickou
	 */
	public static void parseHttpContent(HttpMessage httpMessage, String rawMessage) {
		
		//parsujeme obsah zpravy
		String content= rawMessage;
		httpMessage.setContent(content);
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
		String content = null;
		
		//replace the old content lenght with new one
		if(httpMessage.isChanged()){
			content = httpMessage.getChangedContent();
			changedHeader= matcher.replaceFirst("Content-Length: " + (content.getBytes().length));
			httpMessage.setChangedHttpHeader(changedHeader);
		}
			
		else{
			content = httpMessage.getContent();
			changedHeader= matcher.replaceFirst("Content-Length: " + (content.getBytes().length));
			httpMessage.setHttpHeader(changedHeader);
		}
	}
		
		
	/**
	 * Decompress content from GZip 
	 * @param encoded - encoded data
	 * @return String - decoded string
	 */
	public static String decodeGzip(byte[] encoded){
		String decoded = encoded.toString();
		
		String s1 = null;

	    try
	    {
	        byte b[] = decoded.getBytes();
	        InputStream bais = new ByteArrayInputStream(b);
	        GZIPInputStream gs = new GZIPInputStream(bais);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        int numBytesRead = 0;
	        byte [] tempBytes = new byte[6000];
	        try
	        {
	            while ((numBytesRead = gs.read(tempBytes, 0, tempBytes.length)) != -1)
	            {
	                baos.write(tempBytes, 0, numBytesRead);
	            }

	            s1 = new String(baos.toByteArray());
	            s1= baos.toString();
	        }
	        catch(ZipException e)
	        {
	            e.printStackTrace();
	        }
	    }
	    catch(Exception e) {
	        e.printStackTrace();
	    }
	    return s1;
	}
	
	
	/**
	 * Compress content to the GZip
	 * @param decoded
	 * @return
	 */
	public static byte[] encodeGzip(String decoded){
		
		byte[] encoded = decoded.getBytes();
		byte[] compressedData= null;
		ByteArrayOutputStream byteStream = null;
		GZIPOutputStream zipStream = null;
		try{
			
				byteStream =new ByteArrayOutputStream(encoded.length);
	      
				zipStream = new GZIPOutputStream(byteStream);
	     
				zipStream.write(encoded);
				compressedData = byteStream.toByteArray();
		
		
				byteStream.close();
		    	zipStream.close();
			
			
	    }catch(Exception e){
	    	
	      e.printStackTrace();
	    }finally{
	    	
	    	
	    }
			
		return  compressedData;
	}
	
	/**
	 * Decompress the content to the Deflate 
	 * @param encoded
	 * @return
	 */
	public static String decodeDeflate(byte[] encoded){
		
		
		Inflater ifl = new Inflater();   //mainly generate the extraction
        //df.setLevel(Deflater.BEST_COMPRESSION);
        ifl.setInput(encoded);
 
        ByteArrayOutputStream baos = new ByteArrayOutputStream(encoded.length);
        byte[] output = null;
        try{
        	byte[] buff = new byte[1024];
	        while(!ifl.finished())
	        {
	            int count = ifl.inflate(buff);
	            baos.write(buff, 0, count);
	        }
	        baos.close();
	        output = baos.toByteArray();
		
        }catch(Exception ex){
        	
        }
		return new String(output);
	}
	
	/**
	 * Compress content to the deflate
	 * @param decoded
	 * @return
	 */
	public static byte[] encodeDeflate(String decoded){
		
		byte[] encoded = decoded.getBytes();
		 
		byte[] output = new byte[1024];
	    Deflater compresser = new Deflater();
	    compresser.setInput(encoded);
	    compresser.finish();
	    compresser.deflate(output);
		
		
        return encoded;
		
	}
	
	/**
	 * Add header to headers
	 * @param name - name of header
	 * @param value - value of header
	 * @param message - http message structure
	 */
	public static void addHeaderValue(String name, String value, HttpMessage message){
		
		
		if(message.isChanged()){
			String headers = message.getChangedHttpHeader().substring(0, message.getChangedHttpHeader().length()-2);
			headers+=name+": "+value+"\r\n";
			message.setChangedHttpHeader(headers+"\r\n");
		}else{
			String headers = message.getHttpHeader().substring(0, message.getHttpHeader().length()-2);
			headers+= name+": "+value+"\r\n";
			message.setHttpHeader(headers+"\r\n");
		}
	}
	
	/**
	 * Change value header
	 * @param name - name of header
	 * @param value - old value of header
	 * @param newValue - new value of header
	 * @param message - http message structure
	 */
	public static void changeHeaderValue(String name, String value, String newValue,HttpMessage message){
	//HeaderCorruptionFault uz mohl vytvorit changedHttpHeader..
			String changedHeader;
			if (message.getChangedHttpHeader() == null)
				changedHeader= new String(message.getHttpHeader());
			else
				changedHeader= new String(message.getChangedHttpHeader());
			
			Pattern pattern= Pattern.compile("^"+name+": "+value+"$", Pattern.MULTILINE);
			Matcher matcher= pattern.matcher(changedHeader);
			if(message.isChanged()){
				
				changedHeader= matcher.replaceFirst(name+": " + newValue);
				message.setChangedHttpHeader(changedHeader);
			}
				
			else{
				changedHeader= matcher.replaceFirst(name+": " + newValue);
				message.setHttpHeader(changedHeader);
			}
	}
	
	/**
	 * Remove header line from headers
	 * @param name - name of header
	 * @param message - http message structure
	 */
	public static void removeHeaderValue(String name,HttpMessage message){
		//HeaderCorruptionFault uz mohl vytvorit changedHttpHeader..
				String changedHeader;
				
				if (message.getChangedHttpHeader() == null)
					changedHeader= new String(message.getHttpHeader());
				else
					changedHeader= new String(message.getChangedHttpHeader());
				
				Pattern pattern= Pattern.compile("(^"+name+": (.*)\\s\\s)", Pattern.MULTILINE);
				Matcher matcher= pattern.matcher(changedHeader);
				if(message.isChanged()){
					
					changedHeader= matcher.replaceFirst("");
					message.setChangedHttpHeader(changedHeader);
				}
					
				else{
					changedHeader= matcher.replaceFirst("");
					message.setHttpHeader(changedHeader);
				}
		}
	
	
}
