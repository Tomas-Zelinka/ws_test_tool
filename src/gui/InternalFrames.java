package gui;

import javax.swing.JInternalFrame;

public class InternalFrames extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5759265931306139689L;
	
	static int openFrameCount = 0;
	static final int xOffset = 30, yOffset = 30;

	public InternalFrames() {
	    super("Document #" + (++openFrameCount),
	          true, //resizable
	          true, //closable
	          true, //maximizable
	          true);//iconifiable
	    //...Create the GUI and put it in the window...
	    //...Then set the window size or call pack...
	    //...
	    //Set the window's location.
	    setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
	}
}
