package modalWindows;

import gui.MainWindow;
import gui.Navigator;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.Document;

import logging.ConsoleLog;

import data.DataProvider;
import data.TestList;

/**
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class NewTestSuiteDialog extends InputModalWindow {
	/**
	 * 
	 */
	
	private JTextField testSuiteName;
	private JLabel label;
	private JButton okButton;
	private JButton cancelButton;
	private static final long serialVersionUID = 9187751988881264097L;

	public NewTestSuiteDialog(){
		super("New Test Suite", 640,480);
		
	}
	
	/**
	 * 
	 */
	@Override
	protected void initComponents() {
		testSuiteName= new JTextField(30);
		label = new JLabel("Test Suite Name");
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
	}
	
	/**
	 * 
	 */
	@Override
	protected void putContent(){
				
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
		
		okButton.addActionListener(new OkButtonAction());
		cancelButton.addActionListener(new CancelButtonAction()); 
		
		Document projectField = testSuiteName.getDocument();
		projectField.addDocumentListener(new ButtonStateController(okButton,testSuiteName,messageLabel));
		
		if(testEmptyInput().isEmpty())
			okButton.setEnabled(false);
		
		addButton(okButton);
		addButton(cancelButton);
		
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	private class OkButtonAction  implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			 
             File newTestSuite = new File(MainWindow.getDataRoot()+File.separator+getSuiteName());
             File testListFile = new File(newTestSuite.getPath()+File.separator+"testlist.xml");
             TestList testListData = new TestList();
             DataProvider writer = new DataProvider();
             
             
             
             if (newTestSuite.exists()){
            	 messageLabel.setText("Test suite with this name already exists");
             }else{
            	 
            	 newTestSuite.mkdir();
            	 try {
            		 testListFile.createNewFile();
            		 writer.writeObject(testListFile.getPath(), testListData);
            	 }catch(Exception b){
            		 b.printStackTrace();
            	 }
            	 Navigator.refreshTree();
            	 ConsoleLog.Print("[ModalWindow] New test suite created,name: "+newTestSuite.getPath());
            	 setVisible(false);
            	 dispose();
            	 
            }
         }
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	private class CancelButtonAction  implements ActionListener{
				
		public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
	}
	
}
