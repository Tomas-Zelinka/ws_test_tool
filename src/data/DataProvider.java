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
 * 
 * The class is for serializing and deserializing objects into/from XML files
 * 
 * updated with Tomas Zelinka (xzelin15@stud.fit.vutbr.cz)
 * @author martz Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 * 
 */
public class DataProvider {
	
	private XStream stream;
	private static final String resourcePath = ".."+File.separator+ "resources" + File.separator;
	
	
	public DataProvider(){
		stream = new XStream();
	}
	
	/**
	 * Returns path for resources
	 * @return
	 */
	public static String getResourcePath(){
		return DataProvider.resourcePath;
	}
	
	public void createDir(String path){
		
		File dir = new File(path);
		
		if(!dir.exists()){
			dir.mkdir();
		}
			
	}
	
	/**
	 * Serialize and write the given object into file
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
	 * Deserialize the object from the given file and return it
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
