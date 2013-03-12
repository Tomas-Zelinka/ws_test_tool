/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package cz.vutbr.fit.dip.fiws.data;

import java.util.List;

/**
 * Perzistentni trida uchovavajici mapovani jednotlivych testu ze seznamu na XML soubory na disku.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class Settings {
	
	private int proxyPort;
	private int testedWsPort;
	private String proxyHost;
	
	private List<String> filePathList;

	
	public Settings()
	{}
	public Settings(int proxyPort, int testedWsPort, String proxyHost, List<String> filePathList) {
		
		this.proxyPort = proxyPort;
		this.testedWsPort =testedWsPort;
		this.proxyHost = proxyHost;
		this.filePathList = filePathList;
	}

	/**
	 * Metoda pro ziskani proxy umisteni.
	 * @return proxy umisteni
	 */
	public String getProxyHost() {
		
		return proxyHost;
	}

	/**
	 * Metoda pro ziskani proxy portu.
	 * @return proxy port
	 */
	public int getProxyPort() {
		
		return proxyPort;
	}

	/**
	 * Metoda pro ziskani portu testovane webove sluzby.
	 * @return port testovane sluzby
	 */
	public int getTestedWsPort() {
		
		return testedWsPort;
	}
	
	/**
	 * Metoda pro ziskani seznamu cest k XML souborum uchovavajicim jednotlive testy na disku.
	 * @return seznam cest
	 */
	public List<String> getFilePathList() {
		
		return filePathList;
	}
	
	
	
	
	
	
}
