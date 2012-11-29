/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package cz.vutbr.fit.dip.fiws.business;

/**
 * Trida reprezentujici http odpoved v komunikace webovych sluzeb.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class HttpResponse implements HttpMessage {
	
	private String httpCode;
	private String httpCodeDesc;
	private String errorMessage;
	private String httpHeader;
	private String changedHttpHeader;
	private int contentLength;
	private String transferEncoding;
	private String contentEncoding;
	
	private String content;
	private String formattedContent;
	
	private String changedContent;
	private String changedFormattedContent;
	
	
	
	public HttpResponse(String httpCode, String httpCodeDesc, String errorMessage, String httpHeader,
			int contentLength, String transferEncoding, String contentEncoding) {
		
		this.httpCode = httpCode;
		this.httpCodeDesc= httpCodeDesc;
		this.errorMessage = errorMessage;
		this.httpHeader= httpHeader;
		this.contentLength= contentLength;
		this.transferEncoding= transferEncoding;
		this.contentEncoding= contentEncoding;
		
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
	 * Nastaveni formatovaneho obsahu zpravy
	 * @param formattedContent formatovany obsah
	 */
	@Override
	public void setFormattedContent(String formattedContent) {
		
		this.formattedContent = formattedContent;
	}
	
	
	/**
	 * Ziskani pole Content-Length
	 * @return pole Content-Length
	 */
	@Override
	public int getContentLength() {
		
		return contentLength;
	}

	/**
	 * Ziskani pole Transfer-Encoding.
	 * @return Transfer-Encoding
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
	 * Metoda pro ziskani formatovaneho obsahu zpravy.
	 * @return formatovany obsah zpravy
	 */
	@Override
	public String getFormattedContent() {
		
		return formattedContent;
	}
	
	/**
	 * Metoda pro ziskani osahu zpravy.
	 * @return obsah zpravy
	 */
	@Override
	public String getContent() {
		
		return content;
	}

	/**
	 * Metoda pro ziskani http hlavicky.
	 * @return http hlavicka
	 */
	@Override
	public String getHttpHeader() {
		
		return httpHeader;
	}
	
	
	/**
	 * Metoda pro ziskani chybove zpravy.
	 * @return chybova zprava
	 */
	public String getErrorMessage() {
		
		return errorMessage;
	}

	/**
	 * Metoda pro ziskani navratoveho kodu.
	 * @return navratovy kod
	 */
	public String getHttpCode() {
		
		return httpCode;
	}

	/**
	 * Metoda pro ziskani popisu navratoveho kodu.
	 * @return popis navratoveho kodu
	 */
	public String getHttpCodeDesc() {
		
		return httpCodeDesc;
	}
	
	/**
	 * Metoda pro ziskani pozmeneneho obsahu zpravy.
	 * @return pozmeneny obsah zpravy
	 */
	@Override
	public String getChangedContent() {
		
		return changedContent;
	}

	/**
	 * Metoda pro ziskani pozmeneneho formatovaneho obsahu zpravy.
	 * @return pozmeneny formatovany obsah zpravy
	 */
	@Override
	public String getChangedFormattedContent() {
		
		return changedFormattedContent;
	}

	/**
	 * Metoda pro nastaveni pozmeneneho obsahu
	 * @param changedContent pozmeneny obsah
	 */
	@Override
	public void setChangedContent(String changedContent) {
		
		this.changedContent= changedContent;
	}

	/**
	 * Metoda pro nastaveni pozmeneneho formatovaneho obsahu zpravy.
	 * @param changedFormattedContent pozmeneny formatovany obsah zpravy
	 */
	@Override
	public void setChangedFormattedContent(String changedFormattedContent) {
		
		this.changedFormattedContent= changedFormattedContent;
	}
	
	
	
	
	
	
			
	
}
