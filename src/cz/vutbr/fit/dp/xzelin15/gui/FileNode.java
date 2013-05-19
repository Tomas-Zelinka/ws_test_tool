package cz.vutbr.fit.dp.xzelin15.gui;




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
     * Test if the node is test suite
     * 
     * @return
     */
    public boolean isSuite(){
    
    	String node = this.getParent();
    	if(node.compareTo(MainWindow.getDataRoot()) == 0){
    		return true;
    	}
    	
    	return false;
    }
    /**
     * 
     * Test if the node is test case
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
     * Test if the node is test list
     * 
     * @return
     */
    public boolean isTestList(){
	    	
    	if (this.isFile() && (this.getName().compareTo("testlist.xml") == 0)){
    		    		
    		//ConsoleLog.Print("wsdl test case clicked");
    		return true;
    	}
    	return false;
    }
    
    
    /**
     * Test if the node is test settings
     * @return
     */
    public boolean isSettings(){
    	
    	if (this.isFile() && (this.getName().compareTo("settings.xml") == 0)){
    		    		
    		//ConsoleLog.Print("wsdl test case clicked");
    		return true;
    	}
    	return false;
    }
    
    
    /**
     * 
     * 
     * Test if the node is fault injection
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
     * Prints name of the node
     */
    @Override
    public String toString() {
        return getName();
    }
    
    /**
     * Return absolute paht of the node
     */
    public String getAbsolutePath() {
        return this.getPath();
    }
}
