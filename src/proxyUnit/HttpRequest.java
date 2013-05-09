/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package proxyUnit;

/**
 * Trida reprezentujici http pozadavek jako soucast komunikace webovych sluzeb.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class HttpRequest implements HttpMessage {
	
	private String httpMethod;
	private String initiatorIp;
	private int initiatorPort;
	private String uri;
	private String httpHeader;
	private String changedHttpHeader;
	private int contentLength;
	private String transferEncoding;
	private String contentEncoding;
	private boolean changed;
	private String contentType;
	
	private String content;
	private String formattedContent;
	
	private String changedContent;
	private String changedFormattedContent;

	
	public HttpRequest(String httpMethod, String initiatorIp, int initiatorPort, String uri, String httpHeader,
			int contentLength, String transferEncoding, String contentEncoding, String contentType, boolean changed) {
		
		this.httpMethod = httpMethod;
		this.initiatorIp = initiatorIp;
		this.initiatorPort= initiatorPort;
		this.uri = uri;
		this.httpHeader= httpHeader;
		this.contentLength= contentLength;
		this.transferEncoding= transferEncoding;
		this.contentEncoding= contentEncoding;
		this.contentType = contentType;
		
	}

	/**
	 * Metoda pro ziskani pozmenene http hlavicky.
	 * @return pozmenena http hlavicka
	 */
	@Override
	public String getChangedHttpHeader() {
		
		return changedHttpHeader;
	}

	/**
	 * Metoda pro nastaveni pozmenene http hlavicky.
	 * @param changedHttpHeader pozmenena http hlavicka
	 */
	@Override
	public void setChangedHttpHeader(String changedHttpHeader) {
		
		this.changedHttpHeader = changedHttpHeader;
	}
		

	/**
	 * Metoda pro nastaveni obsahu zpravy.
	 * @param content obsah zpravy
	 */
	@Override
	public void setContent(String content) {
		
		this.content = content;
	}

	/**
	 * Metoda pro nastaveni formatovaneho obsahu zpravy.
	 * @param formattedContent formatovany obsah zpravy
	 */
	@Override
	public void setFormattedContent(String formattedContent) {
		
		this.formattedContent = formattedContent;
	}
	
	/**
	 * Metoda pro ziskani pole Content-Length
	 * @return Content-Length pole
	 */
	@Override
	public int getContentLength() {
		
		return contentLength;
	}

	/**
	 * Metoda pro ziskani pole Transfer-Encoding
	 * @return Transfer-Encoding pole
	 */
	@Override
	public String getTransferEncoding() {
		
		return transferEncoding;
	}

	/**
	 * Metoda pro ziskani pole Content-Encoding
	 * @return pole Content-Encoding
	 */
	@Override
	public String getContentEncoding() {
		
		return contentEncoding;
	}
		
	/**
	 * Metoda pro ziskani formatovaneho obsahu zpravy
	 * @return formatovany obsah zpravy
	 */
	@Override
	public String getFormattedContent() {
		
		return formattedContent;
	}
	
	/**
	 * Metoda pro ziskani obsahu zpravy.
	 * @return obsah zpravy
	 */
	@Override
	public String getContent() {
		
		return content;
	}

	/**
	 * Metoda pro ziskani http hlavicky
	 * @return http hlavicka
	 */
	@Override
	public String getHttpHeader() {
		
		return httpHeader;
	}
	
	/**
	 * Metoda pro ziskani pouzite http metody.
	 * @return pouzita http metoda
	 */
	public String getHttpMethod() {
		
		return httpMethod;
	}

	/**
	 * Metoda pro ziskani iniciatora spojeni.
	 * @return iniciator spojeni
	 */
	public String getInitiator() {
		
		return initiatorIp + ":" + initiatorPort;
	}
	
	/**
	 * Metoda pro ziskani IP adresy iniciatora spojeni.
	 * @return IP adresa iniciatora spojeni
	 */
	public String getInitiatorIp() {
		
		return initiatorIp;
	}

	/**
	 * Metoda pro ziskani portu iniciatora spojeni.
	 * @return port iniciatora spojeni
	 */
	public int getInitiatorPort() {
		
		return initiatorPort;
	}
	
	
	/**
	 * Ziskani URI http pozadavku.
	 * @return URI pozadavku
	 */
	public String getUri() {
		
		return uri;
	}

	/**
	 * Ziskani pozmeneneho obsahu zpravy
	 * @return pozmeneny obsah zpravy
	 */
	@Override
	public String getChangedContent() {
		
		return changedContent;
	}

	/**
	 * Metoda pro ziskani pozmeneneho formatovaneho obsahu zpravy
	 * @return pozmeneny formatovany obsah zpravy
	 */
	@Override
	public String getChangedFormattedContent() {
		
		return changedFormattedContent;
	}

	/**
	 * Metoda pro nastaveni pozmeneneho obsahu zpravy
	 * @param changedContent pozmeneny obsah zpravy
	 */
	@Override
	public void setChangedContent(String changedContent) {
		
		this.changedContent= changedContent;
	}

	/**
	 * Nastaveni pozmeneneho formatovaneho obsahu zpravy.
	 * @param changedFormattedContent pozmeneny formatovany obsah zpravy
	 */
	@Override
	public void setChangedFormattedContent(String changedFormattedContent) {
		
		this.changedFormattedContent= changedFormattedContent;
	}
	
	/**
	 * Get the boolean changed-flag
	 * @return flag showing the change of the message
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * Method for set the changed flag
	 * @param boolean status
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	@Override
	public String getContentType() {
		
		return this.contentType;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
		
	}
	
	
	
	
	
}
