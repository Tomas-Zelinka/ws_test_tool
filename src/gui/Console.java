package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import logging.ConsoleLog;

/**
 * The JPanel holds Textarea for logging information and tool responses
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 * 
 */
public class Console extends JPanel {

	/**
	 * ID for serialization
	 */
	private static final long serialVersionUID = 4989941948035851333L;
	
	/**
	 *  Head component of this class 
	 */
	private JTextArea console;
	
	/**
	 *  Constant for minimal height of the console
	 */
	private final int MIN_HEIGHT = 40;
	
	/**
	 * Constant for preferred height of the console
	 */
	private final int PREFERRED_HEIGHT = 80;
		
	/**
	 * Public constructor, at first is initialized the text area of console.
	 * Then the area is put into the scrollpane and put into the JPanel container
	 */
	public Console(){
		
		setLayout(new BorderLayout());
	    
		this.console = new JTextArea("",5,5);
		
	    this.setPreferredSize(new Dimension(getWidth(),PREFERRED_HEIGHT));
        this.setMinimumSize(new Dimension(getWidth(),MIN_HEIGHT));
        redirectSystemStreams();
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
	    scrollPane.getViewport().add(console);
	    add(BorderLayout.CENTER, scrollPane);
	    ConsoleLog.Print("Testing tool ready");
	    
	}
	
	/**
	 * 	This method is responsible for updating the text area
	 *  The updates are processed in new thread.
	 * @param text String will be shown in text area on new line
	 */
	public void updateTextArea(final String text) {
		  SwingUtilities.invokeLater(new Runnable() {
			  public void run() {
				  console.append(text);
				  console.setCaretPosition(console.getDocument().getLength());
		    }
		  });
		}
	
	/**
	 * This method makes new output stream which is updating the
	 * text area instead stdout
	 */
	private void redirectSystemStreams() {
		OutputStream out = new OutputStream() {
		
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}
	 
			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len));
			}
	 
			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
			};
	 
			System.setOut(new PrintStream(out, true));
			System.setErr(new PrintStream(out, true));
		}

	
}