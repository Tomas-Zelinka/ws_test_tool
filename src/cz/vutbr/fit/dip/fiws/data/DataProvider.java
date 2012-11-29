/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package cz.vutbr.fit.dip.fiws.data;

import com.thoughtworks.xstream.XStream;
import cz.vutbr.fit.dip.fiws.business.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Trida slouzi jako handler pro ziskavani/ukladani testu a nastsaveni z/do XML souboru pomoci knihovny XStream.
 * @author martz Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class DataProvider {
	
	
	/**
	 * Metoda pro ulozeni nastaveni a referenci na testy do souboru settings.xml
	 * @param settings instance tridy Settings
	 */
	public static void serializeSettingsToXML(Settings settings) {
		
		XStream xstream= new XStream();
		File dbFolder= new File("dbdata");
		if (!dbFolder.exists())
			dbFolder.mkdir();
		String filePath= "dbdata/settings.xml";
		
		//ulozeni nastaveni do souboru
		try {
			PrintWriter writer= new PrintWriter(new FileWriter(filePath));
			writer.print(xstream.toXML(settings));
			writer.close();
		}
		catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		
	}
	
	/**
	 * Metoda pro ulozeni vsech testu ze seznamu do prislusnych XML souboru na disku.
	 * @param testList seznam s testy
	 */
	public static void serializeTestsToXML(List<Test> testList) {
		
		XStream xstream= new XStream();
		File dbFolder= new File("dbdata");
		if (!dbFolder.exists())
			dbFolder.mkdir();
		for (Test currentTest : testList) {
			String filePath= currentTest.getFilePath();
			
			//pokud se jedna o nove vytvoreny test..vytvorime mu cestu
			if (filePath == null) {
				filePath= "dbdata/" + currentTest.getTestId() + ".xml";
				currentTest.setFilePath(filePath);
			}
			
			//ulozeni testu do souboru
			try {
				PrintWriter writer= new PrintWriter(new FileWriter(filePath));
				writer.print(xstream.toXML(currentTest));
				writer.close();
			}
			catch (IOException ex) {
				System.err.println(ex.getMessage());
			}
		}
	}
	
	/**
	 * Metoda pro nacteni souboru s nastavenim z XML.
	 * @return objekt reprezentujici soubor s nastavenim
	 */
	public static Settings deserializeSettingsFromXML() {
		
		Settings settings= null;
		try {
			XStream xstream= new XStream();
			settings= (Settings) xstream.fromXML(new FileInputStream(new File("dbdata/settings.xml")));
		}
		catch (FileNotFoundException ex) {
			System.err.println(ex.getMessage());
			System.exit(-1);
		}
		
		return settings;
	}
	
	/**
	 * Metoda pro nacteni vsech testu z XML.
	 * @param filePathList seznam s cestami k souborum
	 * @return seznam instanci testu
	 */
	public static List<Test> deserializeTestsFromXML(List<String> filePathList) {
		
		List<Test> testList= new ArrayList<Test>();
		try {
			XStream xstream= new XStream();
			for (String filePath : filePathList) {
				Test loadedTest= (Test) xstream.fromXML(new FileInputStream(new File(filePath)));
				testList.add(loadedTest);
			}
		}
		catch (FileNotFoundException ex) {
			System.err.println(ex.getMessage());
			System.exit(-1);
		}
		
		return testList;
		
	}
	
}