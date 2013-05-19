/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package cz.vutbr.fit.dp.xzelin15.proxyUnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import cz.vutbr.fit.dp.xzelin15.logging.ConsoleLog;


/**
 * Trida predstavuje vlakno proxy serveru starajici se bud o prichozi nebo odchozi pozadavky.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class ProxyThread extends Thread {
	
	private static final int HTTP_CONTENT_LENGTH= 0;
	private static final int HTTP_CHUNKED_ENCODING= 1;
	
	private final int READ_HEADERS = 2;
	private final int READ_CONTENT = 3;
	
	private final int READ_CHUNK_SIZE = 4;
	private final int READ_CHUNK_CONTENT = 5;
	private final int END = 6;
	private static final int BUFFER_SIZE= 512;
	
	private int interactionId;
	private Socket incomingSocket;
	private Socket outgoingSocket;
	
	OutputStream outputStream;
	InputStream inputStream;
	
	private ProxyMonitoringUnit proxyUnit;
	private String rawMessage;

	
	public ProxyThread(int interactionId, ProxyMonitoringUnit unit, Socket incomingSocket, Socket outgoingSocket) {
		this.proxyUnit = unit;
		this.interactionId= interactionId;
		this.incomingSocket= incomingSocket;
		this.outgoingSocket= outgoingSocket;
		rawMessage= "";
	}

	/**
	 * Metoda pro beh predpripraveneho proxy vlakna, jenz nasloucha na zadanem portu a v pripade navazani
	 * spojeni danou zpravu preda proxy monitorovaci jednotce.
	 */
	@Override
	public void run() {
		
		
		char[] buffer= new char[BUFFER_SIZE];
		char[] chunk = null;
		byte[] byteMessage = null;
		byte[] messageToSend = null;
		ArrayList<char[]> allChunks = new ArrayList<char[]>();
		HttpMessage httpMessage= null;
		ConsoleLog.Print("[ProxyThread] vlakno spusteno");	
		int bytesToBeRead= -1;
		int readMode = READ_HEADERS;
		int chunkSize = 0;
		int httpMode= -1;
		int messageCounter = 0;
		int bytesRead = -1;
		int chunkedMessagSize = 0;
		int offset = 0;
		String header = "";
		String strChunkSize = "";
		
		
		
		try {
			outputStream= outgoingSocket.getOutputStream();
			inputStream= incomingSocket.getInputStream();	
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			
			while(true) {
				ConsoleLog.Print("[READ HEADERS] cyklus");
				
				if(readMode == READ_HEADERS){
					
					
					header = reader.readLine();
					ConsoleLog.Print("[READ HEADERS] Hlavicka: " + header);
					if(header == null && inputStream.available() == 0){
						incomingSocket.close();
						outgoingSocket.close();
						break;
					}
					
					if(header.equals("")){
						rawMessage += "\r\n";
						readMode = READ_CONTENT;
						String transferEncoding = null;
						ConsoleLog.Print("[ProxyTread] New message");
						httpMessage= HttpMessageParser.parseHttpHeader(rawMessage, incomingSocket);
						
						transferEncoding= httpMessage.getTransferEncoding();
						
						
						
						if (httpMessage.getContentLength() == -1){
							
							if (transferEncoding != null && transferEncoding.equals("chunked")){
								httpMode= HTTP_CHUNKED_ENCODING;
								readMode = READ_CHUNK_SIZE;
							}
						
						}else{
							byteMessage = new byte[httpMessage.getContentLength()];
							httpMode= HTTP_CONTENT_LENGTH;
						}
						
					}else{
						
						rawMessage +=header+"\r\n";
						continue;
					}
					
				}
				

				
				switch (httpMode) {
					//---------------------------------- HTTP CONTENT LENGTH -------------------------------
					case HTTP_CONTENT_LENGTH:
						ConsoleLog.Print("[LENGTH MODE] Musim jeste precist:" + bytesToBeRead);
						bytesRead= reader.read(buffer, 0, BUFFER_SIZE);
						ConsoleLog.Print("[LENGTH MODE] Precetl jsem:" + bytesRead);
						
						if (bytesRead == -1) {
							
							readMode = READ_HEADERS;
							break;
						}
						
						for (int i= 0; i < bytesRead; i++)
							byteMessage[offset+i]+= buffer[i];
						
						if (bytesToBeRead == -1) {
							//spocitame, kolik bytu ma byt jeste ze streamu nacteno ke zhotoveni http zpravy
							//velikost obsahu - (pocet jiz nactenych bytu - velikost nactene http hlavicky)
							bytesToBeRead= httpMessage.getContentLength() - bytesRead;
							
						}else if (bytesToBeRead > 0){
								offset +=bytesRead;
								bytesToBeRead-= bytesRead;
						}
						
						
						//pokud jiz byla cela http zprava nactena..
						if (bytesToBeRead == 0) {
							//rozparsujeme telo zpravy
							ConsoleLog.Print("[LENGTH MODE] Vysledek:" +httpMessage.getHttpHeader() + httpMessage.getContent());
							
							messageToSend =  processContent( interactionId + messageCounter, httpMessage, byteMessage);
							
							
							ConsoleLog.Print("[LENGTH MODE] Vysledek2:" +httpMessage.getHttpHeader()+ httpMessage.getContent());
							
							if(httpMessage.isChanged())
								outputStream.write((httpMessage.getChangedHttpHeader()+"\r\n").getBytes());
							else
								outputStream.write((httpMessage.getHttpHeader()+"\r\n").getBytes());
							
							outputStream.write(messageToSend);
							outputStream.flush();
								
							bytesToBeRead= -1;
							rawMessage= "";
							messageCounter++;	
							readMode = READ_HEADERS;
						}
					break;
						
					//----------------------------------- HTTP CHUNKED ENCODING ------------------------------	
					//TODO: chunked encoding zatim nefunguje
					case HTTP_CHUNKED_ENCODING:
						
						if(readMode == READ_CHUNK_CONTENT){
							ConsoleLog.Print("[CHUNKED MODE] Chunked content mode" );
							bytesRead= reader.read(buffer, 0, chunkSize);
							bytesToBeRead -= bytesRead;				
							
							if (bytesRead == -1) {
								incomingSocket.close();
								outgoingSocket.close();
								break;
							}
													
							for (int i= 0; i < bytesRead; i++)
								chunk[i]= buffer[i];
							
							allChunks.add(chunk);
							
							if(bytesToBeRead == 0){
								//ConsoleLog.Print("[CHUNKED MODE] Chunked size mode" );
								readMode = READ_CHUNK_SIZE;
								strChunkSize = reader.readLine();
							}
							
							
						}else{
							ConsoleLog.Print("[CHUNKDE MODE] Chunked size mode" );
							
							strChunkSize = reader.readLine();

							chunkSize = Integer.parseInt(strChunkSize,16);
							chunk= new char[chunkSize];
							ConsoleLog.Print(""+chunkSize);
							chunkedMessagSize+=chunkSize;
							bytesToBeRead = chunkSize;
							readMode = READ_CHUNK_CONTENT;
							
							//outputStream.write(httpMessage.getHttpHeader().getBytes());
							if(chunkSize == 0){
								strChunkSize = reader.readLine();
								
								char[] chunkedMessage = new char[chunkedMessagSize];
								
								Iterator<char[]> it = allChunks.iterator();
								offset = 0;
								
								while(it.hasNext()){
									char[] oneChunk = it.next();
									
									for(int a = 0; a < oneChunk.length; a++){
										chunkedMessage[a +offset] = oneChunk[a];
									}
									offset += oneChunk.length;
								}
								
								byteMessage = new String(chunkedMessage).getBytes();
								
								
								messageToSend =  processContent( interactionId + messageCounter, httpMessage, byteMessage);
								
								
								ConsoleLog.Print(httpMessage.getHttpHeader()+"\r\n"+ new String(messageToSend));
								
								if(httpMessage.isChanged())
									outputStream.write((httpMessage.getChangedHttpHeader()+"\r\n").getBytes());
								else
									outputStream.write((httpMessage.getHttpHeader()+"\r\n").getBytes());
								outputStream.write(messageToSend);
								outputStream.flush();
								
								readMode = READ_HEADERS;
								bytesToBeRead= -1;
								rawMessage= "";
								messageCounter++;
								chunkedMessagSize = 0;
																
								
							}
						}
						
						
						
						
						break;
					
					default:
						//pokud na vstupu jiz nejsou v tuto chvili data..pro jistotu chvili pockame
						
						ConsoleLog.Print("[NON LENGTH MODE] nepodporuju" );
						//readMode = END;
						//break;
						if (inputStream.available() == 0) {
							try {
								sleep(10);
							}
							catch (Exception ex) {
								System.err.println(ex.getMessage());
								System.exit(1);
							}
							//pokud stale nejsou na vstupu data..predpokladame, ze bylo vse jiz poslano
							if (inputStream.available() == 0) {
								
								if(httpMessage.isChanged())
									outputStream.write(httpMessage.getChangedHttpHeader().getBytes());
								else
									outputStream.write(httpMessage.getHttpHeader().getBytes());
								byte c = 0;
								while(c != -1){
									c = (byte)inputStream.read();
								//messageToSend =  processContent( interactionId + messageCounter, httpMessage, rawMessage);
									outputStream.write(c);
								}
								bytesToBeRead= -1;
								rawMessage= "";
								messageCounter++;
							
							}
							
						}
						
					break;
				}//switch end
				
				if(readMode == END){
					incomingSocket.close();
					outgoingSocket.close();
					break;
				}
				
			}// while end
					
			
		}
		catch (IOException ex) {

			
		}
		catch (ArrayIndexOutOfBoundsException ex) {

			
		}
		
		
		
	}
	
	
	private byte[] processContent(int interactionId,HttpMessage message,byte[] byteMessage) throws IOException{
		
		String decodedMessage = decodeMessage (message, byteMessage );
		String processedMessage = null;
		byteMessage = null;
		
		
		
		HttpMessageParser.parseHttpContent(message, decodedMessage);
		
		
		//ConsoleLog.Print(decodedMessage);
		
		proxyUnit.newMessageNotifier(interactionId, message);
		
		if(message.isChanged())
			processedMessage= message.getChangedContent();
		else
			processedMessage=decodedMessage;
		
		byteMessage = encodeMessage (message,processedMessage);
		
		
		
		return byteMessage ;
		
	}
	

	private String decodeMessage (HttpMessage message, byte[] byteMessage ){
		
		String decodedMessage = "";
		
		
		if((message.getTransferEncoding() != null) && (message.getTransferEncoding().contains("gzip"))){
			
			decodedMessage = HttpMessageParser.decodeGzip(byteMessage);
		}else if((message.getTransferEncoding() != null) && (message.getTransferEncoding().contains("deflate"))){
			
			decodedMessage = HttpMessageParser.decodeDeflate(byteMessage);
		}else if(message.getTransferEncoding() != null && message.getTransferEncoding().contains("chunked")){
			
			HttpMessageParser.removeHeaderValue("Transfer-Encoding", message);
			message.setTransferEncoding("");
			HttpMessageParser.addHeaderValue("Content-Length",""+byteMessage.length, message);
			
		}
		
		
		if((message.getContentEncoding() != null) && (message.getTransferEncoding().contains("gzip"))){
			
			decodedMessage = HttpMessageParser.decodeGzip(byteMessage);
		}else if((message.getContentEncoding() != null) && (message.getTransferEncoding().contains("delfate"))){
			
			decodedMessage = HttpMessageParser.decodeDeflate(byteMessage);
		}else{
			
			decodedMessage = new String(byteMessage);
		}
		
		
		return decodedMessage;
	}
	
	
	
	
	private byte[] encodeMessage (HttpMessage message, String strMessage ){
		
		byte[] byteMessage = null;
		
		if(message.getTransferEncoding() != null && message.getTransferEncoding().contains("gzip")){
			
			byteMessage = HttpMessageParser.encodeGzip(strMessage);
			ConsoleLog.Print("comprimace vystup");
		}else if(message.getTransferEncoding() != null && message.getTransferEncoding().contains("deflate")){
			
			byteMessage = HttpMessageParser.encodeDeflate(strMessage);
		}
		
		
		if(message.getContentEncoding() != null && message.getContentEncoding().contains("gzip")){
			
			byteMessage = HttpMessageParser.encodeGzip(strMessage);
		}else if(message.getContentEncoding() != null && message.getContentEncoding().contains("deflate")){
			
			byteMessage = HttpMessageParser.encodeDeflate(strMessage);
		}else{
			byteMessage = strMessage.getBytes();
			
		}
		
		
		
		return byteMessage;
	}
	
	
	
	
}


	

