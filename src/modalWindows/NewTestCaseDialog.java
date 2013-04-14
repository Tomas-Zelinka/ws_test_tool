package modalWindows;

import gui.MainWindow;
import gui.ProjectNavigator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;

import logging.ConsoleLog;

import data.DataProvider;
import data.TestCaseSettingsData;

/**
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class NewTestCaseDialog extends InputModalWindow {
	/**
	 * 
	 */
	
	private JTextField testCaseName;
	private JTextField selectedTestSuite;
		
	private static final long serialVersionUID = 9187751988881264097L;

	public NewTestCaseDialog(){
		super("New Test Case", 640,580);
	}
	
		
	/**
	 * 
	 */
	@Override
	protected void putContent(){
		
		JPanel labels = new JPanel(new GridLayout(0,1,0,10));
		JPanel fields = new JPanel(new GridLayout(0,1,0,10));
		
		String selectedPath = MainWindow.getSuitePath();
		if(selectedPath.isEmpty()){
			messageLabel.setText("Test suit not selected. Please select Test Suite");
		}
		
		testCaseName = new JTextField(10);
		selectedTestSuite = new JTextField(MainWindow.getSuitePath(),10);
		JLabel label = new JLabel("Test Case: ");
		JLabel pathLabel = new JLabel("Test Suite: ");
        
		labels.add(pathLabel);
		labels.add(label);
		
		selectedTestSuite.setEditable(false);
			
		fields.add(selectedTestSuite);
		fields.add(testCaseName);
		fields.setSize(new Dimension(200,50));
		
				
		label.setHorizontalAlignment(JLabel.RIGHT);
        label.setFont(label.getFont().deriveFont(Font.PLAIN,14.0f));
        pathLabel.setHorizontalAlignment(JLabel.RIGHT);
        pathLabel.setFont(label.getFont().deriveFont(Font.PLAIN,14.0f));
        
        getSecondInsidePanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getSecondInsidePanel().setLayout(new BorderLayout());
        getSecondInsidePanel().add(labels,BorderLayout.WEST);
        getSecondInsidePanel().add(fields);
    }
     
    
	/**
	 * 
	 * @param text
	 * @return
	 */
	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
	    filler.setHorizontalAlignment(JLabel.CENTER);
	    panel.setLayout(new GridLayout(1, 1));
	    panel.add(filler);
	    return panel;
	}
	/**
	 * 
	 * 
	 */
	@Override
	protected String testEmptyInput(){
		return getCaseName();
	}
	
	/**
	 * 
	 */
	private String getCaseName(){
		return testCaseName.getText();
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
		Document projectField = testCaseName.getDocument();
		projectField.addDocumentListener(new ButtonStateController(okButton,testCaseName,messageLabel));
		
		if(testEmptyInput().isEmpty())
			okButton.setEnabled(false);
		
		addButton(okButton);
		addButton(cancelButton);
		
	}
	
	/**
	 * 
	 */
	private class OkButtonAction  implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
             
			File newTestCase = new File(MainWindow.getDataRoot()+File.separator+MainWindow.getSuitePath()+File.separator+getCaseName());
			File settings = new File(newTestCase.getPath() + File.separator + "settings.xml");
            DataProvider writer = new DataProvider();
            TestCaseSettingsData testCase = new TestCaseSettingsData();
            testCase.setName(getCaseName());
			
			
			if (newTestCase.exists()){
            	 messageLabel.setText("Test case with this name already exists");
             }else{
            	 
            	 newTestCase.mkdir();
            	 try{
            		 settings.createNewFile();
            		 writer.writeObject(settings.getPath(), testCase);
            		 
            	 }catch(Exception b){
            		 b.printStackTrace();
            	 }
            	 ProjectNavigator.refreshTree();
			
            	 ConsoleLog.Print("New case suite created,name: "+ getCaseName());
            	 setVisible(false);
            	 dispose();
             }
		}
	}
	/**
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
