/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package proxyUnit;

/**
 * Rozhrani pro tridy spadajici do kategorie http zprava musi implementovat prislusne metody.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public interface HttpMessage {

	
	
	
	/**
	 * Metoda pro ziskani iniciatora spojeni.
	 * @return iniciator spojeni
	 */
	public String getInitiator(); 
	
	/**
	 * Metoda pro ziskani IP adresy iniciatora spojeni.
	 * @return IP adresa iniciatora spojeni
	 */
	public String getInitiatorIp(); 

	/**
	 * Metoda pro ziskani portu iniciatora spojeni.
	 * @return port iniciatora spojeni
	 */
	public int getInitiatorPort();
	
	/**
	 * Ziskani http hlavicky.
	 * @return http hlavicka
	 */
	public String getHttpHeader();
	
	/**
	 * Ziskani pozmenene http hlavicky.
	 * @return pozmenena http hlavicka
	 */
	public String getChangedHttpHeader();
	
	/**
	 * Nastaveni pozmenene http hlavicky
	 * @param changedHttpHeader pozmenena http hlavicka
	 */
	public void setChangedHttpHeader(String changedHttpHeader);
	
	/**
	 * Ziskani velikosti tela http zpravy v bytech.
	 * @return pocet bytu tvorici telo http zpravy
	 */
	public int getContentLength();
	
	
	/**
	 * Zjisteni presnosoveho typu kodovani http zpravy (napriklad chunked)
	 * @return typ kodovani
	 */
	public String getTransferEncoding();
	
	
	/**
	 * Zjisteni typu kodovani obsahu http zpravy 
	 * @return typ kodovani
	 */
	public String getContentEncoding();

		
	/**
	 * Ziskani neformatovaneho obsahu zpravy.
	 * @return obsah zpravy
	 */
	public String getContent();
	
	/**
	 * Ziskani formatovaneho obsahu zpravy.
	 * @return formatovany obsah zpravy
	 */
	public String getFormattedContent();
	
	/**
	 * Nastaveni obsahu zpravy.
	 * @param content obsah zpravy
	 */
	public void setContent(String content);
	
	/**
	 * Nastaveni formatovaneho obsahu zpravy.
	 * @param formattedContent formatovany obsah zpravy
	 */
	public void setFormattedContent(String formattedContent);
	
	/**
	 * Metoda pro ziskani pozmeneneho obsahu zpravy.
	 * @return pozmeneny obsah zpravy
	 */
	public String getChangedContent();
	
	/**
	 * Metoda pro ziskani formatovaneho pozmeneneho obsahu zpravy.
	 * @return formatovany pozmeneny obsah zpravy
	 */
	public String getChangedFormattedContent();
	
	/**
	 * Metoda pro nastaveni pozmeneneho obsahu zpravy.
	 * @param changedContent pozmeneny obsah zpravy
	 */
	public void setChangedContent(String changedContent);
	
	/**
	 * Metoda pro nastaveni formatovaneho pozmeneneho obsahu zpravy.
	 * @param changedFormattedContent formatovany pozmeneny obsah zpravy
	 */
	public void setChangedFormattedContent(String changedFormattedContent);
	
	/**
	 * This method allows check wether the message was changed during
	 * the injection phase
	 * 
	 * @return boolean value determining change in the message 
	 */
	public boolean isChanged();
	
	/**
	 * Method for set changed flag in the message. The flag is for
	 * determining whether the message was changed
	 * @param status
	 */
	public void setChanged(boolean status);
	
}
