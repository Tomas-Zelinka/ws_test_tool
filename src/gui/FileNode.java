package gui;


public class FileNode extends java.io.File {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2796997206556668704L;

	/**
	 * 
	 * @param directory
	 */
	public FileNode(String directory) {
        super(directory);
    }

	/**
	 * 
	 * @param parent
	 * @param child
	 */
    public FileNode(FileNode parent, String child) {
        super(parent, child);
    }
    
    /**
     * 
     * @return
     */
    public boolean isHttpTestCase(){
    	
    	if (this.isDirectory() && (this.getName().compareTo("Http") == 0)){
    		    		
    		//ConsoleLog.Print("http test case clicked");
    		return true;
    	}
    	return false;
    }

    
    /**
     * 
     * @return
     */
    public boolean isFaultInjectionTestCase(){
    	
    	if (this.isDirectory() && (this.getName().compareTo("FaultInjection") == 0)){
    		
    		//ConsoleLog.Print("FaultInjection case clicked");
    		return true;
    	}
    	return false;
    }
    
      
    /**
     * 
     */
    @Override
    public String toString() {
        return getName();
    }
    
    public String getAbsolutePath() {
        return this.getPath();
    }
}
