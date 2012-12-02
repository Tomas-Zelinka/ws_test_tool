package bookStore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVIO {
	
	
	public  static List<List<String>> readTXTFile(String csvFileName)	throws IOException {

	    String line = null;
	    BufferedReader stream = null;
	    List<List<String>> csvData = new ArrayList<List<String>>();

	    try {
	        stream = new BufferedReader(new FileReader(csvFileName));
	        while ((line = stream.readLine()) != null) {
	            String[] splitted = line.split(",");
	            List<String> dataLine = new ArrayList<String>(splitted.length);
	            for (String data : splitted)
	                dataLine.add(data);
	            csvData.add(dataLine);
	        }
	    } finally {
	        if (stream != null)
	            stream.close();
	    }

	    return csvData;
	}
	
	
	
	
	public static void writeTXTFile (List<List<String>> csvData,String csvFileName) throws IOException{
		
		
		BufferedWriter stream = null;
		
		
		try{
			stream = new BufferedWriter(new FileWriter(csvFileName));
		
		
			for (List<String> lines: csvData){
				for  (String value: lines){
					stream.write(value + ",");
				}
				stream.newLine();
				stream.flush();
			}
		}finally{
			if (stream != null){
				stream.close();
			}
		}
		
		
		
		stream.write(csvFileName);
		
		
	}
	
	
	
	
	
}
