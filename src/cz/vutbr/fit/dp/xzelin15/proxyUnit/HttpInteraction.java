/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package cz.vutbr.fit.dp.xzelin15.proxyUnit;

import java.util.Calendar;

/**
 * Trida predstavuje vlakno proxy serveru starajici se bud o prichozi nebo odchozi pozadavky.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class HttpInteraction {
	
	private Calendar requestTime;
	private Calendar responseTime;
	
	private HttpRequest httpRequest;
	private HttpResponse httpResponse;

	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Ziskani casu pozadavku.
	 * @return cas pozadavku
	 */
	public Calendar getRequestTime() {
		
		return requestTime;
	}

	/**
	 * Nastaveni casu pozadavku.
	 * @param requestTime cas pozadavku
	 */
	public void setRequestTime(Calendar requestTime) {
		
		this.requestTime= requestTime;
	}

	/**
	 * Ziskani casu odpovedi.
	 * @return cas odpovedi
	 */
	public Calendar getResponseTime() {
		
		return responseTime;
	}

	/**
	 * Nastaveni casu odpovedi.
	 * @param responseTime cas odpovedi
	 */
	public void setResponseTime(Calendar responseTime) {
		
		this.responseTime= responseTime;
	}
		
	
	/**
	 * Metoda pro ziskani http pozadavku.
	 * @return objekt http pozadavku
	 */
	public HttpRequest getHttpRequest() {
		
		return httpRequest;
	}

	/**
	 * Nastaveni objektu http pozadavku.
	 * @param httpRequest objekt http pozadavku
	 */
	public void setHttpRequest(HttpRequest httpRequest) {
		
		this.httpRequest= httpRequest;
	}

	/**
	 * Ziskani http odpovedi.
	 * @return objekt http odpovedi
	 */
	public HttpResponse getHttpResponse() {
		
		return httpResponse;
	}

	/**
	 * Nastaveni http odpovedi.
	 * @param httpResponse objekt http odpovedi
	 */
	public void setHttpResponse(HttpResponse httpResponse) {
		
		this.httpResponse= httpResponse;
	}
	
	
	
	
	
}
