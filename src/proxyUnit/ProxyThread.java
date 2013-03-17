/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package proxyUnit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Trida predstavuje vlakno proxy serveru starajici se bud o prichozi nebo odchozi pozadavky.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class ProxyThread extends Thread {
	
	private static final int HTTP_CONTENT_LENGTH= 0;
	private static final int HTTP_NO_CONTENT_LENGTH= 1;
	private static final int HTTP_CHUNKED_ENCODING= 2;
	
	private static final int BUFFER_SIZE= 512;
	
	private int interactionId;
	private Controller controller;
	private Socket incomingSocket;
	private Socket outgoingSocket;
	
	OutputStream outputStream;
	InputStream inputStream;
	
	
	private String rawMessage;

	
	public ProxyThread(int interactionId, Controller controller, Socket incomingSocket, Socket outgoingSocket) {
		
		this.interactionId= interactionId;
		this.controller= controller;
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
		
		
		byte[] buffer= new byte[BUFFER_SIZE];
			
		
		try {
			outputStream= outgoingSocket.getOutputStream();
			inputStream= incomingSocket.getInputStream();		
			int bytesToBeRead= -1;
			HttpMessage httpMessage= null;
			int httpMode= -1;
			
			while(true) {
				
				int bytesRead= inputStream.read(buffer, 0, BUFFER_SIZE);
				//pokud byl stream uzavren jednou z komunikujicich stran...zavrit sokety
				if (bytesRead == -1) {
					incomingSocket.close();
					outgoingSocket.close();
					bytesToBeRead= -2;
				}

//				//TODO: vyzkouset...melo by byt v poradku, ale rychlejsi
//				String substring= new String(buffer);
//				testString+= substring;

//				ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
//				GZIPInputStream gzis = new GZIPInputStream(bais);
//				InputStreamReader reader = new InputStreamReader(gzis);
//				BufferedReader in = new BufferedReader(reader);
//				
//				String readLine;
//				while ((readLine = in.readLine()) != null) {
//					rawMessage+= readLine;
//				}
				
				for (int i= 0; i < bytesRead; i++)
					rawMessage+= (char) buffer[i];
				
				
				//pokud nacitame novou http zpravu..rozparsujeme hlavicku a podle dostupnych udaju vybereme
				//preposilaci mod
				int contentLength= -1;
				String transferEncoding;
				if (bytesToBeRead == -1) {
					httpMessage= HttpMessageParser.parseHttpHeader(rawMessage, incomingSocket);
					contentLength= httpMessage.getContentLength();
					transferEncoding= httpMessage.getTransferEncoding();
					
					
					//hlavicka neobsahuje Content-Length
					if (contentLength == -1)
						//pokud obsahuje chunkove kodovani
						if (transferEncoding != null && transferEncoding.equals("chunked"))
							httpMode= HTTP_CHUNKED_ENCODING;
						//pokud neobsahuje chunkove kodovani
						else
							httpMode= HTTP_NO_CONTENT_LENGTH;
					//hlavicka obsahuje pole Content-Length
					else
						httpMode= HTTP_CONTENT_LENGTH;
					
				}
				
				
				switch (httpMode) {
					//---------------------------------- HTTP CONTENT LENGTH -------------------------------
					case HTTP_CONTENT_LENGTH:
						if (bytesToBeRead == -1) {
							//spocitame, kolik bytu ma byt jeste ze streamu nacteno ke zhotoveni http zpravy
							//velikost obsahu - (pocet jiz nactenych bytu - velikost nactene http hlavicky)
							bytesToBeRead= contentLength - (bytesRead - httpMessage.getHttpHeader().length());
						}
						else
							if (bytesToBeRead > 0)
								bytesToBeRead-= bytesRead;
						
						//pokud jiz byla cela http zprava nactena..
						if (bytesToBeRead == 0) {
							//rozparsujeme telo zpravy
							HttpMessageParser.parseHttpContent(httpMessage, rawMessage, false);
							//upozornime controller na tuto udalost
							controller.newMessageNotifier(interactionId, httpMessage);
		
							//odeslani pozmenene zpravy
							bytesToBeRead= -1;
							rawMessage= "";
							String changedMessage= httpMessage.getChangedHttpHeader() + httpMessage.getChangedContent();
							outputStream.write(changedMessage.getBytes());

							//outputStream.write(rawMessage.getBytes());
						}
						break;
						
					//------------------------------------- HTTP NO CONTENT LENGTH --------------------------	
					case HTTP_NO_CONTENT_LENGTH:
						//pokud na vstupu jiz nejsou v tuto chvili data..pro jistotu chvili pockame
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
								//rozparsujeme telo zpravy
								HttpMessageParser.parseHttpContent(httpMessage, rawMessage, false);
								//upozornime controller na tuto udalost
								controller.newMessageNotifier(interactionId, httpMessage);

								//odeslani pozmenene zpravy
								bytesToBeRead= -1;
								rawMessage= "";
								String changedMessage= httpMessage.getChangedHttpHeader() + httpMessage.getChangedContent();
								outputStream.write(changedMessage.getBytes());

							}
						}
												
						
						break;
						
					//----------------------------------- HTTP CHUNKED ENCODING ------------------------------	
					//TODO: chunked encoding zatim nefunguje
					case HTTP_CHUNKED_ENCODING:
						//pokud na vstupu jiz nejsou v tuto chvili data..pro jistotu chvili pockame
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
								//rozparsujeme telo zpravy
								HttpMessageParser.parseHttpContent(httpMessage, rawMessage, true);
								
								
								
								//upozornime controller na tuto udalost
								controller.newMessageNotifier(interactionId, httpMessage);

								//odeslani pozmenene zpravy
								bytesToBeRead= -1;
								rawMessage= "";
								String changedMessage= httpMessage.getChangedHttpHeader() + httpMessage.getChangedContent();
								outputStream.write(changedMessage.getBytes());

							}
						}
						break;
						
						
						
				}
				
				
				
					
				//outputStream.write(buffer, 0, numOfBytes);
			}
					
			
		}
		catch (IOException ex) {

			
		}
		catch (ArrayIndexOutOfBoundsException ex) {

			
		}
		
		
		
	}
	
	
	
	
}
