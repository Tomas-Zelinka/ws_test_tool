package central;

import java.io.File;
import java.net.URL;

public class IOWSDL {

	
	private URL _url;
	private final int REMOTE = 1;
	private final int LOCAL = 2;
	private String _localPath = "./book.wsdl";
	private String _remotePath = "http://localhost/bookStore/bookStore?wsdl";
	URL remoteFile;
	
	public void get(String path)
	{
		File file = new File(_localPath);
	}
	
	public void get()
	{
		File localFile = new File(_localPath);
		
		try {
			remoteFile = new URL(_remotePath);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(localFile.toURI());
		System.out.println(remoteFile);
	}
	
	private void readRemoteFile(String inputPath, String outputPath)
	{
			
		
	}
	
	private void readLocalFile(String filePath, String outputPath)
	{
		
		
	}
	
	
	
	
	
	
}
