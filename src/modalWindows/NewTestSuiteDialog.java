package modalWindows;

import gui.MainWindow;
import gui.ProjectNavigator;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class NewTestSuiteDialog extends InputModalWindow {
	/**
	 * 
	 */
	
	private JTextField testSuiteName;
	private static final long serialVersionUID = 9187751988881264097L;

	public NewTestSuiteDialog(){
		super("New Test Suite", 640,480);
		
	}
	
		
	/**
	 * 
	 */
	@Override
	protected void putContent(){

		testSuiteName= new JTextField(30);
		JLabel label = new JLabel("Test Suite Name");
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setFont(label.getFont().deriveFont(Font.PLAIN,14.0f));
        
        addToSecondPanel(label);
        addToSecondPanel(testSuiteName);
	}
	
	/**
	 * 
	 * 
	 */
	@Override
	protected String testEmptyInput(){
		return getSuiteName();
	}
	
	/**
	 * 
	 */
	private String getSuiteName(){
		return testSuiteName.getText();
	}
	
	/**
	 * 
	 * 
	 */
	@Override
	protected void initButtons(){
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new OkButtonAction());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelButtonAction()); 
		Document projectField = testSuiteName.getDocument();
		projectField.addDocumentListener(new ButtonStateController(okButton,testSuiteName,messageLabel));
		
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
			 
             File newTestSuite = new File(MainWindow.getDataRoot()+File.separator+getSuiteName());
             File testList = new File(newTestSuite.getPath()+File.separator+"testlist.xml");
             
             if (newTestSuite.exists()){
            	 messageLabel.setText("Test suite with this name already exists");
             }else{
            	 
            	 newTestSuite.mkdir();
            	 try {
            		 testList.createNewFile();
            	 }catch(Exception b){
            		 b.printStackTrace();
            	 }
            	 ProjectNavigator.refreshTree();
            	 System.out.println("New test suite created,name: "+newTestSuite.getPath());
            	 setVisible(false);
            	 dispose();
            	 
            }
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
	@Override
	protected void initComponents() {
		// TODO Auto-generated method stub
		
	}
	
}
