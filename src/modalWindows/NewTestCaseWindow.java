package modalWindows;

import gui.MainWindow;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.Document;


public class NewTestCaseWindow extends InputModalWindow {
	/**
	 * 
	 */
	
	private JTextField testCaseName;
	private static final long serialVersionUID = 9187751988881264097L;

	public NewTestCaseWindow(){
		super("New Test Case", 640,480);
		
	}
	
		
	/*
	 * 
	 */
	@Override
	protected void putContent(){

		testCaseName = new JTextField(MainWindow.dataPath,30);
		JLabel label = new JLabel("Test Case Name");
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setFont(label.getFont().deriveFont(Font.PLAIN,14.0f));
        
        addToSecondPanel(label);
        addToSecondPanel(testCaseName);
		
		
	}
	
	/*
	 * 
	 * 
	 */
	@Override
	protected String testEmptyInput(){
		return getProjectName();
	}
	
	/*
	 * 
	 */
	private String getProjectName(){
		return testCaseName.getText();
	}
	
	/*
	 * 
	 * 
	 */
	@Override
	protected void initButtons(){
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new OkButtonAction());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelButtonAction()); 
		Document projectField = testCaseName.getDocument();
		projectField.addDocumentListener(new ButtonStateController(okButton));
		
		if(testEmptyInput().isEmpty())
			okButton.setEnabled(false);
		
		addButton(okButton);
		addButton(cancelButton);
		
	}
	
	/*
	 * 
	 */
	private class OkButtonAction  implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
             System.out.println("New project name: "+getProjectName());
         	 setVisible(false);
             dispose();
         }
	}
	/*
	 * 
	 */
	private class CancelButtonAction  implements ActionListener{
				
		public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
	}
	
}
