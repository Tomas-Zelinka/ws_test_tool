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
				
				//reading only headers
				if(readMode == READ_HEADERS){
					
					//read one line of header
					header = reader.readLine();
					ConsoleLog.Print("[READ HEADERS] Hlavicka: " + header);
					
					//red nothing so stream is going to be closed
					if(header == null && inputStream.available() == 0){
						incomingSocket.close();
						outgoingSocket.close();
						break;
					}
					
					//read blank line - all headers are read
					if(header.equals("")){
						//put the blank line back
						rawMessage += "\r\n";
						
						//now the content will be read
						readMode = READ_CONTENT;
						String transferEncoding = null;
						ConsoleLog.Print("[ProxyTread] New message");
						
						//get info from the headers
						httpMessage= HttpMessageParser.parseHttpHeader(rawMessage, incomingSocket);
						transferEncoding= httpMessage.getTransferEncoding();
						
						
						//Content-Length is missing - it is a chunked message usually 
						if (httpMessage.getContentLength() == -1){
							
							if (transferEncoding != null && transferEncoding.equals("chunked")){
								httpMode= HTTP_CHUNKED_ENCODING;
								readMode = READ_CHUNK_SIZE;
							}
						
						}else{ // not chunked message
							byteMessage = new byte[httpMessage.getContentLength()];
							httpMode= HTTP_CONTENT_LENGTH;
						}
						
					}else{
						//put the header among another headers and continue
						rawMessage +=header+"\r\n";
						continue;
					}
					
				}
				

				
				switch (httpMode) {
					//---------------------------------- HTTP CONTENT LENGTH -------------------------------
					case HTTP_CONTENT_LENGTH:
						
						//content lenghmode
						ConsoleLog.Print("[LENGTH MODE] Musim jeste precist:" + bytesToBeRead);
						bytesRead= reader.read(buffer, 0, BUFFER_SIZE);
						ConsoleLog.Print("[LENGTH MODE] Precetl jsem:" + bytesRead);
						
						//nothing to read back to the header mode where the stream will be closed
						if (bytesRead == -1) {
							
							readMode = READ_HEADERS;
							break;
						}
						
						//something was read so copy it to the message
						for (int i= 0; i < bytesRead; i++)
							byteMessage[offset+i]+= buffer[i];
						
						//first occurance in the mode
						if (bytesToBeRead == -1) {
							
							//count the rest of the message
							bytesToBeRead= httpMessage.getContentLength() - bytesRead;
						
						//something left to be read count how many bytes
						}else if (bytesToBeRead > 0){
								offset +=bytesRead;
								bytesToBeRead-= bytesRead;
						}
						
						
						//pokud jiz byla cela http zprava nactena..
						if (bytesToBeRead == 0) {
							//rozparsujeme telo zpravy
							ConsoleLog.Print("[LENGTH MODE] Vysledek:" +httpMessage.getHttpHeader() + httpMessage.getContent());
							
							//process the message with decoding - injection - encoding
							messageToSend =  processContent( interactionId + messageCounter, httpMessage, byteMessage);
							
							
							ConsoleLog.Print("[LENGTH MODE] Vysledek2:" +httpMessage.getHttpHeader()+ httpMessage.getContent());
							
							//send headers first
							if(httpMessage.isChanged())
								outputStream.write((httpMessage.getChangedHttpHeader()+"\r\n").getBytes());
							else
								outputStream.write((httpMessage.getHttpHeader()+"\r\n").getBytes());
							
							//then send the message
							outputStream.write(messageToSend);
							outputStream.flush();
								
							bytesToBeRead= -1;
							rawMessage= "";
							messageCounter++;	
							readMode = READ_HEADERS;
						}
					break;
						
					//----------------------------------- HTTP CHUNKED ENCODING ------------------------------	
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
							
							//read the size of the chunk
							strChunkSize = reader.readLine();
							
							//initialize the buffers for the chunk content
							chunkSize = Integer.parseInt(strChunkSize,16);
							chunk= new char[chunkSize];
							ConsoleLog.Print(""+chunkSize);
							chunkedMessagSize+=chunkSize;
							bytesToBeRead = chunkSize;
							
							//switch to the chunk content mode
							readMode = READ_CHUNK_CONTENT;
							
							//ending chunk is read so put all together and process it
							if(chunkSize == 0){
								
								//read the last line after zero length chunk
								strChunkSize = reader.readLine();
								
								//put all chunks to one message
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
								
								// convert it to byte array
								byteMessage = new String(chunkedMessage).getBytes();
								
								//process the message with decoding - injection - encoding
								messageToSend =  processContent( interactionId + messageCounter, httpMessage, byteMessage);
								
								ConsoleLog.Print(httpMessage.getHttpHeader()+"\r\n"+ new String(messageToSend));
								
								
								//headers first
								if(httpMessage.isChanged())
									outputStream.write((httpMessage.getChangedHttpHeader()+"\r\n").getBytes());
								else
									outputStream.write((httpMessage.getHttpHeader()+"\r\n").getBytes());
								
								//then message
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
		catch (IOException ex) {}
		catch (ArrayIndexOutOfBoundsException ex) {}
	}
	
	/**
	 *  Head processing method - decode - inject, send to gui - encode 
	 * @param interactionId - interaction identifier
	 * @param message - http message structure 
	 * @param byteMessage - message content in bytes
	 * @return byte [] - encoded message in bytes
	 * @throws IOException
	 */
	private byte[] processContent(int interactionId,HttpMessage message,byte[] byteMessage) throws IOException{
		
		
		//decode the message from it encoding
		String decodedMessage = decodeMessage (message, byteMessage );
		String processedMessage = null;
		byteMessage = null;
		
		//read the content of the message and send it to injector and gui
		HttpMessageParser.parseHttpContent(message, decodedMessage);
		proxyUnit.newMessageNotifier(interactionId, message);
		
		//get the processed content
		if(message.isChanged())
			processedMessage= message.getChangedContent();
		else
			processedMessage=decodedMessage;
		
		//encode the message to its encoding
		byteMessage = encodeMessage (message,processedMessage);
		
		return byteMessage ;
		
	}
	
	/**
	 * 
	 * Decode the message from transfer and content encoding
	 * 
	 * @param message - http message structure
	 * @param byteMessage - message content in bytes
	 * @return String - decoded message in string
	 */
	private String decodeMessage (HttpMessage message, byte[] byteMessage ){
		
		String decodedMessage = "";
		
		//transfer encoding gzip
		if((message.getTransferEncoding() != null) && (message.getTransferEncoding().contains("gzip"))){
			
			decodedMessage = HttpMessageParser.decodeGzip(byteMessage);
			
		//transfer encoding deflate	
		}else if((message.getTransferEncoding() != null) && (message.getTransferEncoding().contains("deflate"))){
			
			decodedMessage = HttpMessageParser.decodeDeflate(byteMessage);
			
		//transfer encoding chunked	
		}else if(message.getTransferEncoding() != null && message.getTransferEncoding().contains("chunked")){
			
			HttpMessageParser.removeHeaderValue("Transfer-Encoding", message);
			message.setTransferEncoding("");
			HttpMessageParser.addHeaderValue("Content-Length",""+byteMessage.length, message);
			
		}
		
		//content encoding gzip
		if((message.getContentEncoding() != null) && (message.getTransferEncoding().contains("gzip"))){
			
			decodedMessage = HttpMessageParser.decodeGzip(byteMessage);
			
		//content encoding deflate		
		}else if((message.getContentEncoding() != null) && (message.getTransferEncoding().contains("delfate"))){
			
			decodedMessage = HttpMessageParser.decodeDeflate(byteMessage);
			
		// any encoding
		}else{
			
			decodedMessage = new String(byteMessage);
		}
		
		
		return decodedMessage;
	}
	
	
	
	/**
	 * Encode the message to the content encoding and transfer encoding
	 * @param message - http message structure
	 * @param strMessage - message content to be encoded
	 * @return - encoded message in bytes
	 */
	private byte[] encodeMessage (HttpMessage message, String strMessage ){
		
		byte[] byteMessage = null;
		
		//content encoding gzip
		if(message.getContentEncoding() != null && message.getContentEncoding().contains("gzip")){
			
			byteMessage = HttpMessageParser.encodeGzip(strMessage);
			
		//content encoding deflate	
		}else if(message.getContentEncoding() != null && message.getContentEncoding().contains("deflate")){
			
			byteMessage = HttpMessageParser.encodeDeflate(strMessage);
		}
		
	
		//transfer encoding gzip
		if(message.getTransferEncoding() != null && message.getTransferEncoding().contains("gzip")){
			
			byteMessage = HttpMessageParser.encodeGzip(strMessage);
			ConsoleLog.Print("comprimace vystup");
		
		//transfer encoding deflate		
		}else if(message.getTransferEncoding() != null && message.getTransferEncoding().contains("deflate")){
			
			byteMessage = HttpMessageParser.encodeDeflate(strMessage);
		
		// any encoding	
		}else{
			byteMessage = strMessage.getBytes();
			
		}
		
		return byteMessage;
	}
	
	
	
	
}


	

