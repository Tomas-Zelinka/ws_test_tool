package modalWindows;

import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class ButtonStateController	implements DocumentListener {
	 JButton button;
	  
	 ButtonStateController(JButton button) {
	     this.button = button ;
	  }

	  public void changedUpdate(DocumentEvent e) {
	    disableIfEmpty(e);
	  }

	  public void insertUpdate(DocumentEvent e) {
	    disableIfEmpty(e);
	  }

	  public void removeUpdate(DocumentEvent e) {
	    disableIfEmpty(e);
	  }

	  public void disableIfEmpty(DocumentEvent e) {
	    button.setEnabled(e.getDocument().getLength() > 0);
	  }
	  
	
}
