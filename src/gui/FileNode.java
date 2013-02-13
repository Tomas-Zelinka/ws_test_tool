package gui;

import logging.ConsoleLog;

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
    public boolean isTestCase(){
    	
    	if (this.isDirectory()){
    		ConsoleLog.Print("testcase double clicked");
    		return true;
    	}
    	return true;
    }

    /**
     * 
     * @return
     */
    public boolean isTestSuite(){
    	
    	if(this.isDirectory()){
    		ConsoleLog.Print("testsuite double clicked");
    		return true;
    	}
    	
    	return true;
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
