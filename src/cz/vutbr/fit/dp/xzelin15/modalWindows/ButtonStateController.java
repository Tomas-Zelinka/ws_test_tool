package cz.vutbr.fit.dp.xzelin15.modalWindows;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class ButtonStateController	implements DocumentListener {
	 JButton button;
	 private JTextField text;
	 private JLabel label;
	 
	 ButtonStateController(JButton button,JTextField input, JLabel outputLabel) {
	     this.button = button ;
	     this.text = input;
	     this.label = outputLabel;
	  }

	 /**
	  * 
	  */
	  public void changedUpdate(DocumentEvent e) {
	    disableIfEmpty(e);
	    checkText();
	  }
	  
	  /**
	   * 
	   */
	  public void insertUpdate(DocumentEvent e) {
	    disableIfEmpty(e);
	    checkText();
	  }

	  /**
	   * 
	   */
	  public void removeUpdate(DocumentEvent e) {
	    disableIfEmpty(e);
	    checkText();
	  }

	  /**
	   * 
	   * @param e
	   */
	  public void disableIfEmpty(DocumentEvent e) {
	    button.setEnabled(e.getDocument().getLength() > 0);
	  }
	  
	  
	  /**
	   * 
	   */
	  public void checkText(){
			 String value = text.getText();
			 Pattern regex = Pattern.compile("[$&+,:;=?@#|]");
			 Matcher matcher = regex.matcher(value);
			 
	         if (matcher.find()) {
	        	 	label.setText("Don't use characters like $&+,:;=?@#| "); 
	        	 	 button.setEnabled(false);
	         } else {
	        	 label.setText(" "); 
	                 
	                 
	         }
	 
		}
}
