package gui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;

public class Console extends JPanel {

	/**
	 * 
	 */
	
	private JTextArea console;
	
	private static final long serialVersionUID = 4989941948035851333L;
	
	public Console(){
		setLayout(new BorderLayout());

	    // Make a tree list with all the nodes, and make it a JTree
	    this.console = new JTextArea("ahoj\n",5,5);
   
        JScrollPane scrollpane = new JScrollPane();
	    scrollpane.getViewport().add(console);
	    add(BorderLayout.CENTER, scrollpane);
	    redirectSystemStreams();
	    console.setCaretPosition(console.getDocument().getLength());
    
	    
	}
	
	public void updateTextArea(final String text) {
		  SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		      console.append(text);
    		  console.setCaretPosition(console.getDocument().getLength());
		    }
		  });
		}
	
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