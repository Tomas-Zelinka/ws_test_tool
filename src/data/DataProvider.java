/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import logging.ConsoleLog;

import com.thoughtworks.xstream.XStream;

/**
 * Trida slouzi jako handler pro ziskavani/ukladani testu a nastsaveni z/do XML souboru pomoci knihovny XStream.
 * @author martz Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class DataProvider {
	
	private XStream stream;
	private static final String resourcePath = ".."+File.separator+ "resources" + File.separator;
	
	public DataProvider(){
		stream = new XStream();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getResourcePath(){
		return DataProvider.resourcePath;
	}
	
	/**
	 * 
	 * @param path
	 * @param obj
	 */
	public void writeObject(String path, Object obj){
		PrintWriter writer = null;
		try{
			writer = new PrintWriter(new FileWriter(path));
			writer.print(stream.toXML(obj));
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(writer != null)
				writer.close();
		}
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public Object readObject(String path){
		
		Object obj = null;
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(new File(path));
			obj= stream.fromXML(inStream);
			inStream.close();
		}
		catch (FileNotFoundException ex) {
			ConsoleLog.Print("File " +path+ " not found");
			//ConsoleLog.Print(ex.getMessage());
			//System.exit(-1);
		}catch(IOException ex){
			ex.printStackTrace();
		}
		catch(Exception ex)	{
			ex.printStackTrace();
		}
		
		return obj;
	}
}
